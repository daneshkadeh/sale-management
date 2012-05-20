/*
 * MainProgram
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.domain.BuyManagementDomain;
import com.s3s.ssm.view.domain.CatalogManagementDomain;
import com.s3s.ssm.view.domain.ContactManagementDomain;
import com.s3s.ssm.view.domain.FinanceManagementDomain;
import com.s3s.ssm.view.domain.ReportDomain;
import com.s3s.ssm.view.domain.SalesManagementDomain;
import com.s3s.ssm.view.domain.StoreManagementDomain;
import com.s3s.ssm.view.domain.SystemManagementDomain;
import com.s3s.ssm.view.security.LoginDialog;

public class MainProgram {
    private static final Dimension WINDOW_MIN_SIZE = new Dimension(400, 300);
    public static Log s_logger = LogFactory.getLog(MainProgram.class);
    private static final String[] MESSSAGE_FILES = new String[] { "i18n/messages", "i18n/config_messages",
            "i18n/catalog_messages", "i18n/finance_messages", "i18n/sales_messages", "i18n/shipment_messages",
            "i18n/contact_messages", "i18n/store_messages", "i18n/supplychain_messages", "i18n/operator_messages",
            "i18n/gui_messages", "i18n/ui_messages", "i18n/label", "i18n/config_label", "i18n/catalog_label",
            "i18n/finance_label", "i18n/sales_label", "i18n/shipment_label", "i18n/contact_label", "i18n/store_label",
            "i18n/supplychain_label", "i18n/operator_label", "i18n/gui_label", "i18n/error", "i18n/config_error",
            "i18n/catalog_error", "i18n/finance_error", "i18n/sales_error", "i18n/shipment_error",
            "i18n/contact_error", "i18n/store_error", "i18n/supplychain_error", "i18n/operator_error",
            "i18n/gui_error", "i18n/common_gui_label" };

    private static SSplashScreen splashScreen;

    private static JFrame frame;
    private static Container contentPane;

    private static JSplitPane institutionPane;
    private static JSplitPane organizationPane;
    private static JSplitPane saleChannelPane;

    private static JToggleButton institutionBtn;
    private static JToggleButton organizationBtn;
    private static JToggleButton saleChannelBtn;

    public static void main(String[] args) {
        // Not find solution to get class path from ssm-core.
        // String classpath = MainProgram.class.getClassLoader().get
        // DOMConfigurator.configure("log4j.xml");
        s_logger.info("Starting super sales management application...");
        Locale.setDefault(new Locale("vi"));

        setLooknFeel();
        // ///////////// Splash screen///////////////////
        splashScreen = new SSplashScreen();
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setVisible(true);
        splashScreen.toFront();

        // ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
        // ConfigProvider configProvider = ConfigProvider.getInstance();
        ControlConfigUtils.init();
        ControlConfigUtils.setLabelMessageBundle(Locale.getDefault(), MESSSAGE_FILES);

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        // SwingUtilities.invokeLater(new Runnable() {
        // @Override
        // public void run() {
        createAndShowGUI();
        // }
        // });
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        ToolTipManager.sharedInstance().setInitialDelay(200);
        // Create and set up the window.
        frame = new JFrame("Izi RMS" + " - " + getCustomerName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(WINDOW_MIN_SIZE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        initCenterPanes();
        contentPane = createContentPane();
        frame.setJMenuBar(createMenuBar());
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
        // splashScreen.close();
        // login
        LoginDialog loginDialog = new LoginDialog(frame, new Runnable() {

            @Override
            public void run() {
            }
        }, new Runnable() {

            @Override
            public void run() {
                frame.dispose();
            }
        });
        loginDialog.setVisible(true);
        splashScreen.dispose();
    }

    public static void setLooknFeel() {
        // Set System L&F
        try {
            // System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Seaglass L&F (not working with java 7, see
            // http://code.google.com/p/seaglass/issues/detail?id=77#makechanges)
            // UIManager.setLookAndFeel(SeaGlassLookAndFeel.class.getName());

            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Resource bundle for swingX component.
            UIManager.getDefaults().addResourceBundle("i18n/swingx/swingx");
            UIManager.getDefaults().addResourceBundle("i18n/swingx/DatePicker");
            UIManager.getDefaults().addResourceBundle("i18n/swingx/ErrorPane");
            // UIManager.put("ToggleButton.background", Color.BLUE);
            UIManager.put("control", new ColorUIResource(227, 236, 250));
            UIManager.put("nimbusBase", new ColorUIResource(60, 60, 180));
            UIManager.put("controlShadow", new ColorUIResource(217, 226, 240));
            UIManager.put("controlDkShadow", new ColorUIResource(217, 226, 240));
            UIManager.put("Menu.background", new ColorUIResource(217, 226, 240));
            UIManager.put("ToolBar.background", new ColorUIResource(217, 226, 240));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private static String getCustomerName() {
        // TODO Should get from institution info.
        return "THU SPORTS";
    }

    /**
     * 
     */
    protected static void initCenterPanes() {
        institutionPane = createInstitutionPanel();

        splashScreen.setValue(60, "Loading organization context ...");
        organizationPane = createOrganizationPanel();

        splashScreen.setValue(80, "Loading sale channel context ...");
        saleChannelPane = createSaleChannelPanel();
    }

    private static Container createContentPane() {
        JPanel contentPane = new JPanel(new BorderLayout());
        // JToolBar toolbar = createToolbar();
        // contentPane.add(toolbar, BorderLayout.PAGE_START);
        contentPane.add(new JPanel());
        // contentPane.add(new JLabel("Business Active - demo version"), BorderLayout.PAGE_END);
        return contentPane;
    }

    private static JSplitPane createInstitutionPanel() {
        JScrollPane treeMenuScrollPane = new JScrollPane();
        // treeMenuScrollPane.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        JScrollPane contentViewScrollPane = new JScrollPane();
        List<AbstractDomain> institutionDomains = createInstitutionDomains(treeMenuScrollPane, contentViewScrollPane);
        return createMainSplitPane(treeMenuScrollPane, contentViewScrollPane, institutionDomains);
    }

    private static JSplitPane createOrganizationPanel() {
        JScrollPane treeMenuScrollPane = new JScrollPane();
        // treeMenuScrollPane.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        JScrollPane contentViewScrollPane = new JScrollPane();
        List<AbstractDomain> organizationDomains = createOrganizationDomains(treeMenuScrollPane, contentViewScrollPane);
        return createMainSplitPane(treeMenuScrollPane, contentViewScrollPane, organizationDomains);
    }

    private static JSplitPane createSaleChannelPanel() {
        JScrollPane treeMenuScrollPane = new JScrollPane();
        // treeMenuScrollPane.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        JScrollPane contentViewScrollPane = new JScrollPane();
        List<AbstractDomain> saleChannelDomains = createSaleChannelDomains(treeMenuScrollPane, contentViewScrollPane);
        return createMainSplitPane(treeMenuScrollPane, contentViewScrollPane, saleChannelDomains);
    }

    private static JSplitPane createMainSplitPane(JScrollPane treeMenuScrollPane, JScrollPane contentViewScrollPane,
            List<AbstractDomain> domains) {
        treeMenuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        treeMenuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false);
        splitPane.setOneTouchExpandable(true);
        splitPane.setRightComponent(contentViewScrollPane);

        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        leftSplitPane.setTopComponent(treeMenuScrollPane);
        leftSplitPane.setBottomComponent(createDomainPane(domains));
        leftSplitPane.setResizeWeight(1);
        leftSplitPane.setDividerSize(2);
        splitPane.setLeftComponent(leftSplitPane);

        splitPane.setLastDividerLocation(splitPane.getLastDividerLocation());
        return splitPane;
    }

    private static JToolBar createToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.add(Box.createHorizontalGlue());
        return toolbar;
    }

    private static void initContextButtons() {
        institutionBtn = new JToggleButton(IziImageUtils.getSmallIcon(IziImageConstants.INSTITUTION_ICON));
        institutionBtn.setText(ControlConfigUtils.getString("icon.tooltip.institution"));
        organizationBtn = new JToggleButton(IziImageUtils.getSmallIcon(IziImageConstants.ORGANIZATION_ICON));
        organizationBtn.setText(ControlConfigUtils.getString("icon.tooltip.organization"));
        saleChannelBtn = new JToggleButton(IziImageUtils.getSmallIcon(IziImageConstants.SALECHANNEL_ICON));
        saleChannelBtn.setText(ControlConfigUtils.getString("icon.tooltip.saleChannel"));
        ButtonGroup contextGroup = new ButtonGroup();
        contextGroup.add(institutionBtn);
        contextGroup.add(organizationBtn);
        contextGroup.add(saleChannelBtn);

        institutionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.remove(0); // remove the center panel
                contentPane.add(institutionPane, BorderLayout.CENTER); // re-add new center-panel
                contentPane.repaint();
                contentPane.validate();
            }
        });
        organizationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.remove(0);
                contentPane.add(organizationPane, BorderLayout.CENTER);
                contentPane.repaint();
                contentPane.validate();
            }
        });
        saleChannelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.remove(0);
                contentPane.add(saleChannelPane, BorderLayout.CENTER);
                contentPane.repaint();
                contentPane.validate();
            }
        });
    }

    /**
     * @return
     */
    private static List<AbstractDomain> createInstitutionDomains(JScrollPane treeScrollPane,
            JScrollPane contentSrollPane) {
        List<AbstractDomain> domains = new ArrayList<>();
        ButtonGroup buttonGroup = new ButtonGroup();

        splashScreen.setValue(20, "Loading institution: System domain ...");
        SystemManagementDomain systemDomain = new SystemManagementDomain(treeScrollPane, contentSrollPane);
        CatalogManagementDomain catalogDomain = new CatalogManagementDomain(treeScrollPane, contentSrollPane);

        splashScreen.setValue(30, "Loading institution: Report domain ...");
        ReportDomain reportDomain = new ReportDomain(treeScrollPane, contentSrollPane);
        buttonGroup.add(systemDomain);
        buttonGroup.add(catalogDomain);
        buttonGroup.add(reportDomain);
        domains.add(systemDomain);
        domains.add(catalogDomain);
        domains.add(reportDomain);
        systemDomain.doClick();
        return domains;
    }

    private static List<AbstractDomain> createOrganizationDomains(JScrollPane treeScrollPane,
            JScrollPane contentSrollPane) {
        List<AbstractDomain> domains = new ArrayList<>();
        ButtonGroup buttonGroup = new ButtonGroup();

        splashScreen.setValue(40, "Loading organization: Contact domain ...");
        ContactManagementDomain contactDomain = new ContactManagementDomain(treeScrollPane, contentSrollPane);

        splashScreen.setValue(50, "Loading organization: Store domain ...");
        StoreManagementDomain storeDomain = new StoreManagementDomain(treeScrollPane, contentSrollPane);

        splashScreen.setValue(60, "Loading organization: Supplier chain domain ...");
        // SupplyChainDomain supplyChainDomain = new SupplyChainDomain(treeScrollPane, contentSrollPane);
        buttonGroup.add(contactDomain);
        buttonGroup.add(storeDomain);
        // buttonGroup.add(supplyChainDomain);
        domains.add(contactDomain);
        domains.add(storeDomain);
        // domains.add(supplyChainDomain);

        contactDomain.doClick();
        return domains;
    }

    private static List<AbstractDomain> createSaleChannelDomains(JScrollPane treeScrollPane,
            JScrollPane contentSrollPane) {
        List<AbstractDomain> domains = new ArrayList<>();
        ButtonGroup buttonGroup = new ButtonGroup();

        splashScreen.setValue(70, "Loading sale channel: Buy management domain ...");
        BuyManagementDomain buyDomain = new BuyManagementDomain(treeScrollPane, contentSrollPane);
        splashScreen.setValue(80, "Loading sale channel: Sales management domain ...");
        SalesManagementDomain salesDomain = new SalesManagementDomain(treeScrollPane, contentSrollPane);
        splashScreen.setValue(90, "Loading sale channel: Finance management domain ...");
        FinanceManagementDomain financeDomain = new FinanceManagementDomain(treeScrollPane, contentSrollPane);
        splashScreen.setValue(95, "Loading sale channel: Resource management domain ...");
        // ResourceManagementDomain resourceDomain = new ResourceManagementDomain(treeScrollPane, contentSrollPane);
        buttonGroup.add(buyDomain);
        buttonGroup.add(salesDomain);
        buttonGroup.add(financeDomain);
        // buttonGroup.add(resourceDomain);
        domains.add(buyDomain);
        domains.add(salesDomain);
        domains.add(financeDomain);
        // domains.add(resourceDomain);
        buyDomain.doClick();
        return domains;
    }

    private static JPanel createDomainPane(List<AbstractDomain> domains) {
        JPanel panel = new JPanel(new MigLayout("wrap, gap 0, ins 0, fill", "grow"));
        for (AbstractDomain domain : domains) {
            panel.add(domain, "grow");
        }
        return panel;
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(ControlConfigUtils.getString("JMenuBar.File"));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenu helpMenu = new JMenu(ControlConfigUtils.getString("JMenuBar.Help"));

        JMenuItem changeContextMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.changeContext"));
        changeContextMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Bang: add popup change context here.
                // The authentication service always return "admin" currently. Should show this popup after fix
                // authentication.
            }
        });

        JMenuItem userMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.User"));
        userMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //
                // ListOperatorView listOperatorView = new ListOperatorView();
                // listOperatorView.setVisible(true);
                // frame.setContentPane(listOperatorView);
                // frame.repaint();
                // frame.pack();
            }
        });

        JMenuItem customerMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Customer"));
        customerMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        customerMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

        JMenuItem manufacturerMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Manufacturer"));
        manufacturerMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        manufacturerMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // ListManufacturerView listManufacturerView = new ListManufacturerView();
                // listManufacturerView.setVisible(true);
                // listManufacturerView.loadView();
                // frame.setContentPane(listManufacturerView);
                // frame.repaint();
                // frame.pack();
            }
        });

        JMenuItem productMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Product"));
        productMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        productMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

        JMenuItem statisticMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Statistic"));
        statisticMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        JMenuItem invoiceMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Invoice"));
        invoiceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        invoiceMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
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
                LoginDialog loginDialog = new LoginDialog(frame, new Runnable() {

                    @Override
                    public void run() {
                        // Do nothing for test

                    }
                }, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                loginDialog.setVisible(true);
            }
        });
        // End test
        fileMenu.add(changeContextMenuItem);
        // fileMenu.add(userMenuItem);
        // fileMenu.add(customerMenuItem);
        // fileMenu.add(productMenuItem);
        // fileMenu.add(manufacturerMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(statisticMenuItem);
        // fileMenu.add(invoiceMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        fileMenu.add(loginItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // Add context button to the right of JMenuBar
        initContextButtons();
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(institutionBtn);
        menuBar.add(organizationBtn);
        menuBar.add(saleChannelBtn);
        return menuBar;
    }
}
