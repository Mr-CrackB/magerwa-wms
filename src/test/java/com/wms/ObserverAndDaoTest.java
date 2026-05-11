package com.wms;

import com.wms.model.InventoryItem;
import com.wms.observer.InventorySubject;
import com.wms.observer.StockAlertObserver;
import com.wms.repository.InventoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Observer Pattern and DAO Pattern.
 */
class ObserverAndDaoTest {

    private InventorySubject subject;
    private StockAlertObserver alertObserver;

    @BeforeEach
    void setUp() {
        subject = new InventorySubject();
        alertObserver = new StockAlertObserver();
        subject.addObserver(alertObserver);
    }

    // ── Observer Pattern Tests ─────────────────────────

    @Test
    @DisplayName("TC-PAT-004: Observer detects low stock and generates alert")
    void testObserverLowStockAlert() {
        // Simulate a stock change that triggers a low-stock alert
        subject.notifyObservers("Rice Bags", 50, 5);

        List<String> alerts = alertObserver.getAlertLog();
        assertFalse(alerts.isEmpty(), "Alert log should not be empty after low stock");
        assertTrue(alerts.get(0).contains("LOW STOCK"),
                "Alert should contain LOW STOCK message");
        assertTrue(alerts.get(0).contains("Rice Bags"),
                "Alert should mention the product name");
    }

    @Test
    @DisplayName("Observer detects out-of-stock and generates alert")
    void testObserverOutOfStockAlert() {
        subject.notifyObservers("Textile Rolls", 10, 0);

        List<String> alerts = alertObserver.getAlertLog();
        assertFalse(alerts.isEmpty());
        assertTrue(alerts.get(0).contains("OUT OF STOCK"),
                "Alert should contain OUT OF STOCK message");
    }

    @Test
    @DisplayName("Observer does NOT alert when stock is adequate")
    void testObserverNoAlertForAdequateStock() {
        alertObserver.clearAlerts();
        subject.notifyObservers("Cement Bags", 100, 80);

        assertTrue(alertObserver.getAlertLog().isEmpty(),
                "No alert should be generated when stock is above threshold");
    }

    // ── DAO Pattern Tests ──────────────────────────────

    @Test
    @DisplayName("TC-PAT-005: DAO findAll returns inventory items")
    void testDaoFindAll() {
        InventoryDAO dao = new InventoryDAO();
        List<InventoryItem> items = dao.findAll();

        assertNotNull(items, "findAll should not return null");
        assertFalse(items.isEmpty(), "Inventory should contain sample data");
    }

    @Test
    @DisplayName("DAO findById returns correct item")
    void testDaoFindById() {
        InventoryDAO dao = new InventoryDAO();
        Optional<InventoryItem> item = dao.findById("INV001");

        assertTrue(item.isPresent(), "INV001 should exist in inventory");
        assertEquals("Cement Bags (50kg)", item.get().getProductName(),
                "INV001 should be Cement Bags");
    }

    @Test
    @DisplayName("DAO findById returns empty for non-existent ID")
    void testDaoFindByIdNotFound() {
        InventoryDAO dao = new InventoryDAO();
        Optional<InventoryItem> item = dao.findById("INVALID_ID");

        assertTrue(item.isEmpty(), "Non-existent ID should return empty Optional");
    }

    @Test
    @DisplayName("DAO save adds a new item")
    void testDaoSave() {
        InventoryDAO dao = new InventoryDAO();
        int sizeBefore = dao.findAll().size();

        InventoryItem newItem = new InventoryItem("INV_TEST", "Test Product",
                "Test", 25, "Zone X", LocalDate.now(), "In Stock");
        dao.save(newItem);

        assertEquals(sizeBefore + 1, dao.findAll().size(),
                "Item count should increase by 1 after save");

        // Clean up
        dao.delete("INV_TEST");
    }

    @Test
    @DisplayName("DAO delete removes an item")
    void testDaoDelete() {
        InventoryDAO dao = new InventoryDAO();

        // Add and then delete
        InventoryItem temp = new InventoryItem("INV_DEL", "Delete Me",
                "Test", 1, "Zone X", LocalDate.now(), "In Stock");
        dao.save(temp);
        int sizeAfterAdd = dao.findAll().size();

        dao.delete("INV_DEL");

        assertEquals(sizeAfterAdd - 1, dao.findAll().size(),
                "Item count should decrease by 1 after delete");
    }

    @Test
    @DisplayName("DAO findLowStockItems returns only low/out-of-stock items")
    void testDaoFindLowStockItems() {
        InventoryDAO dao = new InventoryDAO();
        List<InventoryItem> lowStock = dao.findLowStockItems();

        for (InventoryItem item : lowStock) {
            assertTrue(
                    item.getStatus().equals("Low Stock") || item.getStatus().equals("Out of Stock"),
                    "Every item should be Low Stock or Out of Stock");
        }
    }
}
