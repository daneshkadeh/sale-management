package com.s3s.ssm.view.detail.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.config.Address;
import com.s3s.ssm.entity.contact.Individual;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerAddressLink;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * This view edit address in the link.
 * 
 * @author phamcongbang
 * 
 */
public class EditPartnerAddressView extends AbstractSingleEditView<PartnerAddressLink> {

    private static final String REF_PRESENTER = "REF_PRESENTER";
    /**
     * 
     */
    private static final long serialVersionUID = 8679708515328480406L;

    public EditPartnerAddressView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, PartnerAddressLink entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("address.name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address.address", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address.district", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address.city", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address.postalCode", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address.fixPhone", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address.fax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address.remark", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("presenter", DetailFieldType.DROPDOWN).referenceDataId(REF_PRESENTER);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, PartnerAddressLink entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Individual> individuals = new ArrayList<Individual>(((Partner) getParentObject()).getIndividuals());
        // individuals.add(0, null);
        refDataModel.putRefDataList(REF_PRESENTER, individuals);
    }

    @Override
    protected void bindingValue(PartnerAddressLink entity, String name, Object value, DetailAttribute detailAttribute) {
        super.bindingValue(entity, name, value, detailAttribute);
        entity.setPartner(daoHelper.getDao(Partner.class).findById((Long) request.get(PARAM_PARENT_ID)));
    }

    @Override
    protected PartnerAddressLink loadForCreate(Map<String, Object> request) {
        PartnerAddressLink entity = super.loadForCreate(request);
        entity.setAddress(new Address());
        return entity;
    }

}
