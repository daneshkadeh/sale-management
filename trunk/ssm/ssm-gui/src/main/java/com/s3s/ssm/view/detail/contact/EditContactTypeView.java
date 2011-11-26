package com.s3s.ssm.view.detail.contact;

import java.util.Arrays;

import com.s3s.ssm.entity.contact.ContactFamilyType;
import com.s3s.ssm.entity.contact.ContactType;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditContactTypeView extends AbstractDetailView<ContactType> {

    private static final String REF_CONTACT_FAMILY = "REF_CONTACT_FAMILY";

    public EditContactTypeView(ContactType entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ContactType entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("description", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("contactFamilyType", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CONTACT_FAMILY);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ContactType entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_CONTACT_FAMILY, Arrays.asList(ContactFamilyType.values()), null);
    }

}
