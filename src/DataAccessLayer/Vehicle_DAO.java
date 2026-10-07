package DataAccessLayer;

import BusinessLayer.Vehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vehicle_DAO {
    private DB_Connection dbConnection;

    public Vehicle_DAO() {
        this.dbConnection = new DB_Connection();
    }

    public Vehicle_DAO(DB_Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Load all vehicles with customer name from MySQL
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT v.*, c.full_name AS customer_name "
                   + "FROM vehicles v "
                   + "LEFT JOIN customers c ON v.customer_id = c.customer_id "
                   + "ORDER BY v.vehicle_id DESC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehicle v = new Vehicle(
                    rs.getInt("vehicle_id"),
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getString("registration_no"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("vehicle_year"),
                    rs.getInt("mileage")
                );
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Add a vehicle to MySQL
    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (customer_id, registration_no, make, model, vehicle_year, mileage) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicle.getCustomerId());
            ps.setString(2, vehicle.getRegistrationNo());
            ps.setString(3, vehicle.getMake());
            ps.setString(4, vehicle.getModel());
            ps.setInt(5, vehicle.getVehicleYear());
            ps.setInt(6, vehicle.getMileage());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete vehicle from MySQL by registration number
    public boolean deleteVehicle(String registrationNo) {
        String sql = "DELETE FROM vehicles WHERE registration_no = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, registrationNo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update vehicle details in MySQL by registration number
    public boolean updateVehicle(String regNo, String make, String model, int year, int mileage) {
        String sql = "UPDATE vehicles SET make = ?, model = ?, vehicle_year = ?, mileage = ? WHERE registration_no = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, make);
            ps.setString(2, model);
            ps.setInt(3, year);
            ps.setInt(4, mileage);
            ps.setString(5, regNo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search / Get vehicle by registration number (with customer name)
    public Vehicle getVehicleByRegNo(String regNo) {
        String sql = "SELECT v.*, c.full_name AS customer_name "
                   + "FROM vehicles v "
                   + "LEFT JOIN customers c ON v.customer_id = c.customer_id "
                   + "WHERE v.registration_no = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, regNo.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("registration_no"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("vehicle_year"),
                        rs.getInt("mileage")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
