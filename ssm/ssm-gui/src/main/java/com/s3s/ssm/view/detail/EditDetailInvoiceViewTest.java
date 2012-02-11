/*
 * EditDetailInvoiceViewTest
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

import com.s3s.ssm.entity.DetailInvoiceTest;
import com.s3s.ssm.entity.InvoiceTest;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditDetailInvoiceViewTest extends AbstractSingleEditView<DetailInvoiceTest> {
    private static final long serialVersionUID = 4336242795515386078L;

    public EditDetailInvoiceViewTest(DetailInvoiceTest entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, DetailInvoiceTest detailInvoice) {
        detailDataModel.addAttribute("goodsId", FieldTypeEnum.TEXTBOX).enable(true);
        detailDataModel.addAttribute("goodsName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("quantity", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("priceBeforeTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("tax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX);

        detailDataModel.addAttribute("moneyBeforeTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("moneyOfTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX);

        // listDataModel.add(new DetailDataModel("invoiceId",
        // FieldTypeEnum.TEXT_BOX));
        // TODO: work-around to pass onBindAndValidate. InvoiceId will be set
        // later.
        detailInvoice.setInvoice(new InvoiceTest());

    }

    @Override
    protected void saveOrUpdate(DetailInvoiceTest entity2) {
        // Do nothing. Wait for saveOrUpdate by EditMasterInvoiceView
        // Set a fake id if add a new entity, pretend the entity is saved.
        // if (entity2.getId() == null) {
        // entity2.setId(-1L);
        // }
    }

}
