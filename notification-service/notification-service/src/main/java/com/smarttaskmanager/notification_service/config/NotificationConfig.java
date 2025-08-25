package com.smarttaskmanager.notification_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import com.smarttaskmanager.common.bean.NotificationDto;

@Configuration
public class NotificationConfig {
	
	@Bean
	public DefaultErrorHandler errorHandler() {
	    DefaultErrorHandler handler = new DefaultErrorHandler(
	        new FixedBackOff(2000L, 3) // 3 retries with 2s delay
	    );

	    // Log final failure after retries are exhausted
	    handler.setRetryListeners((record, ex, deliveryAttempt) -> {
	        if (deliveryAttempt == 4) { // 1 original + 3 retries
	            System.err.println("Final failure after retries for record: " + record.value());
	            ex.printStackTrace();
	        }
	    });

	    return handler;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, NotificationDto> kafkaListenerContainerFactory(
	        ConsumerFactory<String, NotificationDto> consumerFactory,
	        DefaultErrorHandler errorHandler) {

	    ConcurrentKafkaListenerContainerFactory<String, NotificationDto> factory =
	            new ConcurrentKafkaListenerContainerFactory<>();

	    factory.setConsumerFactory(consumerFactory);
	    factory.setCommonErrorHandler(errorHandler); 

	    return factory;
	}
}
