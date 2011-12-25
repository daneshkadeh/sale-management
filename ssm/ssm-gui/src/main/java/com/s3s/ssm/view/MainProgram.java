package com.s3s.ssm.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.ImageChooser;
import com.s3s.ssm.view.component.RadioButtonsGroup;
import com.s3s.ssm.view.domain.BuyManagementDomain;
import com.s3s.ssm.view.domain.ContactManagementDomain;
import com.s3s.ssm.view.domain.FinanceManagementDomain;
import com.s3s.ssm.view.domain.InventoryManagementDomain;
import com.s3s.ssm.view.domain.ReportDomain;
import com.s3s.ssm.view.domain.ResourceManagementDomain;
import com.s3s.ssm.view.domain.SalesManagementDomain;
import com.s3s.ssm.view.domain.SupplyChainDomain;
import com.s3s.ssm.view.domain.SystemManagementDomain;
import com.s3s.ssm.view.list.ListCustomerViewTest;
import com.s3s.ssm.view.list.ListGoodsViewTest;
import com.s3s.ssm.view.list.ListInvoiceViewTest;
import com.s3s.ssm.view.list.param.ListManufacturerView;
import com.s3s.ssm.view.list.security.ListUserView;
import com.s3s.ssm.view.security.LoginDialog;

public class MainProgram {
    private static final Dimension WINDOW_MIN_SIZE = new Dimension(400, 300);
    public static Log s_logger = LogFactory.getLog(MainProgram.class);
    private static final String[] MESSSAGE_FILES = new String[] { "i18n/messages", "i18n/param_messages",
            "i18n/finance_messages", "i18n/sales_messages", "i18n/shipment_messages", "i18n/contact_messages",
            "i18n/store_messages", "i18n/supplychain_messages", "i18n/bi_messages", "i18n/ui_messages" };
    private static JFrame frame;

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
        // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e.getCause());
        }

        // Create and set up the window.
        frame = new JFrame("Sales Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(WINDOW_MIN_SIZE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        JMenuBar menuBar = createMenuBar(frame);

        frame.setJMenuBar(menuBar);

        Container contentPane = frame.getContentPane();
        //login
        LoginDialog loginDialog = new LoginDialog(frame);
        loginDialog.setVisible(true);
        if (loginDialog.isLogin()) {
            addComponents(contentPane);
            frame.pack();
            frame.setVisible(true);
        } else {
            frame.dispose();
        }
    }

    private static void addComponents(Container contentPane) {
        final JPanel componentPanel = createDemoComponentPanel();
        final JScrollPane contentViewScrollPane = new JScrollPane(componentPanel);
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setRightComponent(contentViewScrollPane);

        JScrollPane treeMenuScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        leftSplitPane.setBottomComponent(createLeftBottomPane(treeMenuScrollPane, contentViewScrollPane));
        leftSplitPane.setTopComponent(treeMenuScrollPane);

        splitPane.setLeftComponent(leftSplitPane);
        splitPane.setLastDividerLocation(splitPane.getLastDividerLocation());
        contentPane.add(splitPane);
    }

    private static JPanel createLeftBottomPane(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        JPanel panel = new JPanel(new MigLayout("wrap, gap 0, ins 0, fill", "grow"));

        ButtonGroup buttonGroup = new ButtonGroup();

        SystemManagementDomain systemDomain = new SystemManagementDomain(treeScrollPane, contentScrollPane);
        BuyManagementDomain buyDomain = new BuyManagementDomain(treeScrollPane, contentScrollPane);
        SalesManagementDomain salesDomain = new SalesManagementDomain(treeScrollPane, contentScrollPane);
        InventoryManagementDomain inventoryDomain = new InventoryManagementDomain(treeScrollPane, contentScrollPane);
        ContactManagementDomain contactDomain = new ContactManagementDomain(treeScrollPane, contentScrollPane);
        FinanceManagementDomain financeDomain = new FinanceManagementDomain(treeScrollPane, contentScrollPane);
        SupplyChainDomain supplyChainDomain = new SupplyChainDomain(treeScrollPane, contentScrollPane);
        ResourceManagementDomain resourceDomain = new ResourceManagementDomain(treeScrollPane, contentScrollPane);
        ReportDomain reportDomain = new ReportDomain(treeScrollPane, contentScrollPane);

        buttonGroup.add(systemDomain);
        buttonGroup.add(buyDomain);
        buttonGroup.add(salesDomain);
        buttonGroup.add(inventoryDomain);
        buttonGroup.add(contactDomain);
        buttonGroup.add(financeDomain);
        buttonGroup.add(supplyChainDomain);
        buttonGroup.add(resourceDomain);
        buttonGroup.add(reportDomain);

        panel.add(systemDomain, "grow");
        panel.add(buyDomain, "grow");
        panel.add(salesDomain, "grow");
        panel.add(inventoryDomain, "grow");
        panel.add(contactDomain, "grow");
        panel.add(financeDomain, "grow");
        panel.add(supplyChainDomain, "grow");
        panel.add(resourceDomain, "grow");
        panel.add(reportDomain, "grow");

        systemDomain.doClick();
        return panel;
    }

    private static JPanel createDemoComponentPanel() {
        JPanel componentPanel = new JPanel(new MigLayout("wrap 2"));
        componentPanel.add(new JLabel("Image component"), "top");
        componentPanel.add(new ImageChooser());
        componentPanel.add(new JLabel("Radio button group"), "top");
        componentPanel.add(new RadioButtonsGroup<>(Arrays.asList("Table", "Chair", "Ruler")));
        return componentPanel;
    }

    private static JMenuBar createMenuBar(final JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(ControlConfigUtils.getString("JMenuBar.File"));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenu helpMenu = new JMenu(ControlConfigUtils.getString("JMenuBar.Help"));

        JMenuItem userMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.User"));
        userMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ListUserView listRoleView = new ListUserView();
                listRoleView.setVisible(true);
                frame.setContentPane(listRoleView);
                frame.repaint();
                frame.pack();
            }
        });
        
        JMenuItem customerMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Customer"));
        customerMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        customerMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ListCustomerViewTest listCustomerView = new ListCustomerViewTest();
                listCustomerView.setVisible(true);
                listCustomerView.loadView();
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
                listManufacturerView.loadView();
                frame.setContentPane(listManufacturerView);
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
                listGoodsView.loadView();
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
                listInvoiceView.loadView();
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
