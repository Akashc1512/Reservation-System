# Reservation System

A comprehensive **Hotel Reservation System** built using a microservices architecture. This project is designed to provide modularity, scalability, and efficient management of hotel bookings and related services.

---

## Project Structure

This system is divided into multiple microservices, each handling a specific responsibility:

| **Microservice**                | **Description**                                   | **Last Commit Message**                  | **Last Commit Date** |
|----------------------------------|---------------------------------------------------|------------------------------------------|-----------------------|
| **apigateway**                   | Handles API Gateway and routing.                 | Initial commit with customized project   | 1 hour ago           |
| **customers**                    | Manages customer profiles and user-related data. | Initial commit with customized project   | 1 hour ago           |
| **discovery**                    | Implements service discovery using Eureka.       | Initial commit with customized project   | 1 hour ago           |
| **hotel-management-service**     | Manages hotel details, rooms, and availability.  | Initial commit with customized project   | 1 hour ago           |
| **notification**                 | Handles notifications (e.g., email, SMS).        | Initial commit with customized project   | 1 hour ago           |
| **payment**                      | Processes payments and manages transactions.     | Initial commit with customized project   | 1 hour ago           |
| **reservations**                 | Handles booking and reservation processes.       | Initial commit with customized project   | 1 hour ago           |

---

## Features

- **API Gateway**: Centralized routing for microservices.
- **Customer Management**: Manage user profiles, preferences, and login.
- **Hotel Management**: Handle hotel data, room availability, and pricing.
- **Reservation System**: Book and manage reservations seamlessly.
- **Payment Processing**: Secure and efficient payment gateway integration.
- **Notifications**: Email and SMS notifications for booking updates.
- **Service Discovery**: Service registration and lookup using Eureka.

---

## Technologies Used

- **Languages**: Java
- **Frameworks**: Spring Boot, Spring Cloud
- **Database**: MySQL
- **Caching**: Redis
- **Message Queue**: Apache Kafka
- **Build Tool**: Maven
- **Deployment**: Docker, Kubernetes

