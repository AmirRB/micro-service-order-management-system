# Order Management System
This project is a simple Order Management System composed of multiple microservices: Order Service, Inventory Service, and Shipping Service. The system handles order creation, inventory management, and order fulfillment. It uses Spring Boot for development, RabbitMQ for asynchronous communication, MySQL for data persistence, and Docker for containerization.

### Table of Contents
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Setup and Installation](#setup-and-installation)
- [Users and Roles](#users-and-roles)
- [API Documentation](#api-documentation)
- [Gateway Service](#gateway-service)

## Architecture

The system is composed of the following services:
- **Order Service**: Handles order creation and management.
- **Inventory Service**: Manages inventory and checks item availability.
- **Shipping Service**: Processes orders for shipment.
- **Gateway Service**: Routes incoming requests to the appropriate microservice.
- **RabbitMQ**: Used for asynchronous communication between services.
- **MySQL**: Used for data storage.

## Technologies Used
- **Spring Boot:** Framework for building microservices.
- **Spring Cloud Gateway:** API Gateway for routing requests.
- **MySQL:** Database for persisting order and inventory data.
- **RabbitMQ:** Messaging broker for asynchronous communication.
- **Docker:** Containerization platform.
- **Spring Security:** For securing the microservices.


## Prerequisites

- Java 22
- maven
- Docker
- Docker Compose

## Setup and Installation
1. Clone the Repository
```bash
git clone https://github.com/AmirRB/micro-service-order-management-system.git
cd order-management-system
```

2. Build the Project


``` bash
mvn clean compile package -f gateway
mvn clean compile package -f inventory
mvn clean compile package -f order
mvn clean compile package -f shipping
```

3. Docker Setup
   - Ensure Docker is installed.
   - Run `docker-compose` to start all services.
    ```bash
    docker-compose up --biuld -d
    ```


## Users and Roles
This project uses Spring Security to manage authentication and authorization. Basic authentication is implemented, and users are assigned different roles to access certain resources.

### Roles
1. USER:
   - Access to `/orders/**` endpoints.
2. ADMIN:
   - Access to `/inventory/**` endpoints.

### Default Users
The application comes pre-configured with two default users:

   1. User:
        - Username: user
        - Password: root
        - Role: USER
      
   2. Admin:
        - Username: admin
        - Password: root
        - Role: ADMIN


## Inventory Registration at Startup

Upon application startup, the **Inventory Service** registers the following items in the system with their respective quantities:

| Item  | Quantity |
|-------|----------|
| tool1 | 11       |
| tool2 | 12       |
| tool3 | 13       |
| tool4 | 14       |
| tool5 | 15       |
| tool6 | 16       |
| tool6 | 17       |
| tool7 | 18       |
| tool9 | 19       |


These items are available for users to order through the **Order Service**. When a user creates an order, the **Order Service** communicates with the **Inventory Service** to check the availability of the requested item. If the item is in stock, the order is processed, and the inventory quantity is updated accordingly.




   
## API Documentation
### Order Service
- Create a New Order

    - Endpoint: `POST /orders`
    - Request Body:
        ```json
        {
          "itemId": "tool1",
          "quantity": 2
        }
        ```
    - description
      - When a user creates an order, the following process takes place:

        1. **Order Creation Request:**
            A request is sent to the Order Service.

        2. **Inventory Check:**
        The Order Service communicates with the Inventory Service to check the availability of the ordered items.

        3. **Order Saved to Database:**
        If the items are available, the order is saved in the MySQL database managed by the Order Service.

        4. **Order Notification:**
        After successfully creating the order, the Order Service publishes an event to RabbitMQ to notify other services about the new order.

        5. **Shipping Process:**
        The Shipping Service listens for new order events from RabbitMQ. Once an event is received:
           - It calls the Inventory Service to reduce the inventory count.
           - It waits for 20 seconds and then updates the order status from `NEW` to `SHIPPED` in the Order Service.


      
- Get Order Details
    - Endpoint: `GET /orders`
    - Endpoint: `GET /orders/{orderId}`

- Change status order to "SHIPPED"
    - Endpoint: `PUT /orders/shipped`
      - Query Parameter: quantity



    
### Inventory Service
  - Check Item Availability

    - Endpoint: `GET /inventory/{itemId}`
      - Query Parameter: `quantity`
- Reduce Inventory

    - Endpoint: `POST /inventory/reduce`
    - Request Body:
        ```json
        {
          "itemId": "tool1",
          "quantity": 2
        }
        ```
### Shipping Service
- Listen for Order Creation
  - This service listens for events from RabbitMQ and updates the order status to "Shipped."



## Gateway Service
The Gateway Service acts as the entry point for all client requests. It routes the requests to the appropriate microservices based on the specified configuration. The gateway is exposed on port 8080, allowing clients to access the APIs of the Order Service, Inventory Service, and Shipping Service through a single entry point.

### Accessing APIs Through the Gateway
With the Gateway Service configured on port 8080, you can access the microservices via the following endpoints:

- **Order Service**:  
  `http://localhost:8080/orders/**`  
  This endpoint is routed to the **Order Service**.

- **Inventory Service**:  
  `http://localhost:8080/inventory/**`  
  This endpoint is routed to the **Inventory Service**.

The Gateway Service simplifies access to your microservices, consolidating them under one port while handling the routing logic behind the scenes.