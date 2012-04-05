/*
 * EditContactShopVirtualView
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

import java.util.Map;

import com.s3s.ssm.entity.config.Address;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditContactShopVirtualView extends AbstractSingleEditView<Address> {

    public EditContactShopVirtualView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Address entity) {
        // detailDataModel.addAttribute("contact", FieldTypeEnum.TEXTBOX).editable(false);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("fixPhone", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("fax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("remark", DetailFieldType.TEXTBOX);
    }

    @Override
    protected void saveOrUpdate(Address entity) {
        // Fake id
        // TODO: How to put this entity to the listContactShop in EditContactView?
        // if (entity.getId() == null) {
        // entity.setId(-1L);
        // }
    }

}
