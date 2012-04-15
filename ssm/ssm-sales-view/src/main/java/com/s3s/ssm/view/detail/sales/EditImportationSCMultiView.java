package com.s3s.ssm.view.detail.sales;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.sales.ImportationSC;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.sales.ListAddedSCFeeView;

public class EditImportationSCMultiView extends AbstractMultiEditView<ImportationSC> {
    public EditImportationSCMultiView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected AbstractSingleEditView<ImportationSC> constructMainView(TreeNodeWithView root, ImportationSC entity,
            Map<String, Object> request) {
        EditImportationSCView detailView = new EditImportationSCView(request);
        TreeNodeWithView node = new TreeNodeWithView(
                ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, ImportationSC entity, Map<String, Object> request) {
        TreeNodeWithView nodeAddedFees = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SubMenu.ImportationSC.addedFee"));
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListAddedSCFeeView itemsView = new ListAddedSCFeeView(listRequest);
        nodeAddedFees.setView(itemsView);
        root.add(nodeAddedFees);
    }

}
