package DataAccessLayer;

import BusinessLayer.Appointment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Appointment_DAO - Simple database operations for appointments.
 */
public class Appointment_DAO {

    private final DB_Connection db = new DB_Connection();

    // 1. Fetch all appointments from database
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT a.appointment_id, a.appointment_date, a.appointment_time, "
                   + "       v.registration_no, CONCAT(v.make, ' ', v.model) AS vehicle_name, "
                   + "       COALESCE(c.full_name, 'Customer') AS customer_name, "
                   + "       COALESCE(a.notes, 'General Service') AS service_name, a.status "
                   + "FROM appointments a "
                   + "LEFT JOIN vehicles v ON a.vehicle_id = v.vehicle_id "
                   + "LEFT JOIN customers c ON a.customer_id = c.customer_id "
                   + "WHERE a.status != 'Completed' "
                   + "ORDER BY a.appointment_id DESC";

        try {
            Connection conn = db.getConnection();
            if (conn == null) return list;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Appointment(
                    rs.getInt("appointment_id"),
                    rs.getString("appointment_date"),
                    rs.getString("appointment_time"),
                    rs.getString("registration_no"),
                    rs.getString("vehicle_name"),
                    rs.getString("customer_name"),
                    rs.getString("service_name"),
                    rs.getString("status")
                ));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Helper to execute SQL updates in 2 lines
    private boolean execute(String sql, Object... params) {
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    // 2. Confirm booking (1 line)
    public boolean confirmAppointment(int id) {
        return execute("UPDATE appointments SET status = 'Confirmed' WHERE appointment_id = ?", id);
    }

    // 3. Move car into Service Queue (2 lines)
    public boolean checkInToQueue(int appId, String regNo) {
        execute("UPDATE appointments SET status = 'Completed' WHERE appointment_id = ?", appId);
        BusinessLayer.Vehicle v = new Vehicle_DAO().getVehicleByRegNo(regNo);
        return v != null && new ServiceQueue_DAO().addToQueue(v.getVehicleId(), "General Service / Oil Change", "Ahmed");
    }

    // 4. Reschedule appointment (1 line)
    public boolean rescheduleAppointment(int id, String date, String time) {
        return execute("UPDATE appointments SET appointment_date = ?, appointment_time = ? WHERE appointment_id = ?", date, formatTime(time), id);
    }

    // 5. Delete appointment (1 line)
    public boolean deleteAppointment(int id) {
        return execute("DELETE FROM appointments WHERE appointment_id = ?", id);
    }

    // 6. Add new appointment (2 lines)
    public boolean addAppointment(int custId, int vehId, String date, String time, String service) {
        if (date == null || date.trim().isEmpty()) date = java.time.LocalDate.now().toString();
        return execute("INSERT INTO appointments (customer_id, vehicle_id, appointment_date, appointment_time, status, notes) VALUES (?, ?, ?, ?, 'Confirmed', ?)",
                       custId, vehId, date.trim(), formatTime(time), service);
    }

    private String formatTime(String t) {
        try {
            if (t == null || t.trim().isEmpty()) return "10:00:00";
            t = t.trim().toUpperCase();
            if (t.contains("AM") || t.contains("PM")) {
                java.time.LocalTime lt = java.time.LocalTime.parse(t, java.time.format.DateTimeFormatter.ofPattern("hh:mm a", java.util.Locale.US));
                return lt.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
            return t.length() == 5 ? t + ":00" : t;
        } catch (Exception e) {
            return "10:00:00";
        }
    }
}
