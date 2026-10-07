package PresentationLayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import BusinessLayer.Bill;
import DataAccessLayer.Billing_DAO;

/**
 * CarCare - Billing & Invoicing Management Screen
 */
public class Billing_Screen extends javax.swing.JFrame {

    private final Color primaryBlue = new Color(8, 37, 79);
    private final Billing_DAO billingDAO = new Billing_DAO();
    private List<Bill> allBills = new ArrayList<>();
    private List<Bill> displayedBills = new ArrayList<>();
    private Bill selectedBill = null;
    private final ButtonGroup paymentGroup = new ButtonGroup();

    /**
     * Creates new form Billing_Screen
     */
    public Billing_Screen() {
        initComponents();
        setupWindow();
        setupRoundedCorners();
        setupTable();
        paymentGroup.add(jRadioButton1);
        paymentGroup.add(jRadioButton2);
        jRadioButton1.setSelected(true);
        setupEvents();
        loadBills();
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

    // Rounded Corners for Main Card & Buttons (Matches other tabs)
    private void setupRoundedCorners() {
        jPanel1.setBackground(primaryBlue);
        jPanel1.setOpaque(true);

        jPanel2.putClientProperty("FlatLaf.style", "arc: 25; background: #ffffff");

        // Pill rounded corners for inputs and buttons
        jTextField1.putClientProperty("JComponent.roundRect", true);
        jTextField1.putClientProperty("JTextField.placeholderText", "Search by Inv #, Reg No, Customer...");
        jComboBox1.putClientProperty("JComponent.roundRect", true);

        jButton1.putClientProperty("JButton.buttonType", "roundRect");
        jButton2.putClientProperty("JButton.buttonType", "roundRect");
        jButton3.putClientProperty("JButton.buttonType", "roundRect");
        jButton4.putClientProperty("JButton.buttonType", "roundRect");
        jButton5.putClientProperty("JButton.buttonType", "roundRect");
        jButton6.putClientProperty("JButton.buttonType", "roundRect");
    }

    // Table Header & Row Styling matching Vehicle & Customer screens
    private void setupTable() {
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
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
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
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setForeground(new java.awt.Color(215, 82, 17));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(8, 37, 79));
        jLabel1.setText("Billing & Invoicing");

        jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 210, 225), 1, true));
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
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Invoice #", "Date", "Reg No", "Vehicle", "Customer", "Amount", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(32);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("All");
        jButton3.setPreferredSize(new java.awt.Dimension(90, 40));

        jButton4.setText("Paid");
        jButton4.setPreferredSize(new java.awt.Dimension(90, 40));

        jButton6.setText("Pending");
        jButton6.setPreferredSize(new java.awt.Dimension(90, 40));

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(8, 37, 79));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Invoice #INV-1042");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(100, 116, 139));
        jLabel3.setText("Ali Ahmed • Honda Civic (ABC-3228)");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(30, 41, 59));
        jLabel4.setText("Oil Change                                                 $50");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(30, 41, 59));
        jLabel5.setText("Diagnostics                                                $30");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(30, 41, 59));
        jLabel6.setText("Labor                                                         $20");

        jButton1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(8, 37, 79));
        jButton1.setText("+ Add Extra Part ");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(71, 85, 105));
        jLabel7.setText("Subtotal:                                           $100.00");

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None (0%)", "5% Discount ", "10% Discount" }));

        jLabel8.setText("Discount");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(220, 38, 38));
        jLabel10.setText("-$5.00");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(71, 85, 105));
        jLabel11.setText("Tax:                                                        $5.00");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(25, 135, 84));
        jLabel13.setText(" Grand Total:                $95.00");

        jRadioButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jRadioButton1.setText("Cash");

        jRadioButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jRadioButton2.setText("Card");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Payment Method:");

        jButton2.setBackground(new java.awt.Color(25, 135, 84));
        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Collect Payment");

        jButton5.setBackground(new java.awt.Color(8, 37, 79));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Print Receipt");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(63, 63, 63)
                                        .addComponent(jLabel10))
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2))))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
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
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
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

        jTabbedPane1.addTab("Service queue", jPanel1);

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

    // ==========================================
    // SIMPLE BILLING HELPER METHODS
    // ==========================================

    private void setupEvents() {
        // 1. Table row click: show selected bill on right card
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            int row = jTable1.getSelectedRow();
            if (row != -1 && row < displayedBills.size()) {
                showBillDetails(displayedBills.get(row));
            }
        });

        // 2. Search bar typing
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                filterBills();
            }
        });

        // 3. Filter buttons (All, Paid, Pending)
        jButton3.addActionListener(e -> filterStatus("All"));
        jButton4.addActionListener(e -> filterStatus("Paid"));
        jButton6.addActionListener(e -> filterStatus("Unpaid"));

        // 4. Action buttons & Discount dropdown
        jComboBox1.addActionListener(e -> applyDiscount());
        jButton1.addActionListener(e -> addExtraPart());
        jButton2.addActionListener(e -> collectPayment());
        jButton5.addActionListener(e -> printReceipt());
    }

    // Load all bills from database
    public void loadBills() {
        allBills = billingDAO.getAllBills();
        filterBills();
    }

    // Filter bills by search bar query
    private void filterBills() {
        String query = jTextField1.getText().trim().toLowerCase();
        displayedBills = new ArrayList<>();
        for (Bill b : allBills) {
            if (query.isEmpty() || b.getCustomer().toLowerCase().contains(query) || b.getRegNo().toLowerCase().contains(query)) {
                displayedBills.add(b);
            }
        }
        refreshTable();
    }

    // Filter bills by status (All, Paid, Unpaid)
    private void filterStatus(String status) {
        displayedBills = new ArrayList<>();
        for (Bill b : allBills) {
            if (status.equalsIgnoreCase("All") || b.getStatus().equalsIgnoreCase(status)) {
                displayedBills.add(b);
            }
        }
        refreshTable();
    }

    // Refresh table rows
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (Bill b : displayedBills) {
            model.addRow(new Object[]{
                "INV-" + b.getBillId(), "Today", b.getRegNo(), b.getVehicle(), b.getCustomer(), "Rs. " + String.format("%.0f", b.getTotal()), b.getStatus()
            });
        }
        if (!displayedBills.isEmpty()) {
            jTable1.setRowSelectionInterval(0, 0);
            showBillDetails(displayedBills.get(0));
        }
    }

    // Display selected bill details on the right card
    private void showBillDetails(Bill b) {
        selectedBill = b;
        jLabel2.setText("Invoice #INV-" + b.getBillId());
        jLabel3.setText(b.getCustomer() + " • " + b.getVehicle() + " (" + b.getRegNo() + ")");

        int w = jPanel3.getWidth() > 50 ? (jPanel3.getWidth() - 30) : 290;
        double total = b.getTotal();
        double baseCost = total >= 5000 ? 5000 : total;
        double extraParts = total > 5000 ? (total - 5000) : 0;

        jLabel4.setText("<html><table width='" + w + "'><tr><td>• " + b.getService() + "</td><td align='right'><b>Rs. " + String.format("%.0f", baseCost) + "</b></td></tr></table></html>");
        if (extraParts > 0) {
            jLabel5.setText("<html><table width='" + w + "'><tr><td>• Extra Parts / Addons</td><td align='right'><b>Rs. " + String.format("%.0f", extraParts) + "</b></td></tr></table></html>");
        } else {
            jLabel5.setText("<html><table width='" + w + "'><tr><td>• Diagnostics & Inspection</td><td align='right'><font color='#16a34a'>Included</font></td></tr></table></html>");
        }
        jLabel6.setText("<html><table width='" + w + "'><tr><td>• Workshop Labor Charges</td><td align='right'><font color='#16a34a'>Included</font></td></tr></table></html>");
        jLabel7.setText("<html><table width='" + w + "'><tr><td><b>Subtotal:</b></td><td align='right'><b>Rs. " + String.format("%.0f", total) + "</b></td></tr></table></html>");

        jLabel10.setText("-Rs. 0");
        jLabel11.setText("<html><table width='" + w + "'><tr><td>Tax (0%):</td><td align='right'>Rs. 0</td></tr></table></html>");
        jLabel13.setText("<html><table width='" + w + "'><tr><td><font size='+1'><b>Grand Total:</b></font></td><td align='right'><font size='+2' color='#198754'><b>Rs. " + String.format("%.0f", total) + "</b></font></td></tr></table></html>");
        jComboBox1.setSelectedIndex(0);

        boolean isPaid = b.getStatus().equalsIgnoreCase("Paid");
        jButton2.setEnabled(!isPaid);
        jButton2.setText(isPaid ? "Paid" : "Collect Payment");
    }

    // Calculate discount when combobox selection changes
    private void applyDiscount() {
        if (selectedBill == null) return;
        int idx = jComboBox1.getSelectedIndex();
        double discountPct = (idx == 1) ? 0.05 : (idx == 2) ? 0.10 : 0.0;
        double discountAmt = selectedBill.getTotal() * discountPct;
        double grandTotal = selectedBill.getTotal() - discountAmt;
        int w = jPanel3.getWidth() > 50 ? (jPanel3.getWidth() - 30) : 290;

        jLabel10.setText("-Rs. " + String.format("%.0f", discountAmt));
        jLabel13.setText("<html><table width='" + w + "'><tr><td><font size='+1'><b>Grand Total:</b></font></td><td align='right'><font size='+2' color='#198754'><b>Rs. " + String.format("%.0f", grandTotal) + "</b></font></td></tr></table></html>");
    }

    // Add extra part charge to the bill
    private void addExtraPart() {
        if (selectedBill == null) return;
        String input = JOptionPane.showInputDialog(this, "Enter Part Price (Rs):");
        if (input == null || input.trim().isEmpty()) return;

        try {
            double price = Double.parseDouble(input.trim());
            double newTotal = selectedBill.getTotal() + price;
            billingDAO.updateBillTotal(selectedBill.getBillId(), newTotal);
            loadBills();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric price.");
        }
    }

    // Mark current bill as Paid in MySQL
    private void collectPayment() {
        if (selectedBill == null) return;
        billingDAO.markBillPaid(selectedBill.getBillId());
        JOptionPane.showMessageDialog(this, "Payment collected successfully!");
        loadBills();
    }

    // Show printable receipt preview
    private void printReceipt() {
        if (selectedBill == null) return;
        String method = jRadioButton1.isSelected() ? "Cash" : "Card";
        String receipt = "====================================\n"
                       + "       CARCARE AUTO SERVICES        \n"
                       + "          Customer Receipt          \n"
                       + "====================================\n"
                       + "Invoice:  #INV-" + selectedBill.getBillId() + "\n"
                       + "Customer: " + selectedBill.getCustomer() + "\n"
                       + "Vehicle:  " + selectedBill.getVehicle() + " (" + selectedBill.getRegNo() + ")\n"
                       + "Service:  " + selectedBill.getService() + "\n"
                       + "Method:   " + method + "\n"
                       + "Total:    Rs. " + String.format("%.0f", selectedBill.getTotal()) + "\n"
                       + "Status:   Paid\n"
                       + "====================================\n"
                       + "      Thank you for your visit!     \n"
                       + "====================================\n";
        javax.swing.JTextArea area = new javax.swing.JTextArea(receipt);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new javax.swing.JScrollPane(area), "Receipt Preview", JOptionPane.PLAIN_MESSAGE);
    }

    public javax.swing.JPanel getBillingPanel() {
        return jPanel1;
    }

    public javax.swing.JPanel getServiceQueuePanel() {
        return jPanel1;
    }

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
        java.awt.EventQueue.invokeLater(() -> new Billing_Screen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
