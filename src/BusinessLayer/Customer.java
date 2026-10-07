package BusinessLayer;

/**
 * Customer Model Class
 * Represents customer details from the database.
 * Supports both Adding new customers and Editing existing customers.
 */
public class Customer {
    private int customerId;
    private String fullName;
    private String phone;
    private String email;
    private String address;

    // 1. Default constructor
    public Customer() { }

    // 2. Constructor for Adding a new customer (without ID, MySQL auto-generates it)
    public Customer(String fullName, String phone, String email, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // 3. Constructor for Editing / Loading existing customer (with ID)
    public Customer(int customerId, String fullName, String phone, String email, String address) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Getters (to read data for display or saving)
    public int getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    // Setters (essential when Editing existing customer details)
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
}
