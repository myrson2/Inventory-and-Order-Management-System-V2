
---

# SmartStock v2

**Layered Inventory & Order Management System (Java | CLI Adapter | File Persistence)**

---

## 🏗 Architectural Overview

SmartStock v2 follows a **layered backend architecture** inspired by Spring Boot and Domain-Driven Design (DDD).

The system separates:

* **Domain Layer** → Core business entities (pure models)
* **Application Layer** → Business use cases and orchestration
* **Infrastructure Layer** → Technical implementations (file, logging, notifications)
* **Presentation Layer** → CLI adapter

### Layered Dependency Flow

```text
CLI → Application → Domain
             ↓
      Infrastructure
             ↓
            Util
```

### Architectural Rules

* CLI Layer – Handles user interaction and input/output.

* Application Layer – Orchestrates use cases and business workflows.

* Domain Layer – Contains pure business models and core rules.

* Infrastructure Layer – Technical implementations (file handling, logging, notifications).

* Util Layer – Shared helper utilities used across layers.

---

# 📂 Updated Project Structure

```text
smartstock/
│
├── cli/                         # Presentation Layer
│   ├── ConsoleUI.java
│   └── Menu.java
│
├── domain/                      # Core Business Models (No external dependencies)
│   ├── product/
│   │   ├── Product.java
│   │   ├── PerishableProduct.java
│   │   └── NonPerishableProduct.java
│   │
│   ├── inventory/
│   │   └── Inventory.java
│   │
│   ├── order/
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   └── OrderStatus.java
│   │
│   └── user/
│       ├── User.java
│       ├── Admin.java
│       └── Customer.java
│
├── application/                 # Business Use Cases / Services
│   ├── inventory/
│   │   └── InventoryService.java
│   │
│   ├── order/
│   │   └── OrderService.java
│   │
│   └── user/
│       ├── UserService.java
│       ├── AdminService.java
│       └── CustomerService.java
│
├── infrastructure/              # Technical Implementations
│   ├── file/
│   │   └── FileManager.java
│   │
│   ├── history/
│   │   ├── InventoryHistory.java
│   │   └── OrderHistory.java
│   │
│   ├── log/
│   │   └── LoggerService.java
│   │
│   └── notification/
│       └── NotificationService.java
│
├── util/                        # Shared Utilities (Stateless Helpers)
│   ├── IdGenerator.java
│   ├── DateUtils.java
│   ├── InputValidator.java
│   └── StringFormatter.java
│
└── exception/                   # Custom Exceptions
    ├── InsufficientStockException.java
    └── EntityNotFoundException.java
```

---

# 📌 Component Responsibilities

---

# 1️⃣ Presentation Layer — `cli`

## ConsoleUI

Acts as the application entry point and user interaction controller.

### Responsibilities

* Manage login flow
* Route commands based on user role
* Delegate actions to appropriate services
* Handle input/output formatting only

### Key Methods

* `start()` → Initializes and starts CLI loop.
* `showLoginMenu()` → Displays authentication options.
* `showAdminMenu()` → Displays admin operations.
* `showCustomerMenu()` → Displays customer operations.
* `handleUserInput()` → Delegates actions to services.
* `logout()` → Ends session.

No validation or business rules here.

---

## Menu

Provides reusable CLI menu rendering.

### Methods

* `displayMainMenu()` → Prints main menu.
* `displayAdminOptions()` → Prints admin commands.
* `displayCustomerOptions()` → Prints customer commands.

---

# 2️⃣ Domain Layer — `domain`

Pure business models.
No service calls.
No file access.
No logging.

---

## domain.product

### Product (Abstract)

Represents a product entity.

* `increaseStock(int)` → Adds stock quantity.
* `decreaseStock(int)` → Reduces stock safely.
* `getProductDetails()` → Returns formatted summary.

---

### PerishableProduct

* `isExpired()` → Checks expiration status.
* `getProductDetails()` → Returns product details including expiration.

---

### NonPerishableProduct

* `getProductDetails()` → Returns product details including warranty.

---

## domain.inventory

### Inventory

Encapsulates in-memory product collection.

* `addProduct(Product)` → Adds product to storage.
* `removeProduct(String)` → Removes product by ID.
* `getProductById(String)` → Retrieves product.
* `getAllProducts()` → Returns all products.

No persistence or validation logic.

---

## domain.user

### User (Abstract)

Represents authenticated identity.

Fields:

* id
* name
* email
* password

No operational logic.

---

### Admin

Represents administrator identity.

POJO only.

---

### Customer

Represents customer identity.

POJO only.

---

## domain.order

### Order

Represents transactional aggregate.

* `addItem(OrderItem)` → Adds line item.
* `calculateTotal()` → Computes total cost.
* `changeOrderStatus(OrderStatus)` → Updates lifecycle state.
* `validateOrder()` → Validates integrity before confirmation.

---

### OrderItem

* `calculateSubTotal()` → Computes item subtotal.
* `getItemDetails()` → Returns formatted item details.

---

### OrderStatus

Enum representing order lifecycle:

* PENDING
* CONFIRMED
* SHIPPED
* DELIVERED
* CANCELLED

---

# 3️⃣ Application Layer — `application`

Contains all business logic and use cases.

---

## application.user

### UserService

Handles authentication and session state.

* `login(String, String)` → Authenticates and returns User.
* `logout()` → Clears active session.
* `getCurrentUser()` → Returns logged-in user.

---

### AdminService

Handles privileged operations.

Injected:

* InventoryService

* OrderService

* LoggerService

* InventoryHistory

* `addProduct(Product)` → Validates and persists product.

* `updateStock(String, int)` → Adjusts product quantity.

* `removeProduct(String)` → Deletes product.

* `viewAllOrders()` → Retrieves all orders.

* `viewInventoryHistory()` → Returns audit records.

* `viewLogs()` → Retrieves system logs.

---

### CustomerService

Handles customer workflows.

Injected:

* OrderService

* InventoryService

* LoggerService

* `browseProducts()` → Retrieves available products.

* `createOrder(Customer)` → Initializes new order.

* `addItemToOrder(Order, Product, int)` → Adds validated product.

* `finalizeOrder(Order)` → Completes transaction.

* `cancelOrder(String)` → Cancels eligible order.

* `viewOrderHistory(Customer)` → Retrieves customer orders.

---

## application.inventory

### InventoryService

Central inventory business logic.

Injected:

* Inventory

* FileManager

* LoggerService

* InventoryHistory

* NotificationService

* `addProduct(Product)` → Validates and saves product.

* `updateStock(String, int)` → Safely updates stock.

* `removeProduct(String)` → Removes product and logs event.

* `checkLowStock(Product)` → Triggers notification if threshold reached.

* `loadInventory()` → Loads products from storage.

* `saveInventory()` → Persists products.

---

## application.order

### OrderService

Handles transactional order processing.

Injected:

* FileManager

* LoggerService

* OrderHistory

* InventoryService

* `createOrder(Customer)` → Creates order instance.

* `addItemToOrder(Order, Product, int)` → Adds validated item.

* `finalizeOrder(Order)` → Confirms order and updates inventory.

* `cancelOrder(String)` → Cancels order if allowed.

* `loadOrders()` → Loads persisted orders.

* `saveOrders()` → Saves orders to file.

---

# 4️⃣ Infrastructure Layer — `infrastructure`

Handles technical implementations only.

---

## FileManager

* `saveProducts(List<Product>)` → Persists product data.
* `loadProducts()` → Loads product data.
* `saveOrders(List<Order>)` → Persists order data.
* `loadOrders()` → Loads order data.
* `appendToFile(String, String)` → Appends audit/log entries.

---

## LoggerService

* `logInfo(String)` → Writes informational log.
* `logWarning(String)` → Writes warning log.
* `logError(String)` → Writes error log.

---

## NotificationService

* `notify(String)` → Displays system notification.
* `notifyLowStock(Product)` → Alerts low inventory.
* `notifyOrderStatusChange(Order)` → Alerts order updates.

---

## InventoryHistory

* `recordStockIncrease(String, int)` → Logs stock addition.
* `recordStockDecrease(String, int)` → Logs stock deduction.
* `recordProductRemoval(String)` → Logs deletion event.

---

## OrderHistory

* `recordOrderCreation(String)` → Logs order creation.
* `recordStatusChange(String, OrderStatus)` → Logs status updates.
* `recordCancellation(String)` → Logs cancellation event.

---
# 🧩 Util Package Responsibilities

The util package contains reusable, stateless helper classes used across the system.

* IdGenerator - Generates unique identifiers for domain entities.

* DateUtils - Provides centralized date parsing, formatting, and comparison logic.

* InputValidator - Validates CLI input to ensure data integrity before reaching the application layer.

* StringFormatter - Standardizes output formatting for consistent console display.

---
# 🔐 Exception Layer

All exceptions extend RuntimeException.

Thrown only from:

* Application layer

Examples:

* InsufficientStockException
* ProductNotFoundException
* InvalidOrderException
* UnauthorizedActionException

---

# 🔄 Main.java

Acts as a lightweight dependency injection container.

Responsibilities:

* Instantiate infrastructure components
* Wire application services
* Inject dependencies via constructors
* Start ConsoleUI

---

# 🚀 What This Architecture Demonstrates

* Layered backend structure
* Domain purity
* Service-based business logic
* Manual dependency injection
* Clear separation of technical concerns
* Role-based access control
* Audit and logging systems
* Scalable, Spring-ready architecture

---

This structure allows SmartStock v2 to transition easily into:

* Spring Boot REST API
* Database persistence (JPA)
* Multi-user systems
* Enterprise backend systems

---
