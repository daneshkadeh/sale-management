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

import com.s3s.ssm.entity.store.DetailInventoryStore;
import com.s3s.ssm.entity.store.InventoryStoreForm;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.list.store.ListInventoryStoreFormView;
import com.s3s.ssm.view.util.StoreViewHelper;

public class EditInventoryStoreFormView extends AbstractSingleEditView<InventoryStoreForm> {
    private static final long serialVersionUID = -5213074051357171091L;

    public EditInventoryStoreFormView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected InventoryStoreForm loadForCreate(Map<String, Object> request) {
        InventoryStoreForm form = super.loadForCreate(request);
        Store store = (Store) request.get(ListInventoryStoreFormView.STORE_ENTITY);
        StoreViewHelper.initInventoryStoreForm(form, store);
        return form;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void preSaveOrUpdate(InventoryStoreForm entity) {
        super.preSaveOrUpdate(entity);
        int curQtyTotal = 0;
        int realQtyTotal = 0;
        Money curAmtTotal = Money.create(CurrencyEnum.VND, 0L);
        Money realAmtTotal = Money.create(CurrencyEnum.VND, 0L);
        for (DetailInventoryStore detail : entity.getDetailInventoryStores()) {
            curQtyTotal += detail.getCurQty();
            realQtyTotal += detail.getRealQty();
            curAmtTotal = curAmtTotal.plus(detail.getCurPriceSubtotal());
            realAmtTotal = realAmtTotal.plus(detail.getRealPriceSubtotal());
        }
        entity.setCurQtyTotal(curQtyTotal);
        entity.setRealQtyTotal(realQtyTotal);
        entity.setCurAmtTotal(curAmtTotal);
        entity.setRealAmtTotal(realAmtTotal);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, InventoryStoreForm entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("store", DetailFieldType.DROPDOWN).enable(false).newColumn()
                .cacheDataId(CacheId.REF_LIST_STORE);
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("staff", DetailFieldType.SEARCHER)
                .componentInfo(ComponentFactory.createStorekeeperComponentInfo()).newColumn();
        detailDataModel.addAttribute("detailInventoryStores", DetailFieldType.LIST).componentInfo(
                createMoveDetailsComponentInfo());
    }

    private IComponentInfo createMoveDetailsComponentInfo() {
        ListInventoryDetailComponent component = new ListInventoryDetailComponent(null, null, null);
        return new ListComponentInfo(component, "inventoryForm");
    }

}
