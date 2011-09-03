package com.hbsoft.ssm.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.util.i18n.ControlConfigUtils;
import com.hbsoft.ssm.view.list.ListGoodsView;
import com.hbsoft.ssm.view.security.LoginDialog;

public class MainProgram {
    private static final Dimension WINDOW_MIN_SIZE = new Dimension(400, 300);
    public static Log s_logger = LogFactory.getLog(MainProgram.class);
    private static final String MESSSAGE_FILE = "i18n/messages";

    public static void main(String[] args) {
        // Not find solution to get class path from ssm-core.
        // String classpath = MainProgram.class.getClassLoader().get
        DOMConfigurator.configure("src/main/resources/log4j.xml");
        s_logger.info("Testing project Hibernate-Spring-log4j-Swing");
        ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
        ConfigProvider configProvider = ConfigProvider.getInstance();
        ControlConfigUtils.init();
        ControlConfigUtils.setLabelMessageBundle(Locale.FRENCH, MESSSAGE_FILE);
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        final JFrame frame = new JFrame("Sales Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(WINDOW_MIN_SIZE);
        JMenuBar menuBar = createMenuBar(frame);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
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

            public void actionPerformed(ActionEvent e) {
                CustomerView customerView = new CustomerView();
                customerView.setVisible(true);
                frame.setContentPane(customerView);
                frame.repaint();
                frame.pack();
            }
        });

        JMenuItem productMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Product"));
        productMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        productMenuItem.addActionListener(new ActionListener() {

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
        JMenuItem exitMenuItem = new JMenuItem(ControlConfigUtils.getString("JMenu.File.Exit"));
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        //test
        JMenuItem loginItem = new JMenuItem("Login");
        loginItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginDialog loginDialog = new LoginDialog(frame);
                loginDialog.setVisible(true);
            }
        });
        //End test
        fileMenu.add(userMenuItem);
        fileMenu.add(customerMenuItem);
        fileMenu.add(productMenuItem);
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
