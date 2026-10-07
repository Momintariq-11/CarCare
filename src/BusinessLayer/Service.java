package BusinessLayer;

public class Service {
    private int serviceId;
    private String serviceName;
    private String description;
    private double cost;
    private int estimatedTime;
    private String status;

    public Service() {}

    public Service(int serviceId, String serviceName, String description, double cost, int estimatedTime, String status) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.cost = cost;
        this.estimatedTime = estimatedTime;
        this.status = status;
    }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public int getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(int estimatedTime) { this.estimatedTime = estimatedTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
