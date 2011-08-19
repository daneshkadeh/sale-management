package com.hbsoft.ssm.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.service.CustomerService;
import com.hbsoft.ssm.util.ConfigProvider;

public class CustomerView extends JFrame {
    private static final long serialVersionUID = 99225375801248919L;

    private Log logger = LogFactory.getLog(CustomerView.class);

    JLabel titleLabel;

    JLabel idEditLabel;
    JTextField idEditTextField;
    JLabel nameEditLabel;
    JTextField nameEditTextField;
    JButton btnAdd;
    JButton btnUpdate;
    JButton btnDelete;
    JButton btnGoToCart;

    JLabel customerListLabel;
    JLabel idSearchLabel;
    JTextField idSearchTextField;
    JLabel nameSearchLabel;
    JTextField nameSearchTextField;
    JButton btnSearch;
    JTable tblCustomerList;
    JScrollPane jScrollPane1;

    private Boolean selector = false;
    private Integer JTEXTFIELD_SIZE = 20;
    CustomerService customerService = ConfigProvider.getInstance().getCustomerSerice();

    public CustomerView() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        titleLabel = new JLabel();
        titleLabel.setText("Customer management");

        idEditLabel = new JLabel();
        idEditLabel.setText("Id");
        idEditTextField = new JTextField(JTEXTFIELD_SIZE);
        idEditTextField.setEditable(false);

        nameEditLabel = new JLabel();
        nameEditLabel.setText("Name");
        nameEditTextField = new JTextField(JTEXTFIELD_SIZE);

        btnAdd = new JButton();
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate = new JButton();
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete = new JButton();
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnGoToCart = new JButton();
        btnGoToCart.setText("Go to cart");
        btnGoToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoToCartActionPerformed(evt);
            }
        });

        customerListLabel = new JLabel();
        customerListLabel.setText("List customers");

        idSearchLabel = new JLabel();
        idSearchLabel.setText("Id");
        idSearchTextField = new JTextField(JTEXTFIELD_SIZE);

        nameSearchLabel = new JLabel();
        nameSearchLabel.setText("Name");
        nameSearchTextField = new JTextField(JTEXTFIELD_SIZE);

        btnSearch = new JButton();
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblCustomerList = new JTable();
        tblCustomerList.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null },

        }, new String[] { "Id", "Name" }));
        displayCustomerList(customerService.findAll());
        tblCustomerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCustomerList.getSelectionModel().addListSelectionListener(new RowListener());

        jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(tblCustomerList);

        // Layout the screen
        Container container = getContentPane();
        container.setLayout(new MigLayout("fillx,insets 1, width :500:"));

        JPanel titlePanel = new JPanel(new MigLayout("wrap 1, center, width :500:"));
        titlePanel.add(titleLabel, "center");
        container.add(titlePanel, "wrap");

        JPanel editPanel = new JPanel(new MigLayout());
        editPanel.add(idEditLabel, "gap unrelated");
        editPanel.add(idEditTextField, "wrap");
        editPanel.add(nameEditLabel);
        editPanel.add(nameEditTextField);
        container.add(editPanel, "wrap");

        JPanel editButtonPanel = new JPanel(new MigLayout());
        editButtonPanel.add(btnAdd, "gap 10");
        editButtonPanel.add(btnUpdate, "gap 10");
        editButtonPanel.add(btnDelete, "gap 10");
        editButtonPanel.add(btnGoToCart, "gap 10");
        container.add(editButtonPanel, "wrap");

        JPanel listPanel = new JPanel(new MigLayout());
        listPanel.add(customerListLabel, "wrap");
        listPanel.add(idSearchLabel, "gap 10");
        listPanel.add(idSearchTextField, "");
        listPanel.add(nameSearchLabel, "gap 10");
        listPanel.add(nameSearchTextField, "wrap");
        container.add(listPanel, "wrap");

        JPanel tablePanel = new JPanel(new MigLayout());
        tablePanel.add(btnSearch, "wrap");
        tablePanel.add(jScrollPane1);
        container.add(tablePanel);

        pack();
    }

    private void displayCustomerList(List<Customer> customerList) {
        Vector<String> tableHeaders = new Vector<String>();
        Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
        tableHeaders.add("Id");
        tableHeaders.add("Name");
        for (Customer customer : customerList) {
            Vector<Object> oneRow = new Vector<Object>();
            oneRow.add(customer.getId());
            oneRow.add(customer.getName());
            tableData.add(oneRow);
        }
        tblCustomerList.setModel(new DefaultTableModel(tableData, tableHeaders));
        selector = false;
        idEditTextField.setText("");
        nameEditTextField.setText("");
    }

    private void btnAddActionPerformed(ActionEvent evt) {
        Customer customer = new Customer();
        customer.setName(nameEditTextField.getText());
        try {
            customerService.save(customer);
        } catch (Exception e) {
            logger.error("Error when insert a customer!", e);
            JOptionPane.showMessageDialog(this, "Technical error! Can not add new customer!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        displayCustomerList(customerService.findAll());
    }

    private void btnUpdateActionPerformed(ActionEvent evt) {
        if (selector) {
            Integer id = Integer.parseInt(tblCustomerList.getValueAt(tblCustomerList.getSelectedRows()[0], 0)
                    .toString());
            Customer customer = customerService.findById(id);
            customer.setName(nameEditTextField.getText());
            try {
                customerService.update(customer);
            } catch (Exception e) {
                logger.error("Error when update a customer!", e);
                JOptionPane.showMessageDialog(this, "Technical error! Can not update customer!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            displayCustomerList(customerService.findAll());
        } else {
            JOptionPane.showMessageDialog(this, "Please select one row to update!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnDeleteActionPerformed(ActionEvent evt) {
        if (selector) {
            Integer id = Integer.parseInt(tblCustomerList.getValueAt(tblCustomerList.getSelectedRows()[0], 0)
                    .toString());

            // TODO: should support delete by Id to reduce 1 select query.
            Customer customer = customerService.findById(id);
            try {
                customerService.delete(customer);
            } catch (Exception e) {
                logger.error("Error when delete a customer!", e);
                JOptionPane.showMessageDialog(this, "Technical error! Can not delete customer!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            displayCustomerList(customerService.findAll());
        } else {
            JOptionPane.showMessageDialog(this, "Please select one row to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void btnGoToCartActionPerformed(ActionEvent evt) {
        if (selector) {
            InvoiceView invoiceView = new InvoiceView(Integer.valueOf(idEditTextField.getText()));
            invoiceView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void btnSearchActionPerformed(ActionEvent evt) {
        if (!"".equals(idSearchTextField.getText().trim())) {
            Customer customer = customerService.findById(Integer.valueOf(idSearchTextField.getText()));
            if (customer == null) {
                displayCustomerList(new ArrayList<Customer>());
            } else {
                displayCustomerList(Arrays.asList(customer));
            }
        } else if (!"".equals(nameSearchTextField.getText().trim())) {
            displayCustomerList(customerService.findLikeName(nameSearchTextField.getText().trim()));
        } else {
            JOptionPane.showMessageDialog(this, "Please put the criteria to search!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    protected class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            try {
                idEditTextField.setText(tblCustomerList.getValueAt(tblCustomerList.getSelectedRow(), 0).toString());
                nameEditTextField.setText(tblCustomerList.getValueAt(tblCustomerList.getSelectedRow(), 1).toString());

                selector = true;
            } catch (Exception e) {
            }
        }
    }
}
