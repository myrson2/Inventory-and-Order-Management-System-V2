# 📘 SmartStock v2

**Layered Inventory & Order Management System (Java | CLI Adapter | File Persistence)**

---

# 🧩 1. System Requirements

## 💡 Description
Defines the scope, purpose, and constraints of SmartStock v2. This ensures the system is focused, scalable, and aligned with real-world inventory management needs.

---

## 📌 Problem Statement
Manual inventory tracking leads to:
- Inaccurate stock levels
- Poor order tracking
- Lack of audit/history logs

---

## ✅ Functional Requirements
- User authentication (Admin, Customer)
- Product management (CRUD)
- Inventory tracking
- Order creation and processing
- Logging and history tracking
- Notification system (low stock, order updates)

---

## ⚙️ Non-Functional Requirements
- Performance: Fast CLI response time
- Scalability: Extendable to web/API
- Security: Role-based access control
- Maintainability: Clean layered architecture

---

## ❓ Key Questions
1. What problems does the system solve?  
2. What are the core features the system MUST have?  
3. What constraints or limitations exist?

---

# 👥 2. System Actors

## 💡 Description
Defines the users interacting with the system and their responsibilities.

---

## 🎭 Actors

### Admin
- Manages products and inventory
- Views logs and history
- Monitors system activity

### Customer
- Browses products
- Creates and manages orders

---

## ❓ Key Questions
1. Who will use the system?  
2. What are the roles and responsibilities of each actor?  
3. How do actors interact with the system?

---

# 🔝 3. Top Use Cases

## 💡 Description
Represents the most critical workflows in the system.

---

## 📌 Core Use Cases

### 1. User Login
- Input credentials
- Validate user
- Redirect to role-based menu

### 2. Manage Inventory (Admin)
- Add product
- Update stock
- Remove product

### 3. Create Order (Customer)
- Browse products
- Add items to order
- Confirm order

### 4. Process Order
- Validate stock
- Deduct inventory
- Update order status

---

## ❓ Key Questions
1. What are the most important actions users perform?  
2. What is the step-by-step flow of each action?  
3. What are the possible errors or edge cases?

---

# 🧱 4. Class Diagram (Conceptual)

## 💡 Description
Defines the structure of the system using object-oriented design.

---

## 📌 Core Entities

- User (Admin, Customer)
- Product (Perishable, NonPerishable)
- Inventory
- Order
- OrderItem

---

## 🔗 Relationships
- User → inherits → Admin / Customer
- Order → contains → OrderItem
- Inventory → manages → Product
- OrderItem → references → Product

---

## ❓ Key Questions
1. What are the main entities in the system?  
2. What attributes and methods does each class have?  
3. How are classes related?

---

# 🔄 5. Activity Diagrams (Conceptual)

## 💡 Description
Shows the flow of actions and decision-making in the system.

---

## 📌 Example Flows

### Order Processing Flow
1. Start
2. Create Order
3. Add Items
4. Validate Stock
5. Deduct Inventory
6. Confirm Order
7. End

---

## ❓ Key Questions
1. What is the start and end of the process?  
2. What decisions affect the flow?  
3. What are alternative paths (errors/failures)?

---

# 🏗 6. Architectural Overview

SmartStock v2 follows a **layered backend architecture** inspired by Spring Boot and Domain-Driven Design (DDD).

---

## 🔁 Layered Dependency Flow

```text
CLI → Application → Domain
             ↓
      Infrastructure
             ↓
            Util
```

---

## 📌 Architectural Rules

- **Presentation Layer (CLI)** → Handles user interaction only  
- **Application Layer** → Contains business logic and workflows  
- **Domain Layer** → Pure business entities (no dependencies)  
- **Infrastructure Layer** → Handles technical concerns  
- **Util Layer** → Shared helpers  

---

# 📂 7. Project Structure

```text
smartstock/
├── cli/
├── domain/
├── application/
├── infrastructure/
├── util/
└── exception/
```

(See full structure below for detailed breakdown.)

---

# 📌 8. Component Responsibilities

## 🖥 Presentation Layer — `cli`
Handles user interaction and delegates logic to services.

## 🧠 Domain Layer — `domain`
Pure business models with no external dependencies.

## ⚙️ Application Layer — `application`
Contains all business rules and use cases.

## 🏗 Infrastructure Layer — `infrastructure`
Handles file storage, logging, and notifications.

## 🧩 Util Layer — `util`
Reusable helper classes.

---

# 🔐 9. Exception Handling

Custom exceptions ensure clean error handling.

Examples:
- InsufficientStockException
- EntityNotFoundException
- UnauthorizedActionException

---

# 🔄 10. System Initialization

## Main.java
Acts as a **manual dependency injection container**.

Responsibilities:
- Initialize services
- Inject dependencies
- Start CLI interface

---

# 🚀 11. Architectural Strengths

- Clean layered design
- Strong separation of concerns
- Scalable to Spring Boot
- Maintainable and testable
- Real-world backend structure

---

# 🔮 12. Future Enhancements

- Integrate MySQL database
- Support multiple inventories
- Convert to Spring Boot REST API
- Build web/mobile interface

---

# 🧠 Summary

This system design follows three key layers:

| Layer        | Covered By                          |
|-------------|------------------------------------|
| WHAT        | Requirements, Actors               |
| HOW         | Use Cases, Activity Flows          |
| STRUCTURE   | Class Diagram, Architecture        |

---
