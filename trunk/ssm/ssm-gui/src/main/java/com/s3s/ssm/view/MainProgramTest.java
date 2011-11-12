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

public class MainProgramTest {
    private static final Dimension WINDOW_MIN_SIZE = new Dimension(400, 300);
    public static Log s_logger = LogFactory.getLog(MainProgramTest.class);
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
        addComponentsToTest(contentPane);

        frame.pack();
        frame.setVisible(true);
    }

    private static void addComponentsToTest(Container contentPane) {

        // Just demo. In production we should not init we user still not request open the view.
        final ListCustomerViewTest customerListView = new ListCustomerViewTest();
        final ListGoodsViewTest goodListView = new ListGoodsViewTest();
        final JPanel componentPanel = createDemoComponentPanel();
        final JScrollPane scrollPane = new JScrollPane(componentPanel);
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setRightComponent(scrollPane);

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Sale management");
        DefaultMutableTreeNode userEntry = new DefaultMutableTreeNode("Customer");
        DefaultMutableTreeNode goodEntry = new DefaultMutableTreeNode("Good");
        DefaultMutableTreeNode demoEntry = new DefaultMutableTreeNode("Component demo");
        rootNode.add(userEntry);
        rootNode.add(goodEntry);
        rootNode.add(demoEntry);
        final JTree treeMenu = new JTree(rootNode);
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
                    // splitPane.setRightComponent(customerListView);
                    scrollPane.setViewportView(customerListView);
                } else if (nodeInfo.equals("Good")) {
                    // splitPane.setRightComponent(goodListView);
                    scrollPane.setViewportView(goodListView);
                } else if (nodeInfo.equals("Component demo")) {
                    scrollPane.setViewportView(componentPanel);
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
