package com.s3s.ssm.view.detail.contact;

import java.util.Map;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfile;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;
import com.s3s.ssm.entity.contact.SupplierProfile;
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
    protected Partner loadForCreate(Map<String, Object> request) {
        Partner entity = super.loadForCreate(request);
        PartnerProfile profile = entity.getPartnerProfile(PartnerProfileTypeEnum.SUPPLIER);
        if (profile == null) {
            profile = new SupplierProfile();
            entity.addPartnerProfile(profile);
        }
        return entity;
    }

    @Override
    protected void addIndividualGroup(DetailDataModel detailDataModel) {
        // TODO Auto-generated method stub
        super.addIndividualGroup(detailDataModel);
    }

}
