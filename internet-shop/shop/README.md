# 🛍️ Internet Shop — Spring Boot REST API

Финальный проект: Интернет-магазин на Java Spring Boot.

## 🏗️ Архитектура

```
Client (HTTP)
    ↓
Controller Layer  (UserController, ProductController, OrderController, CategoryController)
    ↓
Service Layer     (бизнес-логика: UserService, ProductService, OrderService, CategoryService)
    ↓
Repository Layer  (Spring Data JPA: UserRepository, ProductRepository, ...)
    ↓
Database          (H2 / PostgreSQL)
```

## 📦 Сущности (Entities)

| Сущность     | Описание                            |
|-------------|-------------------------------------|
| User        | Пользователь (покупатель / админ)   |
| Category    | Категория товаров                   |
| Product     | Товар                               |
| Order       | Заказ пользователя                  |
| OrderItem   | Позиция в заказе                    |

## 🚀 Запуск проекта

### Требования
- Java 17+
- Maven 3.8+

### Запуск
```bash
mvn spring-boot:run
```

Приложение стартует на: **http://localhost:8080**

H2 Console (БД в браузере): **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:shopdb`
- Username: `sa`
- Password: *(пусто)*

## 📋 API Endpoints

### Users `/api/users`
| Method | URL                    | Описание              |
|--------|------------------------|-----------------------|
| POST   | `/api/users/register`  | Регистрация           |
| GET    | `/api/users`           | Все пользователи      |
| GET    | `/api/users/{id}`      | Пользователь по ID    |
| DELETE | `/api/users/{id}`      | Удалить пользователя  |

### Categories `/api/categories`
| Method | URL                      | Описание          |
|--------|--------------------------|-------------------|
| POST   | `/api/categories`        | Создать категорию |
| GET    | `/api/categories`        | Все категории     |
| GET    | `/api/categories/{id}`   | Категория по ID   |
| PUT    | `/api/categories/{id}`   | Обновить          |
| DELETE | `/api/categories/{id}`   | Удалить           |

### Products `/api/products`
| Method | URL                           | Описание                  |
|--------|-------------------------------|---------------------------|
| POST   | `/api/products`               | Создать товар             |
| GET    | `/api/products`               | Все товары                |
| GET    | `/api/products/{id}`          | Товар по ID               |
| GET    | `/api/products/search?name=`  | Поиск по названию         |
| GET    | `/api/products/category/{id}` | Товары по категории       |
| GET    | `/api/products/price?min=&max=` | Товары по диапазону цен |
| PUT    | `/api/products/{id}`          | Обновить товар            |
| DELETE | `/api/products/{id}`          | Удалить товар             |

### Orders `/api/orders`
| Method | URL                       | Описание             |
|--------|---------------------------|----------------------|
| POST   | `/api/orders`             | Создать заказ        |
| GET    | `/api/orders`             | Все заказы           |
| GET    | `/api/orders/{id}`        | Заказ по ID          |
| GET    | `/api/orders/user/{id}`   | Заказы пользователя  |
| PATCH  | `/api/orders/{id}/status` | Изменить статус      |
| PATCH  | `/api/orders/{id}/cancel` | Отменить заказ       |

## 📝 Примеры запросов

### Регистрация пользователя
```json
POST /api/users/register
{
  "username": "alice",
  "email": "alice@example.com",
  "password": "password123"
}
```

### Создание товара
```json
POST /api/products
{
  "name": "MacBook Air",
  "description": "Apple laptop M2",
  "price": 1299.99,
  "stock": 30,
  "categoryId": 1
}
```

### Создание заказа
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

### Изменить статус заказа
```json
PATCH /api/orders/1/status
{
  "status": "CONFIRMED"
}
```

## 🔑 Статусы заказа
`PENDING` → `CONFIRMED` → `SHIPPED` → `DELIVERED`  
Любой статус → `CANCELLED` (кроме DELIVERED)

## 👤 Демо данные (загружаются автоматически)

**Пользователи:**
- admin / admin123 (ADMIN)
- john / password123 (USER)

**Категории:** Electronics, Clothing, Books  
**Товары:** Laptop Pro 15, Smartphone X, Basic T-Shirt, Spring Boot in Action

## 🛠️ Технологии

- **Java 17**
- **Spring Boot 3.2**
- **Spring Data JPA** (Hibernate)
- **Spring Security** (BCrypt)
- **H2 Database** (dev) / **PostgreSQL** (prod)
- **Lombok**
- **Maven**
