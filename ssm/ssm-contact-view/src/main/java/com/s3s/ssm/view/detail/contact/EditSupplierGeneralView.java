package com.s3s.ssm.view.detail.contact;

import java.util.Map;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.view.edit.DetailDataModel;

public class EditSupplierGeneralView extends EditPartnerGeneralView<Partner> {

    /**
     * 
     */
    private static final long serialVersionUID = -7970162778648235752L;

    public EditSupplierGeneralView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void addIndividualGroup(DetailDataModel detailDataModel) {
        // TODO Auto-generated method stub
        super.addIndividualGroup(detailDataModel);
    }

}
