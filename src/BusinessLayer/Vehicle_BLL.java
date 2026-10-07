package BusinessLayer;

import DataAccessLayer.Vehicle_DAO;
import java.util.List;

public class Vehicle_BLL {

    private Vehicle_DAO vehicleDAO;

    public Vehicle_BLL() {
        this.vehicleDAO = new Vehicle_DAO();
    }

    // Get all vehicles for the table
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }

    // Business validation before saving to DB
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        if (vehicle.getRegistrationNo() == null || vehicle.getRegistrationNo().trim().isEmpty()) {
            return false;
        }
        if (vehicle.getVehicleYear() < 1900 || vehicle.getVehicleYear() > 2030) {
            return false;
        }
        if (vehicle.getMileage() < 0) {
            return false;
        }
        return vehicleDAO.addVehicle(vehicle);
    }

    // Validate and delete vehicle
    public boolean deleteVehicle(String registrationNo) {
        if (registrationNo == null || registrationNo.trim().isEmpty()) {
            return false;
        }
        return vehicleDAO.deleteVehicle(registrationNo.trim());
    }
}
