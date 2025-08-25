package com.smarttaskmanager.task_service.config;

import com.smarttaskmanager.task_service.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (jwtService.validateToken(token)) {
                username = jwtService.extractUsername(token);
                int userId = Optional.ofNullable(jwtService.extractUserId(token))
                                                           .orElseThrow(() -> new RuntimeException("User ID missing in token"));
                String email = jwtService.extractEmail(token);
                request.setAttribute("userId", userId);
                request.setAttribute("email", email);
                request.setAttribute("username", username);

		        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		            UsernamePasswordAuthenticationToken authToken =
		                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
		            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		            SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
            }
        }
	    filterChain.doFilter(request, response);
    }

}

