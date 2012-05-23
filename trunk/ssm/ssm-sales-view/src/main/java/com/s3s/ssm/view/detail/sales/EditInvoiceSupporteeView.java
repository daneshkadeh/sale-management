package com.s3s.ssm.view.detail.sales;

import java.util.Date;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import com.s3s.ssm.context.OrgSalesContextProvider;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.config.Address;
import com.s3s.ssm.entity.contact.Partner;
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
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class EditInvoiceSupporteeView extends AbstractSingleEditView<Invoice> {
    private static final String REF_CURRENCY = "REF_CURRENCY";
    private static final String REF_ITEM = "item";
    private static final String REF_PACKLINE = "packageLine";
    private static final String REF_INVOICE_TYPE = "type";
    private static final String REF_D_INVOICE_TYPE = "detailtype";
    private static final String REF_INVOICE_STATUS = "status";
    private static final String REF_D_INVOICE_STATUS = "detailstatus";
    private static final String REF_PAY_STATUS = "paymentStatus";
    private static final String REF_STORE_STATUS = "REF_STORE_STATUS";

    public EditInvoiceSupporteeView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Invoice entity, Map<String, Object> request) {
        detailDataModel.addAttribute("invoiceNumber", DetailFieldType.TEXTBOX).editable(false);
        // detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_INVOICE_TYPE).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_INVOICE_STATUS)
                .newColumn();

        detailDataModel.addAttribute("moneyAfterTax", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("paymentStatus", DetailFieldType.DROPDOWN).referenceDataId(REF_PAY_STATUS)
                .newColumn();

        // TODO: contact will be chosen from and listSearchView
        detailDataModel.addAttribute("contact", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_PARTNER);

        detailDataModel.addAttribute("storeStatus", DetailFieldType.DROPDOWN).referenceDataId(REF_STORE_STATUS)
                .newColumn();

        // TODO: editable = false not work for rawAttribute
        detailDataModel.addRawAttribute("contact.info", DetailFieldType.TEXTAREA)
                .value(getContactInfo(entity.getContact())).editable(false);
        detailDataModel.addAttribute("remark", DetailFieldType.TEXTAREA).newColumn();

        detailDataModel.addAttribute("staff", DetailFieldType.SEARCHER).mandatory(true)
                .componentInfo(ComponentFactory.createOperatorComponentInfo());

        detailDataModel.addAttribute("usedTimeSpan", DetailFieldType.TIME_COMPONENT);
        detailDataModel.addAttribute("usedStartDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("usedEndDate", DetailFieldType.DATE);

        detailDataModel.addAttribute("detailInvoices", DetailFieldType.LIST).componentInfo(
                createInvoiceDetailsComponentInfo());

    }

    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, Invoice entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        // InvoiceSupportee always has totalAmount=0 VND

        // final MoneyComponent mc = (MoneyComponent) name2AttributeComponent.get("moneyAfterTax").getComponent();
        // final AListInvoiceDetailComponent list = (AListInvoiceDetailComponent) name2AttributeComponent.get(
        // "detailInvoices").getComponent();
        // list.addChangeListener(new ChangeListener() {
        //
        // @Override
        // public void stateChanged(ChangeEvent e) {
        // mc.setMoney(list.getTotalAmounts());
        // }
        // });
    }

    @Override
    protected Invoice loadForCreate(Map<String, Object> request) {
        Invoice invoice = super.loadForCreate(request);
        invoice.setType(InvoiceType.SUPPORT);
        invoice.setUsedStartDate(new Date());
        invoice.setCreatedDate(new Date());
        invoice.setInvoiceNumber(serviceProvider.getService(InvoiceService.class).getNextInvoiceNumber());
        invoice.setStaff(((OrgSalesContextProvider) contextProvider).getCurrentOperator());
        return invoice;
    }

    private IComponentInfo createInvoiceDetailsComponentInfo() {
        AListInvoiceDetailComponent component = new AListInvoiceDetailComponent(null, null, null);
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

}
