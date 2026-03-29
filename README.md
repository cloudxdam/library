# Library API

REST API for managing a library with books, users, and loans.

## Architecture

The application follows a layered architecture:

- Controller → handles HTTP requests and responses
- Service → contains business logic and rules
- Repository → data access layer using Spring Data JPA
- DTO + Mapper → isolates API from persistence layer
- Exception Handler → centralizes error handling

Entities are never exposed directly in the API.

## Stack

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA (Hibernate)
- Bean Validation
- OpenAPI / Swagger
- H2 in-memory database (default)
- MySQL configuration ready for production use

## Features

- Full CRUD for books
- Full CRUD for users
- Loan creation and return flow
- Request validation
- Global error handling
- Interactive API documentation with Swagger UI

## Business Rules

- A book cannot be created with a duplicated ISBN
- A user cannot be created with a duplicated email
- A book cannot have more than one active loan at the same time
- A user cannot have more than 3 active loans
- A user with active loans cannot be deleted
- An active loan cannot be deleted
- A returned loan cannot be returned again

## Project Structure

The main code lives in `api/`:

```text
api/
|- src/main/java/com/pachedev/library
|  |- config
|  |- controller
|  |- dto
|  |- exception
|  |- mapper
|  |- model
|  |- repository
|  \- service
\- src/main/resources
```

## Run the Application

1. Go to `api/`
2. Start the application:

```bash
./mvnw spring-boot:run
```

By default, the API starts with an in-memory H2 database and loads seed data from `api/src/main/resources/data.sql`.

## Database Configuration

In `api/src/main/resources/application.properties`, the project uses H2 by default:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
```

There is also a commented MySQL base configuration that you can enable if you want persistent storage.

## API Documentation

With the application running:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Main Endpoints

### Books

- `POST /api/books`
- `GET /api/books`
- `GET /api/books/{id}`
- `PUT /api/books/{id}`
- `PATCH /api/books/{id}`
- `DELETE /api/books/{id}`
- `GET /api/books/search?author={author}&pages={pages}`
- `GET /api/books/range?min={min}&max={max}`

Create example:

```json
{
  "title": "Domain-Driven Design",
  "author": "Eric Evans",
  "isbn": "9780321125217",
  "pages": 560
}
```

### Users

- `POST /api/users`
- `GET /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `PATCH /api/users/{id}`
- `DELETE /api/users/{id}`

Create example:

```json
{
  "name": "Maria Lopez",
  "email": "maria@example.com"
}
```

### Loans

- `POST /api/loans`
- `GET /api/loans`
- `GET /api/loans/{id}`
- `PATCH /api/loans/{id}/loan-date`
- `PATCH /api/loans/{id}/return`
- `DELETE /api/loans/{id}`

Create example:

```json
{
  "loanDate": "2026-03-28",
  "userId": 1,
  "bookId": 2
}
```

## Seed Data

When the application starts, it loads sample users, books, and loans from `api/src/main/resources/data.sql`.

It includes:

- 2 initial users
- 4 initial books
- 3 sample loans

## Testing

Run the tests with:

```bash
./mvnw test
```

## Error Responses

The API returns structured error responses for:

- not found resources (`404`)
- duplicate resources (`409`)
- validation or business rule violations (`400`)

## Suggested Improvements

- add authentication and authorization
- split H2 and MySQL into separate profiles
- add more integration and controller tests
- add pagination and advanced filters
