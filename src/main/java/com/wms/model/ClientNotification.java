package com.wms.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a notification sent to a client when new stock is added */
public class ClientNotification {
    private String message;
    private String timestamp;
    private boolean read;

    public ClientNotification(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
        this.read = false;
    }

    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
    public boolean isRead() { return read; }
    public void markRead() { this.read = true; }
}
