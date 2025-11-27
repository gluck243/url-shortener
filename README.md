# URL Shortener Service (Portfolio Demo)

*A practice project exploring Java & Kotlin interoperability with Spring Boot.*

## 📋 Overview

This project is a lightweight URL shortening service built to demonstrate backend development skills and explore the hybrid use of **Java** and **Kotlin** within a single **Spring Boot** application.

* **Core Logic:**  Written in Java (Base62 Encoding) to demonstrate algorithmic logic
* **Web Layer:** Written in Kotlin using Spring Boot for concise REST controllers
* **Persistence:** PostgreSQL with Spring Data JPA
* **Containerization:** Deployed using Docker

It serves as a playground for testing concepts and filling out my GitHub portfolio with a working example of modern backend practices.

## 🛠️ Tech Stack

* **Languages:** Java 21, Kotlin 2.2
* **Framework:** Spring Boot 3
* **Database:** PostgreSQL 17
* **Containerization:** Docker & Docker Compose
* **Build Tool:** Maven

### Quick Start
1.  Clone the repository.
    ```bash
    git clone https://github.com/gluck243/url-shortener.git
    ```
    
2.  Create a `.env` file in the root directory with the following variables:
    ```properties
    POSTGRES_USER=postgres
    POSTGRES_PASSWORD=password
    POSTGRES_DB=url_shortener
    ```

3.  Build the application:
    ```bash
    mvn clean package -DskipTests
    ```
4.  Launch the stack (App + Database):
    ```bash
    docker compose up --build
    ```
5.  Access the API Documentation:
    * [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

##  API Endpoints

* `POST /api/shorten` - Accepts `{"url": "..."}` and returns a short link.
* `GET /api/{shortCode}` - Redirects to the original URL.

