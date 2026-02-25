# Fexty Leather Bags (Spring Boot)

Spring Boot + Thymeleaf rewrite of the original PHP e-commerce project.

## Project Structure

```text
fexty-leather-java/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/fextyleather/
│   │   │   ├── FextyLeatherApplication.java
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── dto/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
└── README.md
```

## MySQL setup

```sql
CREATE DATABASE fexty_leather;
USE fexty_leather;

CREATE TABLE categories (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  quantity INT DEFAULT 0,
  price DECIMAL(10,2),
  category_id INT,
  image VARCHAR(255),
  status ENUM('Active', 'Inactive') DEFAULT 'Active',
  trending TINYINT DEFAULT 0,
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  mobile VARCHAR(15),
  email VARCHAR(255) UNIQUE,
  password VARCHAR(255)
);
```

### Sample data

```sql
INSERT INTO categories (name) VALUES
('Louis Vuitton'),
('Gucci'),
('Prada');

INSERT INTO products (name, price, category_id, image, status) VALUES
('HM Ladies Paris Straw tote Bag', 1499.00, 1, 'HM.webp', 'Active');
```

## Run

1. Update `src/main/resources/application.properties` with your MySQL credentials.
2. Run:

```bash
mvn spring-boot:run
```

Visit: `http://localhost:8080/`

- Shop: `/shop`
- Cart: `/cart`
- Register/Login: `/register`, `/login`
- Admin add product page: `/admin/addpro`

Cart storage is session-based (similar to PHP `$_SESSION`).
