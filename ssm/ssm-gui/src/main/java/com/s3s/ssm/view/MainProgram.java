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
import java.awt.SplashScreen;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;

import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.domain.BuyManagementDomain;
import com.s3s.ssm.view.domain.ContactManagementDomain;
import com.s3s.ssm.view.domain.FinanceManagementDomain;
import com.s3s.ssm.view.domain.ReportDomain;
import com.s3s.ssm.view.domain.ResourceManagementDomain;
import com.s3s.ssm.view.domain.SalesManagementDomain;
import com.s3s.ssm.view.domain.StoreManagementDomain;
import com.s3s.ssm.view.domain.SupplyChainDomain;
import com.s3s.ssm.view.domain.SystemManagementDomain;
import com.s3s.ssm.view.security.LoginDialog;

public class MainProgram {
    private static final Dimension WINDOW_MIN_SIZE = new Dimension(400, 300);
    public static Log s_logger = LogFactory.getLog(MainProgram.class);
    private static final String[] MESSSAGE_FILES = new String[] { "i18n/messages",
            "i18n/config_messages", "i18n/catalog_messages", "i18n/finance_messages", "i18n/sales_messages",
            "i18n/shipment_messages", "i18n/contact_messages", "i18n/store_messages", "i18n/supplychain_messages",
            "i18n/operator_messages", "i18n/gui_messages", "i18n/ui_messages", "i18n/label", "i18n/config_label",
            "i18n/catalog_label", "i18n/finance_label", "i18n/sales_label", "i18n/shipment_label",
            "i18n/contact_label", "i18n/store_label", "i18n/supplychain_label", "i18n/operator_label",
            "i18n/gui_label", "i18n/error", "i18n/config_error", "i18n/catalog_error", "i18n/finance_error",
            "i18n/sales_error", "i18n/shipment_error", "i18n/contact_error", "i18n/store_error",
            "i18n/supplychain_error", "i18n/operator_error", "i18n/gui_error" };

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
        DOMConfigurator.configure("log4j.xml");
        s_logger.info("Starting super sales management application...");
        // ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
        // ConfigProvider configProvider = ConfigProvider.getInstance();
        Locale.setDefault(Locale.FRENCH);
        ControlConfigUtils.init();
        ControlConfigUtils.setLabelMessageBundle(Locale.getDefault(), MESSSAGE_FILES);
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
        // Splash screen
        SplashScreen splashScreen = SplashScreen.getSplashScreen();

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
        frame = new JFrame("Business Active");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(WINDOW_MIN_SIZE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        initCenterPanes();
        contentPane = createContentPane();
        frame.setJMenuBar(createMenuBar());
        frame.setContentPane(contentPane);
        // institutionBtn.doClick();
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
                // frame.dispose();
            }
        });
        loginDialog.setVisible(true);
    }

    /**
     * 
     */
    protected static void initCenterPanes() {
        institutionPane = createInstitutionPanel();
        organizationPane = createOrganizationPanel();
        saleChannelPane = createSaleChannelPanel();
    }

    private static Container createContentPane() {
        JPanel contentPane = new JPanel(new BorderLayout());
        // JToolBar toolbar = createToolbar();
        // contentPane.add(toolbar, BorderLayout.PAGE_START);
        contentPane.add(new JPanel());
        return contentPane;
    }

    private static JSplitPane createInstitutionPanel() {
        JScrollPane treeMenuScrollPane = new JScrollPane();
        JScrollPane contentViewScrollPane = new JScrollPane();
        List<AbstractDomain> institutionDomains = createInstitutionDomains(treeMenuScrollPane, contentViewScrollPane);
        return createMainSplitPane(treeMenuScrollPane, contentViewScrollPane, institutionDomains);
    }

    private static JSplitPane createOrganizationPanel() {
        JScrollPane treeMenuScrollPane = new JScrollPane();
        JScrollPane contentViewScrollPane = new JScrollPane();
        List<AbstractDomain> organizationDomains = createOrganizationDomains(treeMenuScrollPane, contentViewScrollPane);
        return createMainSplitPane(treeMenuScrollPane, contentViewScrollPane, organizationDomains);
    }

    private static JSplitPane createSaleChannelPanel() {
        JScrollPane treeMenuScrollPane = new JScrollPane();
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
        institutionBtn = new JToggleButton(ImageUtils.getSmallIcon(ImageConstants.INSTITUTION_ICON));
        institutionBtn.setToolTipText(ControlConfigUtils.getString("icon.tooltip.institution"));
        organizationBtn = new JToggleButton(ImageUtils.getSmallIcon(ImageConstants.ORGANIZATION_ICON));
        organizationBtn.setToolTipText(ControlConfigUtils.getString("icon.tooltip.organization"));
        saleChannelBtn = new JToggleButton(ImageUtils.getSmallIcon(ImageConstants.SALECHANNEL_ICON));
        saleChannelBtn.setToolTipText(ControlConfigUtils.getString("icon.tooltip.saleChannel"));
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
        SystemManagementDomain systemDomain = new SystemManagementDomain(treeScrollPane, contentSrollPane);
        ReportDomain reportDomain = new ReportDomain(treeScrollPane, contentSrollPane);
        buttonGroup.add(systemDomain);
        buttonGroup.add(reportDomain);
        domains.add(systemDomain);
        domains.add(reportDomain);
        systemDomain.doClick();
        return domains;
    }

    private static List<AbstractDomain> createOrganizationDomains(JScrollPane treeScrollPane,
            JScrollPane contentSrollPane) {
        List<AbstractDomain> domains = new ArrayList<>();
        ButtonGroup buttonGroup = new ButtonGroup();
        ContactManagementDomain contactDomain = new ContactManagementDomain(treeScrollPane, contentSrollPane);
        StoreManagementDomain storeDomain = new StoreManagementDomain(treeScrollPane, contentSrollPane);
        SupplyChainDomain supplyChainDomain = new SupplyChainDomain(treeScrollPane, contentSrollPane);
        buttonGroup.add(contactDomain);
        buttonGroup.add(storeDomain);
        buttonGroup.add(supplyChainDomain);
        domains.add(contactDomain);
        domains.add(storeDomain);
        domains.add(supplyChainDomain);

        contactDomain.doClick();
        return domains;
    }

    private static List<AbstractDomain> createSaleChannelDomains(JScrollPane treeScrollPane,
            JScrollPane contentSrollPane) {
        List<AbstractDomain> domains = new ArrayList<>();
        ButtonGroup buttonGroup = new ButtonGroup();
        BuyManagementDomain buyDomain = new BuyManagementDomain(treeScrollPane, contentSrollPane);
        SalesManagementDomain salesDomain = new SalesManagementDomain(treeScrollPane, contentSrollPane);
        FinanceManagementDomain financeDomain = new FinanceManagementDomain(treeScrollPane, contentSrollPane);
        ResourceManagementDomain resourceDomain = new ResourceManagementDomain(treeScrollPane, contentSrollPane);
        buttonGroup.add(buyDomain);
        buttonGroup.add(salesDomain);
        buttonGroup.add(financeDomain);
        buttonGroup.add(resourceDomain);
        domains.add(buyDomain);
        domains.add(salesDomain);
        domains.add(financeDomain);
        domains.add(resourceDomain);
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
                    public void run() {

                    }
                });
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

        // Add context button to the right of JMenuBar
        initContextButtons();
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(institutionBtn);
        menuBar.add(organizationBtn);
        menuBar.add(saleChannelBtn);
        return menuBar;
    }
}
