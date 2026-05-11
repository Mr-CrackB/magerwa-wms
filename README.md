# Warehouse Management System (WMS)
## Case Study: Magerwa Ltd, Rwanda

### SENG 8240 — Best Programming Practices and Design Patterns
**Adventist University of Central Africa | Phase 2: Software Prototype**

---

## How to Run

### Prerequisites
- **Java JDK 17+** — Download from https://www.oracle.com/java/technologies/downloads/
- **Maven** — Download from https://maven.apache.org/download.cgi

### Steps
```bash
# 1. Unzip the project and enter the folder
cd WMS

# 2. Run with Maven (downloads dependencies automatically)
mvn spring-boot:run

# 3. Open your browser
# Go to: http://localhost:8080
```

### Demo Login Credentials
| Username  | Password  | Role                |
|-----------|-----------|---------------------|
| admin     | admin123  | System Administrator|
| manager   | mgr123   | Warehouse Manager   |
| staff1    | staff123  | Warehouse Staff     |

---

## Technology Stack

| Component       | Technology              |
|-----------------|-------------------------|
| Language        | Java 17                 |
| Framework       | Spring Boot 3.2         |
| Template Engine | Thymeleaf (HTML)        |
| Styling         | CSS3                    |
| Build Tool      | Maven                   |
| Architecture    | MVC (Spring MVC)        |
| Database        | In-memory (simulated)   |

---

## Design Patterns Used

### 1. Singleton Pattern — `DatabaseConnection.java`
**Location:** `com.wms.repository.DatabaseConnection`

Ensures only one database connection exists. Uses double-checked locking.
```java
public static DatabaseConnection getInstance() {
    if (instance == null) {
        synchronized (DatabaseConnection.class) {
            if (instance == null) { instance = new DatabaseConnection(); }
        }
    }
    return instance;
}
```

### 2. Factory Method Pattern — `ReportFactory.java`
**Location:** `com.wms.factory.ReportFactory`

Creates different report types without exposing creation logic.
```java
public static String createReport(String reportType, String generatedBy) {
    switch (reportType) {
        case TYPE_INVENTORY: return generateInventoryReport(...);
        case TYPE_ORDER:     return generateOrderReport(...);
        case TYPE_LOW_STOCK: return generateLowStockReport(...);
        default: throw new IllegalArgumentException("Unknown type");
    }
}
```

### 3. Observer Pattern — `observer/` package
**Location:** `com.wms.observer.*`

Monitors inventory changes and generates alerts automatically.
- `InventoryObserver` — interface for observers
- `InventorySubject` — maintains and notifies observers
- `StockAlertObserver` — generates low-stock alerts

### 4. DAO Pattern — `repository/` package
**Location:** `com.wms.repository.*`

Separates data access from business logic.
- `GenericDAO<T>` — interface with CRUD methods
- `InventoryDAO`, `OrderDAO`, `ClientDAO`, `UserDAO` — implementations

### 5. MVC Pattern — Spring Boot Architecture
- **Model:** `com.wms.model.*` — POJOs
- **View:** `templates/*.html` — Thymeleaf pages
- **Controller:** `com.wms.controller.*` — Spring MVC controllers

---

## Project Structure

```
WMS/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/wms/
    │   ├── WmsApplication.java              # Spring Boot entry point
    │   ├── model/                           # Data models (POJOs)
    │   │   ├── User.java
    │   │   ├── InventoryItem.java
    │   │   ├── Order.java
    │   │   └── Client.java
    │   ├── repository/                      # ★ SINGLETON + DAO PATTERNS
    │   │   ├── DatabaseConnection.java      # Singleton
    │   │   ├── GenericDAO.java              # DAO interface
    │   │   ├── InventoryDAO.java
    │   │   ├── OrderDAO.java
    │   │   ├── ClientDAO.java
    │   │   └── UserDAO.java
    │   ├── factory/                         # ★ FACTORY METHOD PATTERN
    │   │   └── ReportFactory.java
    │   ├── observer/                        # ★ OBSERVER PATTERN
    │   │   ├── InventoryObserver.java
    │   │   ├── InventorySubject.java
    │   │   └── StockAlertObserver.java
    │   ├── service/                         # Business logic layer
    │   │   ├── AuthenticationService.java
    │   │   └── InventoryService.java
    │   └── controller/                      # ★ MVC PATTERN (Controllers)
    │       ├── LoginController.java
    │       ├── DashboardController.java
    │       ├── InventoryController.java
    │       ├── OrderController.java
    │       ├── ClientController.java
    │       └── ReportController.java
    └── resources/
        ├── application.properties
        ├── static/css/style.css             # Styling
        └── templates/                       # ★ MVC PATTERN (Views)
            ├── login.html
            ├── dashboard.html
            ├── inventory.html
            ├── orders.html
            ├── clients.html
            └── reports.html
```

## User Journey
```
Login → Dashboard → Inventory (add/search/delete)
                  → Orders (add/filter/update status)
                  → Clients (add/delete)
                  → Reports (Factory Pattern generates reports)
                  → Logout → Login
```

## Best Practices Applied
1. **Meaningful names** — `findLowStockItems()`, `AuthenticationService`, `InventoryDAO`
2. **Single Responsibility** — each class handles one concern
3. **Proper indentation** — consistent 4-space formatting
4. **Comments** — Javadoc on classes, inline on key logic
5. **Code organization** — packages by function (model, repository, service, controller)
6. **No unnecessary complexity** — clean, readable code throughout

---
**Author:** Blaise | **Course:** SENG 8240 | **Institution:** AUCA | **Case Study:** Magerwa Ltd
