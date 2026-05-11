package com.wms.model;

import java.time.LocalDate;

/**
 * Represents a warehouse order for cargo dispatch or receipt.
 */
public class Order {
    private String orderId;
    private String clientName;
    private String productName;
    private int quantity;
    private LocalDate orderDate;
    private String status;
    private String orderType;

    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_PROCESSING = "Processing";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String TYPE_INBOUND = "Inbound";
    public static final String TYPE_OUTBOUND = "Outbound";

    public Order() {}

    public Order(String orderId, String clientName, String productName,
                 int quantity, LocalDate orderDate, String status, String orderType) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.productName = productName;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = status;
        this.orderType = orderType;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String id) { this.orderId = id; }
    public String getClientName() { return clientName; }
    public void setClientName(String name) { this.clientName = name; }
    public String getProductName() { return productName; }
    public void setProductName(String name) { this.productName = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int qty) { this.quantity = qty; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate date) { this.orderDate = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOrderType() { return orderType; }
    public void setOrderType(String type) { this.orderType = type; }
}
