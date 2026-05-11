package com.wms.repository;

import com.wms.model.*;
import com.wms.model.User.UserRole;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DESIGN PATTERN: Singleton Pattern
 * 
 * Ensures only one database connection instance exists throughout
 * the application lifecycle. Uses double-checked locking for thread safety.
 * 
 * In production, this would manage a real JDBC connection to MySQL/PostgreSQL.
 * For this prototype, ArrayList collections simulate database tables.
 * 
 * Implementation:
 * - Private constructor prevents external instantiation
 * - Static getInstance() provides the single global access point
 * - volatile + synchronized ensures thread-safe lazy initialization
 */
public class DatabaseConnection {

        // The single instance (volatile for thread safety)
        private static volatile DatabaseConnection instance;

        // Simulated database tables
        private final List<User> usersTable;
        private final List<InventoryItem> inventoryTable;
        private final List<Order> ordersTable;
        private final List<Client> clientsTable;

        /** Private constructor — prevents direct instantiation (Singleton) */
        private DatabaseConnection() {
                this.usersTable = new ArrayList<>();
                this.inventoryTable = new ArrayList<>();
                this.ordersTable = new ArrayList<>();
                this.clientsTable = new ArrayList<>();
                initializeSampleData();
                System.out.println("[Singleton] Database connection created");
        }

        /**
         * Returns the single instance of DatabaseConnection.
         * Thread-safe with double-checked locking.
         */
        public static DatabaseConnection getInstance() {
                if (instance == null) {
                        synchronized (DatabaseConnection.class) {
                                if (instance == null) {
                                        instance = new DatabaseConnection();
                                }
                        }
                }
                return instance;
        }

        // Table accessors
        public List<User> getUsersTable() {
                return usersTable;
        }

        public List<InventoryItem> getInventoryTable() {
                return inventoryTable;
        }

        public List<Order> getOrdersTable() {
                return ordersTable;
        }

        public List<Client> getClientsTable() {
                return clientsTable;
        }

        /** Populates tables with realistic Magerwa sample data */
        private void initializeSampleData() {
                // Users
                usersTable.add(new User("U001", "admin", "admin123",
                                "Blaise Byiringiro", "admin@magerwa.rw", UserRole.ADMIN));
                usersTable.add(new User("U002", "manager", "mgr123",
                                "Alice Uwimana", "alice@magerwa.rw", UserRole.MANAGER));
                usersTable.add(new User("U003", "staff1", "staff123",
                                "Eric Habimana", "eric@magerwa.rw", UserRole.STAFF));
                usersTable.add(new User("U004", "mcd_client", "mcd123",
                                "MCD Corporation Ltd", "patrick@mcd-corp.rw", UserRole.CLIENT));
                usersTable.add(new User("U005", "inyange_client", "inyange123",
                                "Inyange Industries", "grace@inyange.rw", UserRole.CLIENT));

                // Inventory
                inventoryTable.add(new InventoryItem("INV001", "Cement Bags (50kg)", "Construction",
                                150, "Gikondo - Zone A, Bay 1", LocalDate.of(2026, 3, 10), "In Stock"));
                inventoryTable.add(new InventoryItem("INV002", "Steel Bars Bundle", "Construction",
                                45, "Gikondo - Zone A, Bay 3", LocalDate.of(2026, 3, 15), "In Stock"));
                inventoryTable.add(new InventoryItem("INV003", "Rice Bags (25kg)", "Food & Beverages",
                                8, "Gikondo - Zone B, Bay 1", LocalDate.of(2026, 2, 28), "Low Stock"));
                inventoryTable.add(new InventoryItem("INV004", "Electronic Equipment", "Electronics",
                                200, "Airport - Zone C, Bay 2", LocalDate.of(2026, 4, 1), "In Stock"));
                inventoryTable.add(new InventoryItem("INV005", "Textile Rolls", "Textiles",
                                0, "Gikondo - Zone B, Bay 4", LocalDate.of(2026, 1, 20), "Out of Stock"));
                inventoryTable.add(new InventoryItem("INV006", "Pharmaceutical Supplies", "Healthcare",
                                75, "Airport - Zone C, Bay 1", LocalDate.of(2026, 4, 5), "In Stock"));
                inventoryTable.add(new InventoryItem("INV007", "Fertilizer Bags", "Agriculture",
                                5, "Rusizi - Zone A, Bay 2", LocalDate.of(2026, 3, 22), "Low Stock"));
                inventoryTable.add(new InventoryItem("INV008", "Vehicle Spare Parts", "Automotive",
                                120, "Gikondo - Zone D, Bay 1", LocalDate.of(2026, 4, 8), "In Stock"));

                // Orders
                ordersTable.add(new Order("ORD001", "MCD Corporation", "Cement Bags (50kg)",
                                50, LocalDate.of(2026, 4, 1), "Completed", "Outbound"));
                ordersTable.add(new Order("ORD002", "Inyange Industries", "Rice Bags (25kg)",
                                100, LocalDate.of(2026, 4, 5), "Processing", "Inbound"));
                ordersTable.add(new Order("ORD003", "Rwanda Trading Co.", "Steel Bars Bundle",
                                20, LocalDate.of(2026, 4, 8), "Pending", "Outbound"));
                ordersTable.add(new Order("ORD004", "Horizon Pharma", "Pharmaceutical Supplies",
                                30, LocalDate.of(2026, 4, 10), "Pending", "Inbound"));
                ordersTable.add(new Order("ORD005", "BK TecHouse", "Electronic Equipment",
                                15, LocalDate.of(2026, 4, 12), "Processing", "Outbound"));

                // Clients
                clientsTable.add(new Client("C001", "MCD Corporation Ltd",
                                "Patrick Ndayisaba", "patrick@mcd-corp.rw", "+250 788 123 456", "Rusizi, Rwanda"));
                clientsTable.add(new Client("C002", "Inyange Industries",
                                "Grace Mukamana", "grace@inyange.rw", "+250 788 234 567", "Kigali, Rwanda"));
                clientsTable.add(new Client("C003", "Rwanda Trading Company",
                                "Jean Claude Nkusi", "jc@rwandatrading.rw", "+250 788 345 678", "Kigali, Rwanda"));
                clientsTable.add(new Client("C004", "Horizon Pharmaceuticals",
                                "Marie Claire Iradukunda", "mc@horizonpharma.rw", "+250 788 456 789", "Huye, Rwanda"));
                clientsTable.add(new Client("C005", "BK TecHouse",
                                "Emmanuel Kamanzi", "emmanuel@bktechouse.rw", "+250 788 567 890", "Kigali, Rwanda"));
        }
}
