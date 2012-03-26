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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.IMoneyChangedListener;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditSupplierView extends AbstractSingleEditView<Supplier> {
    /**
     * 
     */
    private static final String TEST_ID = "idTest";
    /**
     * 
     */
    private static final String MONEY_ID = "MONEY_ID";
    private static final long serialVersionUID = -8101155807024861715L;
    private static final String SEX_ID = "SEX_ID";

    public EditSupplierView(Map<String, Object> request) {
        super(request);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Supplier entity) {
        detailDataModel.tab("We are S3S", "This is a tooltip", ImageUtils.getIcon(ImageConstants.USER_ICON));
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).width(5).newColumn();
        detailDataModel.startGroup("Group 1");
        detailDataModel.addAttribute("representer", DetailFieldType.TEXTBOX).width(40);
        detailDataModel.addAttribute("sex", DetailFieldType.RADIO_BUTTON_GROUP).referenceDataId(SEX_ID);
        detailDataModel.addAttribute("position", DetailFieldType.TEXTBOX);
        detailDataModel.endGroup();
        detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA);

        detailDataModel.addRawAttribute("rawAttribute1", DetailFieldType.TEXTBOX).value("Init value");
        detailDataModel.addRawAttribute("rawAttribute2", DetailFieldType.DROPDOWN).value("value 2")
                .referenceDataId(TEST_ID);
        detailDataModel.addRawAttribute("rawAttribute3", DetailFieldType.MULTI_SELECT_TREE_BOX);
        detailDataModel.addRawAttribute("rawAttribute4", DetailFieldType.MONEY).value(Money.zero("VND"))
                .referenceDataId(MONEY_ID);

        detailDataModel.tab("We make it work!", "Tab 2", null);
        detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX);
        detailDataModel.startGroup("Group 2");
        detailDataModel.addAttribute("fax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX);
        detailDataModel.endGroup();
        detailDataModel.addAttribute("isActive", DetailFieldType.CHECKBOX);
        detailDataModel.addAttribute("comment", DetailFieldType.TEXTAREA);
        // TODO: todo test code
        // System.out.println("call store service from contact module: "
        // + serviceProvider.getService(StoreService.class).testService());
        System.out.println("call cache service of config service: "
                + cacheDataService.getReferenceDataList(CacheId.REF_LIST_CURRENCY));
    }

    @Override
    protected void bindingValue(Supplier entity, String name, boolean isRaw, Object value) {
        super.bindingValue(entity, name, isRaw, value);
        if (name.equals("rawAttribute1")) {
            entity.setPosition((String) value);
        }
    }

    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, Supplier entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JTextField email = (JTextField) name2AttributeComponent.get("email").getComponent();
        final JTextField position = (JTextField) name2AttributeComponent.get("position").getComponent();
        JCheckBox cb = (JCheckBox) name2AttributeComponent.get("isActive").getComponent();
        cb.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                email.setVisible(e.getStateChange() == ItemEvent.DESELECTED);
            }
        });

        MoneyComponent mc = (MoneyComponent) name2AttributeComponent.get("rawAttribute4").getComponent();
        mc.addMoneyChangeListener(new IMoneyChangedListener() {

            @Override
            public void doMoneyChanged(ChangeEvent e) {
                MoneyComponent m = (MoneyComponent) e.getSource();
                position.setText(m.getMoney().toString());
            }
        });

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Supplier entity) {
        super.setReferenceDataModel(refDataModel, entity);
        Map<Boolean, String> sex2String = new HashMap<>(2);
        sex2String.put(true, ControlConfigUtils.getString("Male"));
        sex2String.put(false, ControlConfigUtils.getString("Female"));
        refDataModel.putRefDataList(SEX_ID, refDataModel.new ReferenceData<>(sex2String));
        refDataModel.putRefDataList(TEST_ID, new String[] { "value 1", "value 2", "value 3" });
        refDataModel.putRefDataList(MONEY_ID, new String[] { "USD", "VND", "HPP" });
    }

}
