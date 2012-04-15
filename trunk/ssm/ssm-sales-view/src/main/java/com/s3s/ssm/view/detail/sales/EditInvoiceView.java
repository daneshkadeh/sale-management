/*
 * EditInvoiceView
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
package com.s3s.ssm.view.detail.sales;

import java.awt.Dialog;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import com.s3s.ssm.entity.catalog.Goods;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.entity.catalog.Service;
import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.entity.config.Address;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.EntityDialog;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class EditInvoiceView extends AbstractMasterDetailView<Invoice, DetailInvoice> {

    private static final String REF_CURRENCY = "REF_CURRENCY";
    private static final String REF_ITEM = "item";
    private static final String REF_PACKLINE = "packageLine";
    private static final String REF_INVOICE_TYPE = "type";
    private static final String REF_D_INVOICE_TYPE = "detailtype";
    private static final String REF_CONTACT = "contact";
    private static final String REF_INVOICE_STATUS = "status";
    private static final String REF_D_INVOICE_STATUS = "detailstatus";
    private static final String REF_PAY_STATUS = "paymentStatus";

    public EditInvoiceView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        // listDataModel.setEditable(true);
        listDataModel.addColumn("product", ListRendererType.TEXT);
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_ITEM)
                .width(150, 50, 200);
        listDataModel.addColumn("package", ListRendererType.TEXT);
        listDataModel.addColumn("packageLine", ListRendererType.TEXT, ListEditorType.COMBOBOX)
                .referenceDataId(REF_PACKLINE).notEditable();
        listDataModel.addColumn("amount", ListRendererType.TEXT);
        listDataModel.addColumn("priceAfterTax", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120);
        listDataModel.addColumn("type", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_D_INVOICE_TYPE);
        listDataModel.addColumn("status", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_D_INVOICE_STATUS);
        listDataModel.addColumn("totalAmount", ListRendererType.TEXT).notEditable();
    }

    @Override
    protected boolean preShowEditView(DetailInvoice entity, EditActionEnum action, Map<String, Object> detailParams) {
        if (action == EditActionEnum.NEW) {
            List<ProductTypeCommand> productFamilyTypes = Arrays.asList(
                    new ProductTypeCommand(1L, "GOODS", ControlConfigUtils.getEnumString(ProductFamilyType.class,
                            ProductFamilyType.GOODS)),
                    new ProductTypeCommand(2L, "SERVICE", ControlConfigUtils.getEnumString(ProductFamilyType.class,
                            ProductFamilyType.SERVICE)),
                    new ProductTypeCommand(3L, "VOUCHER", ControlConfigUtils.getEnumString(ProductFamilyType.class,
                            ProductFamilyType.VOUCHER)),
                    new ProductTypeCommand(4L, "PACKAGE", ControlConfigUtils.getEnumString(ProductFamilyType.class,
                            ProductFamilyType.PACKAGE)));
            EntityDialog<ProductTypeCommand> entityDialog = new EntityDialog<ProductTypeCommand>(null,
                    Dialog.ModalityType.APPLICATION_MODAL, productFamilyTypes);
            entityDialog.setLocationRelativeTo(SwingUtilities.getRootPane(this));
            entityDialog.setVisible(true);
            ProductTypeCommand selectedProductType = null;
            if (entityDialog.isPressedOK() == 1) {
                selectedProductType = (ProductTypeCommand) entityDialog.getSelectedEntity();
                detailParams.put("productFamilyType", selectedProductType.getCode());
                // TODO: select package, goods, service, voucher here, then testing
                switch (selectedProductType.getCode()) {
                case "PACKAGE":
                    EntityDialog<SPackage> packageDialog = new EntityDialog<>(null,
                            Dialog.ModalityType.APPLICATION_MODAL, daoHelper.getDao(SPackage.class).findAll());
                    packageDialog.setLocationRelativeTo(SwingUtilities.getRootPane(this));
                    packageDialog.setVisible(true);

                    if (packageDialog.isPressedOK() == 1) {
                        SPackage selectedPackage = packageDialog.getSelectedEntity();
                        detailParams.put("package", selectedPackage);
                    } else {
                        return false;
                    }
                    break;
                case "GOODS":
                    EntityDialog<Goods> goodsDialog = new EntityDialog<>(null, Dialog.ModalityType.APPLICATION_MODAL,
                            daoHelper.getDao(Goods.class).findAll());
                    goodsDialog.setLocationRelativeTo(SwingUtilities.getRootPane(this));
                    goodsDialog.setVisible(true);

                    if (goodsDialog.isPressedOK() == 1) {
                        Goods selectedProduct = goodsDialog.getSelectedEntity();
                        detailParams.put("product", selectedProduct);
                    } else {
                        return false;
                    }
                    break;
                case "SERVICE":
                    EntityDialog<Service> serviceDialog = new EntityDialog<>(null,
                            Dialog.ModalityType.APPLICATION_MODAL, daoHelper.getDao(Service.class).findAll());
                    serviceDialog.setLocationRelativeTo(SwingUtilities.getRootPane(this));
                    serviceDialog.setVisible(true);

                    if (serviceDialog.isPressedOK() == 1) {
                        Service selectedProduct = serviceDialog.getSelectedEntity();
                        detailParams.put("product", selectedProduct);
                    } else {
                        return false;
                    }
                    break;
                case "VOUCHER":
                    EntityDialog<Voucher> voucherDialog = new EntityDialog<>(null,
                            Dialog.ModalityType.APPLICATION_MODAL, daoHelper.getDao(Voucher.class).findAll());
                    voucherDialog.setLocationRelativeTo(SwingUtilities.getRootPane(this));
                    voucherDialog.setVisible(true);

                    if (voucherDialog.isPressedOK() == 1) {
                        Voucher selectedProduct = voucherDialog.getSelectedEntity();
                        detailParams.put("product", selectedProduct);
                    } else {
                        return false;
                    }
                    break;
                default:
                    break;
                }

            } else {
                return false;
            }
            return super.preShowEditView(entity, action, detailParams);
        } else {
            if (entity.getPackage() != null) {
                detailParams.put("productFamilyType", "PACKAGE");
                detailParams.put("package", entity.getPackage());
            } else {
                detailParams.put("productFamilyType", entity.getProduct().getType().getProductFamilyType().toString());
                detailParams.put("product", entity.getProduct());
            }

            // TODO Hack: reset entityId for package, parent detailInvoice always used.
            entity = entity.getParent() != null ? entity.getParent() : entity;
            detailParams.put(PARAM_ENTITY_ID, entity != null ? entity.getId() : null);
            return super.preShowEditView(entity, action, detailParams);
        }
    }

    // TODO: This class is used to show type of product selection
    protected class ProductTypeCommand {
        private Long id;
        private String code;
        private String name;

        public ProductTypeCommand(Long id, String code, String name) {
            setId(id);
            setCode(code);
            setName(name);
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    @Override
    protected Class<? extends AbstractEditView<DetailInvoice>> getChildDetailViewClass() {
        return EditDetailInvoiceVirtualView.class;
    }

    @Override
    protected String getParentFieldName() {
        return "invoice";
    }

    @Override
    protected String getChildFieldName() {
        return "detailInvoices";
    }

    @Override
    protected Invoice loadForCreate(Map<String, Object> request) {
        Invoice invoice = super.loadForCreate(request);
        invoice.setCreatedDate(new Date());
        invoice.setInvoiceNumber(serviceProvider.getService(InvoiceService.class).getNextInvoiceNumber());
        return invoice;
    }

    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Invoice entity, Map<String, Object> request) {
        detailDataModel.addAttribute("invoiceNumber", DetailFieldType.TEXTBOX).editable(false);
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_INVOICE_TYPE).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_INVOICE_STATUS)
                .newColumn();

        detailDataModel.addAttribute("moneyAfterTax", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("paymentStatus", DetailFieldType.DROPDOWN).referenceDataId(REF_PAY_STATUS)
                .newColumn();

        // TODO: contact will be chosen from and listSearchView
        detailDataModel.addAttribute("contact", DetailFieldType.DROPDOWN).referenceDataId(REF_CONTACT);
        // TODO: editable = false not work for rawAttribute
        detailDataModel.addRawAttribute("contact.info", DetailFieldType.TEXTAREA)
                .value(getContactInfo(entity.getContact())).editable(false);
    }

    @Override
    protected void bindingValue(Invoice entity, String name, boolean isRaw, Object value) {
        super.bindingValue(entity, name, isRaw, value);
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
        refDataModel.putRefDataList(REF_CONTACT, getDaoHelper().getDao(Partner.class).findAll());
        refDataModel.putRefDataList(REF_INVOICE_STATUS, InvoiceStatus.values());
        refDataModel.putRefDataList(REF_INVOICE_TYPE, InvoiceType.values());
        refDataModel.putRefDataList(REF_D_INVOICE_STATUS, DetailInvoiceStatus.values());
        refDataModel.putRefDataList(REF_D_INVOICE_TYPE, DetailInvoiceType.values());
        refDataModel.putRefDataList(REF_PAY_STATUS, InvoicePaymentStatus.values());
        refDataModel.putRefDataList(REF_ITEM, getDaoHelper().getDao(Item.class).findAll());
        refDataModel.putRefDataList(REF_PACKLINE, getDaoHelper().getDao(PackageLine.class).findAll());
        refDataModel.putRefDataList(REF_CURRENCY, serviceProvider.getService(IConfigService.class).getCurrencyCodes());
    }

    // @Override
    // protected void addDetailIntoMaster(Invoice masterEntity, DetailInvoice detailEntity) {
    // masterEntity.addDetailInvoice(detailEntity);
    // }

    @Override
    protected void saveOrUpdate(Invoice masterEntity) {
        masterEntity.setMoneyBeforeTax(masterEntity.getMoneyAfterTax());
        super.saveOrUpdate(masterEntity);
    }

}
