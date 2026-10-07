package BusinessLayer;

import java.sql.Timestamp;

/**
 * ServiceTicket - Data Model representing a vehicle entry in the Service Queue.
 * Holds vehicle details, owner info, assigned service, mechanic, and queue status.
 */
public class ServiceTicket {
    private int queueId;
    private int vehicleId;
    private Integer recordId;
    private int queuePosition;
    private String registrationNo;
    private String make;
    private String model;
    private int vehicleYear;
    private String customerName;
    private String serviceName;
    private String mechanicName;
    private Timestamp addedAt;
    private Timestamp startedAt;
    private Timestamp completedAt;
    private String status; // 'Waiting', 'In Service', 'Completed', 'Cancelled'

    public ServiceTicket() {}

    public ServiceTicket(int queueId, int vehicleId, Integer recordId, int queuePosition,
                         String registrationNo, String make, String model, int vehicleYear,
                         String customerName, String serviceName, String mechanicName,
                         Timestamp addedAt, Timestamp startedAt, Timestamp completedAt, String status) {
        this.queueId = queueId;
        this.vehicleId = vehicleId;
        this.recordId = recordId;
        this.queuePosition = queuePosition;
        this.registrationNo = registrationNo;
        this.make = make;
        this.model = model;
        this.vehicleYear = vehicleYear;
        this.customerName = customerName;
        this.serviceName = serviceName;
        this.mechanicName = mechanicName;
        this.addedAt = addedAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.status = status;
    }

    // Getters and Setters
    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(int queuePosition) {
        this.queuePosition = queuePosition;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(int vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleFullName() {
        String name = (make != null ? make : "") + " " + (model != null ? model : "");
        if (vehicleYear > 0) {
            name += " (" + vehicleYear + ")";
        }
        return name.trim();
    }

    public String getCustomerName() {
        return customerName != null ? customerName : "N/A";
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceName() {
        return serviceName != null ? serviceName : "General Service";
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMechanicName() {
        return mechanicName != null ? mechanicName : "Unassigned";
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
