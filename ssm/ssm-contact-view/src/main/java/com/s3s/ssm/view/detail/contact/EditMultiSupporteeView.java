package com.s3s.ssm.view.detail.contact;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.contact.ListPartnerAddressView;

/**
 * View to edit Supportee
 * 
 * @author phamcongbang
 * 
 */
public class EditMultiSupporteeView extends AbstractMultiEditView<Partner> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EditMultiSupporteeView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected AbstractSingleEditView<Partner> constructMainView(TreeNodeWithView root, Partner entity,
            Map<String, Object> request) {
        EditSupporteeGeneralView detailView = new EditSupporteeGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView(
                ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Partner entity, Map<String, Object> request) {
        TreeNodeWithView nodeAddress = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SubMenu.Partner.Address"));
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListPartnerAddressView addressView = new ListPartnerAddressView(listRequest);
        nodeAddress.setView(addressView);
        root.add(nodeAddress);
    }
}
