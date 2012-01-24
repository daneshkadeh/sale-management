package com.s3s.ssm.view.detail.contact;

import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditSupplierView extends AbstractDetailView<Supplier> {

    public EditSupplierView(Supplier entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Supplier entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        // detailDataModel.addAttribute("title", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("representer", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("sex", FieldTypeEnum.SEX_RADIO);
        detailDataModel.addAttribute("position", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("address", FieldTypeEnum.RICH_TEXTBOX);
        detailDataModel.addAttribute("phone", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("email", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isActive", FieldTypeEnum.CHECKBOX);
        detailDataModel.addAttribute("comment", FieldTypeEnum.RICH_TEXTBOX);
    }

}
