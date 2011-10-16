package com.s3s.ssm.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.s3s.ssm.entity.DetailInvoice;
import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.service.CustomerService;
import com.s3s.ssm.service.DetailInvoiceService;
import com.s3s.ssm.service.InvoiceService;
import com.s3s.ssm.util.ConfigProvider;

@Deprecated
public class InvoiceView extends JFrame {
    private static final long serialVersionUID = -2959605446443105863L;

    private Log logger = LogFactory.getLog(InvoiceView.class);

    private JLabel lblTitle;
    private JLabel lblInvoiceId;
    private JTextField tflInvoiceId;
    private JLabel lblCustomerId;
    private JTextField tflCustomerId;
    private JLabel lblCreatedDate;
    private JTextField tflCreatedDate;
    private JLabel lblTotalBeforeTax;
    private JTextField tflTotalBeforeTax;
    private JLabel lblTax;
    private JTextField tflTax;
    private JLabel lblTotalAfterTax;
    private JTextField tflTotalAfterTax;
    private JButton btnOK;
    private JButton btnCancel;

    private JLabel lblDetailInvoice;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JTable tblInvoiceDetailList;
    private JScrollPane jScrollPane1;

    private Boolean selector = false;
    private Integer JTEXTFIELD_SIZE = 20;
    private InvoiceService invoiceService = ConfigProvider.getInstance().getInvoiceService();
    private DetailInvoiceService detailInvoiceService = ConfigProvider.getInstance().getDetailInvoiceService();
    private CustomerService customerService = ConfigProvider.getInstance().getCustomerSerice();

    private Integer customerId;
    private Date createdDate = new Date();
    private List<DetailInvoice> listDetailInvoice = new ArrayList<DetailInvoice>();

    public InvoiceView(Integer customerId) {
        this.customerId = customerId;
        initComponents();
    }

    private void initComponents() {
        // TODO: should support I18N
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lblTitle = new JLabel();
        lblTitle.setText("Invoice of customer: " + customerId + " - " + customerService.findById(customerId).getName());

        lblInvoiceId = new JLabel();
        lblInvoiceId.setText("Invoice Id");
        tflInvoiceId = new JTextField(JTEXTFIELD_SIZE);
        tflInvoiceId.setEditable(false);

        lblCustomerId = new JLabel();
        lblCustomerId.setText("Customer Id");
        tflCustomerId = new JTextField(JTEXTFIELD_SIZE);
        tflCustomerId.setText(customerId.toString());
        tflCustomerId.setEditable(false);

        lblCreatedDate = new JLabel();
        lblCreatedDate.setText("Created date");
        tflCreatedDate = new JTextField(JTEXTFIELD_SIZE);
        SimpleDateFormat formatter = new SimpleDateFormat();
        tflCreatedDate.setText(formatter.format(createdDate));
        tflCreatedDate.setEditable(false);

        lblTotalBeforeTax = new JLabel();
        lblTotalBeforeTax.setText("Total before tax");
        tflTotalBeforeTax = new JTextField(JTEXTFIELD_SIZE);

        lblTax = new JLabel();
        lblTax.setText("Tax");
        tflTax = new JTextField(JTEXTFIELD_SIZE);

        lblTotalAfterTax = new JLabel();
        lblTotalAfterTax.setText("Total after tax");
        tflTotalAfterTax = new JTextField(JTEXTFIELD_SIZE);

        btnOK = new JButton("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblDetailInvoice = new JLabel();
        lblDetailInvoice.setText("Detail invoice");

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        tblInvoiceDetailList = new JTable();
        tblInvoiceDetailList.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null,
                null, null, null }, }, new String[] { "GoodId", "GoodName", "Quan", "PriceBT", "Tax", "PriceAT",
                "MoneyBT", "MoneyTax", "MoneyAT" }));
        displayDetailInvoiceList(listDetailInvoice);
        tblInvoiceDetailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblInvoiceDetailList.getSelectionModel().addListSelectionListener(new RowListener());
        // tblInvoiceDetailList.setSize(1024, 768);

        jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(tblInvoiceDetailList);
        // jScrollPane1.setSize(1024, 768);

        // Layout the screen
        Container container = getContentPane();
        container.setLayout(new MigLayout("fillx,insets 1, width :1024:"));

        JPanel titlePanel = new JPanel(new MigLayout("wrap 1, center, , width :1024:"));
        titlePanel.add(lblTitle, "center");
        container.add(titlePanel, "wrap");

        JPanel ptnInvoiceButton = new JPanel(new MigLayout("width :100:"));
        ptnInvoiceButton.add(btnOK);
        ptnInvoiceButton.add(btnCancel);
        container.add(ptnInvoiceButton, "center, wrap");

        JPanel invoicePanel = new JPanel(new MigLayout("fillx,insets 1"));
        invoicePanel.add(lblInvoiceId);
        invoicePanel.add(tflInvoiceId, "wrap");
        invoicePanel.add(lblCustomerId);
        invoicePanel.add(tflCustomerId, "wrap");
        invoicePanel.add(lblCreatedDate);
        invoicePanel.add(tflCreatedDate, "wrap");
        invoicePanel.add(lblTotalBeforeTax);
        invoicePanel.add(tflTotalBeforeTax, "wrap");
        invoicePanel.add(lblTax);
        invoicePanel.add(tflTax, "wrap");
        invoicePanel.add(lblTotalAfterTax);
        invoicePanel.add(tflTotalAfterTax, "wrap");
        container.add(invoicePanel, "wrap");

        JPanel pnlDetailInvoice = new JPanel(new MigLayout("width :1024:"));
        pnlDetailInvoice.add(lblDetailInvoice, "split, span, gaptop 10");
        pnlDetailInvoice.add(new JSeparator(), "growx, wrap, gaptop 10");
        pnlDetailInvoice.add(btnAdd, "gap 10");
        pnlDetailInvoice.add(btnUpdate);
        pnlDetailInvoice.add(btnDelete, "wrap");
        container.add(pnlDetailInvoice, "wrap");

        JPanel pnlTable = new JPanel(new MigLayout("width :1024:"));
        pnlTable.add(jScrollPane1);
        container.add(pnlTable, "wrap");
        pack();
    }

    private void displayDetailInvoiceList(List<DetailInvoice> detailInvoiceList) {
        Vector<String> tableHeaders = new Vector<String>();
        Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
        tableHeaders.addAll(Arrays.asList("GoodId", "GoodName", "Quan", "PriceBT", "Tax", "PriceAT", "MoneyBT",
                "MoneyTax", "MoneyAT"));
        for (DetailInvoice detailInvoice : detailInvoiceList) {
            Vector<Object> oneRow = new Vector<Object>();
            oneRow.add(detailInvoice.getGoodsId());
            oneRow.add(detailInvoice.getGoodsName());
            oneRow.add(detailInvoice.getQuantity());
            oneRow.add(detailInvoice.getPriceBeforeTax());
            oneRow.add(detailInvoice.getTax());
            oneRow.add(detailInvoice.getPriceAfterTax());
            oneRow.add(detailInvoice.getMoneyBeforeTax());
            oneRow.add(detailInvoice.getMoneyOfTax());
            oneRow.add(detailInvoice.getMoneyAfterTax());
            tableData.add(oneRow);
        }
        tblInvoiceDetailList.setModel(new DefaultTableModel(tableData, tableHeaders));
        selector = false;
    }

    protected void btnCancelActionPerformed(ActionEvent evt) {
        dispose();
    }

    protected void btnOKActionPerformed(ActionEvent evt) {
        // TODO: should have generic bindAndValidate mechanism here!
        if (tflTax.getText().isEmpty() || tflTotalBeforeTax.getText().isEmpty() || tflTotalAfterTax.getText() == null
                || CollectionUtils.isEmpty(listDetailInvoice)) {
            JOptionPane.showMessageDialog(this, "Please fill all madatory fields! Invoice is not saved.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setCreatedDate(createdDate);
        invoice.setTaxTotal(Double.valueOf(tflTax.getText()));
        invoice.setTotalBeforeTax(Double.valueOf(tflTotalBeforeTax.getText()));
        invoice.setTotalAfterTax(Double.valueOf(tflTotalAfterTax.getText()));
        try {
            invoiceService.save(invoice);

            for (DetailInvoice detailInvoice : listDetailInvoice) {
                detailInvoice.setInvoice(invoice);
            }
            detailInvoiceService.saveOrUpdate(listDetailInvoice);
            JOptionPane.showMessageDialog(this, "Inserted new invoice successfully!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error when insert new invoice!", "Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Error when insert new invoice! Please check data!", e);
        }
    }

    protected void btnAddActionPerformed(ActionEvent evt) {
        EditInvoiceDetailView detailView = new EditInvoiceDetailView(this, null, true);
        detailView.setVisible(true);
    }

    private void btnUpdateActionPerformed(ActionEvent evt) {
        if (selector) {
            Integer goodsId = Integer.parseInt(tblInvoiceDetailList.getValueAt(
                    tblInvoiceDetailList.getSelectedRows()[0], 0).toString());

            DetailInvoice detailInvoice = getDetailInvoice(goodsId);
            EditInvoiceDetailView detailView = new EditInvoiceDetailView(this, detailInvoice, false);
            detailView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select one row to update!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnDeleteActionPerformed(ActionEvent evt) {
        if (selector) {
            Integer goodsId = Integer.parseInt(tblInvoiceDetailList.getValueAt(
                    tblInvoiceDetailList.getSelectedRows()[0], 0).toString());

            // TODO: should support delete by Id to reduce 1 select query.
            DetailInvoice detailInvoice = getDetailInvoice(goodsId);
            listDetailInvoice.remove(detailInvoice);
            displayDetailInvoiceList(listDetailInvoice);
        } else {
            JOptionPane.showMessageDialog(this, "Please select one row to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            try {
                selector = true;
            } catch (Exception e) {
            }
        }
    }

    public void notifyEditInvoiceDetail(DetailInvoice detailInvoice, boolean isNew) {
        DetailInvoice sameGood = getDetailInvoice(detailInvoice.getGoodsId());
        if (sameGood == null) {
            listDetailInvoice.add(detailInvoice);
        } else if (isNew) {
            sameGood.setQuantity(sameGood.getQuantity() + detailInvoice.getQuantity());
            sameGood.setPriceBeforeTax(sameGood.getPriceBeforeTax() + detailInvoice.getPriceBeforeTax());
            sameGood.setPriceAfterTax(sameGood.getPriceAfterTax() + detailInvoice.getPriceAfterTax());
            sameGood.setTax(sameGood.getTax() + detailInvoice.getTax());
            sameGood.setMoneyBeforeTax(sameGood.getMoneyBeforeTax() + detailInvoice.getMoneyBeforeTax());
            sameGood.setMoneyOfTax(sameGood.getMoneyOfTax() + detailInvoice.getMoneyOfTax());
            sameGood.setMoneyAfterTax(sameGood.getMoneyAfterTax() + detailInvoice.getMoneyAfterTax());
        } else { // is updated
            sameGood = detailInvoice;
        }
        displayDetailInvoiceList(listDetailInvoice);
    }

    private DetailInvoice getDetailInvoice(Integer goodsId) {
        for (DetailInvoice detailInvoice : listDetailInvoice) {
            if (detailInvoice.getGoodsId().equals(goodsId)) {
                return detailInvoice;
            }
        }
        return null;
    }
}
