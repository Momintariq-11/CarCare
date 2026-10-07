/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PresentationLayer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import com.formdev.flatlaf.FlatLightLaf;
//imports data 
import BusinessLayer.Vehicle;
import BusinessLayer.Vehicle_BLL;

/**
 *
 * @author mmt
 */
public class VehicleManagement_Screen extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VehicleManagement_Screen.class.getName());

    /**
     * Creates new form dashboard
     */
    private final Color primaryBlue = new Color(8, 37, 79);
    private final Vehicle_BLL vehicleBLL = new Vehicle_BLL();
    private java.util.List<Vehicle> allVehicles = new java.util.ArrayList<>();
    private java.util.List<Vehicle> displayedVehicles = new java.util.ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 8;
    private Dashboard_Screen dashboardScreen;
    private CustomerManagement_Screen customerScreen;
    private ServiceQueue_Screen queueScreen;
    private Billing_Screen billingScreen;
    private Appointment_Screen appointmentScreen;
    private ServiceManagement_Screen serviceScreen;

    public VehicleManagement_Screen() {
        initTheme();
        initComponents();
        setupWindow();
        setupTabs();
        setupControls();
        setupTable();
        setupPagination();
        loadVehicles();
    }

    
    // 1. THEME INITIALIZATION
    private void initTheme() {
        try {
            // Apply FlatLaf clean modern look and feel
            javax.swing.UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 2. MAIN WINDOW CONFIGURATION
    private void setupWindow() {
        setTitle("Car-Care");
        setSize(1100, 700);
        setPreferredSize(new java.awt.Dimension(1100, 700));
        setMinimumSize(new java.awt.Dimension(1020, 640));
        setResizable(true);
        setLocationRelativeTo(null); // Center window on screen
        getContentPane().setBackground(primaryBlue); // Base navy background
    }

    // 3. TOP TAB BAR NAVIGATION (jTabbedPane1)
    private void setupTabs() {
        jTabbedPane1.setFont(new Font("Arial", Font.BOLD, 13));
        jTabbedPane1.setForeground(Color.WHITE);
        jTabbedPane1.setBackground(primaryBlue);
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.putClientProperty("JTabbedPane.tabType", "card");
        jTabbedPane1.putClientProperty("JTabbedPane.tabArc", 16); // 16px smooth rounded corners
        jTabbedPane1.putClientProperty("JTabbedPane.tabAreaInsets", new java.awt.Insets(8, 25, 0, 25));
        jTabbedPane1.putClientProperty("JTabbedPane.tabInsets", new java.awt.Insets(6, 20, 6, 20));
        jTabbedPane1.putClientProperty("JTabbedPane.tabHeight", 42);
        jTabbedPane1.putClientProperty("JTabbedPane.selectedBackground", Color.WHITE);
        jTabbedPane1.putClientProperty("JTabbedPane.selectedForeground", primaryBlue);
        jTabbedPane1.putClientProperty("JTabbedPane.showTabSeparators", false);
        jTabbedPane1.putClientProperty("JTabbedPane.tabWidthMode", "equal");
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        // Add Dashboard Tab as first tab (index 0)
        dashboardScreen = new Dashboard_Screen();
        jTabbedPane1.insertTab("  Dashboard", null, dashboardScreen.getDashboardPanel(), null, 0);

        // Add Customers Tab
        customerScreen = new CustomerManagement_Screen();
        jTabbedPane1.addTab("  Customers", customerScreen.getCustomerPanel());

        // Add Service Queue Tab
        queueScreen = new ServiceQueue_Screen();
        jTabbedPane1.addTab("  Service Queue", queueScreen.getServiceQueuePanel());

        // Add Billing Tab
        billingScreen = new Billing_Screen();
        jTabbedPane1.addTab("  Billing", billingScreen.getBillingPanel());

        // Add Appointments Tab
        appointmentScreen = new Appointment_Screen();
        jTabbedPane1.addTab("  Appointments", appointmentScreen.getAppointmentsPanel());

        // Add Services Tab
        serviceScreen = new ServiceManagement_Screen();
        jTabbedPane1.addTab("  Services", serviceScreen.getServicePanel());

        // Apply dark blue background to all tabs
        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
            jTabbedPane1.setBackgroundAt(i, primaryBlue);
            jTabbedPane1.setForegroundAt(i, Color.WHITE);
            ((javax.swing.JPanel) jTabbedPane1.getComponentAt(i)).setBackground(primaryBlue);
        }

        // Set Dashboard as active default tab
        jTabbedPane1.setSelectedIndex(0);

        // Auto-refresh data when switching tabs
        jTabbedPane1.addChangeListener(e -> {
            int idx = jTabbedPane1.getSelectedIndex();
            if (idx == 0 && dashboardScreen != null) {
                dashboardScreen.loadDashboardStats();
            } else if (idx == 1) {
                loadVehicles();
            } else if (idx == 2 && customerScreen != null) {
                customerScreen.loadCustomers();
            } else if (idx == 3 && queueScreen != null) {
                queueScreen.loadQueueData();
            } else if (idx == 4 && billingScreen != null) {
                billingScreen.loadBills();
            } else if (idx == 5 && appointmentScreen != null) {
                appointmentScreen.loadAppointments();
            } else if (idx == 6 && serviceScreen != null) {
                serviceScreen.loadServices();
                serviceScreen.loadVehicles();
            }
        });
    }

    // 4. HEADER CONTROLS, SEARCH BAR & ADD BUTTON
    private void setupControls() {
        // Outer Dark Blue Panel
        jPanel1.setBackground(primaryBlue);
        jPanel1.setOpaque(true);

        jPanel2.putClientProperty("FlatLaf.style", "arc: 25; background: #ffffff");

        // Screen Heading Label (jLabel1 - "Vehicle Management")
        jLabel1.setFont(new Font("Georgia", Font.BOLD, 22));
        jLabel1.setForeground(primaryBlue);

        // Search Input Field (jTextField1)
        jTextField1.setText("");
        jTextField1.setFont(new Font("Arial", Font.PLAIN, 13));
        jTextField1.putClientProperty("JTextField.arc", 20); // 20px rounded pill shape
        jTextField1.putClientProperty("JTextField.placeholderText", "Search vehicle...");
        jTextField1.putClientProperty("JTextField.showClearButton", true); // Clickable (X) to clear

        // SEARCH LISTENER: Automatically filters table in real-time as user types
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterVehicles(jTextField1.getText()); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterVehicles(jTextField1.getText()); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterVehicles(jTextField1.getText()); }
        });

        // BUTTON 1: "+ Add Vehicle" Button (jButton1)
        jButton1.setBackground(primaryBlue);
        jButton1.setForeground(Color.WHITE);
        jButton1.setFont(new Font("Arial", Font.BOLD, 13));
        jButton1.setPreferredSize(new java.awt.Dimension(135, 34));
        jButton1.setMinimumSize(new java.awt.Dimension(130, 34));
        jButton1.setBorderPainted(false);
        jButton1.setFocusPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.putClientProperty("JButton.buttonType", "roundRect");
        jButton1.putClientProperty("JButton.arc", 20); // 20px rounded corners
    }

    // 5. TABLE STYLING & ACTION BUTTONS (jTable1)
    private void setupTable() {
        // Table Header: Dark Blue with bold white text
        jTable1.getTableHeader().setBackground(primaryBlue);
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        jTable1.getTableHeader().setReorderingAllowed(false);

        // Table Rows & Body
        jTable1.setRowHeight(40);
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(false);
        jTable1.setGridColor(new Color(230, 230, 230));
        jTable1.setFont(new Font("Arial", Font.PLAIN, 13));
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        // Alternating Zebra Row Colors across ALL data columns (White & Soft Blue)
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 247, 252));
                }
                setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Nice clean cell padding
                return c;
            }
        });

        // ACTION BUTTONS: Load and scale 'edit.png' & 'trash.png'
        java.net.URL editUrl = getClass().getResource("/PresentationLayer/assets/edit.png");
        java.net.URL trashUrl = getClass().getResource("/PresentationLayer/assets/trash.png");
        javax.swing.ImageIcon editIcon = (editUrl != null) ? new javax.swing.ImageIcon(new javax.swing.ImageIcon(editUrl).getImage().getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)) : null;
        javax.swing.ImageIcon trashIcon = (trashUrl != null) ? new javax.swing.ImageIcon(new javax.swing.ImageIcon(trashUrl).getImage().getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)) : null;

        // ACTION COLUMN (Index 6): Render Edit & Delete icons
        jTable1.getColumnModel().getColumn(6).setMinWidth(90);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(130);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 14, 8));
                panel.setOpaque(true);
                panel.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? Color.WHITE : new Color(245, 247, 252)));

                // Edit Icon Button
                javax.swing.JLabel lblEdit = new javax.swing.JLabel(editIcon);
                lblEdit.setToolTipText("Edit Vehicle");
                lblEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Delete (Trash) Icon Button
                javax.swing.JLabel lblTrash = new javax.swing.JLabel(trashIcon);
                lblTrash.setToolTipText("Delete Vehicle");
                lblTrash.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                panel.add(lblEdit);
                panel.add(lblTrash);
                return panel;
            }
        });

        // ACTION BUTTONS CLICK LISTENER: Detects clicks on Edit vs Delete
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = jTable1.rowAtPoint(e.getPoint());
                int col = jTable1.columnAtPoint(e.getPoint());

                if (col == 6 && row >= 0) { // Action column
                    int cellX = e.getX() - jTable1.getCellRect(row, col, false).x;
                    int cellWidth = jTable1.getColumnModel().getColumn(col).getWidth();
                    String regNo = (String) jTable1.getValueAt(row, 0);

                    // 1. EDIT ACTION (Clicked on left half of cell)
                    if (cellX < cellWidth / 2) {
                        Vehicle selectedVehicle = null;
                        for (Vehicle v : allVehicles) {
                            if (v.getRegistrationNo().equals(regNo)) {
                                selectedVehicle = v;
                                break;
                            }
                        }
                        if (selectedVehicle != null) {
                            EditVehicle_SCREEN editDialog = new EditVehicle_SCREEN(VehicleManagement_Screen.this, true);
                            editDialog.setVehicleData(selectedVehicle);
                            editDialog.setVisible(true);
                            loadVehicles(); // Refresh table from database
                        }
                    } 
                    // 2. DELETE ACTION (Clicked on right half of cell)
                    else {
                        int confirm = javax.swing.JOptionPane.showConfirmDialog(VehicleManagement_Screen.this,
                                "Are you sure you want to delete vehicle " + regNo + "?",
                                "Delete Vehicle", javax.swing.JOptionPane.YES_NO_OPTION);

                        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                            if (vehicleBLL.deleteVehicle(regNo)) {
                                javax.swing.JOptionPane.showMessageDialog(VehicleManagement_Screen.this,
                                        "Vehicle " + regNo + " deleted successfully!",
                                        "Deleted", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                loadVehicles(); // Refresh table from database
                            } else {
                                javax.swing.JOptionPane.showMessageDialog(VehicleManagement_Screen.this,
                                        "Failed to delete vehicle.",
                                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });
    }

    // 6. PAGINATION CONTROLS & BUTTON LISTENERS
    private void setupPagination() {
        // Counter Label (jLabel3 - "Showing X–Y out of Z Vehicles")
        jLabel3.setFont(new Font("Arial", Font.PLAIN, 12));
        jLabel3.setForeground(new Color(120, 120, 120));

        // Style all pagination buttons (<, 1, 2, 3, 4, >)
        javax.swing.JButton[] pageButtons = {jButton2, jButton3, jButton4, jButton5, jButton6, jButton7};
        for (javax.swing.JButton btn : pageButtons) {
            btn.putClientProperty("JButton.buttonType", "roundRect");
            btn.putClientProperty("JButton.arc", 8);
            btn.setFocusPainted(false);
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }

        // Window Resize Listener: 8 rows on normal screen, 16 rows on full screen
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int newSize = getHeight() >= 780 ? 15 : 8;
                if (newSize != pageSize) {
                    pageSize = newSize;
                    displayPage(1); // Reset to page 1 on resize
                }
            }
        });

        // BUTTON 2: Previous Page Button ('<')
        jButton2.addActionListener(e -> displayPage(currentPage - 1));

        // BUTTON 3: Page 1 Button ('1')
        jButton3.addActionListener(e -> displayPage(1));

        // BUTTON 4: Page 2 Button ('2')
        jButton4.addActionListener(e -> displayPage(2));

        // BUTTON 5: Page 3 Button ('3')
        jButton5.addActionListener(e -> displayPage(3));

        // BUTTON 6: Page 4 Button ('4')
        jButton6.addActionListener(e -> displayPage(4));

        // BUTTON 7: Next Page Button ('>')
        jButton7.addActionListener(e -> displayPage(currentPage + 1));
    }

    // 7. SEARCH FILTER: Filters vehicles by Reg No, Make, Model, or Customer Name
    private void filterVehicles(String query) {
        if (query == null || query.trim().isEmpty()) {
            displayedVehicles = new java.util.ArrayList<>(allVehicles);
        } else {
            String q = query.trim().toLowerCase();
            displayedVehicles = new java.util.ArrayList<>();
            for (Vehicle v : allVehicles) {
                if (v.getRegistrationNo().toLowerCase().contains(q)
                        || v.getMake().toLowerCase().contains(q)
                        || v.getModel().toLowerCase().contains(q)
                        || v.getCustomerName().toLowerCase().contains(q)) {
                    displayedVehicles.add(v);
                }
            }
        }
        displayPage(1); // Always reset to page 1 for search results
    }

    // 8. DATABASE LOADER: Fetch all vehicles from BLL
    
    public void loadVehicles() {
        allVehicles = vehicleBLL.getAllVehicles();
        filterVehicles(jTextField1.getText());
    }

    // 9. PAGINATION RENDERER: Slices vehicle list and updates UI
    public void displayPage(int page) {
        int totalVehicles = displayedVehicles.size();
        int totalPages = (int) Math.ceil((double) totalVehicles / pageSize);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;
        currentPage = page;

        // Calculate slice range for the current page
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalVehicles);

        // Populate Table Model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear old rows

        for (int i = startIndex; i < endIndex; i++) {
            Vehicle v = displayedVehicles.get(i);
            model.addRow(new Object[]{
                v.getRegistrationNo(),
                v.getMake(),
                v.getModel(),
                v.getVehicleYear(),
                v.getMileage() + " km",
                v.getCustomerName(),
                "Edit | Delete"
            });
        }

        // Update Counter Label
        jLabel3.setText(totalVehicles == 0 ? "Showing 0 Vehicles" : "Showing " + (startIndex + 1) + "–" + endIndex + " out of " + totalVehicles + " Vehicles");

        // Dynamic Pagination Visibility (Only show buttons needed for actual pages)
        jButton2.setVisible(totalPages > 1);
        jButton3.setVisible(totalPages >= 1);
        jButton4.setVisible(totalPages >= 2);
        jButton5.setVisible(totalPages >= 3);
        jButton6.setVisible(totalPages >= 4);
        jButton7.setVisible(totalPages > 1);
        jButton2.setEnabled(currentPage > 1);
        jButton7.setEnabled(currentPage < totalPages);

        // Highlight Active Page Button (Dark Blue = Active, White = Inactive)
        jButton3.setBackground(currentPage == 1 ? primaryBlue : Color.WHITE);
        jButton3.setForeground(currentPage == 1 ? Color.WHITE : primaryBlue);
        jButton4.setBackground(currentPage == 2 ? primaryBlue : Color.WHITE);
        jButton4.setForeground(currentPage == 2 ? Color.WHITE : primaryBlue);
        jButton5.setBackground(currentPage == 3 ? primaryBlue : Color.WHITE);
        jButton5.setForeground(currentPage == 3 ? Color.WHITE : primaryBlue);
        jButton6.setBackground(currentPage == 4 ? primaryBlue : Color.WHITE);
        jButton6.setForeground(currentPage == 4 ? Color.WHITE : primaryBlue);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setForeground(new java.awt.Color(215, 82, 17));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(8, 37, 79));
        jLabel1.setText("Vehicle Management");

        jButton1.setBackground(new java.awt.Color(8, 37, 79));
        jButton1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("+ Add Vehicle");
        jButton1.setBorderPainted(false);
        jButton1.setSize(new java.awt.Dimension(120, 38));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jTextField1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.setText("Search vehicle... ");
        jTextField1.setSize(new java.awt.Dimension(220, 32));
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jTable1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Registration No.", "Make", "Model", "Year", "Mileage", "Customer", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(32);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Showing 1–5 out of 18 Vehicles");

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(8, 37, 79));
        jButton2.setText("<");
        jButton2.setSize(new java.awt.Dimension(35, 30));

        jButton3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(8, 37, 79));
        jButton3.setText("1");
        jButton3.setSize(new java.awt.Dimension(35, 30));

        jButton4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(8, 37, 79));
        jButton4.setText("2");
        jButton4.setSize(new java.awt.Dimension(35, 30));

        jButton5.setText("3");
        jButton5.setSize(new java.awt.Dimension(35, 30));

        jButton6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(8, 37, 79));
        jButton6.setText("4");
        jButton6.setSize(new java.awt.Dimension(35, 30));

        jButton7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(8, 37, 79));
        jButton7.setText(">");
        jButton7.setSize(new java.awt.Dimension(35, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1)
                .addGap(25, 25, 25))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jButton1)
                .addGap(25, 25, 25))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3, jButton4, jButton5, jButton6, jButton7});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7)
                            .addComponent(jButton6)))
                    .addComponent(jLabel3))
                .addGap(16, 16, 16))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3, jButton4, jButton5, jButton6, jButton7});

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

        jTabbedPane1.addTab(" Vehicle", jPanel1);

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

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        filterVehicles(jTextField1.getText());
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                                            
        // Add Vehicle dialog ko modal (popup) ke tor par open karein
        AddVehicle_SCREEN dialog = new AddVehicle_SCREEN(this, true);
        dialog.setVisible(true);
        
        // Jab user dialog band karega ya save karega, to table foran refresh ho jayegi:
        loadVehicles();
        }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VehicleManagement_Screen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
