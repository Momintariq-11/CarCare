package PresentationLayer;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import BusinessLayer.Appointment;
import DataAccessLayer.Appointment_DAO;

/**
 * CarCare - Appointments & Scheduling Screen
 */
public class Appointment_Screen extends javax.swing.JFrame {

    private final Color primaryBlue = new Color(8, 37, 79);
    private final Appointment_DAO appointmentDAO = new Appointment_DAO();
    private List<Appointment> allAppointments = new ArrayList<>();
    private List<Appointment> displayedAppointments = new ArrayList<>();
    private Appointment selectedAppointment = null;
    private String statusFilter = "All";

    public Appointment_Screen() {
        initComponents();
        setupWindow();
        setupDesign();
        setupEvents();
        loadAppointments();
    }

    private void setupWindow() {
        setTitle("Car-Care");
        setSize(1100, 700);
        setPreferredSize(new java.awt.Dimension(1100, 700));
        setMinimumSize(new java.awt.Dimension(1020, 640));
        setResizable(true);
        setLocationRelativeTo(null);
        getContentPane().setBackground(primaryBlue);
    }

    // Visual styling for dark blue panels, rounded card, zebra striping, and table header
    private void setupDesign() {
        jPanel1.setBackground(primaryBlue);
        jPanel1.setOpaque(true);
        jTabbedPane1.setBackground(primaryBlue);
        jTabbedPane1.setOpaque(true);

        jPanel2.putClientProperty("FlatLaf.style", "arc: 25; background: #ffffff");

        jTextField1.putClientProperty("JComponent.roundRect", true);
        jTextField1.putClientProperty("JTextField.placeholderText", "Search by ID, Reg No, Customer...");

        jButton1.putClientProperty("JButton.buttonType", "roundRect");
        jButton3.putClientProperty("JButton.buttonType", "roundRect");
        jButton4.putClientProperty("JButton.buttonType", "roundRect");
        jButton6.putClientProperty("JButton.buttonType", "roundRect");

        // Table Header & Rows matching Vehicle & Customer screens
        jTable1.getTableHeader().setBackground(primaryBlue);
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        jTable1.getTableHeader().setReorderingAllowed(false);

        jTable1.setRowHeight(40);
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(false);
        jTable1.setGridColor(new Color(230, 235, 245));

        // Alternating Zebra Row Colors (White & Soft Blue)
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 247, 252));
                }
                setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });

        // ACTION BUTTONS: Load and scale 'edit.png' & 'trash.png'
        java.net.URL editUrl = getClass().getResource("/PresentationLayer/assets/edit.png");
        java.net.URL trashUrl = getClass().getResource("/PresentationLayer/assets/trash.png");
        javax.swing.ImageIcon editIcon = (editUrl != null) ? new javax.swing.ImageIcon(new javax.swing.ImageIcon(editUrl).getImage().getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)) : null;
        javax.swing.ImageIcon trashIcon = (trashUrl != null) ? new javax.swing.ImageIcon(new javax.swing.ImageIcon(trashUrl).getImage().getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)) : null;

        // ACTION COLUMN (Index 7): Render Edit & Delete icons
        jTable1.getColumnModel().getColumn(7).setMinWidth(90);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(120);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 14, 8));
                panel.setOpaque(true);
                panel.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? Color.WHITE : new Color(245, 247, 252)));

                javax.swing.JLabel lblEdit = new javax.swing.JLabel(editIcon);
                lblEdit.setToolTipText("Reschedule Appointment");
                lblEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                javax.swing.JLabel lblTrash = new javax.swing.JLabel(trashIcon);
                lblTrash.setToolTipText("Delete Appointment");
                lblTrash.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                panel.add(lblEdit);
                panel.add(lblTrash);
                return panel;
            }
        });

        // Column widths
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(65);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(125);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(85);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(125);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
    }

    // Connect user interactions (clicks, search, filters)
    private void setupEvents() {
        // Table row click: show details in summary card
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            int row = jTable1.getSelectedRow();
            if (row >= 0 && row < displayedAppointments.size()) {
                showDetails(displayedAppointments.get(row));
            }
        });

        // ACTION BUTTONS CLICK LISTENER: Detects clicks on Edit vs Delete (Exact same as Vehicle Management)
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = jTable1.rowAtPoint(e.getPoint());
                int col = jTable1.columnAtPoint(e.getPoint());

                if (col == 7 && row >= 0 && row < displayedAppointments.size()) {
                    int cellX = e.getX() - jTable1.getCellRect(row, col, false).x;
                    int cellWidth = jTable1.getColumnModel().getColumn(col).getWidth();
                    Appointment app = displayedAppointments.get(row);

                    // 1. EDIT ACTION (Clicked on left half of cell)
                    if (cellX < cellWidth / 2) {
                        rescheduleAppointment(app);
                    }
                    // 2. DELETE ACTION (Clicked on right half of cell)
                    else {
                        deleteAppointment(app);
                    }
                }
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { applyFilter(); }
        });

        jButton1.addActionListener(e -> openNewBooking());
        jButton3.addActionListener(e -> { statusFilter = "All"; applyFilter(); });
        jButton4.addActionListener(e -> { statusFilter = "Confirmed"; applyFilter(); });
        jButton6.addActionListener(e -> { statusFilter = "Pending"; applyFilter(); });

        jButton2.addActionListener(e -> checkInToQueue());
        jButton5.addActionListener(e -> confirmBooking());
    }

    // Load appointments from database
    public void loadAppointments() {
        allAppointments = appointmentDAO.getAllAppointments();
        applyFilter();
    }

    // Filter appointments by search text and status
    private void applyFilter() {
        String search = jTextField1.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        displayedAppointments.clear();

        for (Appointment a : allAppointments) {
            boolean matchesStatus = statusFilter.equals("All") || a.getStatus().equalsIgnoreCase(statusFilter);
            boolean matchesSearch = search.isEmpty()
                    || a.getCustomer().toLowerCase().contains(search)
                    || a.getRegNo().toLowerCase().contains(search)
                    || a.getVehicle().toLowerCase().contains(search)
                    || String.valueOf(a.getAppId()).contains(search);

            if (matchesStatus && matchesSearch) {
                displayedAppointments.add(a);
                model.addRow(new Object[]{
                    "APP-" + a.getAppId(),
                    a.getDate() + ", " + a.getTime(),
                    a.getRegNo(),
                    a.getVehicle(),
                    a.getCustomer(),
                    a.getService(),
                    a.getStatus(),
                    ""
                });
            }
        }

        if (!displayedAppointments.isEmpty()) {
            jTable1.setRowSelectionInterval(0, 0);
            showDetails(displayedAppointments.get(0));
        } else {
            selectedAppointment = null;
            jLabel3.setText("No Appointments");
            jLabel5.setText("—");
            jLabel7.setText("—");
            jLabel9.setText("—");
            jLabel11.setText("—");
            jLabel13.setText("—");
        }
    }

    // Show selected appointment on the summary card
    private void showDetails(Appointment a) {
        selectedAppointment = a;
        jLabel3.setText("Appointment #APP-" + a.getAppId());
        jLabel5.setText(a.getCustomer());
        jLabel7.setText(a.getVehicle() + " (" + a.getRegNo() + ")");
        jLabel9.setText(a.getDate() + ", " + a.getTime());
        jLabel11.setText(a.getService());
        jLabel13.setText("Bay 2  •  " + a.getStatus());
    }

    // Reschedule appointment date and time
    private void rescheduleAppointment(Appointment app) {
        String newDate = JOptionPane.showInputDialog(this, "Enter New Date (YYYY-MM-DD):", app.getDate());
        if (newDate == null || newDate.trim().isEmpty()) return;

        String newTime = JOptionPane.showInputDialog(this, "Enter New Time (HH:MM:SS):", app.getTime());
        if (newTime == null || newTime.trim().isEmpty()) return;

        if (appointmentDAO.rescheduleAppointment(app.getAppId(), newDate.trim(), newTime.trim())) {
            JOptionPane.showMessageDialog(this, "Appointment #APP-" + app.getAppId() + " rescheduled successfully!");
            loadAppointments();
        }
    }

    // Delete appointment
    private void deleteAppointment(Appointment app) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete Appointment #APP-" + app.getAppId() + " (" + app.getCustomer() + ")?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (appointmentDAO.deleteAppointment(app.getAppId())) {
                JOptionPane.showMessageDialog(this, "Appointment deleted successfully!");
                loadAppointments();
            }
        }
    }

    // Open New Booking action (2 lines)
    private void openNewBooking() {
        new NewBooking_SCREEN((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true).setVisible(true);
        loadAppointments();
    }

    // Check-in car to FIFO service queue (Green Button)
    private void checkInToQueue() {
        if (selectedAppointment == null) return;
        appointmentDAO.checkInToQueue(selectedAppointment.getAppId(), selectedAppointment.getRegNo());
        JOptionPane.showMessageDialog(this, "Vehicle " + selectedAppointment.getRegNo() + " checked-in to queue!");
        loadAppointments();
    }

    // Confirm booking in database (Navy Button)
    private void confirmBooking() {
        if (selectedAppointment == null) return;
        appointmentDAO.confirmAppointment(selectedAppointment.getAppId());
        JOptionPane.showMessageDialog(this, "Appointment confirmed!");
        loadAppointments();
    }

    // Return main panel for VehicleManagement tab integration
    public javax.swing.JPanel getAppointmentsPanel() {
        return jPanel1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(8, 37, 79));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(8, 37, 79));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(8, 37, 79));
        jLabel1.setText("Appointments");

        jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 210, 225), 1, true));
        jTable1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "App #", "Date & Time", "Reg No", "Vehicle", "Customer", "Service", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(38);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jTextField1.setPreferredSize(new java.awt.Dimension(210, 34));

        jButton1.setBackground(new java.awt.Color(8, 37, 79));
        jButton1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("+ New Booking");
        jButton1.setPreferredSize(new java.awt.Dimension(135, 34));

        jButton3.setText("All");
        jButton3.setPreferredSize(new java.awt.Dimension(65, 34));

        jButton4.setText("Confirmed");
        jButton4.setPreferredSize(new java.awt.Dimension(100, 34));

        jButton6.setText("Pending");
        jButton6.setPreferredSize(new java.awt.Dimension(90, 34));

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(208, 208, 208), 1, true));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(8, 37, 79));
        jLabel2.setText("Appointment Summary");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(100, 116, 139));
        jLabel3.setText("Appointment #APP-104");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(100, 116, 139));
        jLabel4.setText("Customer");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(30, 41, 59));
        jLabel5.setText("Hamza Tariq");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(100, 116, 139));
        jLabel6.setText("Vehicle & Reg No");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(30, 41, 59));
        jLabel7.setText("Mercedes-Benz AMG GT (AMG-063)");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(100, 116, 139));
        jLabel8.setText("Scheduled Time");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(30, 41, 59));
        jLabel9.setText("Today, 02:30 PM");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(100, 116, 139));
        jLabel10.setText("Service Package");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(30, 41, 59));
        jLabel11.setText("Oil & Filter Service");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(100, 116, 139));
        jLabel12.setText("Assigned Bay & Status");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(22, 163, 54));
        jLabel13.setText("Bay 2  •  Confirmed");

        jButton2.setBackground(new java.awt.Color(25, 135, 84));
        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Check-in to Queue");

        jButton5.setBackground(new java.awt.Color(8, 37, 79));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Confirm Booking");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, 300, 620, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("Appointments", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ignored) {}

        java.awt.EventQueue.invokeLater(() -> new Appointment_Screen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
