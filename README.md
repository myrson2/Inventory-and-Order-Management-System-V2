# SmartStock v2

## Advanced Inventory & Order Management System (Java OOP + File Persistence + CLI Layer)

---

# 📂 Updated Project Structure

```
smartstock/
│
├── cli/
│   ├── ConsoleUI.java
│   ├── Menu.java
│
├── product/
│   ├── Product.java
│   ├── PerishableProduct.java
│   ├── NonPerishableProduct.java
│
├── inventory/
│   ├── Inventory.java
│   ├── InventoryService.java
│   ├── InventoryHistory.java
│
├── user/
│   ├── User.java
│   ├── Admin.java
│   ├── Customer.java
│
├── order/
│   ├── Order.java
│   ├── OrderItem.java
│   ├── OrderStatus.java
│   ├── OrderService.java
│   ├── OrderHistory.java
│
├── exception/
│   ├── InsufficientStockException.java
│   ├── ProductNotFoundException.java
│   ├── InvalidOrderException.java
│   ├── UnauthorizedActionException.java
│
├── notification/
│   ├── Notification.java
│   ├── NotificationService.java
│
├── log/
│   ├── LoggerService.java
│
├── file/
│   ├── FileManager.java
│
└── Main.java
```

---

# 🔹 cli Package

Handles all console interaction. No business logic here.

---

## 1️⃣ ConsoleUI

**Purpose:**
Controls the entire CLI workflow and connects user input to services.

### Fields

* `Scanner scanner`
  → Used to capture user input.

* `InventoryService inventoryService`
  → Handles inventory-related operations.

* `OrderService orderService`
  → Handles order-related operations.

* `User currentUser`
  → Stores the logged-in user.

---

### Methods

* `start()`
  → Entry point of CLI system.

* `showLoginMenu()`
  → Displays login options (Admin/Customer).

* `showAdminMenu()`
  → Displays admin options.

* `showCustomerMenu()`
  → Displays customer options.

* `handleUserInput()`
  → Routes input to correct service methods.

---

## 2️⃣ Menu

**Purpose:**
Contains reusable static menu-printing methods.

### Methods

* `displayMainMenu()`
* `displayAdminOptions()`
* `displayCustomerOptions()`

No logic. Only UI formatting.

---

# 🔹 product Package

---

## 1️⃣ Product (Base Class)

### Fields

* `id (String)`
  → Unique identifier. Must not be null or empty.

* `name (String)`
  → Product name. Required field.

* `price (double)`
  → Must be greater than 0.

* `quantity (int)`
  → Cannot be negative.

---

### Methods

* `increaseStock(int amount)`
  → Adds stock. Amount must be positive.

* `decreaseStock(int amount)`
  → Reduces stock. Cannot go below zero.

* `getProductDetails()`
  → Returns formatted product info.

---

## 2️⃣ PerishableProduct

### Additional Field

* `expirationDate (LocalDate)`
  → Must not be null. Used to validate expiration.

---

### Methods

* `isExpired()`
  → Returns true if expiration date is before today.

* `getProductDetails()`
  → Includes expiration date in output.

---

## 3️⃣ NonPerishableProduct

### Additional Field

* `warrantyMonths (int)`
  → Must be zero or positive.

---

### Methods

* `getProductDetails()`
  → Includes warranty info.

---

# 🔹 inventory Package

---

## 1️⃣ Inventory

**Purpose:** Stores product collection.

### Fields

* `products (List<Product>)`
  → Central in-memory product storage.

---

### Methods

* `addProduct(Product product)`
  → Adds product if ID does not exist.

* `removeProduct(String productId)`
  → Removes product if exists.

* `getProductById(String productId)`
  → Returns product or throws exception.

* `getAllProducts()`
  → Returns list of products.

---

## 2️⃣ InventoryService

**Purpose:** Business logic layer for inventory.

### Fields

* `Inventory inventory`
* `FileManager fileManager`
* `LoggerService loggerService`
* `InventoryHistory inventoryHistory`
* `NotificationService notificationService`

---

### Methods

* `addProduct(Product product)`
  → Validates and saves product.

* `updateStock(String productId, int amount)`
  → Validates and updates stock.

* `checkLowStock(Product product)`
  → Triggers notification if stock < 5.

* `loadInventory()`
  → Loads products from file.

* `saveInventory()`
  → Saves products to file.

---

## 3️⃣ InventoryHistory

### Methods

* `recordStockIncrease(String productId, int amount)`
* `recordStockDecrease(String productId, int amount)`
* `recordProductRemoval(String productId)`

Appends entries to `inventory_history.txt`.

---

# 🔹 user Package

---

## 1️⃣ User (Abstract)

### Fields

* `id`
* `name`
* `email`
* `password` (for simple authentication)

---

### Methods

* `login(String email, String password)`
  → Validates credentials.

* `viewProducts()`

* `performRoleAction()` (abstract)

---

## 2️⃣ Admin

### Methods

* `addProduct(Product product)`
* `updateStock(String productId, int amount)`
* `viewAllOrders()`
* `viewLogs()`
* `viewInventoryHistory()`

---

## 3️⃣ Customer

### Fields

* `orders (List<Order>)`
  → Stores personal order history.

---

### Methods

* `placeOrder(Order order)`
* `cancelOrder(String orderId)`
* `viewOrderHistory()`

---

# 🔹 order Package

---

## 1️⃣ Order

### Fields

* `orderId (String)`
* `customer (Customer)`
* `items (List<OrderItem>)`
* `totalAmount (double)`
* `orderStatus (OrderStatus)`
* `createdAt (LocalDateTime)`

---

### Methods

* `addItem(Product product, int quantity)`
* `calculateTotal()`
* `changeOrderStatus(OrderStatus newStatus)`
* `validateOrder()`

---

## 2️⃣ OrderItem

### Fields

* `product`
* `quantity`
* `subTotal`

---

### Methods

* `calculateSubTotal()`
* `getItemDetails()`

---

## 3️⃣ OrderStatus (Enum)

Values:

* `PENDING`
* `CONFIRMED`
* `SHIPPED`
* `DELIVERED`
* `CANCELLED`

---

## 4️⃣ OrderService

### Fields

* `FileManager fileManager`
* `LoggerService loggerService`
* `OrderHistory orderHistory`
* `InventoryService inventoryService`

---

### Methods

* `createOrder(Customer customer)`
* `addItemToOrder(Order order, Product product, int quantity)`
* `finalizeOrder(Order order)`
* `cancelOrder(String orderId)`
* `loadOrders()`
* `saveOrders()`

---

## 5️⃣ OrderHistory

### Methods

* `recordOrderCreation(String orderId)`
* `recordStatusChange(String orderId, OrderStatus status)`
* `recordCancellation(String orderId)`

---

# 🔹 exception Package

All extend `RuntimeException`.

* `InsufficientStockException`
* `ProductNotFoundException`
* `InvalidOrderException`
* `UnauthorizedActionException`

Each must:

* Accept custom message
* Be thrown only from service layer

---

# 🔹 notification Package

---

## 1️⃣ Notification

### Fields

* `message`
* `timestamp`

---

## 2️⃣ NotificationService

### Methods

* `notify(String message)`
* `notifyLowStock(Product product)`
* `notifyOrderStatusChange(Order order)`

Outputs to console.

---

# 🔹 log Package

---

## LoggerService

### Methods

* `logInfo(String message)`
* `logError(String message)`
* `logWarning(String message)`

Writes to `logs.txt`.

---

# 🔹 file Package

---

## FileManager

### Responsibilities

* Handle all file read/write operations.

### Methods

* `saveProducts(List<Product>)`
* `loadProducts()`
* `saveOrders(List<Order>)`
* `loadOrders()`
* `appendToFile(String filename, String content)`

No business logic here.

---

# 🔄 Main.java

**Purpose:**
Initializes services and starts ConsoleUI.

---

# 🔥 Now This Project Demonstrates:

* Layered Architecture
* Clean Separation of Concerns
* Exception-Driven Design
* File-Based Persistence
* Logging & History Systems
* Role-Based Access
* CLI Application Structure

---
