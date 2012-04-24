/*
 * EditExchangeStoreFormView
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
package com.s3s.ssm.view.detail.store;

import java.util.Map;

import com.s3s.ssm.entity.store.InventoryStoreForm;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class EditInventoryStoreFormView extends AbstractSingleEditView<InventoryStoreForm> {
    private static final long serialVersionUID = -5213074051357171091L;

    public EditInventoryStoreFormView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, InventoryStoreForm entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("store", DetailFieldType.LABEL).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("staff", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_OPERATOR)
                .newColumn();
        detailDataModel.addAttribute("detailInventoryStores", DetailFieldType.LIST).componentInfo(
                createMoveDetailsComponentInfo());
    }

    private IComponentInfo createMoveDetailsComponentInfo() {
        ListInventoryDetailComponent component = new ListInventoryDetailComponent(null, null, null);
        return new ListComponentInfo(component, "inventoryForm");
    }

    @Override
    protected String getDefaultTitle(InventoryStoreForm entity) {
        return ControlConfigUtils.getString("label.MoveStoreForm.detail.title") + UIConstants.BLANK + entity.getCode();
    }
}
