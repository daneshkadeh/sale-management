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
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractMasterDetailView;

public class EditCustomerView extends AbstractMasterDetailView<Customer, ContactShop> {
    private static final String PARTNER_CATE_REF_ID = "0";
    private static final String REF_BANK = "1";

    public EditCustomerView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("address", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("phone", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("fixPhone", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("fax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("email", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("remark", FieldTypeEnum.TEXTBOX));
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
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("partnerCateSet", FieldTypeEnum.MULTI_SELECT_LIST_BOX).referenceDataId(
                PARTNER_CATE_REF_ID);
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTAREA);
        detailDataModel.addAttribute("fixPhone", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("mobilePhone", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("email", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("taxCode", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("bankAccount.bank", FieldTypeEnum.DROPDOWN).referenceDataId(REF_BANK);
        detailDataModel.addAttribute("bankAccount.accountNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("bankAccount.accountName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isActive", FieldTypeEnum.CHECKBOX);
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
