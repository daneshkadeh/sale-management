/*
 * EditClosingEntryView
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

import com.s3s.ssm.entity.store.ClosingStoreEntry;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditClosingStoreEntryView extends AbstractSingleEditView<ClosingStoreEntry> {
    private static final long serialVersionUID = -2248781528000413406L;

    public EditClosingStoreEntryView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ClosingStoreEntry entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("store", DetailFieldType.DROPDOWN).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_STORE);
        detailDataModel.addAttribute("closingDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("closingStoreSet", DetailFieldType.LIST).componentInfo(
                createClosingStoreComponentInfo());
    }

    private IComponentInfo createClosingStoreComponentInfo() {
        ListClosingStoreDetailComponent component = new ListClosingStoreDetailComponent(null, null, null);
        return new ListComponentInfo(component, "closingEntry");
    }
}
