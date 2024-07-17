# Thortful Challenge - Star Wars API Interface

This project is an implementation of a challenge for joining the Thortful team. It is designed to interface with the
Star Wars API (SWAPI) to fetch and manage information about Star Wars characters, films, and starships. The application
follows a hexagonal architecture, uses Spring Boot 3, and is packaged with Docker.

## Technologies Used

- **Java 21**: Programming language.
- **Spring Boot 3**: Framework for building the application.
- **Gradle**: Build tool.
- **Docker**: Containerization tool.
- **Hexagonal Architecture**: Architectural pattern for maintainability and scalability.
- **Vavr**: Functional programming library. Used for improving code readability and reliability.
- **MapStruct**: Bean mapping library. Simplifies the mapping between DTOs and entities.
- **OkHttp**: HTTP client for making API calls.
- **Swagger/OpenAPI**: API documentation. Provides interactive API documentation.
- **WireMock**: Mocking web services for testing.
- **Lombok**: Code generator for reducing boilerplate.

### Building and Running the Project

Prerequisites

	• Docker
	• Java 21
	• Gradle

To build the project, run the following command:

```bash
./gradlew build
```

To run the project using Docker, follow these steps:

1. Build the Docker image:

```bash
docker build -t thortful-challenge .
```

2. Run the Docker image:

```bash
docker run -p 8080:8080 thortful-challenge
```

The application will be accessible at http://localhost:8080/api/.

## API Documentation

The project uses Swagger/OpenAPI for API documentation. After running the application, you can access the API
documentation at:

    http://localhost:8080/swagger-ui/index.html

## Project Organization

The project follows a hexagonal architecture pattern to separate concerns and promote maintainability. The main
components are:

* **application**: Contains the business logic, DTOs, mappers, and use cases.
* **common**: Contains common utilities and exception handling.
* **domain**: Contains the domain models, repositories, and services.
* **infrastructure**: Contains adapters for web and persistence layers, and configuration classes.
* **test**: Contains the test classes and resources.

## Directory Structure

```mermaid.
├── Dockerfile
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── api
    │   │       └── challenge
    │   │           └── thortful
    │   │               ├── ThortfulApplication.java
    │   │               ├── application
    │   │               │   ├── dto
    │   │               │   ├── mapper
    │   │               │   ├── ports
    │   │               │   │   ├── in
    │   │               │   │   └── out
    │   │               │   └── usercase
    │   │               ├── common
    │   │               │   └── exception
    │   │               ├── domain
    │   │               │   ├── model
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               └── infrastructure
    │   │                   ├── adapters
    │   │                   │   ├── in
    │   │                   │   └── out
    │   │                   │       ├── persistence
    │   │                   │       └── web
    │   │                   └── config
    │   └── resources
    │       ├── application.yml
    │       ├── db
    │       │   └── migration
    │       ├── static
    │       └── templates
    └── test
        ├── java
        │   └── api
        │       └── challenge
        │           └── thortful
        └── resources
```

## Design Decisions

### Scheduler Implementation

The project includes a scheduler that periodically fetches characters from the Star Wars API and updates the local database. 
This scheduler ensures that the character data is kept up to date without manual intervention. The scheduler implementation can be found in the `CharacterUpdateScheduler` class.

### Use of Generics

To avoid code duplication and promote reusability, generics have been utilized in the implementation of various use cases. 
For example, the `FetchEntitiesUseCase` and `GetEntitiesByCharacterUuidUseCase` use generics to handle different types of entities such as films and starships. 
This design decision helps in maintaining a cleaner and more maintainable codebase.

## Conclusion

This project showcases a robust implementation of a Spring Boot application interfacing with an external API,
following best practices and a clean architecture.
It demonstrates the use of modern Java libraries and frameworks to create a maintainable and scalable codebase.