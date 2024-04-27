# HackerNews API

## Overview
This document offers a detailed exposition of the architecture, services, and endpoints of the HackerNews API, which is designed using Clean Architecture and Domain-Driven Design (DDD) principles, along with a focus on immutability and efficient database interactions using HikariCP.

## Architecture

### Clean Architecture
The Clean Architecture model divides the system into distinct layers, each with its own responsibility, which ensures the decoupling of software components. This separation allows for:
- **Independence of Frameworks**: The architecture does not depend on the existence of some library of feature-laden software. This allows you to use such frameworks as tools, rather than having to cram your system into their limited constraints.
- **Testability**: The business rules can be tested without the UI, database, web server, or any other external element.
- **UI Independence**: The UI can change easily, without changing the rest of the system. A web UI could be replaced with a console UI, for example, without changing the business rules.
- **Database Independence**: You can swap out Oracle or SQL Server, for MongoDB, BigTable, CouchDB, or something else. Your business rules are not bound to the database.
- **External Agency Independence**: In fact, your business rules simply don’t know anything at all about the outside world.

### Domain-Driven Design (DDD)
Domain-Driven Design provides a structure of practices and terminology for making design decisions that focus on complex domain issues, facilitating the creation of business software. Benefits include:
- **Tackling Complexity in the Heart of Software**: Directly address the complexity in the domain while minimizing extraneous complexity.
- **Building a Common Language**: Develop a ubiquitous language that tightens the communication between developers and stakeholders, ensuring consistency across the project.
- **Improves Flexibility**: Allows for evolutionary design that is especially important in complex domains or with changing requirements.

### Notification Pattern
The Notification Pattern enhances error handling and reporting within the application by:
- **Centralizing Error Management**: Errors and notifications from different parts of the application are collected and managed centrally, reducing redundancy and improving error tracking.
- **Enhancing User Feedback**: Provides detailed feedback on operations, especially useful in REST APIs where communication about the success or failure of operations is essential.

### Immutability
The application enforces immutability in its domain models where feasible, offering:
- **Predictability**: Immutable objects are much easier to reason about since they do not change their state after creation.
- **Concurrency Safety**: Immutability is a robust strategy for dealing with concurrency, eliminating complex synchronization issues in an environment with multiple threads.

### Hikari Optimization
By utilizing the HikariCP, the API ensures efficient database connection management, which offers:
- **Performance**: Connection pooling reduces the time spent in connecting to the database, drastically improving performance.
- **Reliability**: HikariCP provides a highly reliable and robust platform for database connectivity.
- **Configurability**: Easily configurable settings allow fine-tuning of resource usage and performance characteristics.

## Services

### Algolia Client Service
The Algolia Client Service is crucial for integrating external news sources into our platform. It periodically fetches articles from Algolia's API every hour and imports them into our system using the `ImportArticleUseCase`. This automation ensures our platform remains dynamic and up-to-date with the latest content.

## API Endpoints

### Articles API (`/v1/articles`)
- **PUT** `/v1/articles`: Import articles from external sources, requiring authentication.
- **GET** `/v1/articles`: Fetch a paginated list of articles with support for complex filtering and sorting. Requires authentication.
    - **Endpoint Usage**:
        - URL: `http://localhost:8080/v1/articles`
        - Parameters:
            - `search`: A comma-separated list of filters to apply, which can include `month`, `storyTitle`, `author`, and `tag`.
            - `page`: The page number (starting from 0).
            - `perPage`: Number of articles per page. (max 5 articles per page)
            - `sort`: The attribute to sort by (default is `createdAt`).
            - `dir`: Direction of the sort (`asc` or `desc`).
        - Example: `http://localhost:8080/v1/articles?search=april,comment&page=0&perPage=5&sort=createdAt&dir=asc`
- **DELETE** `/v1/articles/{objectId}`: Delete an article by its unique identifier, requiring authentication.

### Authentication API (`/v1/auth`)
- **POST** `/v1/auth`: Authenticate users and issue access tokens.

### Users API (`/v1/users`)
- **POST** `/v1/users`: Create new users in the system.

## Running the Application

### Using Docker Compose
To deploy the application via Docker, run:
```bash
sudo docker compose up -d
```
This command initiates the services as specified in the `docker-compose.yml` file.

### Initial Data Loading
At startup, the application performs an initial import of data from Algolia, populating the database with articles.

### Accessing the Application
The API is accessible at [http://localhost:8080/api/docs](http://localhost:8080/api/docs) through the Swagger UI, which offers an interactive environment to execute API requests.

## Testing the Endpoints

### For those preferring Postman for API testing

- Import the `HackerNews.postman_collection.json` from the project into Postman to access prepared requests for all API functionalities.

### Steps to Test
1. **Create a User**: Use the `/v1/users` endpoint to register a user.
2. **Obtain an AccessToken**: Authenticate through the `/v1/auth` endpoint to receive an access token.

These steps, along with the ability to import the provided Postman collection, facilitate thorough testing of the API's functionalities including article management and user authentication.