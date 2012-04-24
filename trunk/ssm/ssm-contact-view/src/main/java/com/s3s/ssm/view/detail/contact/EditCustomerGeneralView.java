package com.s3s.ssm.view.detail.contact;

import java.util.Collection;
import java.util.Map;

import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.entity.contact.CustomerProfile;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfile;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.DaoHelperImpl;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCustomerGeneralView extends EditPartnerGeneralView<Partner> {

    /**
     * 
     */
    private static final long serialVersionUID = 8787989010305005928L;
    private static final String REF_LIST_AUDIENCECATE = "REF_LIST_AUDIENCECATE";

    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Partner entity, Map<String, Object> request) {
        super.initialPresentationView(detailDataModel, entity, request);
        detailDataModel.tab(ControlConfigUtils.getString("label.Partner.customerProfile.audienceCates"),
                "Nhom mua hang", null);
        detailDataModel.addRawAttribute("customerProfile.audienceCates", DetailFieldType.MULTI_SELECT_LIST_BOX)
                .referenceDataId(REF_LIST_AUDIENCECATE).value(getAudienceCategories(entity));
    }

    private Object getAudienceCategories(Partner entity) {
        CustomerProfile profile = DaoHelperImpl.downCast(CustomerProfile.class,
                entity.getPartnerProfile(PartnerProfileTypeEnum.CUSTOMER));
        return profile.getAudienceCates();
    }

    @Override
    protected void bindingValue(Partner entity, String name, Object value, DetailAttribute detailAttribute) {
        super.bindingValue(entity, name, value, detailAttribute);
        if ("customerProfile.audienceCates".equals(name)) {
            CustomerProfile profile = DaoHelperImpl.downCast(CustomerProfile.class,
                    entity.getPartnerProfile(PartnerProfileTypeEnum.CUSTOMER));
            addAudienceCates(profile, (Collection<AudienceCategory>) (value));
        }
    }

    private void addAudienceCates(CustomerProfile profile, Collection<AudienceCategory> collection) {
        profile.getAudienceCates().removeAll(profile.getAudienceCates());
        profile.getAudienceCates().addAll(collection);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Partner entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_LIST_AUDIENCECATE, getDaoHelper().getDao(AudienceCategory.class).findAll(),
                null);
    }

    @Override
    protected Partner loadForCreate(Map<String, Object> request) {
        // TODO Auto-generated method stub
        Partner entity = super.loadForCreate(request);
        PartnerProfile profile = entity.getPartnerProfile(PartnerProfileTypeEnum.CUSTOMER);
        if (profile == null) {
            profile = new CustomerProfile();
            entity.addPartnerProfile(profile);
        }
        return entity;
    }

    public EditCustomerGeneralView(Map<String, Object> request) {
        super(request);
    }

}
