package com.s3s.ssm.view.detail.contact;

import java.util.Map;

import com.s3s.ssm.entity.contact.Individual;
import com.s3s.ssm.entity.contact.IndividualRoleEnum;
import com.s3s.ssm.entity.contact.IndividualTitleEnum;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditIndividualView extends AbstractSingleEditView<Individual> {

    private static final String REF_ROLE = "REF_ROLE";
    private static final String REF_TITLE = "REF_TITLE";

    public EditIndividualView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, Individual entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("title", DetailFieldType.DROPDOWN).referenceDataId(REF_TITLE);
        detailDataModel.addAttribute("lastName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("firstName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("position", DetailFieldType.TEXTBOX);
        // detailDataModel.addAttribute("role", DetailFieldType.DROPDOWN).referenceDataId(REF_ROLE);

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Individual entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_TITLE, IndividualTitleEnum.values());
        refDataModel.putRefDataList(REF_ROLE, IndividualRoleEnum.values());
    }

    @Override
    protected Individual loadForCreate(Map<String, Object> request) {
        Individual individual = super.loadForCreate(request);
        individual.setPartner((Partner) getParentObject());
        individual.setRole(IndividualRoleEnum.MEMBER);
        return individual;
    }

    @Override
    protected void bindingValue(Individual entity, String name, Object value, DetailAttribute attribute) {
        super.bindingValue(entity, name, value, attribute);
        if ("lastName".equals(name) || "firstName".equals(name)) {
            entity.setFullName(entity.getLastName() + " " + entity.getFirstName());
        }
    }

}
