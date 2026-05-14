package com.wms.observer;

import com.wms.model.ClientNotification;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * DESIGN PATTERN: Observer Pattern - Concrete Observer
 *
 * Monitors inventory changes and generates:
 * 1. Staff alerts for low/out-of-stock items
 * 2. Client notifications when new stock is added
 */
@Component
public class StockAlertObserver implements InventoryObserver {

    private static final int LOW_STOCK_THRESHOLD = 10;
    private final List<String> alertLog = new ArrayList<>();
    private final List<ClientNotification> clientNotifications = new ArrayList<>();

    @Override
    public void update(String productName, int oldQuantity, int newQuantity) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Staff/admin alerts for low stock
        if (newQuantity == 0) {
            alertLog.add(0, "[" + timestamp + "] OUT OF STOCK: " + productName);
        } else if (newQuantity < LOW_STOCK_THRESHOLD) {
            alertLog.add(0, "[" + timestamp + "] LOW STOCK: " + productName
                    + " — only " + newQuantity + " units left");
        }
    }

    /** Called when a new item is added — notifies clients */
    public void notifyNewItem(String productName, String category, int quantity) {
        clientNotifications.add(0, new ClientNotification(
                "New stock available: " + productName + " (" + category + ") — " + quantity + " units in warehouse"
        ));
    }

    public List<String> getAlertLog() { return alertLog; }
    public void clearAlerts() { alertLog.clear(); }

    public List<ClientNotification> getClientNotifications() { return clientNotifications; }

    public long getUnreadCount() {
        return clientNotifications.stream().filter(n -> !n.isRead()).count();
    }

    public void markAllRead() {
        clientNotifications.forEach(ClientNotification::markRead);
    }
}
