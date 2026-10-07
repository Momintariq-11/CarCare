package DataAccessLayer;

import BusinessLayer.Bill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Billing_DAO - Simple database operations for bills.
 */
public class Billing_DAO {

    private final DB_Connection db = new DB_Connection();

    // 1. Fetch all bills from database
    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        autoCreateBills(); // Auto-create bill for completed services

        String sql = "SELECT b.bill_id, v.registration_no, CONCAT(v.make, ' ', v.model) AS vehicle_name, "
                   + "       COALESCE(c.full_name, 'Customer') AS customer_name, "
                   + "       COALESCE(sr.description, 'General Service') AS service_name, "
                   + "       b.grand_total, b.status "
                   + "FROM bills b "
                   + "LEFT JOIN service_records sr ON b.record_id = sr.record_id "
                   + "LEFT JOIN vehicles v ON sr.vehicle_id = v.vehicle_id "
                   + "LEFT JOIN customers c ON v.customer_id = c.customer_id "
                   + "ORDER BY b.bill_id DESC";

        try {
            Connection conn = db.getConnection();
            if (conn == null) return list;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Bill(
                    rs.getInt("bill_id"),
                    rs.getString("registration_no"),
                    rs.getString("vehicle_name"),
                    rs.getString("customer_name"),
                    rs.getString("service_name"),
                    rs.getDouble("grand_total"),
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

    // 2. Mark a bill as PAID
    public boolean markBillPaid(int billId) {
        String sql = "UPDATE bills SET status = 'Paid' WHERE bill_id = ?";
        try {
            Connection conn = db.getConnection();
            if (conn == null) return false;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, billId);
            boolean ok = ps.executeUpdate() > 0;
            ps.close();
            conn.close();
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Update bill total (for extra parts)
    public boolean updateBillTotal(int billId, double newTotal) {
        String sql = "UPDATE bills SET grand_total = ? WHERE bill_id = ?";
        try {
            Connection conn = db.getConnection();
            if (conn == null) return false;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, newTotal);
            ps.setInt(2, billId);
            boolean ok = ps.executeUpdate() > 0;
            ps.close();
            conn.close();
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Auto-create bill for completed services if missing
    private void autoCreateBills() {
        String sql = "INSERT INTO bills (record_id, bill_date, service_total, grand_total, status) "
                   + "SELECT sr.record_id, CURDATE(), 5000, 5000, 'Unpaid' "
                   + "FROM service_records sr "
                   + "LEFT JOIN bills b ON sr.record_id = b.record_id "
                   + "WHERE b.bill_id IS NULL AND sr.status = 'Completed'";
        try {
            Connection conn = db.getConnection();
            if (conn == null) return;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            // Ignore if tables empty
        }
    }
}
