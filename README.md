# retail-product-api
Retail product api with to list all products, add new product and see detailed information of the product

A full-stack application for managing products, their types, and colours. Built with **Spring Boot (Java)** on the backend and **React + Vite + TypeScript** on the frontend.

---

## 📦 Features

- Add new products with product type and multiple colours
- View a paginated list of products
- View detailed info about any product
- REST API with Spring Boot
- Clean, minimal UI built with React

---

## 📁 Project Structure

retail-product-api/ ├── backend/ # Java Spring Boot app └── frontend/ # React + Vite TypeScript app


---

## ⚙️ Backend Setup (Java + Spring Boot)

### ✅ Requirements

- Java 17+
- Maven
- H2 for in-memory
- IDE (IntelliJ, VSCode)

### 🔌 Run Locally

1. Clone the repo

```bash
git https://github.com/vidhyavijayantech/retail-product-api.git
cd retail-product-api/product-api-backend
```

2. Build 
```bash
mvn clean install
```

3. Run
```bash
mvn spring-boot:run
```

## 📦 API Endpoints

Method | Endpoint           | Description
POST   | /api/products      | Create a new product
GET    | /api/products      | Get list of all products
GET    | /api/products/{id} | Get product detail by ID
GET    | /api/product-types | Get available product types
GET    | /api/colours       | Get available colours


## 🧪 Sample Data
On application startup, the app loads sample data into the in-memory H2 database automatically.
We use the data.sql file located in src/main/resources to insert initial records for ProductType and Colour.

# 🖥️ Frontend Setup (React + Vite + TypeScript)
## ✅ Requirements
Node.js (v18+ recommended)
npm 

## 🔌 Run Locally

Open a new terminal and navigate to frontend:
```bash
cd etail-product-api/product-api-frontend
```

### Install dependencies:

```bash
npm install
```

### Run development server:

```bash
npm run dev
```

The app should be available at http://localhost:5173

### 🌐 Proxy Setup
vite.config.ts includes a proxy so API calls from the frontend are directed to the backend running on port 8080.

### 🧪 Running Tests
```bash
npm run test
```

Make sure you have vitest installed for running React unit tests.

## 🧰 Technologies Used

### Backend
Java 17,
Spring Boot,
Spring Data JPA,
H2,
Lombok

### Frontend
React,
Vite,
TypeScript,
React Router






