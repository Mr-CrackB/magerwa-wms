package com.wms.service;

import com.wms.model.InventoryItem;
import com.wms.observer.InventorySubject;
import com.wms.observer.StockAlertObserver;
import com.wms.repository.InventoryDAO;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Business logic for inventory operations.
 * Integrates with Observer pattern to notify on stock changes.
 */
@Service
public class InventoryService {

    private final InventoryDAO inventoryDAO;
    private final InventorySubject inventorySubject;
    private final StockAlertObserver stockAlertObserver;

    public InventoryService(InventoryDAO inventoryDAO,
                            InventorySubject inventorySubject,
                            StockAlertObserver stockAlertObserver) {
        this.inventoryDAO = inventoryDAO;
        this.inventorySubject = inventorySubject;
        this.stockAlertObserver = stockAlertObserver;
    }

    /** Register the stock alert observer on startup */
    @PostConstruct
    public void init() {
        inventorySubject.addObserver(stockAlertObserver);
    }

    public List<InventoryItem> getAllItems() { return inventoryDAO.findAll(); }

    public List<InventoryItem> getLowStockItems() { return inventoryDAO.findLowStockItems(); }

    public List<InventoryItem> searchItems(String keyword) {
        return inventoryDAO.searchByName(keyword);
    }

    /** Adds a new item and triggers observer notification */
    public void addItem(String productName, String category, int quantity, String location) {
        String itemId = "INV" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        String status = quantity == 0 ? "Out of Stock" : quantity < 10 ? "Low Stock" : "In Stock";

        InventoryItem item = new InventoryItem(itemId, productName, category,
                quantity, location, LocalDate.now(), status);
        inventoryDAO.save(item);
        inventorySubject.notifyObservers(productName, 0, quantity);
    }

    public void deleteItem(String itemId) { inventoryDAO.delete(itemId); }

    public int getTotalItemCount() { return inventoryDAO.findAll().size(); }

    public int getTotalUnitsInStock() {
        return inventoryDAO.findAll().stream().mapToInt(InventoryItem::getQuantity).sum();
    }

    public List<String> getAlerts() { return stockAlertObserver.getAlertLog(); }
}
