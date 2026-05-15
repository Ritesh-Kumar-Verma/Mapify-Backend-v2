# 🌍 Mapify Backend v2 – Secure REST API (Spring Boot + JWT)

Mapify Backend v2 is the **secure backend implementation** of the Mapify real-time location sharing application.

This version introduces **JWT-based authentication and Spring Security** to protect APIs and ensure secure communication between the frontend and backend.

The backend provides REST APIs for **user authentication, friend management, and location sharing**, and is integrated with a **PostgreSQL database**.

---

# 🚀 Features

- User Registration & Login
- JWT Authentication
- Spring Security integration
- Secure REST APIs
- Friend request system
- Location sharing APIs
- PostgreSQL database integration
- Modular and scalable backend architecture

---

# 🛠 Tech Stack

## Backend
- Java
- Spring Boot
- Spring Security

## Authentication
- JWT (JSON Web Token)

## Database
- PostgreSQL

## Tools
- Maven
- REST APIs
- JPA / Hibernate

---

# 🧩 System Architecture

Frontend (React + Tailwind + Leaflet)

⬇ REST API Calls

Spring Boot Backend

⬇

PostgreSQL Database

Authentication is handled using **JWT tokens**, which are validated by **Spring Security filters** before granting access to protected endpoints.

---

# 🔐 Authentication Flow

1. User registers or logs in.
2. Server validates credentials.
3. Server generates a **JWT token**.
4. Token is returned to the client.
5. Client sends the token in **Authorization header**.
6. Spring Security validates the token before allowing access to protected APIs.


Authorization: Bearer <JWT_TOKEN>


---

# 📂 Project Structure

Mapify-Backend-v2
│
├── src
│ ├── main
│ │ ├── java
│ │ │ ├── controller
│ │ │ ├── service
│ │ │ ├── repository
│ │ │ ├── model
│ │ │ ├── security
│ │ │ └── config
│ │ │
│ │ └── resources
│ │ └── application.properties
│
├── pom.xml
└── README.md



---

# ⚙️ Installation & Setup

## 1️⃣ Clone the repository

git clone https://github.com/ritesh-kumar-verma/Mapify-Backend-v2.git


---

## 2️⃣ Navigate to the project

cd Mapify-Backend-v2


---

## 3️⃣ Configure Database

Update `application.properties`

spring.datasource.url=jdbc:postgresql://localhost:5432/mapify
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


---

## 4️⃣ Build the project

mvn clean install


---

## 5️⃣ Run the application

mvn spring-boot:run


The backend will start at:

http://localhost:8080


---

# 🔗 Frontend Repository

Frontend is built using **React.js + Tailwind CSS + Leaflet**

Repository: 
https://github.com/ritesh-kumar-verma/Mapify

Live Demo:
https://ritesh-kumar-verma.github.io/Mapify/#/login


Example header:
