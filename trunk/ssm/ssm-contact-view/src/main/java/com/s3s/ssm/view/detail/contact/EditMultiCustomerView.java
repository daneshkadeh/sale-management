package com.s3s.ssm.view.detail.contact;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.contact.ListPartnerAddressView;

public class EditMultiCustomerView extends AbstractMultiEditView<Partner> {

    /**
     * 
     */
    private static final long serialVersionUID = 317033526623048476L;

    public EditMultiCustomerView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected AbstractSingleEditView<Partner> constructMainView(TreeNodeWithView root, Partner entity,
            Map<String, Object> request) {
        EditCustomerGeneralView detailView = new EditCustomerGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView("Customer", detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Partner entity, Map<String, Object> request) {
        TreeNodeWithView nodeAddress = new TreeNodeWithView("Address");
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListPartnerAddressView addressView = new ListPartnerAddressView(listRequest);
        nodeAddress.setView(addressView);
        root.add(nodeAddress);
    }

}
