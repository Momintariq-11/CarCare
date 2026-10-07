package DataAccessLayer;

import BusinessLayer.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Service_DAO {
    private final DB_Connection db = new DB_Connection();

    // Helper for 2-line SQL executions
    private boolean execute(String sql, Object... params) {
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    // 1. Fetch all services
    public List<Service> getAllServices() {
        List<Service> list = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM services ORDER BY service_id ASC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Service(
                    rs.getInt("service_id"),
                    rs.getString("service_name"),
                    rs.getString("description"),
                    rs.getDouble("cost"),
                    rs.getInt("estimated_time"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {}
        return list;
    }

    // 2. Add new service
    public boolean addService(String name, double cost, int time, String desc) {
        return execute("INSERT INTO services (service_name, description, cost, estimated_time, status) VALUES (?, ?, ?, ?, 'Active')",
                       name, desc, cost, time);
    }

    // 3. Update existing service
    public boolean updateService(int id, String name, double cost, int time) {
        return execute("UPDATE services SET service_name = ?, cost = ?, estimated_time = ? WHERE service_id = ?",
                       name, cost, time, id);
    }

    // 4. Delete service
    public boolean deleteService(int id) {
        return execute("DELETE FROM services WHERE service_id = ?", id);
    }

    // 5. Assign service to vehicle (adds to service records and queue)
    public boolean assignServiceToVehicle(int vehicleId, String serviceName) {
        return new ServiceQueue_DAO().addToQueue(vehicleId, serviceName, "Ahmed");
    }
}
