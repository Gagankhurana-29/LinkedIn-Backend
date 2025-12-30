The repository contains microservices for the backend of the LinkedIn application, written in Java Spring Boot.

It's one of my personal projects.

* api-gateway -> Contains the filter and is used for authentication.

* discovery-service -> Contains the Eureka server discovery code for other services.

* connection-service -> Contains the logic and controllers for adding a friend and building a relationship. The database used is Neo4j Graph DB.

* notification-service -> Contains Kafka consumer code for subscribing to notifications like liking a post and sending a request.

* posts-service -> Contains controller and service code to add/like posts. The database used is PostgreSQL.

* user-service -> Contains controller and service code to create a user and create JWT tokens.

Code repository - https://github.com/Gagankhurana-29/LinkedIn-Backend

Connect with me on LinkedIn - https://www.linkedin.com/in/gagan-khurana-0829441a4/ 
