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
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
import com.s3s.ssm.view.list.ListCustomerView;
import com.s3s.ssm.view.list.ListGoodsView;
import com.s3s.ssm.view.list.ListInvoiceView;
import com.s3s.ssm.view.list.param.ListManufacturerView;
import com.s3s.ssm.view.list.param.ListUnitOfMeasureView;
import com.s3s.ssm.view.list.param.ListUomCategoryView;
import com.s3s.ssm.view.security.LoginDialog;

public class MainProgram {
    private static final Dimension WINDOW_MIN_SIZE = new Dimension(400, 300);
    public static Log s_logger = LogFactory.getLog(MainProgram.class);
    private static final String MESSSAGE_FILE = "i18n/messages";

    public static void main(String[] args) {
        // Not find solution to get class path from ssm-core.
        // String classpath = MainProgram.class.getClassLoader().get
        DOMConfigurator.configure("src/main/resources/log4j.xml");
        s_logger.info("Starting super sales management application...");
        ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
        ConfigProvider configProvider = ConfigProvider.getInstance();
        ControlConfigUtils.init();
        ControlConfigUtils.setLabelMessageBundle(Locale.FRENCH, MESSSAGE_FILE);
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
        contentPane.setLayout(new MigLayout("wrap 2"));
        contentPane.add(new JLabel("Image component"), "top");
        contentPane.add(new S3sImageChooser());
        contentPane.add(new JLabel("Radio button group"), "top");
        contentPane.add(new S3sRadioButtonsGroup<>(Arrays.asList("Table", "Chair", "Ruler")));
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
                ListCustomerView listCustomerView = new ListCustomerView();
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
                ListGoodsView listGoodsView = new ListGoodsView();
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
                ListInvoiceView listInvoiceView = new ListInvoiceView();
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
