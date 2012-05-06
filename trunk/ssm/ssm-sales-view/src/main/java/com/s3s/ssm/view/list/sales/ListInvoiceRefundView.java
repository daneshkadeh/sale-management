package com.s3s.ssm.view.list.sales;

import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.swingx.JXDatePicker;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.Invoice.InvoiceStoreStatus;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.detail.sales.EditInvoiceRefundView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInvoiceRefundView extends AListEntityView<Invoice> {
    private static Log logger = LogFactory.getLog(ListInvoiceRefundView.class);
    private JTextField invoiceNumber;
    // private JComboBox<InvoiceType> type;
    // private EntityChooser customer;
    private JXDatePicker fromDate;
    private JXDatePicker toDate;
    private JComboBox<InvoiceStatus> invoiceStatus;
    private JComboBox<InvoicePaymentStatus> invoicePaymentStatus;
    private JComboBox<InvoiceStoreStatus> invoiceStoreStatus;

    public static final String INVOICE_FORM = "invoiceForm";

    /**
     * {@inheritDoc}
     */
    @Override
    protected JPanel createSearchPanel() {
        invoiceNumber = new JTextField();
        InvoiceType[] types = (InvoiceType[]) ArrayUtils.add(InvoiceType.values(), 0, null);
        // type = new JComboBox<>(types);
        fromDate = new JXDatePicker();
        toDate = new JXDatePicker();
        InvoiceStatus[] statuses = (InvoiceStatus[]) ArrayUtils.add(InvoiceStatus.values(), 0, null);
        invoiceStatus = new JComboBox<>(statuses);
        InvoicePaymentStatus[] paymentStatuses = (InvoicePaymentStatus[]) ArrayUtils.add(InvoicePaymentStatus.values(),
                0, null);
        invoicePaymentStatus = new JComboBox<>(paymentStatuses);

        InvoiceStoreStatus[] storeStatuses = (InvoiceStoreStatus[]) ArrayUtils
                .add(InvoiceStoreStatus.values(), 0, null);
        invoiceStoreStatus = new JComboBox<InvoiceStoreStatus>(storeStatuses);
        JPanel panel = new JPanel(new MigLayout("ins 0, fill", "grow"));
        panel.add(new JLabel(ControlConfigUtils.getString("label.Invoice.invoiceNumber")), "right");
        panel.add(invoiceNumber, "grow");
        // panel.add(new JLabel(ControlConfigUtils.getString("label.Invoice.type")), "right");
        // panel.add(type, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Invoice.fromDate")), "right");
        panel.add(fromDate, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Invoice.toDate")), "right");
        panel.add(toDate, "grow, wrap");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Invoice.status")), "right");
        panel.add(invoiceStatus, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Invoice.paymentStatus")), "right");
        panel.add(invoicePaymentStatus, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Invoice.storeStatus")), "right");
        panel.add(invoiceStoreStatus, "grow");
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void clearCriteria() {
        invoiceNumber.setText(null);
        // type.setSelectedItem(null);
        fromDate.setDate(null);
        toDate.setDate(null);
        invoiceStatus.setSelectedItem(null);
        invoicePaymentStatus.setSelectedItem(null);
    }

    @Override
    protected boolean preShowEditView(Invoice entity, EditActionEnum action, Map<String, Object> detailParams) {
        if (action == EditActionEnum.NEW) {
            String message = ControlConfigUtils.getString("label.InvoiceRefundDialog.InputMessage");
            String title = ControlConfigUtils.getString(ControlConstants.MK_COMMON_INFO);
            String code = (String) JOptionPane.showInputDialog(this.getParent(), message, title,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (code == null) {
                return false;
            } else if ("".equals(code)) {
                return false;
            }
            Invoice invoice = serviceProvider.getService(InvoiceService.class).findInvoiceByCode(code);
            if (invoice == null || !InvoiceType.SALES.equals(invoice.getType())) {
                JOptionPane.showMessageDialog(this.getParent(),
                        ControlConfigUtils.getString("label.InvoiceRefundDialog.NotFoundSalesInvoice"),
                        ControlConfigUtils.getString(ControlConstants.MK_COMMON_ERROR), JOptionPane.PLAIN_MESSAGE);
                return false;
            }
            switch (invoice.getStoreStatus()) {
            case EXPORTED:
                detailParams.put(INVOICE_FORM, invoice);
                break;
            case NO_ACTION:
            case EXPORTING:
                message = ControlConfigUtils.getString("label.InvoiceRefundDialog.SalesInvoiceNotExported");
                title = ControlConfigUtils.getString(ControlConstants.MK_COMMON_ERROR);
                JOptionPane.showMessageDialog(this.getParent(), message, title, JOptionPane.PLAIN_MESSAGE);
                return false;
            default:
                logger.error("Invoice can not have this store status " + invoice.getStoreStatus());
                return false;
            }
        }
        return true;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria criteria = super.getCriteriaForView();
        if (StringUtils.isNotBlank(invoiceNumber.getText())) {
            criteria.add(Restrictions.ilike("invoiceNumber", invoiceNumber.getText(), MatchMode.ANYWHERE));
        }

        criteria.add(Restrictions.eq("type", InvoiceType.REFUND));

        // if (type.getSelectedItem() != null) {
        // criteria.add(Restrictions.eq("type", type.getSelectedItem()));
        // }
        if (fromDate.getDate() != null) {
            criteria.add(Restrictions.ge("createdDate", fromDate.getDate()));
        }
        if (toDate.getDate() != null) {
            criteria.add(Restrictions.le("createdDate", toDate.getDate()));
        }
        if (invoiceStatus.getSelectedItem() != null) {
            criteria.add(Restrictions.eq("status", invoiceStatus.getSelectedItem()));
        }
        if (invoicePaymentStatus.getSelectedItem() != null) {
            criteria.add(Restrictions.eq("paymentStatus", invoicePaymentStatus.getSelectedItem()));
        }
        if (invoiceStoreStatus.getSelectedItem() != null) {
            criteria.add(Restrictions.eq("storeStatus", invoiceStoreStatus.getSelectedItem()));
        }
        return criteria;
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("invoiceNumber", ListRendererType.TEXT);
        listDataModel.addColumn("type", ListRendererType.TEXT);
        listDataModel.addColumn("contact", ListRendererType.TEXT);
        listDataModel.addColumn("createdDate", ListRendererType.TEXT);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
        listDataModel.addColumn("paymentStatus", ListRendererType.TEXT);
        listDataModel.addColumn("storeStatus", ListRendererType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<Invoice>> getEditViewClass() {
        return EditInvoiceRefundView.class;
    }

}
