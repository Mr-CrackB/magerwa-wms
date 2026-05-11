package com.wms.model;

/**
 * Represents a system user with authentication credentials and role.
 * Supports role-based access control for the warehouse management system.
 */
public class User {
    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private UserRole role;
    private boolean approved;

    /** Enum defining available user roles in the system */
    public enum UserRole {
        ADMIN("System Administrator"),
        MANAGER("Warehouse Manager"),
        STAFF("Warehouse Staff"),
        CLIENT("Client");

        private final String displayName;

        UserRole(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }

    public User() {}

    /** Used for seeded/admin-created accounts — approved by default */
    public User(String userId, String username, String password,
                String fullName, String email, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.approved = true;
    }

    /** Used for self-registered accounts — approval required */
    public User(String userId, String username, String password,
                String fullName, String email, UserRole role, boolean approved) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.approved = approved;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    @Override
    public String toString() {
        return fullName + " (" + role.getDisplayName() + ")";
    }
}
