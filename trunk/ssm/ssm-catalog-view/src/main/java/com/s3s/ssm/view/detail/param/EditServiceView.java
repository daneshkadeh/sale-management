package com.s3s.ssm.view.detail.param;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.catalog.Service;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.param.ListItemOfProductView;

public class EditServiceView extends AbstractMultiEditView<Service> {
    private static final long serialVersionUID = 1L;

    public EditServiceView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected AbstractSingleEditView<Service> constructMainView(TreeNodeWithView root, Service entity,
            Map<String, Object> request) {
        EditServiceGeneralView detailView = new EditServiceGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView(
                ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Service entity, Map<String, Object> request) {
        TreeNodeWithView nodeItems = new TreeNodeWithView(ControlConfigUtils.getString("JTree.SubMenu.Product.items"));
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListItemOfProductView itemsView = new ListItemOfProductView(listRequest);
        nodeItems.setView(itemsView);
        root.add(nodeItems);
    }

    protected String getDefaultTitle(Service entity) {
        if (entity.isPersisted()) {
            return (entity.getCode());
        }
        return getDefaultTitle(entity);
    }

}
