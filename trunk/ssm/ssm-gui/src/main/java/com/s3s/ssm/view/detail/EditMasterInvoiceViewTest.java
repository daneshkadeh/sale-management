/*
 * EditMasterInvoiceViewTest
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
package com.s3s.ssm.view.detail;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.entity.DetailInvoiceTest;
import com.s3s.ssm.entity.InvoiceTest;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractMasterDetailView;

/**
 * Edit invoice with list details.
 * 
 * @author phamcongbang
 * 
 */
public class EditMasterInvoiceViewTest extends AbstractMasterDetailView<InvoiceTest, DetailInvoiceTest> {
    private static final long serialVersionUID = -8901891656724849010L;

    /**
     * The default constructor.
     * 
     * @param entity
     */
    public EditMasterInvoiceViewTest(InvoiceTest entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, InvoiceTest invoice) {
        invoice.setCreatedDate(new Date());
        detailDataModel.addAttribute("createdDate", FieldTypeEnum.TEXTBOX).editable(false);
        detailDataModel.addAttribute("customerId", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("totalBeforeTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("taxTotal", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("totalAfterTax", FieldTypeEnum.TEXTBOX);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("goodsId", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("goodsName", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("quantity", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("tax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX));

        listDataModel.add(new DetailAttribute("moneyBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("moneyOfTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX));

    }

    // @Override
    // protected void saveOrUpdate(InvoiceTest invoice, List<DetailInvoiceTest> listDetailInvoice) {
    // // ConfigProvider.getInstance().getInvoiceService().save(invoice);
    // for (DetailInvoiceTest detailInvoice : listDetailInvoice) {
    // detailInvoice.setInvoice(invoice);
    // // ConfigProvider.getInstance().getDetailInvoiceService().save(detailInvoice);
    // }
    // getDaoHelper().getDao(InvoiceTest.class).saveOrUpdate(invoice);
    // }
    @Override
    protected void addDetailIntoMaster(InvoiceTest masterEntity, DetailInvoiceTest detailEntity) {
        masterEntity.addDetailInvoice(detailEntity);
    }

    @Override
    protected String getChildFieldName() {
        return "detailInvoices";
    }

    @Override
    protected Class<? extends AbstractEditView<DetailInvoiceTest>> getChildDetailViewClass() {
        return EditDetailInvoiceViewTest.class;
    }
}
