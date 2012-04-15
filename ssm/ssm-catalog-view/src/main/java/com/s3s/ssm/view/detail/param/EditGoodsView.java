package com.s3s.ssm.view.detail.param;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.catalog.Goods;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.param.ListItemOfProductView;

public class EditGoodsView extends AbstractMultiEditView<Goods> {
    private static final long serialVersionUID = -2705489522838291744L;

    public EditGoodsView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected AbstractSingleEditView<Goods> constructMainView(TreeNodeWithView root, Goods entity,
            Map<String, Object> request) {
        EditGoodsGeneralView detailView = new EditGoodsGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView(
                ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Goods entity, Map<String, Object> request) {
        TreeNodeWithView nodeItems = new TreeNodeWithView("Items");
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListItemOfProductView itemsView = new ListItemOfProductView(listRequest);
        nodeItems.setView(itemsView);
        root.add(nodeItems);
    }

    protected String getDefaultTitle(Goods entity) {
        if (entity.isPersisted()) {
            return (entity.getCode());
        }
        return getDefaultTitle(entity);
    }
}
