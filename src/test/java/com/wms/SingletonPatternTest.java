package com.wms;

import com.wms.repository.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Singleton Pattern (DatabaseConnection).
 * Verifies that only one instance of the database connection exists.
 */
class SingletonPatternTest {

    @Test
    @DisplayName("TC-PAT-001: Singleton returns the same instance every time")
    void testSingletonReturnsSameInstance() {
        // Get two references to the database connection
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();

        // Both references must point to the exact same object
        assertSame(instance1, instance2,
                "Singleton pattern failed: two different instances were created");
    }

    @Test
    @DisplayName("Singleton instance is not null")
    void testSingletonNotNull() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        assertNotNull(instance, "DatabaseConnection instance should not be null");
    }

    @Test
    @DisplayName("Singleton contains pre-loaded sample data")
    void testSingletonContainsSampleData() {
        DatabaseConnection db = DatabaseConnection.getInstance();

        assertFalse(db.getUsersTable().isEmpty(), "Users table should have sample data");
        assertFalse(db.getInventoryTable().isEmpty(), "Inventory table should have sample data");
        assertFalse(db.getOrdersTable().isEmpty(), "Orders table should have sample data");
        assertFalse(db.getClientsTable().isEmpty(), "Clients table should have sample data");
    }
}
