package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;

public class EditVoucherView extends AbstractMultiEditView<Voucher> {
    private static final long serialVersionUID = 1L;

    public EditVoucherView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected AbstractSingleEditView<Voucher> constructMainView(TreeNodeWithView root, Voucher entity,
            Map<String, Object> request) {
        EditVoucherGeneralView detailView = new EditVoucherGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView("General", detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Voucher entity, Map<String, Object> request) {
        // TODO Auto-generated method stub

    }

    protected String getDefaultTitle(Voucher entity) {
        if (entity.isPersisted()) {
            return (entity.getCode());
        }
        return getDefaultTitle(entity);
    }

}
