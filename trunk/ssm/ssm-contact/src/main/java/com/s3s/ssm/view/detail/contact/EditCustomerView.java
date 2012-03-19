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

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.contact.ContactShop;
import com.s3s.ssm.entity.contact.Customer;
import com.s3s.ssm.entity.contact.PartnerCategory;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCustomerView extends AbstractMasterDetailView<Customer, ContactShop> {
    private static final String PARTNER_CATE_REF_ID = "0";
    private static final String REF_BANK = "1";

    public EditCustomerView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("name", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("address", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("phone", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("fixPhone", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("fax", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("email", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("remark", DetailFieldType.TEXTBOX));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Customer loadForCreate() {
        Customer customer = super.loadForCreate();
        customer.setBankAccount(new BankAccount());
        return customer;
    }

    @Override
    protected Customer loadForEdit(List<String> eagerLoadedProperties) {
        Customer customer = super.loadForEdit(eagerLoadedProperties);
        if (customer.getBankAccount() == null) {
            customer.setBankAccount(new BankAccount());
        }
        return customer;
    }

    @Override
    protected Class<? extends AbstractEditView<ContactShop>> getChildDetailViewClass() {
        return EditContactShopVirtualView.class;
    }

    @Override
    protected String getChildFieldName() {
        return "listShops";
    }

    @Override
    protected void saveOrUpdate(Customer masterEntity, List<ContactShop> detailEntities) {
        super.saveOrUpdate(masterEntity, detailEntities);

        // TODO: check bankAccount not updated and do not update in database.
        // getDaoHelper().getDao(BankAccount.class).saveOrUpdate(masterEntity.getBankAccount());
    }

    @Override
    protected void addDetailIntoMaster(Customer masterEntity, ContactShop detailEntity) {
        // masterEntity.addShop(detailEntity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Customer entity) {
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
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Customer entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_BANK, getDaoHelper().getDao(Bank.class).findAll(), null);
        List<PartnerCategory> partnerCateList = getDaoHelper().getDao(PartnerCategory.class).findAll();
        refDataModel.putRefDataList(PARTNER_CATE_REF_ID, refDataModel.new ReferenceData(partnerCateList,
                new DefaultListCellRenderer()));
    }

}
