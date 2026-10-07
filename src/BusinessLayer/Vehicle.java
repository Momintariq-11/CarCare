package BusinessLayer;

public class Vehicle {

    private int vehicleId;
    private int customerId;
    private String customerName;
    private String registrationNo;
    private String make;
    private String model;
    private int vehicleYear;
    private int mileage;

    public Vehicle() {
    }

    public Vehicle(int customerId, String registrationNo, String make, String model, int vehicleYear, int mileage) {
        this.customerId = customerId;
        this.registrationNo = registrationNo;
        this.make = make;
        this.model = model;
        this.vehicleYear = vehicleYear;
        this.mileage = mileage;
    }

    public Vehicle(int vehicleId, int customerId, String customerName, String registrationNo, String make, String model, int vehicleYear, int mileage) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.registrationNo = registrationNo;
        this.make = make;
        this.model = model;
        this.vehicleYear = vehicleYear;
        this.mileage = mileage;
    }

    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName != null ? customerName : "Unknown"; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getRegistrationNo() { return registrationNo; }
    public void setRegistrationNo(String registrationNo) { this.registrationNo = registrationNo; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getVehicleYear() { return vehicleYear; }
    public void setVehicleYear(int vehicleYear) { this.vehicleYear = vehicleYear; }

    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
}