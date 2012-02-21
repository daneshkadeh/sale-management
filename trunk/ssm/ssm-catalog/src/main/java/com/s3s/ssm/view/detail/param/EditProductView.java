package com.s3s.ssm.view.detail.param;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.view.AbstractMultiEditView;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.list.param.ListItemOfProductView;

public class EditProductView extends AbstractMultiEditView<Product> {

    public EditProductView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void constructTreeView(TreeNodeWithView root, Product entity) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(PARAM_ENTITY_ID, entity.getId());
        params.put(PARAM_ACTION, this.request.get(PARAM_ACTION));
        params.put(PARAM_LIST_VIEW, getListView());
        EditProductGeneralView detailView = new EditProductGeneralView(params);
        TreeNodeWithView node = new TreeNodeWithView("General", detailView);

        ListItemOfProductView itemsView = new ListItemOfProductView();
        // TODO: Id can be null for creation case. We should have listener to update items node.
        itemsView.setProductId(entity.getId());
        TreeNodeWithView nodeItems = new TreeNodeWithView("Items", itemsView);
        root.add(node);
        root.add(nodeItems);

    }

}
