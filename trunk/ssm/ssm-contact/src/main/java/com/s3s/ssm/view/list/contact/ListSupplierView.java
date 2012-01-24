package com.s3s.ssm.view.list.contact;

import java.util.List;

import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.contact.EditSupplierView;

public class ListSupplierView extends AbstractListView<Supplier> {
    private static final long serialVersionUID = -1414670444682843015L;

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        // listDataModel.add(new DetailAttribute("title", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("representer", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("sex", FieldTypeEnum.CHECKBOX));
        listDataModel.add(new DetailAttribute("position", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("phone", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("email", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<Supplier>> getDetailViewClass() {
        return EditSupplierView.class;
    }

}
