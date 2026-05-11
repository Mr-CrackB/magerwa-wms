package com.wms.observer;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * DESIGN PATTERN: Observer Pattern - Concrete Observer
 * 
 * Monitors inventory changes and generates alerts when stock
 * falls below a defined threshold. Stores alerts for dashboard display.
 */
@Component
public class StockAlertObserver implements InventoryObserver {

    private static final int LOW_STOCK_THRESHOLD = 10;
    private final List<String> alertLog = new ArrayList<>();

    @Override
    public void update(String productName, int oldQuantity, int newQuantity) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        if (newQuantity == 0) {
            alertLog.add(0, "[" + timestamp + "] OUT OF STOCK: " + productName);
        } else if (newQuantity < LOW_STOCK_THRESHOLD) {
            alertLog.add(0, "[" + timestamp + "] LOW STOCK: " + productName
                    + " — only " + newQuantity + " units left");
        }
    }

    public List<String> getAlertLog() { return alertLog; }
    public void clearAlerts() { alertLog.clear(); }
}
