package PresentationLayer;

import BusinessLayer.Bill;
import BusinessLayer.ServiceTicket;
import DataAccessLayer.Billing_DAO;
import DataAccessLayer.Customer_DAO;
import DataAccessLayer.ServiceQueue_DAO;
import DataAccessLayer.Vehicle_DAO;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;

/**
 * Dashboard_Screen - Workshop Management Dashboard Tab
 */
public class Dashboard_Screen extends javax.swing.JFrame {

    // Data Access Objects to interact with MySQL database
    private final Customer_DAO customerDAO = new Customer_DAO();
    private final Vehicle_DAO vehicleDAO = new Vehicle_DAO();
    private final ServiceQueue_DAO queueDAO = new ServiceQueue_DAO();
    private final Billing_DAO billingDAO = new Billing_DAO();
    private final Color primaryBlue = new Color(8, 37, 79);

    public Dashboard_Screen() {
        initComponents();
        setupWindow();
        setupTabs();
        setupDesign();
        setupTable();
        loadDashboardData();
    }

    // 1. Configures window dimensions and background
    private void setupWindow() {
        setTitle("Car-Care");
        setSize(1100, 700);
        setPreferredSize(new java.awt.Dimension(1100, 700));
        setMinimumSize(new java.awt.Dimension(1020, 640));
        setLocationRelativeTo(null);
        getContentPane().setBackground(primaryBlue);
    }

    // 2. Configures top tab navigation styling (42px height, navy blue)
    private void setupTabs() {
        jTabbedPane1.setFont(new Font("Arial", Font.BOLD, 13));
        jTabbedPane1.setForeground(Color.WHITE);
        jTabbedPane1.setBackground(primaryBlue);
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.putClientProperty("JTabbedPane.tabType", "card");
        jTabbedPane1.putClientProperty("JTabbedPane.tabArc", 16);
        jTabbedPane1.putClientProperty("JTabbedPane.tabAreaInsets", new java.awt.Insets(8, 25, 0, 25));
        jTabbedPane1.putClientProperty("JTabbedPane.tabInsets", new java.awt.Insets(6, 20, 6, 20));
        jTabbedPane1.putClientProperty("JTabbedPane.tabHeight", 42);
        jTabbedPane1.putClientProperty("JTabbedPane.selectedBackground", Color.WHITE);
        jTabbedPane1.putClientProperty("JTabbedPane.selectedForeground", primaryBlue);
        jTabbedPane1.putClientProperty("JTabbedPane.showTabSeparators", false);
        jTabbedPane1.putClientProperty("JTabbedPane.tabWidthMode", "equal");
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
            jTabbedPane1.setBackgroundAt(i, primaryBlue);
            jTabbedPane1.setForegroundAt(i, Color.WHITE);
        }
    }

    // 3. Returns main panel to embed inside top tabbed navigation
    public javax.swing.JPanel getDashboardPanel() {
        return jPanel1;
    }

    // 4. Applies modern theme colors, rounded corners to cards and buttons
    private void setupDesign() {
        // Outer card container
        jPanel2.putClientProperty("FlatLaf.style", "arc: 25; background: #ffffff");

        // 1. Total Customers: Indigo theme
        styleCard(card1, new Color(248, 250, 255), new Color(67, 56, 202));

        // 2. Total Vehicles: Car Blue theme
        styleCard(card2, new Color(240, 249, 255), new Color(2, 132, 199));

        // 3. Waiting in Queue: Amber Gold theme
        styleCard(card3, new Color(255, 251, 235), new Color(217, 119, 6));

        // 4. Currently Under Service: Purple theme
        styleCard(card4, new Color(250, 245, 255), new Color(124, 58, 237));

        // 5. Completed Services: Teal theme
        styleCard(card5, new Color(240, 253, 250), new Color(13, 148, 136));

        // 6. Total Revenue: Green theme with rounded edges
        styleCard(card6, new Color(240, 253, 244), new Color(22, 163, 74));

        // 20px rounded pill buttons with domain colors
        btnRegister.putClientProperty("JButton.buttonType", "roundRect");
        btnRegister.putClientProperty("JButton.arc", 20);
        btnRegister.setBackground(new Color(2, 132, 199));

        btnAppointment.putClientProperty("JButton.buttonType", "roundRect");
        btnAppointment.putClientProperty("JButton.arc", 20);
        btnAppointment.setBackground(primaryBlue);

        btnInvoice.putClientProperty("JButton.buttonType", "roundRect");
        btnInvoice.putClientProperty("JButton.arc", 20);
        btnInvoice.setBackground(new Color(22, 163, 74));
    }

    // Helper: styles individual metric card with background, rounded border, and arc
    private void styleCard(javax.swing.JPanel card, Color bg, Color borderColor) {
        String hexBg = String.format("#%02x%02x%02x", bg.getRed(), bg.getGreen(), bg.getBlue());
        String hexBorder = String.format("#%02x%02x%02x", borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue());
        card.putClientProperty("FlatLaf.style", "arc: 16; background: " + hexBg + "; border: 1,1,1,1," + hexBorder + ",1.5,16");
        card.setBackground(bg);
        card.setBorder(new FlatLineBorder(new java.awt.Insets(1, 1, 1, 1), borderColor, 1.5f, 16));
    }

    // 5. Configures the Recent Service Activity table styling
    private void setupTable() {
        jTable1.getTableHeader().setBackground(primaryBlue);
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 32));
        jTable1.getTableHeader().setReorderingAllowed(false);

        jTable1.setRowHeight(32);
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(false);
        jTable1.setGridColor(new Color(230, 235, 245));
        jTable1.setFont(new Font("Arial", Font.PLAIN, 12));
        jTable1.setFillsViewportHeight(true);
        jTable1.setSelectionBackground(new Color(224, 242, 254));
        jTable1.setSelectionForeground(new Color(3, 105, 161));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        // Alternating zebra row colors and colored status labels
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }

                // Color-code the Status column
                if (column == 5 && value != null) {
                    String status = value.toString();
                    setFont(new Font("Arial", Font.BOLD, 12));
                    if (status.equalsIgnoreCase("Waiting")) {
                        setForeground(new Color(180, 83, 9));
                    } else if (status.equalsIgnoreCase("Completed")) {
                        setForeground(new Color(21, 128, 61));
                    } else {
                        setForeground(new Color(109, 40, 217));
                    }
                } else if (!isSelected) {
                    setForeground(new Color(30, 41, 59));
                    setFont(new Font("Arial", Font.PLAIN, 12));
                }
                setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    // 6. Fetches the 6 dashboard metrics and recent activity from database
    public void loadDashboardData() {
        try {
            // 1. Total Customers
            lblCustomers.setText(String.valueOf(customerDAO.getAllCustomers().size()));

            // 2. Total Vehicles
            lblVehicles.setText(String.valueOf(vehicleDAO.getAllVehicles().size()));

            // 3. Vehicles Waiting in Queue
            lblQueue.setText(queueDAO.getWaitingQueue().size() + " Cars");

            // 4. Vehicles Currently Under Service
            lblUnderService.setText(queueDAO.getUnderServiceCount() + " Cars");

            // 5. Completed Services
            lblCompleted.setText(String.valueOf(queueDAO.getCompletedCount()));

            // 6. Total Revenue
            double revenue = 0;
            for (Bill b : billingDAO.getAllBills()) {
                revenue += b.getTotal();
            }
            lblRevenue.setText(String.format("Rs. %,.0f", revenue));

            // 7. Recent Service Activity Table
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            for (ServiceTicket ticket : queueDAO.getRecentQueueTickets(15)) {
                model.addRow(new Object[]{
                    "#" + ticket.getQueueId(),
                    ticket.getRegistrationNo(),
                    ticket.getCustomerName() != null ? ticket.getCustomerName() : "N/A",
                    ticket.getServiceName() != null ? ticket.getServiceName() : "General Service",
                    ticket.getMechanicName() != null ? ticket.getMechanicName() : "Unassigned",
                    ticket.getStatus()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Alias for tab change listener
    public void loadDashboardStats() {
        loadDashboardData();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        card1 = new javax.swing.JPanel();
        lblTitle1 = new javax.swing.JLabel();
        lblCustomers = new javax.swing.JLabel();
        lblSub1 = new javax.swing.JLabel();
        card2 = new javax.swing.JPanel();
        lblTitle2 = new javax.swing.JLabel();
        lblVehicles = new javax.swing.JLabel();
        lblSub2 = new javax.swing.JLabel();
        card3 = new javax.swing.JPanel();
        lblTitle3 = new javax.swing.JLabel();
        lblQueue = new javax.swing.JLabel();
        lblSub3 = new javax.swing.JLabel();
        card4 = new javax.swing.JPanel();
        lblTitle4 = new javax.swing.JLabel();
        lblUnderService = new javax.swing.JLabel();
        lblSub4 = new javax.swing.JLabel();
        card5 = new javax.swing.JPanel();
        lblTitle5 = new javax.swing.JLabel();
        lblCompleted = new javax.swing.JLabel();
        lblSub5 = new javax.swing.JLabel();
        card6 = new javax.swing.JPanel();
        lblTitle6 = new javax.swing.JLabel();
        lblRevenue = new javax.swing.JLabel();
        lblSub6 = new javax.swing.JLabel();
        lblQuickOps = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        btnAppointment = new javax.swing.JButton();
        btnInvoice = new javax.swing.JButton();
        lblRecentActivity = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblCarCare = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Car-Care");

        jTabbedPane1.setBackground(new java.awt.Color(8, 37, 79));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(8, 37, 79));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(8, 37, 79));
        jLabel1.setText("Dashboard");

        card1.setBackground(new java.awt.Color(248, 250, 255));
        card1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(67, 56, 202), 1, true));

        lblTitle1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(71, 85, 105));
        lblTitle1.setText("Total Customers");

        lblCustomers.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        lblCustomers.setForeground(new java.awt.Color(30, 27, 75));
        lblCustomers.setText("0");

        lblSub1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSub1.setForeground(new java.awt.Color(100, 116, 139));
        lblSub1.setText("Registered vehicle owners");

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(lblCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSub1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        card1Layout.setVerticalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle1)
                .addGap(10, 10, 10)
                .addComponent(lblCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblSub1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        card2.setBackground(new java.awt.Color(240, 249, 255));
        card2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(2, 132, 199), 1, true));

        lblTitle2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(3, 105, 161));
        lblTitle2.setText("Total Vehicles");

        lblVehicles.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        lblVehicles.setForeground(new java.awt.Color(2, 132, 199));
        lblVehicles.setText("0");

        lblSub2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSub2.setForeground(new java.awt.Color(2, 132, 199));
        lblSub2.setText("Active vehicles managed");

        javax.swing.GroupLayout card2Layout = new javax.swing.GroupLayout(card2);
        card2.setLayout(card2Layout);
        card2Layout.setHorizontalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(lblVehicles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSub2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        card2Layout.setVerticalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle2)
                .addGap(10, 10, 10)
                .addComponent(lblVehicles, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblSub2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        card3.setBackground(new java.awt.Color(255, 251, 235));
        card3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(217, 119, 6), 1, true));

        lblTitle3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(180, 83, 9));
        lblTitle3.setText("Waiting in Queue");

        lblQueue.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        lblQueue.setForeground(new java.awt.Color(180, 83, 9));
        lblQueue.setText("0 Cars");

        lblSub3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSub3.setForeground(new java.awt.Color(146, 64, 14));
        lblSub3.setText("Awaiting service bay assignment");

        javax.swing.GroupLayout card3Layout = new javax.swing.GroupLayout(card3);
        card3.setLayout(card3Layout);
        card3Layout.setHorizontalGroup(
            card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(lblQueue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSub3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        card3Layout.setVerticalGroup(
            card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle3)
                .addGap(10, 10, 10)
                .addComponent(lblQueue, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblSub3)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        card4.setBackground(new java.awt.Color(250, 245, 255));
        card4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(124, 58, 237), 1, true));

        lblTitle4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTitle4.setForeground(new java.awt.Color(109, 40, 217));
        lblTitle4.setText("Currently Under Service");

        lblUnderService.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        lblUnderService.setForeground(new java.awt.Color(91, 33, 182));
        lblUnderService.setText("0 Cars");

        lblSub4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSub4.setForeground(new java.awt.Color(124, 58, 237));
        lblSub4.setText("Active service in workshop bays");

        javax.swing.GroupLayout card4Layout = new javax.swing.GroupLayout(card4);
        card4.setLayout(card4Layout);
        card4Layout.setHorizontalGroup(
            card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle4, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(lblUnderService, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSub4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        card4Layout.setVerticalGroup(
            card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle4)
                .addGap(10, 10, 10)
                .addComponent(lblUnderService, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblSub4)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        card5.setBackground(new java.awt.Color(240, 253, 250));
        card5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(13, 148, 136), 1, true));

        lblTitle5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTitle5.setForeground(new java.awt.Color(15, 118, 110));
        lblTitle5.setText("Completed Services");

        lblCompleted.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        lblCompleted.setForeground(new java.awt.Color(17, 94, 89));
        lblCompleted.setText("0");

        lblSub5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSub5.setForeground(new java.awt.Color(15, 118, 110));
        lblSub5.setText("Total completed repair jobs");

        javax.swing.GroupLayout card5Layout = new javax.swing.GroupLayout(card5);
        card5.setLayout(card5Layout);
        card5Layout.setHorizontalGroup(
            card5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(card5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle5, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(lblCompleted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSub5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        card5Layout.setVerticalGroup(
            card5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle5)
                .addGap(10, 10, 10)
                .addComponent(lblCompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblSub5)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        card6.setBackground(new java.awt.Color(240, 253, 244));
        card6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(22, 163, 74), 1, true));

        lblTitle6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTitle6.setForeground(new java.awt.Color(21, 128, 61));
        lblTitle6.setText("Total Revenue");

        lblRevenue.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        lblRevenue.setForeground(new java.awt.Color(20, 83, 45));
        lblRevenue.setText("Rs. 0");

        lblSub6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblSub6.setForeground(new java.awt.Color(22, 101, 52));
        lblSub6.setText("Total billed and collected");

        javax.swing.GroupLayout card6Layout = new javax.swing.GroupLayout(card6);
        card6.setLayout(card6Layout);
        card6Layout.setHorizontalGroup(
            card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle6, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(lblRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSub6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        card6Layout.setVerticalGroup(
            card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle6)
                .addGap(10, 10, 10)
                .addComponent(lblRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblSub6)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lblQuickOps.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblQuickOps.setForeground(new java.awt.Color(8, 37, 79));
        lblQuickOps.setText("Quick Operations");

        btnRegister.setBackground(new java.awt.Color(2, 132, 199));
        btnRegister.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("Register New Vehicle");
        btnRegister.setBorderPainted(false);
        btnRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnAppointment.setBackground(new java.awt.Color(8, 37, 79));
        btnAppointment.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnAppointment.setForeground(new java.awt.Color(255, 255, 255));
        btnAppointment.setText("Book New Appointment");
        btnAppointment.setBorderPainted(false);
        btnAppointment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAppointmentActionPerformed(evt);
            }
        });

        btnInvoice.setBackground(new java.awt.Color(22, 163, 74));
        btnInvoice.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnInvoice.setForeground(new java.awt.Color(255, 255, 255));
        btnInvoice.setText("Generate Customer Invoice");
        btnInvoice.setBorderPainted(false);
        btnInvoice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoiceActionPerformed(evt);
            }
        });

        lblRecentActivity.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblRecentActivity.setForeground(new java.awt.Color(8, 37, 79));
        lblRecentActivity.setText("Recent Service Activity");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Queue #", "Vehicle No", "Customer", "Service", "Mechanic", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(32);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        lblCarCare.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        lblCarCare.setForeground(new java.awt.Color(128, 144, 160));
        lblCarCare.setText("CarCare");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(card1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(card4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(card2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(card5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(card6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblQuickOps, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(btnAppointment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(btnInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblRecentActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)
                    .addComponent(lblCarCare))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(lblQuickOps)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(lblRecentActivity)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(lblCarCare)
                .addGap(10, 10, 10))
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

        jTabbedPane1.addTab("Dashboard", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Quick Operation 1: Register New Vehicle
    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        new AddVehicle_SCREEN(this, true).setVisible(true);
        loadDashboardData();
    }//GEN-LAST:event_btnRegisterActionPerformed

    // Quick Operation 2: Book New Appointment
    private void btnAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppointmentActionPerformed
        new NewBooking_SCREEN(this, true).setVisible(true);
        loadDashboardData();
    }//GEN-LAST:event_btnAppointmentActionPerformed

    // Quick Operation 3: Generate Customer Invoice
    private void btnInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoiceActionPerformed
        new Billing_Screen().setVisible(true);
    }//GEN-LAST:event_btnInvoiceActionPerformed

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new Dashboard_Screen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAppointment;
    private javax.swing.JButton btnInvoice;
    private javax.swing.JButton btnRegister;
    private javax.swing.JPanel card1;
    private javax.swing.JPanel card2;
    private javax.swing.JPanel card3;
    private javax.swing.JPanel card4;
    private javax.swing.JPanel card5;
    private javax.swing.JPanel card6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCarCare;
    private javax.swing.JLabel lblCompleted;
    private javax.swing.JLabel lblCustomers;
    private javax.swing.JLabel lblQueue;
    private javax.swing.JLabel lblQuickOps;
    private javax.swing.JLabel lblRecentActivity;
    private javax.swing.JLabel lblRevenue;
    private javax.swing.JLabel lblSub1;
    private javax.swing.JLabel lblSub2;
    private javax.swing.JLabel lblSub3;
    private javax.swing.JLabel lblSub4;
    private javax.swing.JLabel lblSub5;
    private javax.swing.JLabel lblSub6;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblTitle4;
    private javax.swing.JLabel lblTitle5;
    private javax.swing.JLabel lblTitle6;
    private javax.swing.JLabel lblUnderService;
    private javax.swing.JLabel lblVehicles;
    // End of variables declaration//GEN-END:variables
}
