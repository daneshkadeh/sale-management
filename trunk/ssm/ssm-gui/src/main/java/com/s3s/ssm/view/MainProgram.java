package com.s3s.ssm.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.S3sImageChooser;
import com.s3s.ssm.view.component.S3sRadioButtonsGroup;
import com.s3s.ssm.view.list.ListCustomerViewTest;
import com.s3s.ssm.view.list.ListGoodsViewTest;
import com.s3s.ssm.view.list.ListInvoiceViewTest;
import com.s3s.ssm.view.list.param.ListManufacturerView;
import com.s3s.ssm.view.list.param.ListUnitOfMeasureView;
import com.s3s.ssm.view.list.param.ListUomCategoryView;
import com.s3s.ssm.view.security.LoginDialog;

public class MainProgram {
    private static final Dimension WINDOW_MIN_SIZE = new Dimension(400, 300);
    public static Log s_logger = LogFactory.getLog(MainProgram.class);
    private static final String[] MESSSAGE_FILES = new String[] { "i18n/messages", "i18n/param_messages",
            "i18n/finance_messages", "i18n/sales_messages", "i18n/shipment_messages", "i18n/contact_messages",
            "i18n/store_messages", "i18n/supplychain_messages", "i18n/bi_messages" };

    public static void main(String[] args) {
        // Not find solution to get class path from ssm-core.
        // String classpath = MainProgram.class.getClassLoader().get
        DOMConfigurator.configure("src/main/resources/log4j.xml");
        s_logger.info("Starting super sales management application...");
        ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
        ConfigProvider configProvider = ConfigProvider.getInstance();
        ControlConfigUtils.init();
        ControlConfigUtils.setLabelMessageBundle(Locale.FRENCH, MESSSAGE_FILES);
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Set System L&F
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Create and set up the window.
        final JFrame frame = new JFrame("Sales Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(WINDOW_MIN_SIZE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        JMenuBar menuBar = createMenuBar(frame);

        frame.setJMenuBar(menuBar);

        Container contentPane = frame.getContentPane();
        addComponents(contentPane);

        frame.pack();
        frame.setVisible(true);
    }

    private static void addComponents(Container contentPane) {
        final JPanel componentPanel = createDemoComponentPanel();
        final JScrollPane scrollPane = new JScrollPane(componentPanel);
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setRightComponent(scrollPane);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Sale management");

        // User management
        DefaultMutableTreeNode userManagementEntry = new DefaultMutableTreeNode("User management");
        DefaultMutableTreeNode userNode = new DefaultMutableTreeNode("User");
        DefaultMutableTreeNode profilesNode = new DefaultMutableTreeNode("Profiles");
        DefaultMutableTreeNode exceptionPrivilegeNode = new DefaultMutableTreeNode("Profiles");
        root.add(userManagementEntry);
        userManagementEntry.add(userNode);
        userManagementEntry.add(profilesNode);
        userManagementEntry.add(exceptionPrivilegeNode);

        // Manufacturer management
        DefaultMutableTreeNode mfManagementEntry = new DefaultMutableTreeNode("Manufacturer management");
        root.add(mfManagementEntry);

        // Supplier
        DefaultMutableTreeNode supplierEntry = new DefaultMutableTreeNode("Supplier");
        root.add(supplierEntry);

        // Product management
        DefaultMutableTreeNode productManagementEntry = new DefaultMutableTreeNode("Product management");
        DefaultMutableTreeNode uomCategoryNode = new DefaultMutableTreeNode("Unit-Of-Material Category");
        DefaultMutableTreeNode uomNode = new DefaultMutableTreeNode("Unit-Of-Material");
        DefaultMutableTreeNode productGroupNode = new DefaultMutableTreeNode("Product group");
        DefaultMutableTreeNode productNode = new DefaultMutableTreeNode("Product");
        DefaultMutableTreeNode taxGroupNode = new DefaultMutableTreeNode("Tax group");
        root.add(productManagementEntry);
        productManagementEntry.add(uomCategoryNode);
        productManagementEntry.add(uomNode);
        productManagementEntry.add(productGroupNode);
        productManagementEntry.add(productNode);
        productManagementEntry.add(taxGroupNode);

        // Bank
        DefaultMutableTreeNode bankEntry = new DefaultMutableTreeNode("Bank");
        root.add(bankEntry);

        // Currency management
        DefaultMutableTreeNode currencyManagementEntry = new DefaultMutableTreeNode("Bank");
        DefaultMutableTreeNode currenciesNode = new DefaultMutableTreeNode("Currencies");
        DefaultMutableTreeNode exchangeRateNode = new DefaultMutableTreeNode("Exchange rate");
        root.add(currencyManagementEntry);
        currencyManagementEntry.add(currenciesNode);
        currencyManagementEntry.add(exchangeRateNode);

        // Sales contract
        DefaultMutableTreeNode saleContractEntry = new DefaultMutableTreeNode("Sales contract");
        DefaultMutableTreeNode buyContractNode = new DefaultMutableTreeNode("Hợp đồng mua hàng");
        DefaultMutableTreeNode ttManageNode = new DefaultMutableTreeNode("Quản lý TT");
        DefaultMutableTreeNode lcManageNode = new DefaultMutableTreeNode("Quản lý LC");
        DefaultMutableTreeNode declareContractNode = new DefaultMutableTreeNode("Quản lý tờ khai");
        root.add(saleContractEntry);
        saleContractEntry.add(buyContractNode);
        saleContractEntry.add(ttManageNode);
        saleContractEntry.add(lcManageNode);
        saleContractEntry.add(declareContractNode);

        // Inventory management
        DefaultMutableTreeNode inventoryManagementEntry = new DefaultMutableTreeNode("Inventory management");
        DefaultMutableTreeNode tonKhoNode = new DefaultMutableTreeNode("Tồn kho");
        DefaultMutableTreeNode chuyenKhoNode = new DefaultMutableTreeNode("Chuyển kho");
        DefaultMutableTreeNode phieuXuatKhoNode = new DefaultMutableTreeNode("Phiếu xuất kho");
        DefaultMutableTreeNode phieuNhapKhoNode = new DefaultMutableTreeNode("Phiếu nhập kho");
        root.add(inventoryManagementEntry);
        inventoryManagementEntry.add(tonKhoNode);
        inventoryManagementEntry.add(chuyenKhoNode);
        inventoryManagementEntry.add(phieuXuatKhoNode);
        inventoryManagementEntry.add(phieuNhapKhoNode);

        // Financial management
        DefaultMutableTreeNode fMEntry = new DefaultMutableTreeNode("Financial management");
        DefaultMutableTreeNode receiveFMEntry = new DefaultMutableTreeNode("Receivable financial management");
        DefaultMutableTreeNode thuKemToaHangNode = new DefaultMutableTreeNode("Thu kèm toa hàng");
        DefaultMutableTreeNode thuTienHangNode = new DefaultMutableTreeNode("Thu tiền hàng");
        DefaultMutableTreeNode muonTienNode = new DefaultMutableTreeNode("Mượn tiền");
        DefaultMutableTreeNode otherReceiveNode = new DefaultMutableTreeNode("Khoản thu khác");
        DefaultMutableTreeNode payFMEntry = new DefaultMutableTreeNode("Payable financial management");
        DefaultMutableTreeNode tamUngKHNode = new DefaultMutableTreeNode("Tạm ứng khách hàng");
        DefaultMutableTreeNode chiMuaHangNode = new DefaultMutableTreeNode("Chi mua hàng");
        DefaultMutableTreeNode choVayTienNode = new DefaultMutableTreeNode("Cho vay tiền");
        DefaultMutableTreeNode chiPhiKhacNode = new DefaultMutableTreeNode("Chi phí khác");
        root.add(fMEntry);
        fMEntry.add(receiveFMEntry);
        fMEntry.add(payFMEntry);
        receiveFMEntry.add(thuKemToaHangNode);
        receiveFMEntry.add(thuTienHangNode);
        receiveFMEntry.add(muonTienNode);
        receiveFMEntry.add(otherReceiveNode);
        payFMEntry.add(tamUngKHNode);
        payFMEntry.add(chiMuaHangNode);
        payFMEntry.add(choVayTienNode);
        payFMEntry.add(chiPhiKhacNode);

        // Quản lý công nợ
        DefaultMutableTreeNode quanLyCongNoEntry = new DefaultMutableTreeNode("Quản lý công nợ");
        DefaultMutableTreeNode congNoKHNode = new DefaultMutableTreeNode("Công nợ khách hàng");
        DefaultMutableTreeNode congNoNCCKhacNode = new DefaultMutableTreeNode("Công nợ nhà cung cấp");
        root.add(quanLyCongNoEntry);
        quanLyCongNoEntry.add(congNoKHNode);
        quanLyCongNoEntry.add(congNoNCCKhacNode);

        // CRM Contact management
        DefaultMutableTreeNode contactMagementEntry = new DefaultMutableTreeNode("CRM contact management");
        DefaultMutableTreeNode customerGroupNode = new DefaultMutableTreeNode("Customer group");
        DefaultMutableTreeNode customerNode = new DefaultMutableTreeNode("Customer");
        DefaultMutableTreeNode supplierNode = new DefaultMutableTreeNode("Supplier");
        DefaultMutableTreeNode nguoiDuocTaiTroNode = new DefaultMutableTreeNode("Người được tài trợ");
        root.add(contactMagementEntry);
        contactMagementEntry.add(customerGroupNode);
        contactMagementEntry.add(customerNode);
        contactMagementEntry.add(supplierNode);
        contactMagementEntry.add(nguoiDuocTaiTroNode);

        // Supply chain management
        DefaultMutableTreeNode supplyChainMangamentEntry = new DefaultMutableTreeNode("Supply chain management");
        DefaultMutableTreeNode materialNode = new DefaultMutableTreeNode("Material");
        DefaultMutableTreeNode materialPriceNode = new DefaultMutableTreeNode("Material price");
        DefaultMutableTreeNode materialEndProductExchangeNode = new DefaultMutableTreeNode("Material product exchange");
        DefaultMutableTreeNode endProductNode = new DefaultMutableTreeNode("End-product");
        DefaultMutableTreeNode endProductPriceNode = new DefaultMutableTreeNode("End-product price");
        root.add(supplyChainMangamentEntry);
        supplyChainMangamentEntry.add(materialNode);
        supplyChainMangamentEntry.add(materialPriceNode);
        supplyChainMangamentEntry.add(materialEndProductExchangeNode);
        supplyChainMangamentEntry.add(endProductNode);
        supplyChainMangamentEntry.add(endProductPriceNode);

        // HRM Resource management
        DefaultMutableTreeNode resourceManagementEntry = new DefaultMutableTreeNode("HRM Resource management");
        DefaultMutableTreeNode employeeNode = new DefaultMutableTreeNode("Employee");
        DefaultMutableTreeNode deviceMaterial = new DefaultMutableTreeNode("Device, material");
        root.add(resourceManagementEntry);
        resourceManagementEntry.add(employeeNode);
        resourceManagementEntry.add(deviceMaterial);

        // Report
        DefaultMutableTreeNode reportEntry = new DefaultMutableTreeNode("Report");
        DefaultMutableTreeNode baoDongKichCauNode = new DefaultMutableTreeNode("Báo động kích cầu");
        DefaultMutableTreeNode baoDongHetHangNode = new DefaultMutableTreeNode("Báo động hết hàng");
        DefaultMutableTreeNode hangBanChayNode = new DefaultMutableTreeNode("Hàng bán chạy");
        DefaultMutableTreeNode hangTonQuaLauNode = new DefaultMutableTreeNode("Hàng tồn quá lâu");
        DefaultMutableTreeNode thongKeHangBanNode = new DefaultMutableTreeNode("Thống kê hàng bán");
        DefaultMutableTreeNode thongKeDoanhThuChiPhiNode = new DefaultMutableTreeNode("Thống kê doanh thu - chi phí");
        root.add(reportEntry);
        reportEntry.add(baoDongKichCauNode);
        reportEntry.add(baoDongHetHangNode);
        reportEntry.add(hangBanChayNode);
        reportEntry.add(hangTonQuaLauNode);
        reportEntry.add(thongKeHangBanNode);
        reportEntry.add(thongKeDoanhThuChiPhiNode);

        final JTree treeMenu = new JTree(root);
        treeMenu.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                // Returns the last path element of the selection.
                // This method is useful only when the selection model allows a single selection.
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMenu.getLastSelectedPathComponent();

                if (node == null) {
                    // Nothing is selected.
                    return;
                }

                String nodeInfo = (String) node.getUserObject();
                if (nodeInfo.equals("Customer")) {
                }

            }
        });

        JScrollPane treeMenuScrollPane = new JScrollPane(treeMenu);

        splitPane.setLeftComponent(treeMenuScrollPane);

        contentPane.add(splitPane);
    }

    private static JPanel createDemoComponentPanel() {
        JPanel componentPanel = new JPanel(new MigLayout("wrap 2"));
        componentPanel.add(new JLabel("Image component"), "top");
        componentPanel.add(new S3sImageChooser());
        componentPanel.add(new JLabel("Radio button group"), "top");
        componentPanel.add(new S3sRadioButtonsGroup<>(Arrays.asList("Table", "Chair", "Ruler")));
        return componentPanel;
    }

    private static JMenuBar createMenuBar(final JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(ControlConfigUtils.getString("JMenuBar.File"));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenu helpMenu = new JMenu(ControlConfigUtils.getString("JMenuBar.Help"));

        JMenuItem userMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.User"));
        JMenuItem customerMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Customer"));
        customerMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        customerMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ListCustomerViewTest listCustomerView = new ListCustomerViewTest();
                listCustomerView.setVisible(true);
                frame.setContentPane(listCustomerView);
                frame.repaint();
                frame.pack();
            }
        });

        JMenuItem manufacturerMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Manufacturer"));
        manufacturerMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        manufacturerMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ListManufacturerView listManufacturerView = new ListManufacturerView();
                listManufacturerView.setVisible(true);
                frame.setContentPane(listManufacturerView);
                frame.repaint();
                frame.pack();
            }
        });

        JMenuItem uomCategoryMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.UomCategory"));
        uomCategoryMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        uomCategoryMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ListUomCategoryView listUomCategoryView = new ListUomCategoryView();
                listUomCategoryView.setVisible(true);
                frame.setContentPane(listUomCategoryView);
                frame.repaint();
                frame.pack();
            }
        });

        JMenuItem unitOfMeasureMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.UnitOfMeasure"));
        unitOfMeasureMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        unitOfMeasureMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ListUnitOfMeasureView listUnitOfMeasureView = new ListUnitOfMeasureView();
                listUnitOfMeasureView.setVisible(true);
                frame.setContentPane(listUnitOfMeasureView);
                frame.repaint();
                frame.pack();
            }
        });

        JMenuItem productMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Product"));
        productMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        productMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and set up the content pane.
                ListGoodsViewTest listGoodsView = new ListGoodsViewTest();
                listGoodsView.setVisible(true);
                frame.setContentPane(listGoodsView);
                frame.repaint();
                frame.pack();
            }
        });

        JMenuItem statisticMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Statistic"));
        statisticMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        JMenuItem invoiceMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Invoice"));
        invoiceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        invoiceMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and set up the content pane.
                ListInvoiceViewTest listInvoiceView = new ListInvoiceViewTest();
                listInvoiceView.setVisible(true);
                frame.setContentPane(listInvoiceView);
                frame.repaint();
                frame.pack();
            }
        });

        JMenuItem exitMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Exit"));
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        // test
        JMenuItem loginItem = new JMenuItem("Login");
        loginItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog loginDialog = new LoginDialog(frame);
                loginDialog.setVisible(true);
            }
        });
        // End test
        fileMenu.add(userMenuItem);
        fileMenu.add(customerMenuItem);
        fileMenu.add(productMenuItem);
        fileMenu.add(manufacturerMenuItem);
        fileMenu.add(uomCategoryMenuItem);
        fileMenu.add(unitOfMeasureMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(statisticMenuItem);
        fileMenu.add(invoiceMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        fileMenu.add(loginItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }
}
