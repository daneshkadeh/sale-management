package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;

public class EditItemMultiView extends AbstractMultiEditView<Item> {

    @Override
    protected void constructTreeView(TreeNodeWithView root, Item entity, Map<String, Object> request) {
        // TODO Auto-generated method stub

    }

    @Override
    protected AbstractSingleEditView<Item> constructMainView(TreeNodeWithView root, Item entity,
            Map<String, Object> request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Item entity, Map<String, Object> request) {
        // TODO Auto-generated method stub

    }

}
