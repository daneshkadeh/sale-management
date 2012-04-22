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

import com.s3s.ssm.entity.store.MoveStoreForm;
import com.s3s.ssm.entity.store.MoveStoreOrder;
import com.s3s.ssm.entity.store.MoveStoreStatus;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class EditMoveStoreFormView extends AbstractSingleEditView<MoveStoreForm> {
    // TODO:Hoang remove bellow after ListDataModel support caching
    private static String REF_LIST_MOVE_ORDER = "1";

    public EditMoveStoreFormView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, MoveStoreForm entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_MOVE_STORE_STATUS).newColumn();
        detailDataModel.addAttribute("moveStoreOrder", DetailFieldType.DROPDOWN).referenceDataId(REF_LIST_MOVE_ORDER);
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("transType", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_TRANS_TYPE)
                .newColumn();
        detailDataModel.addAttribute("staff", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_OPERATOR);
        detailDataModel.addAttribute("transporter", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("sentDate", DetailFieldType.DATE);
        detailDataModel.addRawAttribute("fromStore", DetailFieldType.LABEL).editable(false).newColumn();
        detailDataModel.addAttribute("receivedDate", DetailFieldType.DATE);
        detailDataModel.addRawAttribute("destStore", DetailFieldType.LABEL).editable(false).newColumn();
        detailDataModel.addAttribute("detailSet", DetailFieldType.LIST).componentInfo(createMoveDetailsComponentInfo());
    }

    private IComponentInfo createMoveDetailsComponentInfo() {
        ListMoveDetailComponent component = new ListMoveDetailComponent(null, null, null);
        return new ListComponentInfo(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, MoveStoreForm entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_LIST_MOVE_ORDER, serviceProvider.getService(IStoreService.class)
                .findMoveStoreOrderByStatus(MoveStoreStatus.NEW));
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
