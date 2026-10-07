package BusinessLayer;

/**
 * Bill - Simple data model for invoice records.
 */
public class Bill {
    private int billId;
    private String regNo;
    private String vehicle;
    private String customer;
    private String service;
    private double total;
    private String status; // 'Paid' or 'Unpaid'

    public Bill(int billId, String regNo, String vehicle, String customer, String service, double total, String status) {
        this.billId = billId;
        this.regNo = regNo;
        this.vehicle = vehicle;
        this.customer = customer;
        this.service = service;
        this.total = total;
        this.status = status;
    }

    public int getBillId() { return billId; }
    public String getRegNo() { return regNo; }
    public String getVehicle() { return vehicle; }
    public String getCustomer() { return customer; }
    public String getService() { return service; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
