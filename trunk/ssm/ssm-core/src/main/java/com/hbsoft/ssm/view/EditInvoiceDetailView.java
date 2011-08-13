package com.hbsoft.ssm.view;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.hbsoft.ssm.entity.DetailInvoice;

public class EditInvoiceDetailView extends JFrame {
	JLabel lblTitle;

	JLabel lblGoodsId;
	JTextField tflGoodsId;
	JLabel lblGoodsName;
	JTextField tflGoodsName;
	JLabel lblQuantity;
	JTextField tflQuantity;
	JLabel lblPriceBeforeTax;
	JTextField tflPriceBeforeTax;
	JLabel lblTax;
	JTextField tflTax;
	JLabel lblPriceAfterTax;
	JTextField tflPriceAfterTax;
	JLabel lblMoneyBeforeTax;
	JTextField tflMoneyBeforeTax;
	JLabel lblMoneyOfTax;
	JTextField tflMoneyOfTax;
	JLabel lblMoneyAfterTax;
	JTextField tflMoneyAfterTax;

	JButton btnOK;
	JButton btnCancel;

	private Integer JTEXTFIELD_SIZE = 20;
	private InvoiceView parentInvoiceView;
	private DetailInvoice detailInvoice;
	private boolean isNew;

	public EditInvoiceDetailView(InvoiceView parentInvoiceView,
			DetailInvoice detailInvoice, boolean isNew) {
		this.parentInvoiceView = parentInvoiceView;
		this.detailInvoice = detailInvoice;
		this.isNew = isNew;
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		lblGoodsId = new JLabel();
		lblGoodsId.setText("Goods Id");
		tflGoodsId = new JTextField(JTEXTFIELD_SIZE);

		lblGoodsName = new JLabel();
		lblGoodsName.setText("Goods name");
		tflGoodsName = new JTextField(JTEXTFIELD_SIZE);

		lblQuantity = new JLabel();
		lblQuantity.setText("Quantity");
		tflQuantity = new JTextField(JTEXTFIELD_SIZE);

		lblPriceBeforeTax = new JLabel();
		lblPriceBeforeTax.setText("Price before tax");
		tflPriceBeforeTax = new JTextField(JTEXTFIELD_SIZE);

		lblTax = new JLabel("Tax");
		tflTax = new JTextField(JTEXTFIELD_SIZE);

		lblPriceAfterTax = new JLabel("Price after tax");
		tflPriceAfterTax = new JTextField(JTEXTFIELD_SIZE);

		lblMoneyBeforeTax = new JLabel("Money before tax");
		tflMoneyBeforeTax = new JTextField(JTEXTFIELD_SIZE);

		lblMoneyOfTax = new JLabel("Money of tax");
		tflMoneyOfTax = new JTextField(JTEXTFIELD_SIZE);

		lblMoneyAfterTax = new JLabel("Money after tax");
		tflMoneyAfterTax = new JTextField(JTEXTFIELD_SIZE);

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

		lblTitle = new JLabel();

		if (isNew) {
			lblTitle.setText("Create detail invoice");
		} else {
			lblTitle.setText("Edit detail invoice");
		}

		if (detailInvoice == null) {
			detailInvoice = new DetailInvoice();
		} else {
			tflGoodsId.setText(detailInvoice.getGoodsId().toString());
			tflGoodsName.setText(detailInvoice.getGoodsName());
			tflQuantity.setText(detailInvoice.getQuantity().toString());
			tflPriceBeforeTax.setText(detailInvoice.getPriceBeforeTax()
					.toString());
			tflTax.setText(detailInvoice.getTax().toString());
			tflPriceAfterTax.setText(detailInvoice.getPriceAfterTax()
					.toString());
			tflMoneyBeforeTax.setText(detailInvoice.getMoneyBeforeTax()
					.toString());
			tflMoneyOfTax.setText(detailInvoice.getMoneyOfTax().toString());
			tflMoneyAfterTax.setText(detailInvoice.getMoneyAfterTax()
					.toString());
		}

		// Layout the screen
		Container container = getContentPane();
		container.setLayout(new MigLayout("fillx,insets 1, width :500:"));

		JPanel titlePanel = new JPanel(new MigLayout(
				"wrap 1, center, , width :500:"));
		titlePanel.add(lblTitle, "center");
		container.add(titlePanel, "wrap");

		JPanel editPanel = new JPanel(new MigLayout());
		editPanel.add(lblGoodsId);
		editPanel.add(tflGoodsId, "wrap");
		editPanel.add(lblGoodsName);
		editPanel.add(tflGoodsName, "wrap");
		editPanel.add(lblQuantity);
		editPanel.add(tflQuantity, "wrap");
		editPanel.add(lblPriceBeforeTax);
		editPanel.add(tflPriceBeforeTax, "wrap");
		editPanel.add(lblTax);
		editPanel.add(tflTax, "wrap");
		editPanel.add(lblPriceAfterTax);
		editPanel.add(tflPriceAfterTax, "wrap");
		editPanel.add(lblMoneyBeforeTax);
		editPanel.add(tflMoneyBeforeTax, "wrap");
		editPanel.add(lblMoneyOfTax);
		editPanel.add(tflMoneyOfTax, "wrap");
		editPanel.add(lblMoneyAfterTax);
		editPanel.add(tflMoneyAfterTax, "wrap");
		container.add(editPanel, "wrap");

		JPanel pnlButton = new JPanel(new MigLayout("center, , width :500:"));
		pnlButton.add(btnOK, "center");
		pnlButton.add(btnCancel);
		container.add(pnlButton);

		pack();
	}

	protected void btnCancelActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	protected void btnOKActionPerformed(ActionEvent evt) {
		if (tflGoodsId.getText().isEmpty() || tflGoodsName.getText().isEmpty()
				|| tflQuantity.getText().isEmpty()
				|| tflPriceBeforeTax.getText().isEmpty()
				|| tflTax.getText().isEmpty()
				|| tflPriceAfterTax.getText().isEmpty()
				|| tflMoneyBeforeTax.getText().isEmpty()
				|| tflMoneyOfTax.getText().isEmpty()
				|| tflMoneyAfterTax.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Please fill all madatory fields!", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		detailInvoice.setGoodsId(Integer.valueOf(tflGoodsId.getText()));
		detailInvoice.setGoodsName(tflGoodsName.getText());
		detailInvoice.setQuantity(Integer.valueOf(tflQuantity.getText()));
		detailInvoice.setPriceBeforeTax(Double.valueOf(tflPriceBeforeTax
				.getText()));
		detailInvoice.setTax(Double.valueOf(tflTax.getText()));
		detailInvoice.setPriceAfterTax(Double.valueOf(tflPriceAfterTax
				.getText()));
		detailInvoice.setMoneyBeforeTax(Double.valueOf(tflMoneyBeforeTax
				.getText()));
		detailInvoice.setMoneyOfTax(Double.valueOf(tflMoneyOfTax.getText()));
		detailInvoice.setMoneyAfterTax(Double.valueOf(tflMoneyAfterTax
				.getText()));
		parentInvoiceView.notifyEditInvoiceDetail(detailInvoice, isNew);
		this.dispose();
	}
}
