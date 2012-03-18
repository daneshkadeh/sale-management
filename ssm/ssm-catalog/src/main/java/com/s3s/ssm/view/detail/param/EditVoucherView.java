package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.view.AbstractMultiEditView;
import com.s3s.ssm.view.AbstractSingleEditView;
import com.s3s.ssm.view.TreeNodeWithView;

public class EditVoucherView extends AbstractMultiEditView<Voucher> {

    public EditVoucherView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected AbstractSingleEditView<Voucher> constructMainView(TreeNodeWithView root, Voucher entity,
            Map<String, Object> request) {
        EditVoucherGeneralView detailView = new EditVoucherGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView("General", detailView);
        root.add(node);
        return (AbstractSingleEditView) detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Voucher entity, Map<String, Object> request) {
        // TODO Auto-generated method stub

    }

}
