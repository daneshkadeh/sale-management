package com.s3s.ssm.view.list.contact;

import java.util.List;

import com.s3s.ssm.entity.contact.ContactType;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.contact.EditContactTypeView;

public class ListContactTypeView extends AbstractListView<ContactType> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("description", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("contactFamilyType", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<ContactType>> getDetailViewClass() {
        return EditContactTypeView.class;
    }

}
