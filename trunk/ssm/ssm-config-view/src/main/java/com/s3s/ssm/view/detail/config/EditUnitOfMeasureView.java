/*
 * EditUnitOfMeasureView
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
package com.s3s.ssm.view.detail.config;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * 
 * @author Le Thanh Hoang
 * 
 */
public class EditUnitOfMeasureView extends AbstractSingleEditView<UnitOfMeasure> {
    private static final long serialVersionUID = 931208446206176131L;

    public EditUnitOfMeasureView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, UnitOfMeasure entity,
            Map<String, Object> request) {
        String baseUomName = "";
        if (entity.getUomCategory() != null) {
            baseUomName = serviceProvider.getService(IConfigService.class).getBaseUomName(entity.getUomCategory());
        }
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("uomCategory", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_UOM_CATE);
        detailDataModel.addAttribute("isBaseMeasure", DetailFieldType.CHECKBOX);
        detailDataModel.addAttribute("exchangeRate", DetailFieldType.TEXTBOX);
        detailDataModel.addRawAttribute("baseUom", DetailFieldType.TEXTBOX).value(baseUomName);
    }

    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, UnitOfMeasure entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JComboBox<UomCategory> cbUomCate = (JComboBox<UomCategory>) name2AttributeComponent.get("uomCategory")
                .getComponent();
        final JTextField tfdBaseUom = (JTextField) name2AttributeComponent.get("baseUom").getComponent();
        cbUomCate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                UomCategory cate = (UomCategory) cbUomCate.getSelectedItem();
                if (cate != null) {
                    String baseUomName = serviceProvider.getService(IConfigService.class).getBaseUomName(cate);
                    tfdBaseUom.setText(baseUomName);
                } else {
                    tfdBaseUom.setText("");
                }
            }
        });
    }

    @Override
    protected void saveOrUpdate(UnitOfMeasure entity) {
        // if the entity is basic, find the current entity is basic and make it not base measure
        if (entity.getIsBaseMeasure()) {
            List<UnitOfMeasure> uomList = getDaoHelper().getDao(UnitOfMeasure.class).findAll();
            uomList.remove(entity);
            for (UnitOfMeasure uom : uomList) {
                if (uom.getIsBaseMeasure() == true && uom.getUomCategory().equals(entity.getUomCategory())) {
                    uom.setIsBaseMeasure(false);
                    getDaoHelper().getDao(UnitOfMeasure.class).saveOrUpdate(uom);
                    break;
                }
            }
        }
        super.saveOrUpdate(entity);
    }

    @Override
    protected String getDefaultTitle(UnitOfMeasure entity) {
        return ControlConfigUtils.getString("label.UnitOfMeasure.detail.title") + UIConstants.BLANK + entity.getCode();
    }
}
