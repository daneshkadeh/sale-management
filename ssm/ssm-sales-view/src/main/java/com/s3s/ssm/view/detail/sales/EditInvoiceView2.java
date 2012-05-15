package com.s3s.ssm.view.detail.sales;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

import com.s3s.ssm.context.OrgSalesContextProvider;
import com.s3s.ssm.dto.finance.TestDTO;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.config.Address;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.Invoice.InvoiceStoreStatus;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.reports.bean.DetailInvoiceDataBean;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.util.SalesViewHelper;

/**
 * This is sales invoice view.
 * 
 * @author phamcongbang
 * 
 */
public class EditInvoiceView2 extends AbstractSingleEditView<Invoice> {
    private static final long serialVersionUID = -7849498072868923918L;
    private static final String REF_CURRENCY = "REF_CURRENCY";
    private static final String REF_ITEM = "item";
    private static final String REF_PACKLINE = "packageLine";
    private static final String REF_INVOICE_TYPE = "type";
    private static final String REF_D_INVOICE_TYPE = "detailtype";
    private static final String REF_INVOICE_STATUS = "status";
    private static final String REF_D_INVOICE_STATUS = "detailstatus";
    private static final String REF_PAY_STATUS = "paymentStatus";
    private static final String REF_STORE_STATUS = "REF_STORE_STATUS";

    public EditInvoiceView2(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Invoice entity, Map<String, Object> request) {
        detailDataModel.tab(ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), null, null);
        detailDataModel.addAttribute("invoiceNumber", DetailFieldType.TEXTBOX).editable(false);
        // detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_INVOICE_TYPE).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_INVOICE_STATUS)
                .newColumn();

        detailDataModel.addAttribute("moneyAfterTax", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("paymentStatus", DetailFieldType.DROPDOWN).referenceDataId(REF_PAY_STATUS)
                .newColumn();

        // TODO: contact will be chosen from and listSearchView
        detailDataModel.addAttribute("contact", DetailFieldType.SEARCHER).componentInfo(
                ComponentFactory.createCustomerSearchInfo());

        detailDataModel.addAttribute("storeStatus", DetailFieldType.DROPDOWN).referenceDataId(REF_STORE_STATUS)
                .newColumn();

        // TODO: editable = false not work for rawAttribute
        detailDataModel.addRawAttribute("contact.info", DetailFieldType.TEXTAREA)
                .value(getContactInfo(entity.getContact())).editable(false);

        detailDataModel.addAttribute("staff", DetailFieldType.SEARCHER).mandatory(true)
                .componentInfo(ComponentFactory.createOperatorComponentInfo());

        detailDataModel.addAttribute("detailInvoices", DetailFieldType.LIST).componentInfo(
                createInvoiceDetailsComponentInfo());

        // detailDataModel.tab(ControlConfigUtils.getString("tab.EditInvoiceView.commissions"), null, null);
        detailDataModel.addAttribute("commissions", DetailFieldType.LIST)
                .componentInfo(createCommissionComponentInfo());

        detailDataModel.tab(ControlConfigUtils.getString("tab.EditInvoiceView.listPayments"), null, null);
        detailDataModel.addRawAttribute("paymentList", DetailFieldType.LIST)
                .componentInfo(SalesViewHelper.createPaymentComponentInfo())
                .value(SalesViewHelper.createPaymentComponentData(entity)).editable(false);

        // TODO: remove - Testing from Phuc
        detailDataModel.tab(ControlConfigUtils.getString("Testing"), null, null);
        detailDataModel.addRawAttribute("rawList", DetailFieldType.LIST)
                .componentInfo(createTestPaymentComponentInfo()).value(createTestPaymentComponentData())
                .editable(false);
    }

    private List<TestDTO> createTestPaymentComponentData() {
        TestDTO t1 = new TestDTO("value 1", "value 2", "value 3");
        TestDTO t2 = new TestDTO("value 4", "value 5", "value 6");
        TestDTO t3 = new TestDTO("value 7", "value 8", "value 9");
        return Arrays.asList(t1, t2, t3);
    }

    /**
     * @return
     */
    private IComponentInfo createTestPaymentComponentInfo() {
        ListTestDTOComponent t = new ListTestDTOComponent(null, null, null);
        return new ListComponentInfo(t, null);
    }

    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, Invoice entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final MoneyComponent mc = (MoneyComponent) name2AttributeComponent.get("moneyAfterTax").getComponent();
        final AListInvoiceDetailComponent listDetailCom = (AListInvoiceDetailComponent) name2AttributeComponent.get(
                "detailInvoices").getComponent();
        // TODO: Commission percent must be added after invoice is done.
        final ListCommissionComponent listCommissionCom = (ListCommissionComponent) name2AttributeComponent.get(
                "commissions").getComponent();

        listDetailCom.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                mc.setMoney(listDetailCom.getTotalAmounts().plus(listCommissionCom.getSumCommissionAmount()));
            }
        });

        listCommissionCom.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                mc.setMoney(listDetailCom.getTotalAmounts().plus(listCommissionCom.getSumCommissionAmount()));
            }
        });
    }

    @Override
    protected Invoice loadForCreate(Map<String, Object> request) {
        Invoice invoice = super.loadForCreate(request);
        invoice.setType(InvoiceType.SALES);
        invoice.setCreatedDate(new Date());
        invoice.setInvoiceNumber(serviceProvider.getService(InvoiceService.class).getNextInvoiceNumber());
        invoice.setStaff(((OrgSalesContextProvider) contextProvider).getCurrentOperator());
        return invoice;
    }

    private IComponentInfo createInvoiceDetailsComponentInfo() {
        AListInvoiceDetailComponent component = new AListInvoiceDetailComponent(null, null, null);
        return new ListComponentInfo(component, "invoice");
    }

    private IComponentInfo createCommissionComponentInfo() {
        ListCommissionComponent component = new ListCommissionComponent(null, null, null);
        return new ListComponentInfo(component, "invoice");
    }

    @Override
    protected void bindingValue(Invoice entity, String name, Object value, DetailAttribute detailAttribute) {
        super.bindingValue(entity, name, value, detailAttribute);
        // TODO: bad override component! Should change later to handle attribute only.
        // This business is only applied when "contact" lost focus.
        if ("contact".equals(name)) {
            Partner contact = (Partner) value;
            AttributeComponent contactComponent = getName2AttributeComponent().get("contact.info");
            JScrollPane contactNameCom = (JScrollPane) contactComponent.getComponent();
            ((JTextArea) ((JViewport) contactNameCom.getComponent(0)).getView()).setText(getContactInfo(contact));

            // TODO: add paymentAmount, contactDebt, newContactDebt
        }
    }

    private String getContactInfo(Partner contact) {
        if (contact != null) {
            String info = contact.getName();
            info += (contact.getPhone() != null) ? ("\n" + contact.getPhone()) : "";
            Address address = contact.getMainAddressLink().getAddress();
            if (address != null) {
                info += "\n" + address.getAddress() + ", " + address.getDistrict() + ", " + address.getCity();
            }
            return info;
        }
        return "";
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Invoice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_INVOICE_STATUS, InvoiceStatus.values());
        refDataModel.putRefDataList(REF_INVOICE_TYPE, InvoiceType.values());
        refDataModel.putRefDataList(REF_D_INVOICE_STATUS, DetailInvoiceStatus.values());
        refDataModel.putRefDataList(REF_D_INVOICE_TYPE, DetailInvoiceType.values());
        refDataModel.putRefDataList(REF_PAY_STATUS, InvoicePaymentStatus.values());
        refDataModel.putRefDataList(REF_STORE_STATUS, InvoiceStoreStatus.values());
        refDataModel.putRefDataList(REF_ITEM, getDaoHelper().getDao(Item.class).findAll());
        refDataModel.putRefDataList(REF_PACKLINE, getDaoHelper().getDao(PackageLine.class).findAll());
        refDataModel.putRefDataList(REF_CURRENCY, serviceProvider.getService(IConfigService.class).getCurrencyCodes());
    }

    @Override
    protected JToolBar createToolBar() {
        JToolBar tb = super.createToolBar();
        tb.add(new JSeparator(SwingConstants.VERTICAL));

        JButton printBtn = new JButton(ImageUtils.getSmallIcon(ImageConstants.PRINT_ICON));
        printBtn.setText(ControlConfigUtils.getString("EditInvoiceView.print"));
        printBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(EditInvoiceView2.this));
                dialog.setModal(true);
                Map<String, Object> param = new HashMap<>();
                param.put("INVOICE_ID", entity.getId());
                param.put("INVOICE_CODE", entity.getInvoiceNumber());
                param.put("INVOICE_DATE", entity.getCreatedDate());
                Collection<DetailInvoiceDataBean> data = createDetailInvoiceDataBean(entity.getDetailInvoices());
                JRDataSource ds = new JRBeanCollectionDataSource(data);
                try {
                    JasperPrint jp = JasperFillManager.fillReport(
                            getClass().getResourceAsStream("/reports/invoiceBeanDataResource.jasper"), param, ds);
                    JRViewer jv = new JRViewer(jp);
                    dialog.add(jv);
                    dialog.setSize(new Dimension(500, 500));
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                } catch (JRException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }

            private Collection<DetailInvoiceDataBean> createDetailInvoiceDataBean(Set<DetailInvoice> detailInvoices) {
                List<DetailInvoiceDataBean> diDB = new ArrayList<DetailInvoiceDataBean>(detailInvoices.size());
                for (DetailInvoice di : detailInvoices) {
                    diDB.add(new DetailInvoiceDataBean(di));
                }
                return diDB;
            }
        });

        tb.add(printBtn);
        return tb;
    }

}
