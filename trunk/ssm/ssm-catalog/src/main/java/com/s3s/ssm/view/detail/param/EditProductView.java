package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.view.AbstractMultiEditView;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.list.param.ListItemOfProductView;

public class EditProductView extends AbstractMultiEditView<Product> {

    public EditProductView(Product entity) {
        super(entity);
    }

    @Override
    protected void constructTreeView(TreeNodeWithView root, Product entity) {
        EditProductGeneralView detailView = new EditProductGeneralView(entity);
        detailView.setListView(listView);
        TreeNodeWithView node = new TreeNodeWithView("General", detailView);

        ListItemOfProductView itemsView = new ListItemOfProductView();
        // TODO: Id can be null for creation case. We should have listener to update items node.
        itemsView.setProductId(entity.getId());
        TreeNodeWithView nodeItems = new TreeNodeWithView("Items", itemsView);
        root.add(node);
        root.add(nodeItems);

    }

}
