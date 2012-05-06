package com.s3s.ssm.view.detail.contact;

import java.util.Map;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfile;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;
import com.s3s.ssm.entity.contact.SupporteeProfile;

public class EditSupporteeGeneralView extends EditPartnerGeneralView<Partner> {
    public EditSupporteeGeneralView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected Partner loadForCreate(Map<String, Object> request) {
        Partner entity = super.loadForCreate(request);
        PartnerProfile profile = entity.getPartnerProfile(PartnerProfileTypeEnum.SUPPORTEE);
        if (profile == null) {
            profile = new SupporteeProfile();
            entity.addPartnerProfile(profile);
        }
        return entity;
    }
}
