The Food Ordering System enables customers and administrators to manage food delivery orders through a microservice architecture.

Core features:

User management

Supports adding, viewing, editing, and deleting users.

Role-based access control ensures separation between admin and regular users.

Order lifecycle management

Create, schedule, and cancel orders.

Orders transition automatically between statuses (e.g., CREATED → PREPARING → READY → DELIVERED).

Status updates are triggered in the background without manual intervention.

Real-time updates

Implemented polling mechanism for frontend order status refresh.

Architecture prepared for WebSocket or Server-Sent Events (SSE) integration.

Authentication & authorization

Implemented with JWT.

Role and permission checks on each endpoint.

Error handling & logging

Centralized error handling with a dedicated ErrorMessage table.

Consistent error responses for invalid requests or unauthorized actions.

Tech stack

Backend: Spring Boot (Java) with JPA/Hibernate.

Frontend: Angular for user interaction.

Database: PostgreSQL with foreign key dependencies between orders and order_items.
