package com.wms;

import com.wms.model.InventoryItem;
import com.wms.model.User;
import com.wms.repository.UserDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Authentication and Model classes.
 */
class AuthenticationAndModelTest {

    // ── Authentication Tests ───────────────────────────

    @Test
    @DisplayName("TC-AUTH-001: Valid login returns correct user")
    void testValidLogin() {
        UserDAO userDAO = new UserDAO();
        Optional<User> user = userDAO.authenticate("admin", "admin123");

        assertTrue(user.isPresent(), "Valid credentials should return a user");
        assertEquals("Jean Pierre Rutarindwa", user.get().getFullName());
        assertEquals(User.UserRole.ADMIN, user.get().getRole());
    }

    @Test
    @DisplayName("TC-AUTH-002: Invalid password returns empty")
    void testInvalidPassword() {
        UserDAO userDAO = new UserDAO();
        Optional<User> user = userDAO.authenticate("admin", "wrongpassword");

        assertTrue(user.isEmpty(), "Invalid password should return empty");
    }

    @Test
    @DisplayName("TC-AUTH-003: Non-existent username returns empty")
    void testNonExistentUser() {
        UserDAO userDAO = new UserDAO();
        Optional<User> user = userDAO.authenticate("nonexistent", "pass");

        assertTrue(user.isEmpty(), "Non-existent user should return empty");
    }

    @Test
    @DisplayName("Manager login returns Manager role")
    void testManagerLogin() {
        UserDAO userDAO = new UserDAO();
        Optional<User> user = userDAO.authenticate("manager", "mgr123");

        assertTrue(user.isPresent());
        assertEquals(User.UserRole.MANAGER, user.get().getRole());
    }

    // ── InventoryItem Model Tests ──────────────────────

    @Test
    @DisplayName("TC-INV-003: updateQuantity sets Low Stock when qty < 10")
    void testUpdateQuantityLowStock() {
        InventoryItem item = new InventoryItem("TEST", "Test", "Cat",
                50, "Zone A", null, "In Stock");

        item.updateQuantity(5);

        assertEquals(5, item.getQuantity());
        assertEquals("Low Stock", item.getStatus(),
                "Quantity below 10 should set status to Low Stock");
    }

    @Test
    @DisplayName("TC-INV-004: updateQuantity sets Out of Stock when qty = 0")
    void testUpdateQuantityOutOfStock() {
        InventoryItem item = new InventoryItem("TEST", "Test", "Cat",
                50, "Zone A", null, "In Stock");

        item.updateQuantity(0);

        assertEquals(0, item.getQuantity());
        assertEquals("Out of Stock", item.getStatus(),
                "Zero quantity should set status to Out of Stock");
    }

    @Test
    @DisplayName("updateQuantity sets In Stock when qty >= 10")
    void testUpdateQuantityInStock() {
        InventoryItem item = new InventoryItem("TEST", "Test", "Cat",
                5, "Zone A", null, "Low Stock");

        item.updateQuantity(50);

        assertEquals(50, item.getQuantity());
        assertEquals("In Stock", item.getStatus(),
                "Quantity >= 10 should set status to In Stock");
    }
}
