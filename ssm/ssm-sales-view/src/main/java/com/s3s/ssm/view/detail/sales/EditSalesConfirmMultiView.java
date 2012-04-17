package com.s3s.ssm.view.detail.sales;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.sales.SalesConfirm;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.sales.ListSalesContractOfConfirmView;

public class EditSalesConfirmMultiView extends AbstractMultiEditView<SalesConfirm> {

    public EditSalesConfirmMultiView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected AbstractSingleEditView<SalesConfirm> constructMainView(TreeNodeWithView root, SalesConfirm entity,
            Map<String, Object> request) {
        EditSalesConfirmView detailView = new EditSalesConfirmView(request);
        TreeNodeWithView node = new TreeNodeWithView(
                ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, SalesConfirm entity, Map<String, Object> request) {
        TreeNodeWithView nodeSalesContracts = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SubMenu.SalesConfirm.SalesContract"));
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListSalesContractOfConfirmView itemsView = new ListSalesContractOfConfirmView(listRequest);
        nodeSalesContracts.setView(itemsView);
        root.add(nodeSalesContracts);
    }
}
