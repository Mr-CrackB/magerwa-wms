# SOFTWARE TEST PLAN
## Warehouse Management System — Magerwa Ltd, Rwanda
### SENG 8240 — Best Programming Practices and Design Patterns | Phase 4

---

## 1. INTRODUCTION

### 1.1 Purpose
This test plan defines the testing strategy, objectives, scope, and test cases
for the Warehouse Management System (WMS) developed for Magerwa Ltd. It ensures
the application meets all functional requirements and operates reliably before
deployment.

### 1.2 Project Overview
The WMS is a Java Spring Boot web application that manages inventory tracking,
order processing, client management, and report generation for Magerwa Ltd's
warehouse operations across Rwanda (Gikondo, Rusumo, Rusizi, and Kigali Airport).

### 1.3 Document Scope
This test plan covers:
- Unit testing of individual classes and methods
- Integration testing of service and controller layers
- Functional testing of all user-facing features
- User acceptance testing of complete workflows

---

## 2. TESTING GOALS AND OBJECTIVES

| # | Goal | Alignment with System Requirements |
|---|------|-------------------------------------|
| 1 | Verify user authentication works correctly | Login/logout functionality is the entry point of the system |
| 2 | Ensure inventory CRUD operations function properly | Core requirement: digitize inventory tracking |
| 3 | Validate order creation, status updates, and filtering | Core requirement: automate order processing |
| 4 | Confirm client registration and management works | Core requirement: manage warehouse clients |
| 5 | Test report generation produces correct output | Core requirement: generate compliance reports |
| 6 | Verify design patterns are correctly implemented | Singleton, Factory, Observer must work as designed |
| 7 | Validate navigation and user journey flow | All pages must link correctly: Login → Dashboard → Modules → Logout |
| 8 | Ensure data consistency across operations | Adding/deleting items must reflect on dashboard statistics |

---

## 3. FEATURES TO BE TESTED

### 3.1 Authentication Module
- User login with valid credentials
- User login with invalid credentials (error handling)
- Session management (redirect to login if not authenticated)
- Logout functionality

### 3.2 Dashboard Module
- Display of correct statistics (total products, units, pending orders, alerts)
- Recent orders table populated with current data
- Navigation links to all modules

### 3.3 Inventory Management Module
- View all inventory items in a table
- Add a new inventory item via form
- Search/filter inventory by product name
- Delete an inventory item
- Status auto-assignment (In Stock / Low Stock / Out of Stock)

### 3.4 Order Management Module
- View all orders
- Create a new order via form
- Filter orders by status (Pending, Processing, Completed, Cancelled)
- Update order status
- Delete an order

### 3.5 Client Management Module
- View all registered clients
- Register a new client via form
- Delete a client

### 3.6 Report Generation Module (Factory Pattern)
- Generate Inventory Report
- Generate Order Report
- Generate Low Stock Report
- Verify Factory Pattern creates correct report type

### 3.7 Design Pattern Verification
- Singleton: Only one DatabaseConnection instance exists
- Factory: Correct report type created for each selection
- Observer: Stock alerts triggered when low stock detected
- DAO: CRUD operations work through DAO interface

---

## 4. TEST CASES

### 4.1 Authentication Test Cases

| Test ID | Test Case | Steps | Input | Expected Result | Status |
|---------|-----------|-------|-------|-----------------|--------|
| TC-AUTH-001 | Valid login | 1. Open localhost:8080 2. Enter credentials 3. Click Sign In | username: admin, password: admin123 | Redirect to Dashboard, welcome message shows "Jean Pierre Rutarindwa" | ✅ Pass |
| TC-AUTH-002 | Invalid password | 1. Open login page 2. Enter wrong password 3. Click Sign In | username: admin, password: wrongpass | Error message "Invalid username or password" displayed, stays on login page | ✅ Pass |
| TC-AUTH-003 | Empty fields | 1. Open login page 2. Leave fields empty 3. Click Sign In | username: (empty), password: (empty) | Browser shows required field validation, form not submitted | ✅ Pass |
| TC-AUTH-004 | Logout | 1. Login as admin 2. Click Logout in sidebar | N/A | Session destroyed, redirected to login page | ✅ Pass |
| TC-AUTH-005 | Session protection | 1. Without logging in 2. Navigate to /dashboard directly | URL: localhost:8080/dashboard | Redirected to login page | ✅ Pass |

### 4.2 Inventory Test Cases

| Test ID | Test Case | Steps | Input | Expected Result | Status |
|---------|-----------|-------|-------|-----------------|--------|
| TC-INV-001 | View inventory list | 1. Login 2. Click Inventory | N/A | Table displays all 8 inventory items with correct data | ✅ Pass |
| TC-INV-002 | Add new item (normal) | 1. Go to Inventory 2. Fill add form 3. Click Add Item | name: "Cooking Oil", category: "Food & Beverages", qty: 50, location: "Gikondo - Zone B, Bay 3" | New item appears in table with status "In Stock" | ✅ Pass |
| TC-INV-003 | Add item (low stock) | 1. Fill add form with qty < 10 | name: "Sugar Bags", qty: 5 | Item added with status "Low Stock" (yellow badge) | ✅ Pass |
| TC-INV-004 | Add item (zero stock) | 1. Fill add form with qty = 0 | name: "Empty Product", qty: 0 | Item added with status "Out of Stock" (red badge) | ✅ Pass |
| TC-INV-005 | Search inventory | 1. Type "Cement" in search 2. Click Search | search: "Cement" | Only "Cement Bags (50kg)" shown in table | ✅ Pass |
| TC-INV-006 | Search no results | 1. Search for non-existent product | search: "xyz123" | Table shows "No items found" message | ✅ Pass |
| TC-INV-007 | Delete item | 1. Click Delete on an item 2. Confirm deletion | Item ID: INV005 | Item removed from table, no longer visible | ✅ Pass |
| TC-INV-008 | Clear search | 1. Search for something 2. Click Clear | N/A | Full inventory list restored | ✅ Pass |

### 4.3 Order Test Cases

| Test ID | Test Case | Steps | Input | Expected Result | Status |
|---------|-----------|-------|-------|-----------------|--------|
| TC-ORD-001 | View all orders | 1. Login 2. Click Orders | N/A | Table displays all 5 orders | ✅ Pass |
| TC-ORD-002 | Create new order | 1. Fill order form 2. Click Create Order | client: "Test Corp", product: "Steel Bars", qty: 10, type: Outbound | New order appears with status "Pending" | ✅ Pass |
| TC-ORD-003 | Filter by Pending | 1. Select "Pending" from dropdown 2. Click Filter | status: Pending | Only Pending orders shown | ✅ Pass |
| TC-ORD-004 | Filter by Completed | 1. Select "Completed" 2. Click Filter | status: Completed | Only Completed orders shown | ✅ Pass |
| TC-ORD-005 | Update order status | 1. Select "Completed" for ORD003 2. Click Go | orderId: ORD003, newStatus: Completed | ORD003 status changes to "Completed" (green badge) | ✅ Pass |
| TC-ORD-006 | Delete order | 1. Click Del on an order | orderId: ORD001 | Order removed from table | ✅ Pass |

### 4.4 Client Test Cases

| Test ID | Test Case | Steps | Input | Expected Result | Status |
|---------|-----------|-------|-------|-----------------|--------|
| TC-CLI-001 | View clients | 1. Login 2. Click Clients | N/A | Table displays all 5 clients | ✅ Pass |
| TC-CLI-002 | Register new client | 1. Fill registration form 2. Click Register | company: "New Corp Ltd", contact: "John", email: "john@newcorp.rw", phone: "+250 788 999 000", address: "Kigali" | New client appears in table | ✅ Pass |
| TC-CLI-003 | Delete client | 1. Click Delete on a client | clientId: C005 | Client removed from table | ✅ Pass |

### 4.5 Report Test Cases (Factory Pattern)

| Test ID | Test Case | Steps | Input | Expected Result | Status |
|---------|-----------|-------|-------|-----------------|--------|
| TC-RPT-001 | Generate Inventory Report | 1. Go to Reports 2. Click Inventory Report | reportType: "Inventory Report" | Report output shows all items with totals, report ID generated | ✅ Pass |
| TC-RPT-002 | Generate Order Report | 1. Click Order Report button | reportType: "Order Report" | Report shows all orders with Pending/Processing/Completed counts | ✅ Pass |
| TC-RPT-003 | Generate Low Stock Report | 1. Click Low Stock Report button | reportType: "Low Stock Report" | Report shows only items with Low Stock or Out of Stock status | ✅ Pass |
| TC-RPT-004 | Factory creates correct type | 1. Generate each report type | All 3 types | Each report has different format and content matching its type | ✅ Pass |

### 4.6 Design Pattern Test Cases

| Test ID | Test Case | Steps | Expected Result | Status |
|---------|-----------|-------|-----------------|--------|
| TC-PAT-001 | Singleton - single instance | Call DatabaseConnection.getInstance() twice | Both calls return the same object reference | ✅ Pass |
| TC-PAT-002 | Factory - Inventory Report | Call ReportFactory.createReport("Inventory Report", "admin") | Returns content containing "WAREHOUSE INVENTORY REPORT" | ✅ Pass |
| TC-PAT-003 | Factory - invalid type | Call ReportFactory.createReport("Unknown", "admin") | Throws IllegalArgumentException | ✅ Pass |
| TC-PAT-004 | Observer - low stock alert | Add item with qty=3, check alerts | StockAlertObserver.getAlertLog() contains low stock message | ✅ Pass |
| TC-PAT-005 | DAO - CRUD operations | Save, findAll, findById, update, delete on InventoryDAO | All operations execute correctly | ✅ Pass |

### 4.7 Navigation & User Journey Test Cases

| Test ID | Test Case | Steps | Expected Result | Status |
|---------|-----------|-------|-----------------|--------|
| TC-NAV-001 | Full user journey | Login → Dashboard → Inventory → Orders → Clients → Reports → Logout | All pages load correctly, sidebar highlights active page | ✅ Pass |
| TC-NAV-002 | Dashboard stats accuracy | Add an inventory item, return to Dashboard | Total Products count increases by 1 | ✅ Pass |
| TC-NAV-003 | Sidebar navigation | Click each sidebar link | Correct page loads, active link highlighted | ✅ Pass |

---

## 5. TESTING TOOLS AND METHODS

### 5.1 Testing Tools

| Tool | Purpose | Usage in This Project |
|------|---------|----------------------|
| **JUnit 5** | Unit testing framework for Java | Testing model classes, services, DAOs, and design patterns |
| **Spring Boot Test** | Integration testing for Spring MVC | Testing controllers and HTTP request/response |
| **Browser Testing** | Manual functional testing | Verifying UI, forms, navigation, and visual elements |
| **Git** | Version control for tracking changes | Track test results, code changes, and bug fixes |

### 5.2 Issue Tracking Method

Issues discovered during testing are tracked using a structured log:

| Issue ID | Date | Severity | Description | Module | Status | Resolution |
|----------|------|----------|-------------|--------|--------|------------|
| BUG-001 | 2026-04-15 | Medium | Search not clearing when navigating away | Inventory | Fixed | Added "Clear" button to reset search filter |
| BUG-002 | 2026-04-16 | Low | Status badge color not showing for "Cancelled" | Orders | Fixed | Added CSS class for cancelled status (badge-red) |
| BUG-003 | 2026-04-17 | High | Unauthenticated users could access /dashboard | All | Fixed | Added session check in every controller method |

### 5.3 Test Execution Summary

| Category | Total Tests | Passed | Failed | Pass Rate |
|----------|-------------|--------|--------|-----------|
| Authentication | 5 | 5 | 0 | 100% |
| Inventory | 8 | 8 | 0 | 100% |
| Orders | 6 | 6 | 0 | 100% |
| Clients | 3 | 3 | 0 | 100% |
| Reports | 4 | 4 | 0 | 100% |
| Design Patterns | 5 | 5 | 0 | 100% |
| Navigation | 3 | 3 | 0 | 100% |
| **TOTAL** | **34** | **34** | **0** | **100%** |

---

## 6. CONCLUSION

All 34 test cases passed successfully. The Warehouse Management System meets
its functional requirements for inventory management, order processing, client
management, and report generation. All five design patterns (Singleton, Factory,
Observer, DAO, MVC) are verified to function correctly. The application is ready
for deployment.

---

**Prepared by:** Prince
**Course:** SENG 8240 — Best Programming Practices & Design Patterns
**Institution:** Adventist University of Central Africa
**Date:** April 2026
