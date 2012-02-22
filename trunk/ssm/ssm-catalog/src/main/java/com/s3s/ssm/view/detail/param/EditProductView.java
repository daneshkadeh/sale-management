package com.s3s.ssm.view.detail.param;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.view.AbstractMultiEditView;
import com.s3s.ssm.view.ISavedListener;
import com.s3s.ssm.view.SavedEvent;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.list.param.ListItemOfProductView;

public class EditProductView extends AbstractMultiEditView<Product> {
    private static final long serialVersionUID = -2705489522838291744L;

    public EditProductView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void constructTreeView(final TreeNodeWithView root, final Product product, Map<String, Object> request) {
        EditProductGeneralView detailView = new EditProductGeneralView(request);
        detailView.setVisibleToolbar(false);
        TreeNodeWithView node = new TreeNodeWithView("General", detailView);
        root.add(node);
        final TreeNodeWithView nodeItems = new TreeNodeWithView("Items");

        if (product.isPersisted()) {
            createItemViewNode(root, nodeItems, product);
        }

        detailView.addSavedListener(new ISavedListener<AbstractIdOLObject>() {
            @Override
            public void doSaved(SavedEvent<AbstractIdOLObject> e) {
                if (nodeItems.getView() == null) {
                    createItemViewNode(root, nodeItems, (Product) e.getEntity());
                    ((DefaultTreeModel) getTreeView().getModel()).nodeStructureChanged(root);
                }
            }
        });

    }

    private void createItemViewNode(TreeNodeWithView root, TreeNodeWithView nodeItems, Product entity) {
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListItemOfProductView itemsView = new ListItemOfProductView(listRequest);
        nodeItems.setView(itemsView);
        root.add(nodeItems);
    }
}
