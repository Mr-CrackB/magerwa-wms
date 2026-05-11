package com.wms.observer;

/**
 * DESIGN PATTERN: Observer Pattern - Observer Interface
 * 
 * Defines the contract for objects that should be notified
 * when inventory levels change. Any class implementing this
 * interface can register to receive stock update notifications.
 * 
 * Benefits:
 *   - Loose coupling between inventory changes and notification logic
 *   - Easy to add new observers without modifying existing code (Open/Closed)
 *   - Multiple components can react to the same event independently
 */
public interface InventoryObserver {

    /**
     * Called when inventory stock levels change.
     * @param productName the product whose stock changed
     * @param oldQuantity the previous quantity
     * @param newQuantity the updated quantity
     */
    void update(String productName, int oldQuantity, int newQuantity);
}
