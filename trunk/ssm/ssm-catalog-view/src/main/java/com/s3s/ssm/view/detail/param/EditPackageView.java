package com.s3s.ssm.view.detail.param;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.param.ListPackageLineView;

public class EditPackageView extends AbstractMultiEditView<SPackage> {

    public EditPackageView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected AbstractSingleEditView<SPackage> constructMainView(TreeNodeWithView root, SPackage entity,
            Map<String, Object> request) {
        EditPackageGeneralView detailView = new EditPackageGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView("General", detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, SPackage entity, Map<String, Object> request) {
        TreeNodeWithView nodeItems = new TreeNodeWithView("Package lines");
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListPackageLineView linesView = new ListPackageLineView(listRequest);
        nodeItems.setView(linesView);
        root.add(nodeItems);
    }

}
