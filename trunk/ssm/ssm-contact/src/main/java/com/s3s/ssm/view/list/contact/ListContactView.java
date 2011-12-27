package com.s3s.ssm.view.list.contact;

import java.util.List;

import com.s3s.ssm.entity.contact.Contact;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.contact.EditContactView;

public class ListContactView extends AbstractListView<Contact> {
    private static final long serialVersionUID = -5575359338516534903L;

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("fullName", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("contactType", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("address", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("phone", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("fixPhone", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("fax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("email", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("taxCode", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("bankAccount", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("maximumDayDebt", FieldTypeEnum.TEXTBOX));

    }

    @Override
    protected Class<? extends AbstractDetailView<Contact>> getDetailViewClass() {
        return EditContactView.class;
    }

}
