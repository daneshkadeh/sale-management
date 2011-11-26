package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.param.Supplier;
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
        // detailDataModel.addAttribute("mainContact", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("phoneNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fixPhoneNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("faxNumber", FieldTypeEnum.TEXTBOX);
        // detailDataModel.addAttribute("bankAccount.bank", FieldTypeEnum.DROPDOWN);
        // detailDataModel.addAttribute("bankAccount.accountNumber", FieldTypeEnum.TEXTBOX);
        // detailDataModel.addAttribute("bankAccount.accountName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("note", FieldTypeEnum.TEXTBOX);
    }

}
