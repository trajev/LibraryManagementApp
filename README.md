# Library Management System API

A RESTful backend service built with **Spring Boot**, **Spring Security**, **JWT**, and **MySQL** for managing library books, users, and borrowing transactions.

---

## Features

- **Authentication & Authorization**: Stateless JWT authentication with role-based access (`ROLE_USER`, `ROLE_ADMIN`).
- **Book Management**: Full CRUD operations for library books with unique title and availability tracking.
- **Issue & Return System**: 
  - Automatically calculates 14-day due dates.
  - Automatically updates book inventory on issue and return.
- **Centralized Error Handling**: Global exception handling with consistent error response structures.

---

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.1.2
- **Security:** Spring Security & JJWT (`0.13.0`)
- **Database:** MySQL 8.x
- **ORM:** Spring Data JPA / Hibernate
- **Object Mapping:** ModelMapper
- **Build Tool:** Maven

---

## Database Configuration

Update your database credentials in `src/main/resources/application.yaml`:

```yaml
server:
  port: 8080

spring:
  application:
    name: librarymanagement	
  datasource:
    url: jdbc:mysql://localhost:3307/librarymanagement
    username: root
    password: YOUR_PASSWORD
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    database-platform: org.hibernate.dialect.MySQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  secretkey: VGhpc0lzQVNlY3VyZUtleUZvckpXVFNpZ25pbmdJbk15QXBw
  expiration: 86400000 # 24 hours in milliseconds
```

> [!IMPORTANT]
> Change the default port `3307` to `3306` if your MySQL installation runs on the standard port.

---

## How to Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/trajev/LibraryManagementApp.git
   cd LibraryManagementApp
   ```

2. **Build and run using the Maven wrapper:**
   - **Windows:**
     ```cmd
     mvnw.cmd spring-boot:run
     ```
   - **Linux / macOS:**
     ```bash
     ./mvnw spring-boot:run
     ```

The server will run on `http://localhost:8080`.

---

## API Endpoints

### 1. Authentication (`/auth`)

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/auth/registernormaluser` | Public | Register a user (`ROLE_USER`) |
| `POST` | `/auth/login` | Public | Login and receive a JWT token |

#### Register User
- **Endpoint:** `POST /auth/registernormaluser`
- **Request Body:**
  ```json
  {
    "username": "johndoe",
    "email": "john@example.com",
    "password": "password123"
  }
  ```

#### Login
- **Endpoint:** `POST /auth/login`
- **Request Body:**
  ```json
  {
    "username": "johndoe",
    "password": "password123"
  }
  ```
- **Response:**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsIn...",
    "username": "johndoe",
    "roles": ["ROLE_USER"]
  }
  ```

---

### 2. Admin (`/admin`)

> [!NOTE]
> All admin endpoints require `Authorization: Bearer <ADMIN_TOKEN>`.

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/admin/registeradminuser` | Admin (`ROLE_ADMIN`) | Register an admin user |

#### Register Admin
- **Endpoint:** `POST /admin/registeradminuser`
- **Request Body:**
  ```json
  {
    "username": "adminuser",
    "email": "admin@example.com",
    "password": "adminpassword"
  }
  ```

---

### 3. Books (`/books`)

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/books` | Public | Get all books |
| `GET` | `/books/{bookId}` | Public | Get book by UUID |
| `POST` | `/books` | Admin (`ROLE_ADMIN`) | Add a new book |
| `PUT` | `/books/{bookId}` | Admin (`ROLE_ADMIN`) | Update book details |
| `DELETE` | `/books/{bookId}` | Admin (`ROLE_ADMIN`) | Delete a book |

#### Add Book
- **Endpoint:** `POST /books`
- **Headers:** `Authorization: Bearer <ADMIN_TOKEN>`
- **Request Body:**
  ```json
  {
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "isbn": "978-0132350884",
    "quantity": 5,
    "isAvailable": true
  }
  ```

---

### 4. Issue & Return Records (`/issuerecords`)

> [!NOTE]
> These endpoints require a valid user JWT token: `Authorization: Bearer <TOKEN>`.

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/issuerecords/issuebook/{bookId}` | Authenticated | Issue a book to the logged-in user |
| `POST` | `/issuerecords/returnbook/{issueRecordId}` | Authenticated | Return an issued book |

#### Issue a Book
- **Endpoint:** `POST /issuerecords/issuebook/{bookId}`
- **Behavior:**
  - Decrements book quantity by 1.
  - Sets `dueDate` to 14 days from issue date.
  - Automatically associates with the logged-in user.

#### Return a Book
- **Endpoint:** `POST /issuerecords/returnbook/{issueRecordId}`
- **Behavior:**
  - Increments book quantity by 1.
  - Marks record as returned with the current date.

---

## Error Response Format

All runtime exceptions return a standardized error response:

```json
{
  "statusCode": 400,
  "message": "Book not available",
  "timestamp": 1730707200000
}
```
