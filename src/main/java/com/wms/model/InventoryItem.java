package com.wms.model;

import java.time.LocalDate;

/**
 * Tracks inventory quantities and storage details for products.
 * Links a product to its storage location and current stock status.
 */
public class InventoryItem {
    private String itemId;
    private String productName;
    private String category;
    private int quantity;
    private String storageLocation;
    private LocalDate dateReceived;
    private String status;

    public static final String STATUS_IN_STOCK = "In Stock";
    public static final String STATUS_LOW_STOCK = "Low Stock";
    public static final String STATUS_OUT_OF_STOCK = "Out of Stock";

    public InventoryItem() {}

    public InventoryItem(String itemId, String productName, String category,
                         int quantity, String storageLocation,
                         LocalDate dateReceived, String status) {
        this.itemId = itemId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.storageLocation = storageLocation;
        this.dateReceived = dateReceived;
        this.status = status;
    }

    /** Updates quantity and automatically adjusts status based on threshold */
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        if (newQuantity == 0) {
            this.status = STATUS_OUT_OF_STOCK;
        } else if (newQuantity < 10) {
            this.status = STATUS_LOW_STOCK;
        } else {
            this.status = STATUS_IN_STOCK;
        }
    }

    // Getters and Setters
    public String getItemId() { return itemId; }
    public void setItemId(String id) { this.itemId = id; }
    public String getProductName() { return productName; }
    public void setProductName(String name) { this.productName = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int qty) { this.quantity = qty; }
    public String getStorageLocation() { return storageLocation; }
    public void setStorageLocation(String loc) { this.storageLocation = loc; }
    public LocalDate getDateReceived() { return dateReceived; }
    public void setDateReceived(LocalDate date) { this.dateReceived = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
