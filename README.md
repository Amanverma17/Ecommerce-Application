# 🛒 Ecommerce Application — Microservices

A backend e-commerce system built with **Spring Boot Microservices**, featuring independent services for User, Product, and Order management — each with its own PostgreSQL database.

---

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   User Service  │    │ Product Service │    │  Order Service  │
│   Port: 8082    │    │   Port: 8083    │    │   Port: 8084    │
│   PostgreSQL    │    │   PostgreSQL    │    │   PostgreSQL    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

Each service is independently deployable with its own database — no shared state.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 4.x |
| ORM | Spring Data JPA + Hibernate |
| Database | PostgreSQL (per service) |
| Containerization | Docker + Docker Compose |
| Build Tool | Maven |
| Utilities | Lombok, DTO Pattern, Java Streams |

---

## 📦 Services

### 👤 User Service — `localhost:8082`
Manages user registration, profile, and address.

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

---

### 📦 Product Service — `localhost:8083`
Manages product catalog with soft delete support.

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/product` | Get all active products |
| GET | `/api/product/search?keyword=` | Search products |
| POST | `/api/product` | Create product |
| PUT | `/api/product/{id}` | Update product |
| DELETE | `/api/product/{id}` | Soft delete product |

---

### 🛍️ Order Service — `localhost:8084`
Manages cart and order lifecycle.

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cart` | Get cart items |
| POST | `/api/cart` | Add item to cart |
| DELETE | `/api/cart` | Remove item from cart |
| GET | `/api/order` | Get order history |
| POST | `/api/order` | Place order from cart |

> **Note:** Order and Cart endpoints require `X-User-ID` header.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Docker Desktop
- IntelliJ IDEA (recommended)

### 1. Clone the repository
```bash
git clone https://github.com/Amanverma17/Ecommerce-Application.git
cd Ecommerce-Application
```

### 2. Configure each service
Create `application.yml` in each service under `src/main/resources/`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_db_name
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
```

### 3. Start PostgreSQL + pgAdmin
```bash
docker compose up -d
```

### 4. Run each service
Open 3 terminals:
```bash
# Terminal 1 - User Service
cd user && mvn spring-boot:run

# Terminal 2 - Product Service
cd product && mvn spring-boot:run

# Terminal 3 - Order Service
cd order && mvn spring-boot:run
```

---

## 🧪 Sample API Usage

### Create a Product
```bash
POST http://localhost:8083/api/product
Content-Type: application/json

{
  "name": "iPhone 15",
  "description": "Apple iPhone 15",
  "price": 79999.00,
  "stockQuantity": 10,
  "category": "Electronics",
  "imageURL": "https://example.com/iphone.jpg"
}
```

### Add to Cart
```bash
POST http://localhost:8084/api/cart
X-User-ID: 1
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

### Place Order
```bash
POST http://localhost:8084/api/order
X-User-ID: 1
```

---

## 🗂️ Project Structure

```
ecom-microservice/
├── user/                  # User Service (port 8082)
│   └── src/main/java/com/ecommerce/user/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── model/
│       └── dto/
├── product/               # Product Service (port 8083)
│   └── src/main/java/com/ecommerce/product/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── model/
│       └── dto/
├── order/                 # Order Service (port 8084)
│   └── src/main/java/com/ecommerce/order/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── model/
│       └── dto/
└── docker-compose.yml
```

---

## ✨ Key Features

- **Microservices Architecture** — 3 independent services with separate databases
- **Soft Delete** — Products are deactivated instead of permanently deleted
- **DTO Pattern** — Clean separation between API layer and persistence layer
- **Docker Compose** — One command to spin up all databases
- **Java Streams** — Used for mapping and aggregation logic
- **Cart → Order Flow** — Cart items are converted to order on checkout

---

## 👤 Author

**Aman Verma**
- GitHub: [github.com/Amanverma17](https://github.com/Amanverma17)
- LinkedIn: [linkedin.com/in/aman-verma-r-87601134b](https://linkedin.com/in/aman-verma-r-87601134b)
- LeetCode: [amanverma17](https://leetcode.com/amanverma17)
