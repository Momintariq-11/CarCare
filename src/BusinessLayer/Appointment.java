package BusinessLayer;

/**
 * Appointment - Simple model for customer bookings.
 */
public class Appointment {
    private int appId;
    private String date;
    private String time;
    private String regNo;
    private String vehicle;
    private String customer;
    private String service;
    private String status; // 'Confirmed' or 'Pending'

    public Appointment(int appId, String date, String time, String regNo, String vehicle, String customer, String service, String status) {
        this.appId = appId;
        this.date = date;
        this.time = time;
        this.regNo = regNo;
        this.vehicle = vehicle;
        this.customer = customer;
        this.service = service;
        this.status = status;
    }

    public int getAppId() { return appId; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getRegNo() { return regNo; }
    public String getVehicle() { return vehicle; }
    public String getCustomer() { return customer; }
    public String getService() { return service; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
