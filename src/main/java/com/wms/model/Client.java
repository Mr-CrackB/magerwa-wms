package com.wms.model;

/**
 * Represents a warehouse client who stores or retrieves cargo.
 */
public class Client {
    private String clientId;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;

    public Client() {}

    public Client(String clientId, String companyName, String contactPerson,
                  String email, String phone, String address) {
        this.clientId = clientId;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getClientId() { return clientId; }
    public void setClientId(String id) { this.clientId = id; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String name) { this.companyName = name; }
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String person) { this.contactPerson = person; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
