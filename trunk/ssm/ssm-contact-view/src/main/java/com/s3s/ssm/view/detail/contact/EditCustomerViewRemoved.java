/*
 * EditCustomerView
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
package com.s3s.ssm.view.detail.contact;

import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.config.Address;
import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerCategory;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

// This view will be removed. Just keep to refactor code
public class EditCustomerViewRemoved extends AbstractMasterDetailView<Partner, Address> {
    private static final String PARTNER_CATE_REF_ID = "0";
    private static final String REF_BANK = "1";

    public EditCustomerViewRemoved(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("address", ListRendererType.TEXT);
        listDataModel.addColumn("district", ListRendererType.TEXT);
        listDataModel.addColumn("city", ListRendererType.TEXT);
        listDataModel.addColumn("postalCode", ListRendererType.TEXT);
        listDataModel.addColumn("phone", ListRendererType.TEXT);
        listDataModel.addColumn("fixPhone", ListRendererType.TEXT);
        listDataModel.addColumn("fax", ListRendererType.TEXT);
        listDataModel.addColumn("email", ListRendererType.TEXT);
        listDataModel.addColumn("remark", ListRendererType.TEXT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Partner loadForCreate(Map<String, Object> request) {
        Partner customer = super.loadForCreate(request);
        // customer.setBankAccount(new BankAccount());
        return customer;
    }

    @Override
    protected Partner loadForEdit(List<String> eagerLoadedProperties) {
        Partner customer = super.loadForEdit(eagerLoadedProperties);
        // if (customer.getBankAccount() == null) {
        // customer.setBankAccount(new BankAccount());
        // }
        return customer;
    }

    @Override
    protected Class<? extends AbstractEditView<Address>> getChildDetailViewClass() {
        return EditContactShopVirtualView.class;
    }

    @Override
    protected String getChildFieldName() {
        return "listShops"; // TODO Bang: The field name has changed
    }

    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Partner entity, Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("partnerCateSet", DetailFieldType.MULTI_SELECT_LIST_BOX).referenceDataId(
                PARTNER_CATE_REF_ID);
        detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA);
        detailDataModel.addAttribute("fixPhone", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mobilePhone", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("fax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("taxCode", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("bankAccount.bank", DetailFieldType.DROPDOWN).referenceDataId(REF_BANK);
        detailDataModel.addAttribute("bankAccount.accountNumber", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("bankAccount.accountName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("isActive", DetailFieldType.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Partner entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_BANK, getDaoHelper().getDao(Bank.class).findAll(), null);
        List<PartnerCategory> partnerCateList = getDaoHelper().getDao(PartnerCategory.class).findAll();
        refDataModel.putRefDataList(PARTNER_CATE_REF_ID, refDataModel.new ReferenceData(partnerCateList,
                new DefaultListCellRenderer()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParentFieldName() {
        // TODO Bang
        return "";
    }

}