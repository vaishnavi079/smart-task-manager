Smart Task Manager – Microservices Project

A microservices-based task management system built with Spring Boot, Spring Cloud, Kafka, and JWT authentication, demonstrating real-world microservice patterns including service discovery, API Gateway routing, inter-service communication, asynchronous notifications, and API documentation.

Project Architecture
```
          ┌────────────────────────┐
          │        Clients         │
          │(Postman / External)    │
          └─────────┬────────────┘
                    │
            API Gateway (Spring Cloud Gateway)
                    │
   ┌────────────────┴─────────────────┐
   │                                  │
User Service                        Task Service
(Spring Boot + JWT Auth)          (Spring Boot + JWT Validation)
- Register / Login Users          - Task CRUD Operations
- Returns JWT on login            - Create, Update, Get, Delete Tasks
- JWT validated for all other APIs- Feign endpoints: getTasksByUserId, deleteTasksByUserId
- Swagger docs                    - Swagger docs
                                   │
                                   ▼
                        Notification Service
                        (Spring Boot + Kafka)
                        - Kafka Listener/Consumer
                        - Triggered on task creation
                        - Sends email notifications
```

Tech Stack
* Backend: Spring Core, MVC, Rest, Data JPA, Spring Boot 3.x, Spring Cloud (Eureka, Gateway, OpenFeign)
* Authentication: Spring Security, JWT, PasswordEncoder
* Async Messaging: Apache Kafka
* Documentation: Swagger (Springdoc OpenAPI)
* Build & Dependency Management: Maven
* Database: MySql

Features

User Service
* User registration and login (only these APIs are unauthenticated)
* All other APIs require JWT authentication
* Feign client to communicate with Task Service
* Swagger API documentation available

Task Service
* CRUD operations for tasks
* JWT validated for all APIs
* Extracts userId from JWT for task ownership
* Feign endpoints for User Service calls (getTasksByUserId, deleteTasksByUserId)
* Swagger API documentation available

Notification Service
* Listens to Kafka topic for new task creation events
* Sends email notifications when a new task is created
* Fully asynchronous, triggered only by Task Service

API Gateway
* Routes all external requests to the corresponding service
* Provides a single entry point for external clients
* Eureka service discovery enabled for dynamic routing

Endpoints

User Service
* POST /user/register – Register a new user
* POST /user/login – Login and get JWT

Task Service
* POST /task/createTask – Create a new task
* PUT /task/update – Update a task
* GET /task/get – Get all tasks
* GET /task/get/{id} – Get task by task Id
* DELETE /task/delete/{id} – Delete task by task Id
* GET /task/getTasks – Feign endpoint 
* DELETE /task/deleteTasks – Feign endpoint

Notification Service
* Kafka listener only; no public REST endpoints

Inter-Service Communication
* User Service → Task Service: Feign client for task retrieval and deletion
* Task Service → Notification Service: Kafka producer sends messages for email notifications
* External clients → Services: Routed via API Gateway

Project Highlights
* Fully microservices-based architecture
* Service discovery with Eureka
* API Gateway routing for all external calls
* JWT authentication and security
* Asynchronous notifications using Kafka
* Swagger API documentation
* Clean separation of internal vs external service communication

Folder Structure
```
smart-task-manager/
├── api-gateway/
├── eureka-server/
├── user-service/
├── task-service/
├── notification-service/
└── README.md
```

Sample Output
User Service

Register a user:
```
POST /user/register
Request Body:
{
  "username": "Tarun",
  "email": "tarunadithyabandaru@gmail.com",
  "password": "T@run123"
}
Response:
{
  User registered successfully
}
```

Login a user (returns JWT):
```
POST /user/login
Request Body:
{
  "username": "Tarun",
  "password": "T@run123"
}
Response:
{
eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsImVtYWlsIjoidGFydW5hZGl0aHlhYmFuZGFydUBnbWFpbC5jb20iLCJzdWIiOiJUYXJ1biIsImlhdCI6MTc1NjEzOTYyMywiZXhwIjoxNzU2MTQzMjIzfQ.zXpY23UNHbkrM5F5iELYVQmJEqbHQuPlnyR7LXVLx_I
}
```

Task Service

Create a task:
```
POST /task/createTask
Headers: Authorization: Bearer <JWT>
Request Body:
{
  "title": "Buy Groceries",
  "description": "Milk, Bread, Eggs",
  "dueDate": "26-09-2025"
}
Response:
{
  Task is created successfully
}
```

Get tasks by userId: (Feign call from user to task service and userId is extracted from jwt token)
```
GET /task/getTasks
Headers: Authorization: Bearer <JWT>
Response:
[
    {
        "id": 5,
        "title": "Buy Groceries",
        "description": "Milk, Bread, Eggs",
        "dueDate": "26-09-2025",
        "userId": 2
    },
    {
        "id": 6,
        "title": "Play Volley Ball",
        "description": "Atleast 1 hour daily to improve physical exercise",
        "dueDate": "30-10-2025",
        "userId": 2
    }
]
```

Notification Service
```
Triggered automatically via Kafka:
Event: TaskCreated
Email sent to user@example.com: Your task 'Buy Groceries' is successfully created
```

Future Improvements
* Move JWT authentication to API Gateway instead of individually in services
* Add Resilience4j for retries and circuit breaking
* Add Docker Compose for full system deployment
* Implement OpenTelemetry tracing for full distributed tracing

Running the Project
1. Clone the repository:
```
git clone https://github.com/<your-username>/smart-task-manager.git
```
3. Start Kafka (local or Docker).
4. Run services in order:
    * Eureka Server
    * API Gateway
    * User Service
    * Task Service
    * Notification Service
5. Access Swagger UI:
    * User Service: http://localhost:<port>/swagger-ui.html
    * Task Service: http://localhost:<port>/swagger-ui.html
6. Use Postman / Frontend to call APIs via API Gateway
