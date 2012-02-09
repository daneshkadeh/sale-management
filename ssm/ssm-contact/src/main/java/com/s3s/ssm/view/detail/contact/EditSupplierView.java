/*
 * EditSupplierView
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

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractDetailView;

public class EditSupplierView extends AbstractDetailView<Supplier> {
    private static final long serialVersionUID = -8101155807024861715L;
    private static final String SEX_ID = "SEX_ID";

    public EditSupplierView(Supplier entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Supplier entity) {
        detailDataModel
                .tab("We are S3S", "Tab 1", new ImageIcon(EditSupplierView.class.getResource("/icons/user.png")));
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.startGroup("Group 1");
        detailDataModel.addAttribute("representer", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("sex", FieldTypeEnum.RADIO_BUTTON_GROUP).referenceDataId(SEX_ID);
        detailDataModel.addAttribute("position", FieldTypeEnum.TEXTBOX);
        detailDataModel.endGroup();
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTAREA);

        detailDataModel.tab("We make it work!", "Tab 2", null);
        detailDataModel.addAttribute("phone", FieldTypeEnum.TEXTBOX);
        detailDataModel.startGroup("Group 2");
        detailDataModel.addAttribute("fax", FieldTypeEnum.TEXTBOX).newColumn();
        detailDataModel.addAttribute("email", FieldTypeEnum.TEXTBOX);
        detailDataModel.endGroup();
        detailDataModel.addAttribute("isActive", FieldTypeEnum.CHECKBOX);
        detailDataModel.addAttribute("comment", FieldTypeEnum.TEXTAREA);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Supplier entity) {
        super.setReferenceDataModel(refDataModel, entity);
        Map<Boolean, String> sex2String = new HashMap<>(2);
        sex2String.put(true, ControlConfigUtils.getString("Male"));
        sex2String.put(false, ControlConfigUtils.getString("Female"));
        refDataModel.putRefDataList(SEX_ID, refDataModel.new ReferenceData<>(sex2String));
    }

}
