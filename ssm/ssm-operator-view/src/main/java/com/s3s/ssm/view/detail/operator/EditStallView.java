/*
 * EditStallView
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

package com.s3s.ssm.view.detail.operator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.operator.SaleTarget;
import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditStallView extends AbstractSingleEditView<Stall> {
    /**
     * 
     */
    private static final long serialVersionUID = 3764427872042572099L;
    private static final String MANAGER_REF_ID = "1";
    private static final String STAFF_REF_ID = "2";
    private static final String DEFAULT_SALE_TARGET_REF_ID = "3";

    /**
     * @param entity
     */
    public EditStallView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, Stall entity, Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("manager", DetailFieldType.ENTITY_CHOOSER).referenceDataId(MANAGER_REF_ID);
        detailDataModel.addAttribute("isActive", DetailFieldType.CHECKBOX);
        detailDataModel.addAttribute("staffs", DetailFieldType.MULTI_SELECT_LIST_BOX).referenceDataId(STAFF_REF_ID);
        detailDataModel.addAttribute("salesTarget", DetailFieldType.SALE_TARGET).referenceDataId(
                DEFAULT_SALE_TARGET_REF_ID);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Stall entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Operator> userList = getDaoHelper().getDao(Operator.class).findAll();
        List<SaleTarget> defaultSaleTargetList = generateDefaulSaleTargetList(entity);
        refDataModel.putRefDataList(MANAGER_REF_ID, refDataModel.new ReferenceData(userList,
                new DefaultListCellRenderer()));
        refDataModel.putRefDataList(STAFF_REF_ID, refDataModel.new ReferenceData(userList,
                new DefaultListCellRenderer()));
        refDataModel.putRefDataList(DEFAULT_SALE_TARGET_REF_ID, refDataModel.new ReferenceData(defaultSaleTargetList,
                new DefaultListCellRenderer()));
    }

    private List<SaleTarget> generateDefaulSaleTargetList(Stall entity) {
        List<SaleTarget> defaultList = new ArrayList<SaleTarget>();
        Calendar now = Calendar.getInstance();
        SaleTarget newSaleTarget = new SaleTarget();
        newSaleTarget.setStall(entity);
        newSaleTarget.setYear(now.get(Calendar.YEAR));
        defaultList.add(newSaleTarget);
        return defaultList;
    }
}
