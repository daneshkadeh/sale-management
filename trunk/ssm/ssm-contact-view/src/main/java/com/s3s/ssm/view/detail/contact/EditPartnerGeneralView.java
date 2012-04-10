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

import com.s3s.ssm.entity.contact.IndividualTitleEnum;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.IMoneyChangedListener;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPartnerGeneralView<T extends Partner> extends AbstractSingleEditView<Partner> {
    /**
     * 
     */
    private static final String TEST_ID = "idTest";
    /**
     * 
     */
    private static final String MONEY_ID = "MONEY_ID";
    private static final long serialVersionUID = -8101155807024861715L;
    private static final String PARTNER_TITLE = "PARTNER_TITLE";

    public EditPartnerGeneralView(Map<String, Object> request) {
        super(request);
        setEnabled(false);
    }

    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Partner entity, Map<String, Object> request) {
        detailDataModel.tab("General", "Common information of supplier", ImageUtils.getIcon(ImageConstants.USER_ICON));
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).width(50).newColumn();
        addIndividualGroup(detailDataModel);

        detailDataModel.startGroup(ControlConfigUtils
                .getString("label.EditPartnerGeneralView.Partner.CommunicateToPartner"));
        detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("fax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("website", DetailFieldType.TEXTBOX);
        detailDataModel.endGroup();

        detailDataModel.addAttribute("isActive", DetailFieldType.CHECKBOX);
        detailDataModel.addAttribute("comment", DetailFieldType.TEXTAREA);
        // detailDataModel.addRawAttribute("rawAttribute1", DetailFieldType.TEXTBOX).value("Init value");
        // detailDataModel.addRawAttribute("rawAttribute2", DetailFieldType.DROPDOWN).value("value 2")
        // .referenceDataId(TEST_ID);
        // detailDataModel.addRawAttribute("rawAttribute3", DetailFieldType.MULTI_SELECT_TREE_BOX);
        detailDataModel.addRawAttribute("rawAttribute4", DetailFieldType.MONEY).value(null).referenceDataId(MONEY_ID);
        // detailDataModel.addRawAttribute("rawLabel", DetailFieldType.LABEL).value("Hello label");

        detailDataModel.tab(ControlConfigUtils.getString("label.EditPartnerGeneralView.Partner.address"),
                "Address of Partner", null);
        detailDataModel.addAttribute("mainAddressLink.address.name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainAddressLink.address.address", DetailFieldType.TEXTAREA);
        detailDataModel.addAttribute("mainAddressLink.address.district", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainAddressLink.address.city", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainAddressLink.address.postalCode", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainAddressLink.address.fax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainAddressLink.address.fixPhone", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainAddressLink.address.remark", DetailFieldType.TEXTAREA);

        // TODO: todo test code
        // System.out.println("call store service from contact module: "
        // + serviceProvider.getService(StoreService.class).testService());
        // System.out.println("call cache service of config service: "
        // + cacheDataService.getReferenceDataList(CacheId.REF_LIST_CURRENCY));
    }

    protected void addIndividualGroup(DetailDataModel detailDataModel) {
        detailDataModel.startGroup(ControlConfigUtils.getString("label.EditPartnerGeneralView.Partner.representer"));
        detailDataModel.addAttribute("mainIndividual.title", DetailFieldType.DROPDOWN).referenceDataId(PARTNER_TITLE);
        detailDataModel.addAttribute("mainIndividual.firstName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainIndividual.lastName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainIndividual.position", DetailFieldType.TEXTBOX);
        detailDataModel.endGroup();
    }

    @Override
    protected void saveOrUpdate(Partner entity) {
        // Vietnamese: Ho + Ten, USA: Ten + Ho. Application support vietnamese now
        entity.getMainIndividual().setFullName(
                entity.getMainIndividual().getLastName() + " " + entity.getMainIndividual().getFirstName());
        super.saveOrUpdate(entity);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Partner entity) {
        // TODO Auto-generated method stub
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(PARTNER_TITLE, IndividualTitleEnum.values());
        refDataModel.putRefDataList(MONEY_ID, new String[] { "USD", "VND", "HPP" });
    }

    // Just try to keep demo code from Phuc
    protected void bindingValue(Partner entity, String name, boolean isRaw, Object value, boolean test) {
        super.bindingValue(entity, name, isRaw, value);
        if (name.equals("rawAttribute1")) {
            // entity.setPosition((String) value);
        }
    }

    // Just try to keep demo code from Phuc
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, Partner entity,
            boolean test) {
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

    // Just try to keep demo test from Phuc for reference
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Partner entity, boolean test) {
        super.setReferenceDataModel(refDataModel, entity);
        Map<Boolean, String> sex2String = new HashMap<>(2);
        sex2String.put(true, ControlConfigUtils.getString("Male"));
        sex2String.put(false, ControlConfigUtils.getString("Female"));
        refDataModel.putRefDataList(PARTNER_TITLE, refDataModel.new ReferenceData<>(sex2String));
        refDataModel.putRefDataList(TEST_ID, new String[] { "value 1", "value 2", "value 3" });
        refDataModel.putRefDataList(MONEY_ID, new String[] { "USD", "VND", "HPP" });
    }

}
