package DataAccessLayer;

import BusinessLayer.ServiceTicket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ServiceQueue_DAO - Handles all database persistence and FIFO queue queries
 * for the CarCare Service Queue Management module.
 */
public class ServiceQueue_DAO {

    private final DB_Connection dbConnection;

    public ServiceQueue_DAO() {
        this.dbConnection = new DB_Connection();
    }

    public ServiceQueue_DAO(DB_Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Add a vehicle to the service queue (FIFO ordering).
     * Creates a service_record first, then inserts into service_queue.
     */
    public boolean addToQueue(int vehicleId, String serviceName, String mechanicName) {
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            if (conn == null) return false;
            conn.setAutoCommit(false); // Begin transaction

            // 1. Resolve mechanic_id if assigned
            Integer mechanicId = null;
            if (mechanicName != null && !mechanicName.trim().isEmpty()) {
                String mechSql = "SELECT mechanic_id FROM mechanics WHERE full_name LIKE ? LIMIT 1";
                try (PreparedStatement psMech = conn.prepareStatement(mechSql)) {
                    psMech.setString(1, "%" + mechanicName.trim() + "%");
                    try (ResultSet rsMech = psMech.executeQuery()) {
                        if (rsMech.next()) {
                            mechanicId = rsMech.getInt("mechanic_id");
                        }
                    }
                }
            }

            // 2. Create service_record entry
            int recordId = -1;
            String recSql = "INSERT INTO service_records (vehicle_id, mechanic_id, service_date, description, status) "
                          + "VALUES (?, ?, CURDATE(), ?, 'Pending')";
            try (PreparedStatement psRec = conn.prepareStatement(recSql, Statement.RETURN_GENERATED_KEYS)) {
                psRec.setInt(1, vehicleId);
                if (mechanicId != null) {
                    psRec.setInt(2, mechanicId);
                } else {
                    psRec.setNull(2, java.sql.Types.INTEGER);
                }
                psRec.setString(3, serviceName);
                psRec.executeUpdate();

                try (ResultSet rsKeys = psRec.getGeneratedKeys()) {
                    if (rsKeys.next()) {
                        recordId = rsKeys.getInt(1);
                    }
                }
            }

            // 3. Determine next queue_position for 'Waiting' cars
            int nextPos = 1;
            String posSql = "SELECT COALESCE(MAX(queue_position), 0) + 1 AS next_pos FROM service_queue WHERE status = 'Waiting'";
            try (PreparedStatement psPos = conn.prepareStatement(posSql);
                 ResultSet rsPos = psPos.executeQuery()) {
                if (rsPos.next()) {
                    nextPos = rsPos.getInt("next_pos");
                }
            }

            // 4. Insert into service_queue
            String qSql = "INSERT INTO service_queue (vehicle_id, record_id, queue_position, status) VALUES (?, ?, ?, 'Waiting')";
            try (PreparedStatement psQ = conn.prepareStatement(qSql)) {
                psQ.setInt(1, vehicleId);
                if (recordId > 0) {
                    psQ.setInt(2, recordId);
                } else {
                    psQ.setNull(2, java.sql.Types.INTEGER);
                }
                psQ.setInt(3, nextPos);
                psQ.executeUpdate();
            }

            conn.commit(); // Transaction successful
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * Retrieve all waiting vehicles as a FIFO Queue (Ordered by queue_position ASC, added_at ASC).
     */
    public Queue<ServiceTicket> getWaitingQueue() {
        Queue<ServiceTicket> queue = new LinkedList<>();
        String sql = "SELECT sq.queue_id, sq.vehicle_id, sq.record_id, sq.queue_position, sq.added_at, sq.started_at, sq.completed_at, sq.status, "
                   + "       v.registration_no, v.make, v.model, v.vehicle_year, "
                   + "       c.full_name AS customer_name, "
                   + "       sr.description AS service_name, "
                   + "       m.full_name AS mechanic_name "
                   + "FROM service_queue sq "
                   + "JOIN vehicles v ON sq.vehicle_id = v.vehicle_id "
                   + "LEFT JOIN customers c ON v.customer_id = c.customer_id "
                   + "LEFT JOIN service_records sr ON sq.record_id = sr.record_id "
                   + "LEFT JOIN mechanics m ON sr.mechanic_id = m.mechanic_id "
                   + "WHERE sq.status = 'Waiting' "
                   + "ORDER BY sq.queue_position ASC, sq.added_at ASC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ServiceTicket ticket = new ServiceTicket(
                    rs.getInt("queue_id"),
                    rs.getInt("vehicle_id"),
                    rs.getInt("record_id"),
                    rs.getInt("queue_position"),
                    rs.getString("registration_no"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("vehicle_year"),
                    rs.getString("customer_name"),
                    rs.getString("service_name"),
                    rs.getString("mechanic_name"),
                    rs.getTimestamp("added_at"),
                    rs.getTimestamp("started_at"),
                    rs.getTimestamp("completed_at"),
                    rs.getString("status")
                );
                queue.offer(ticket);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return queue;
    }

    /**
     * Get the car currently undergoing service in Bay 1 (status = 'In Service').
     */
    public ServiceTicket getActiveService() {
        String sql = "SELECT sq.queue_id, sq.vehicle_id, sq.record_id, sq.queue_position, sq.added_at, sq.started_at, sq.completed_at, sq.status, "
                   + "       v.registration_no, v.make, v.model, v.vehicle_year, "
                   + "       c.full_name AS customer_name, "
                   + "       sr.description AS service_name, "
                   + "       m.full_name AS mechanic_name "
                   + "FROM service_queue sq "
                   + "JOIN vehicles v ON sq.vehicle_id = v.vehicle_id "
                   + "LEFT JOIN customers c ON v.customer_id = c.customer_id "
                   + "LEFT JOIN service_records sr ON sq.record_id = sr.record_id "
                   + "LEFT JOIN mechanics m ON sr.mechanic_id = m.mechanic_id "
                   + "WHERE sq.status = 'In Service' "
                   + "ORDER BY sq.started_at DESC LIMIT 1";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new ServiceTicket(
                    rs.getInt("queue_id"),
                    rs.getInt("vehicle_id"),
                    rs.getInt("record_id"),
                    rs.getInt("queue_position"),
                    rs.getString("registration_no"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("vehicle_year"),
                    rs.getString("customer_name"),
                    rs.getString("service_name"),
                    rs.getString("mechanic_name"),
                    rs.getTimestamp("added_at"),
                    rs.getTimestamp("started_at"),
                    rs.getTimestamp("completed_at"),
                    rs.getString("status")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Dequeue next vehicle from Waiting queue into Active Service (FIFO).
     */
    public ServiceTicket callNextVehicle() {
        Queue<ServiceTicket> waiting = getWaitingQueue();
        if (waiting.isEmpty()) return null;

        ServiceTicket nextTicket = waiting.poll(); // FIFO Poll

        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Move ticket to 'In Service'
            String sql1 = "UPDATE service_queue SET status = 'In Service', started_at = NOW() WHERE queue_id = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(sql1)) {
                ps1.setInt(1, nextTicket.getQueueId());
                ps1.executeUpdate();
            }

            // 2. Update service_records status
            if (nextTicket.getRecordId() != null) {
                String sql2 = "UPDATE service_records SET status = 'In Progress' WHERE record_id = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                    ps2.setInt(1, nextTicket.getRecordId());
                    ps2.executeUpdate();
                }
            }

            // 3. Re-index remaining waiting queue positions
            String sql3 = "UPDATE service_queue SET queue_position = queue_position - 1 WHERE status = 'Waiting'";
            try (PreparedStatement ps3 = conn.prepareStatement(sql3)) {
                ps3.executeUpdate();
            }

            conn.commit();
            return nextTicket;
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * Mark the currently active service as completed.
     */
    public boolean markAsCompleted(int queueId, Integer recordId) {
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Update queue status
            String sql1 = "UPDATE service_queue SET status = 'Completed', completed_at = NOW() WHERE queue_id = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(sql1)) {
                ps1.setInt(1, queueId);
                ps1.executeUpdate();
            }

            // 2. Update service record status
            if (recordId != null && recordId > 0) {
                String sql2 = "UPDATE service_records SET status = 'Completed' WHERE record_id = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                    ps2.setInt(1, recordId);
                    ps2.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * Remove / Cancel a vehicle from the waiting queue.
     */
    public boolean removeFromQueue(int queueId) {
        String sql = "UPDATE service_queue SET status = 'Cancelled' WHERE queue_id = ? AND status = 'Waiting'";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, queueId);
            boolean ok = ps.executeUpdate() > 0;
            if (ok) {
                // Re-sequence waiting positions
                resequenceQueuePositions();
            }
            return ok;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Re-sequence queue positions so they are continuous: 1, 2, 3...
     */
    private void resequenceQueuePositions() {
        String sqlSelect = "SELECT queue_id FROM service_queue WHERE status = 'Waiting' ORDER BY queue_position ASC, added_at ASC";
        String sqlUpdate = "UPDATE service_queue SET queue_position = ? WHERE queue_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement psSel = conn.prepareStatement(sqlSelect);
             ResultSet rs = psSel.executeQuery();
             PreparedStatement psUpd = conn.prepareStatement(sqlUpdate)) {

            int pos = 1;
            while (rs.next()) {
                psUpd.setInt(1, pos++);
                psUpd.setInt(2, rs.getInt("queue_id"));
                psUpd.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Count vehicles currently waiting in queue.
     */
    public int getWaitingCount() {
        String sql = "SELECT COUNT(*) FROM service_queue WHERE status = 'Waiting'";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Count vehicles completed today.
     */
    public int getCompletedTodayCount() {
        String sql = "SELECT COUNT(*) FROM service_queue WHERE status = 'Completed' AND DATE(completed_at) = CURDATE()";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int getUnderServiceCount() {
        String sql = "SELECT COUNT(*) FROM service_queue WHERE status = 'In Service'";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getCompletedCount() {
        String sql = "SELECT COUNT(*) FROM service_queue WHERE status = 'Completed'";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Get recent queue tickets for dashboard live feed (In Service, Waiting, Completed).
     */
    public List<ServiceTicket> getRecentQueueTickets(int limit) {
        List<ServiceTicket> list = new ArrayList<>();
        String sql = "SELECT sq.queue_id, sq.vehicle_id, sq.record_id, sq.queue_position, sq.added_at, sq.started_at, sq.completed_at, sq.status, "
                   + "       v.registration_no, v.make, v.model, v.vehicle_year, "
                   + "       c.full_name AS customer_name, "
                   + "       sr.description AS service_name, "
                   + "       m.full_name AS mechanic_name "
                   + "FROM service_queue sq "
                   + "JOIN vehicles v ON sq.vehicle_id = v.vehicle_id "
                   + "LEFT JOIN customers c ON v.customer_id = c.customer_id "
                   + "LEFT JOIN service_records sr ON sq.record_id = sr.record_id "
                   + "LEFT JOIN mechanics m ON sr.mechanic_id = m.mechanic_id "
                   + "ORDER BY sq.queue_id DESC LIMIT ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ServiceTicket(
                        rs.getInt("queue_id"),
                        rs.getInt("vehicle_id"),
                        rs.getInt("record_id"),
                        rs.getInt("queue_position"),
                        rs.getString("registration_no"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("vehicle_year"),
                        rs.getString("customer_name"),
                        rs.getString("service_name"),
                        rs.getString("mechanic_name"),
                        rs.getTimestamp("added_at"),
                        rs.getTimestamp("started_at"),
                        rs.getTimestamp("completed_at"),
                        rs.getString("status")
                    ));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
