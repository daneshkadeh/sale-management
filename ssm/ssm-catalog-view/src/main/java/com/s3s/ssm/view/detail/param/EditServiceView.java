package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.Service;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;

public class EditServiceView extends AbstractMultiEditView<Service> {
    private static final long serialVersionUID = 1L;

    public EditServiceView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected AbstractSingleEditView<Service> constructMainView(TreeNodeWithView root, Service entity,
            Map<String, Object> request) {
        EditServiceGeneralView detailView = new EditServiceGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView("General", detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Service entity, Map<String, Object> request) {
        // TODO Auto-generated method stub

    }

}
