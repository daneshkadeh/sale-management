/*
 * EditMoveOrderView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
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

import com.s3s.ssm.entity.store.MoveStoreOrder;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditMoveStoreOrderView extends AbstractSingleEditView<MoveStoreOrder> {
    private static final long serialVersionUID = 5470253225328887813L;

    public EditMoveStoreOrderView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, MoveStoreOrder entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_MOVE_STORE_STATUS).mandatory(true).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("fromStore", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_STORE)
                .mandatory(true);
        detailDataModel.addAttribute("destStore", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_STORE)
                .newColumn();
        detailDataModel.addAttribute("fromDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("destDate", DetailFieldType.DATE).newColumn();
    }

    @Override
    protected String getDefaultTitle(MoveStoreOrder entity) {
        return ControlConfigUtils.getString("label.MoveStoreOrder.detail.title") + UIConstants.BLANK + entity.getCode();
    }
}