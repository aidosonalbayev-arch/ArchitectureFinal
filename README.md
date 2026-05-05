# ShopKZ — Internet Shop

> Full-stack e-commerce web application built with Java Spring Boot

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?style=flat-square&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.9-red?style=flat-square&logo=apachemaven)

---

## About

ShopKZ is a final project for the Java Spring course. It is a fully functional internet shop with a REST API backend, PostgreSQL database, and a frontend built with HTML/CSS/JavaScript.

**Team members:** Aidos · Zhania · Nurbolat · Nazerke

---

## Features

- User registration and login with role-based access (USER / ADMIN)
- Product catalog with search and category filtering
- Shopping cart and order placement
- Order tracking with status management
- Admin panel: full CRUD for products, categories, orders, and users
- RESTful API with proper HTTP status codes
- Global exception handling

---

## Tech Stack

| Layer     | Technology                  |
| --------- | --------------------------- |
| Language  | Java 17                     |
| Framework | Spring Boot 3.2             |
| ORM       | Spring Data JPA / Hibernate |
| Database  | PostgreSQL 17               |
| Security  | Spring Security + BCrypt    |
| Build     | Maven                       |
| Code gen  | Lombok                      |
| Frontend  | HTML5 / CSS3 / Vanilla JS   |

---

## Architecture

```
Client (Browser)
      ↓  HTTP REST
Controller Layer  — UserController, ProductController, OrderController, CategoryController
      ↓
Service Layer     — UserService, ProductService, OrderService, CategoryService
      ↓
Repository Layer  — Spring Data JPA (JpaRepository)
      ↓
PostgreSQL Database (ArchFinal)
```

---

## Database Schema

```
users          categories
  |                |
  | 1:N            | 1:N
  ↓                ↓
orders  ←——  products
  |
  | 1:N
  ↓
order_items
```

**Entities:** `users` · `categories` · `products` · `orders` · `order_items`

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 17

### Setup

**1. Clone the repository**

```bash
https://github.com/aidosonalbayev-arch/ArchitectureFinal.git
cd internet-shop/shop
```

**2. Create the database**

Open pgAdmin or psql and create a database named `ArchFinal`.

**3. Configure database connection**

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ArchFinal
spring.datasource.username=newAidos
spring.datasource.password=aidos
```

**4. Run the application**

```bash
mvn spring-boot:run
```

The app starts at **http://localhost:8080**

---

## Demo Accounts

| Username | Password    | Role  |
| -------- | ----------- | ----- |
| admin    | admin123    | ADMIN |
| john     | password123 | USER  |

Demo data (categories, products, users) loads automatically on first startup.

---

## API Endpoints

### Users `/api/users`

| Method | Endpoint              | Description       |
| ------ | --------------------- | ----------------- |
| POST   | `/api/users/register` | Register new user |
| GET    | `/api/users`          | Get all users     |
| GET    | `/api/users/{id}`     | Get user by ID    |
| DELETE | `/api/users/{id}`     | Delete user       |

### Categories `/api/categories`

| Method | Endpoint               | Description        |
| ------ | ---------------------- | ------------------ |
| POST   | `/api/categories`      | Create category    |
| GET    | `/api/categories`      | Get all categories |
| GET    | `/api/categories/{id}` | Get by ID          |
| PUT    | `/api/categories/{id}` | Update category    |
| DELETE | `/api/categories/{id}` | Delete category    |

### Products `/api/products`

| Method | Endpoint                        | Description        |
| ------ | ------------------------------- | ------------------ |
| POST   | `/api/products`                 | Create product     |
| GET    | `/api/products`                 | Get all products   |
| GET    | `/api/products/{id}`            | Get by ID          |
| GET    | `/api/products/search?name=     | Search by name     |
| GET    | `/api/products/category/{id}`   | Filter by category |
| GET    | `/api/products/price?min=&max=` | Filter by price    |
| PUT    | `/api/products/{id}`            | Update product     |
| DELETE | `/api/products/{id}`            | Delete product     |

### Orders `/api/orders`

| Method | Endpoint                  | Description       |
| ------ | ------------------------- | ----------------- |
| POST   | `/api/orders`             | Create order      |
| GET    | `/api/orders`             | Get all orders    |
| GET    | `/api/orders/{id}`        | Get by ID         |
| GET    | `/api/orders/user/{id}`   | Get user's orders |
| PATCH  | `/api/orders/{id}/status` | Update status     |
| PATCH  | `/api/orders/{id}/cancel` | Cancel order      |

---

## Example Requests

**Register a user**

```json
POST /api/users/register
{
  "username": "alice",
  "email": "alice@example.com",
  "password": "password123"
}
```

**Create a product**

```json
POST /api/products
{
  "name": "MacBook Air M2",
  "description": "Apple laptop",
  "price": 1299.99,
  "stock": 30,
  "categoryId": 1
}
```

**Place an order**

```json
POST /api/orders
{
  "userId": 1,
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

**Update order status**

```json
PATCH /api/orders/1/status
{
  "status": "CONFIRMED"
}
```

---

## Order Status Flow

```
PENDING → CONFIRMED → SHIPPED → DELIVERED
   ↓           ↓          ↓
          CANCELLED
```

When an order is cancelled, stock is automatically restored.

---

## Project Structure

```
shop/
├── pom.xml
└── src/main/
    ├── java/com/shop/
    │   ├── InternetShopApplication.java
    │   ├── model/
    │   │   ├── User.java
    │   │   ├── Category.java
    │   │   ├── Product.java
    │   │   ├── Order.java
    │   │   └── OrderItem.java
    │   ├── repository/
    │   │   ├── UserRepository.java
    │   │   ├── CategoryRepository.java
    │   │   ├── ProductRepository.java
    │   │   └── OrderRepository.java
    │   ├── service/
    │   │   ├── UserService.java
    │   │   ├── CategoryService.java
    │   │   ├── ProductService.java
    │   │   └── OrderService.java
    │   ├── controller/
    │   │   ├── UserController.java
    │   │   ├── CategoryController.java
    │   │   ├── ProductController.java
    │   │   └── OrderController.java
    │   ├── dto/
    │   │   ├── RegisterRequest.java
    │   │   ├── ProductRequest.java
    │   │   └── OrderRequest.java
    │   ├── exception/
    │   │   ├── ResourceNotFoundException.java
    │   │   └── GlobalExceptionHandler.java
    │   └── config/
    │       ├── SecurityConfig.java
    │       └── DataInitializer.java
    └── resources/
        ├── application.properties
        └── static/
            ├── index.html
            ├── products.html
            ├── cart.html
            ├── orders.html
            ├── admin.html
            ├── css/
            │   └── style.css
            └── js/
                ├── api.js
                ├── auth.js
                ├── products.js
                ├── cart.js
                └── orders.js
```

---

## Team Contributions

| Member   | Responsibility                                       |
| -------- | ---------------------------------------------------- |
| Aidos    | Project setup, Entity models, application.properties |
| Zhania   | Repositories, Services (business logic)              |
| Nurbolat | Controllers, DTOs, Exception handling                |
| Nazerke  | Security config, Frontend (HTML/CSS/JS), Admin panel |

---

## License

This project was created for educational purposes as a final project for the Java Spring course.
