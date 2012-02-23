package com.s3s.ssm.view.detail.param;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.view.AbstractMultiEditView;
import com.s3s.ssm.view.AbstractSingleEditView;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.list.param.ListItemOfProductView;

public class EditProductView extends AbstractMultiEditView<Product> {
    private static final long serialVersionUID = -2705489522838291744L;

    public EditProductView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected AbstractSingleEditView<Product> constructMainView(TreeNodeWithView root, Product entity,
            Map<String, Object> request) {
        EditProductGeneralView detailView = new EditProductGeneralView(request);
        detailView.setVisibleToolbar(false);
        TreeNodeWithView node = new TreeNodeWithView("General", detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Product entity, Map<String, Object> request) {
        TreeNodeWithView nodeItems = new TreeNodeWithView("Items");
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListItemOfProductView itemsView = new ListItemOfProductView(listRequest);
        nodeItems.setView(itemsView);
        root.add(nodeItems);
    }
}
