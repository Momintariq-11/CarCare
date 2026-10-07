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
import java.text.SimpleDateFormat;
import java.util.Queue;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.FlatLightLaf;
import BusinessLayer.ServiceTicket;
import DataAccessLayer.ServiceQueue_DAO;

/**
 * CarCare - Service Queue Management Screen
 * Displays FIFO Waiting Queue and Active Service Bay.
 */
public class ServiceQueue_Screen extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ServiceQueue_Screen.class.getName());

    private final Color primaryBlue = new Color(8, 37, 79);
    private final ServiceQueue_DAO queueDAO = new ServiceQueue_DAO();
    private ServiceTicket activeTicket = null;
    private javax.swing.Timer liveTimer = null;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    /**
     * Creates new form dashboard
     */
    public ServiceQueue_Screen() {
        initTheme();
        initComponents();
        setupWindow();
        setupTabs();
        setupControls();
        setupTable();
        setupLiveTimer();
        loadQueueData();
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
        jTabbedPane1.putClientProperty("JTabbedPane.tabHeight", 42);
        jTabbedPane1.putClientProperty("JTabbedPane.tabSelectionHeight", 3);
        jTabbedPane1.putClientProperty("JTabbedPane.selectedBackground", Color.WHITE);
        jTabbedPane1.putClientProperty("JTabbedPane.selectedForeground", primaryBlue);
        jTabbedPane1.putClientProperty("JTabbedPane.showTabSeparators", false);
        jTabbedPane1.putClientProperty("JTabbedPane.tabWidthMode", "equal");
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        // Apply dark blue background to all tabs
        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
            jTabbedPane1.setBackgroundAt(i, primaryBlue);
            jTabbedPane1.setForegroundAt(i, Color.WHITE);
            ((javax.swing.JPanel) jTabbedPane1.getComponentAt(i)).setBackground(primaryBlue);
        }
    }

    // 4. CONTROLS & STYLING
    private void setupControls() {
        // Outer Dark Blue Panel
        jPanel1.setBackground(primaryBlue);
        jPanel1.setOpaque(true);

        jPanel2.putClientProperty("FlatLaf.style", "arc: 25; background: #ffffff");

        // FlatLaf Rounded corners for buttons (Design builder cannot set client properties)
        jButton1.putClientProperty("JButton.buttonType", "roundRect");
        jButton1.putClientProperty("JButton.arc", 12);

        jButton2.putClientProperty("JButton.buttonType", "roundRect");
        jButton2.putClientProperty("JButton.arc", 12);

        jButton5.putClientProperty("JButton.buttonType", "roundRect");
        jButton5.putClientProperty("JButton.arc", 12);

        jButton7.putClientProperty("JButton.buttonType", "roundRect");
        jButton7.putClientProperty("JButton.arc", 12);
       

        // Smooth rounded borders for badges and plates
        jLabel4.putClientProperty("JComponent.roundRect", true); // LIVE badge
        jLabel5.putClientProperty("JComponent.roundRect", true); // Reg. No. plate
        jLabel7.putClientProperty("JComponent.roundRect", true); // Timer box

        // Action listeners
        jButton2.addActionListener(e -> markActiveServiceCompleted());
        jButton5.addActionListener(e -> callNextVehicle());
    }

    // 5. TABLE STYLING
    private void setupTable() {
        // Table Header
        jTable1.getTableHeader().setBackground(primaryBlue);
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        jTable1.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        jTable1.getTableHeader().setReorderingAllowed(false);

        // Table Rows & Body
        jTable1.setRowHeight(38);
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(false);
        jTable1.setGridColor(new Color(230, 230, 230));
        jTable1.setFont(new Font("Arial", Font.PLAIN, 13));
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        // Alternating Zebra Row Colors
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }
                setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setForeground(new java.awt.Color(215, 82, 17));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(8, 37, 79));
        jLabel1.setText("Service Queue");

        jButton1.setBackground(new java.awt.Color(8, 37, 79));
        jButton1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("+ Add to Queue\n");
        jButton1.setBorderPainted(false);
        jButton1.setSize(new java.awt.Dimension(120, 38));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 210, 225), 1, true));
        jTable1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Reg. No.", "Vehicle", "Service", "Time Added"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(32);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 210, 225), 1, true));
        jPanel3.setPreferredSize(new java.awt.Dimension(350, 230));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(8, 37, 79));
        jLabel2.setText("Currently Under Service");
        jLabel2.setPreferredSize(new java.awt.Dimension(250, 25));

        jButton2.setBackground(new java.awt.Color(8, 37, 79));
        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Mark as Completed");

        jLabel4.setBackground(new java.awt.Color(51, 204, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("● LIVE ");
        jLabel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(34, 197, 94), 1, true));
        jLabel4.setOpaque(true);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ABC-3228");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(8, 37, 79));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Honda Civic • 2022");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Mechanic: Ahmed | Service: Oil Change");

        jLabel7.setBackground(new java.awt.Color(235, 243, 252));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(13, 110, 253));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("⏱ 00:45:20");
        jLabel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(191, 219, 254), 1, true));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 210, 225), 1, true));
        jPanel4.setPreferredSize(new java.awt.Dimension(350, 190));

        jButton5.setBackground(new java.awt.Color(25, 135, 84));
        jButton5.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Next Vehicle");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(8, 37, 79));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Queue Action");

        jButton7.setBackground(new java.awt.Color(254, 226, 226));
        jButton7.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jButton7.setForeground(new java.awt.Color(220, 53, 69));
        jButton7.setText("Remove from Queue");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jLabel9.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(8, 37, 79));
        jLabel9.setText("Waiting Queue");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 210, 225), 1, true));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(8, 37, 79));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Live Queue Overview ");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Cars in Queue:          4");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("Estimated Wait:        25 mins");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Completed Today:     12 Cars");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel11, jLabel13});

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
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, 300, 580, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap(20, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        openAddToQueueDialog();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        removeFromQueue();
    }//GEN-LAST:event_jButton7ActionPerformed

    // ==========================================
    // SERVICE QUEUE HELPER METHODS (BEGINNER-FRIENDLY)
    // ==========================================

    /**
     * 1. Open Add to Queue Dialog
     */
    private void openAddToQueueDialog() {
        AddtoQueue_SCREEN dialog = new AddtoQueue_SCREEN(this, true);
        dialog.setVisible(true);
        if (dialog.isQueueAdded()) {
            loadQueueData(); // Refresh queue when car is added
        }
    }

    /**
     * 2. Setup Live Timer for Active Bay
     */
    private void setupLiveTimer() {
        liveTimer = new javax.swing.Timer(1000, e -> updateTimerDisplay());
    }

    /**
     * 3. Update elapsed timer in Active Bay
     */
    private void updateTimerDisplay() {
        if (activeTicket != null && activeTicket.getStartedAt() != null) {
            long elapsed = System.currentTimeMillis() - activeTicket.getStartedAt().getTime();
            if (elapsed < 0) elapsed = 0;
            long sec = elapsed / 1000;
            long h = sec / 3600;
            long m = (sec % 3600) / 60;
            long s = sec % 60;
            jLabel7.setText(String.format("⏱ %02d:%02d:%02d", h, m, s));
        } else {
            jLabel7.setText("⏱ --:--:--");
        }
    }

    /**
     * 4. Load all queue data from database into Table and Cards
     */
    public void loadQueueData() {
        // Table Model
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Fetch Waiting Queue (FIFO)
        Queue<ServiceTicket> waitingQueue = queueDAO.getWaitingQueue();
        int pos = 1;
        for (ServiceTicket ticket : waitingQueue) {
            String time = (ticket.getAddedAt() != null) ? timeFormat.format(ticket.getAddedAt()) : "Just now";
            model.addRow(new Object[]{
                pos++,
                ticket.getRegistrationNo(),
                ticket.getVehicleFullName(),
                ticket.getServiceName(),
                time
            });
        }

        // Active Bay Status
        activeTicket = queueDAO.getActiveService();
        if (activeTicket != null) {
            jLabel4.setText("● LIVE");
            jLabel5.setText(activeTicket.getRegistrationNo());
            jLabel6.setText(activeTicket.getVehicleFullName() + " • " + activeTicket.getCustomerName());
            jLabel3.setText("Mechanic: " + activeTicket.getMechanicName() + " | Service: " + activeTicket.getServiceName());
            jButton2.setEnabled(true);
            if (!liveTimer.isRunning()) liveTimer.start();
            updateTimerDisplay();
        } else {
            jLabel4.setText("IDLE");
            jLabel5.setText("BAY EMPTY");
            jLabel6.setText("No vehicle currently under service");
            jLabel3.setText("Click 'Next Vehicle' to service first waiting car");
            jLabel7.setText("--:--:--");
            jButton2.setEnabled(false);
            if (liveTimer != null && liveTimer.isRunning()) liveTimer.stop();
        }

        // Statistics
        int waitingCount = queueDAO.getWaitingCount();
        jLabel11.setText("Cars in Queue:          " + waitingCount);
        jLabel12.setText("Estimated Wait:        " + (waitingCount * 15) + " mins");
        jLabel13.setText("Completed Today:     " + queueDAO.getCompletedTodayCount() + " Cars");
    }

    /**
     * 5. Call next vehicle into Active Bay (FIFO Poll)
     */
    private void callNextVehicle() {
        if (activeTicket != null) {
            int ans = JOptionPane.showConfirmDialog(this,
                "A vehicle is currently in Bay 1. Do you want to complete it and call next?",
                "Bay Occupied", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                queueDAO.markAsCompleted(activeTicket.getQueueId(), activeTicket.getRecordId());
            } else {
                return;
            }
        }

        ServiceTicket nextCar = queueDAO.callNextVehicle();
        if (nextCar != null) {
            JOptionPane.showMessageDialog(this, "Next Car in Bay: " + nextCar.getRegistrationNo());
            loadQueueData();
        } else {
            JOptionPane.showMessageDialog(this, "Waiting queue is empty!");
        }
    }

    /**
     * 6. Mark currently active vehicle service as completed
     */
    private void markActiveServiceCompleted() {
        if (activeTicket == null) return;

        int ans = JOptionPane.showConfirmDialog(this,
            "Mark service for " + activeTicket.getRegistrationNo() + " as COMPLETED?",
            "Confirm Completion", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            queueDAO.markAsCompleted(activeTicket.getQueueId(), activeTicket.getRecordId());
            JOptionPane.showMessageDialog(this, "Service Completed Successfully!");
            loadQueueData();
        }
    }

    /**
     * 7. Remove selected vehicle from queue
     */
    private void removeFromQueue() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle from the table to remove!");
            return;
        }

        String regNo = jTable1.getValueAt(row, 1).toString();
        int ans = JOptionPane.showConfirmDialog(this,
            "Remove " + regNo + " from the waiting queue?",
            "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            Queue<ServiceTicket> queue = queueDAO.getWaitingQueue();
            for (ServiceTicket t : queue) {
                if (t.getRegistrationNo().equalsIgnoreCase(regNo)) {
                    queueDAO.removeFromQueue(t.getQueueId());
                    break;
                }
            }
            loadQueueData();
        }
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
        java.awt.EventQueue.invokeLater(() -> new ServiceQueue_Screen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
