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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.s3s.ssm.entity.store.DetailMoveStore;
import com.s3s.ssm.entity.store.MoveStoreForm;
import com.s3s.ssm.entity.store.MoveStoreOrder;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class EditMoveStoreFormView extends AbstractSingleEditView<MoveStoreForm> {
    // TODO:Hoang remove bellow after ListDataModel support caching

    public EditMoveStoreFormView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void preSaveOrUpdate(MoveStoreForm entity) {
        super.preSaveOrUpdate(entity);
        int exportQtyTotal = 0;
        int importQtyTotal = 0;
        for (DetailMoveStore detail : entity.getDetailSet()) {
            if (detail.getItem() != null) {
                importQtyTotal += detail.getImportQty();
                exportQtyTotal += detail.getExportQty();
            } else {
                entity.getDetailSet().remove(detail);
            }
        }
        entity.setImportQtyTotal(importQtyTotal);
        entity.setExportQtyTotal(exportQtyTotal);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, MoveStoreForm entity,
            Map<String, Object> request) {
        MoveStoreOrder moveOrder = entity.getMoveStoreOrder();
        String fromStore = "";
        String destStore = "";
        if (moveOrder != null) {
            fromStore = moveOrder.getFromStore().getName();
            destStore = moveOrder.getDestStore().getName();

        }
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_MOVE_STORE_STATUS).newColumn();
        detailDataModel.addAttribute("moveStoreOrder", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_MOVE_STORE).mandatory(true);
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("transType", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_TRANS_TYPE)
                .newColumn();
        detailDataModel.addAttribute("staff", DetailFieldType.SEARCHER).componentInfo(
                ComponentFactory.createStorekeeperComponentInfo());
        detailDataModel.addAttribute("transporter", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("sentDate", DetailFieldType.DATE);
        detailDataModel.addRawAttribute("fromStore", DetailFieldType.LABEL).editable(false).newColumn()
                .value(fromStore);
        detailDataModel.addAttribute("receivedDate", DetailFieldType.DATE);
        detailDataModel.addRawAttribute("destStore", DetailFieldType.LABEL).editable(false).newColumn()
                .value(destStore);
        detailDataModel.addAttribute("detailSet", DetailFieldType.LIST).componentInfo(createMoveDetailsComponentInfo());
    }

    private IComponentInfo createMoveDetailsComponentInfo() {
        ListMoveDetailComponent component = new ListMoveDetailComponent(null, null, null);
        return new ListComponentInfo(component, "moveForm");
    }

    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent,
            final MoveStoreForm entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JComboBox<MoveStoreOrder> cbOrder = (JComboBox<MoveStoreOrder>) name2AttributeComponent.get(
                "moveStoreOrder").getComponent();
        final JLabel tfdFromStore = (JLabel) name2AttributeComponent.get("fromStore").getComponent();
        final JLabel tfdDestStore = (JLabel) name2AttributeComponent.get("destStore").getComponent();
        cbOrder.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                MoveStoreOrder order = (MoveStoreOrder) e.getItem();
                Store fromStore = order.getFromStore();
                Store destStore = order.getDestStore();
                tfdFromStore.setText(fromStore.getName());
                tfdDestStore.setText(destStore.getName());
            }
        });
    }

    @Override
    protected String getDefaultTitle(MoveStoreForm entity) {
        return ControlConfigUtils.getString("label.MoveStoreForm.detail.title") + UIConstants.BLANK + entity.getCode();
    }
}
