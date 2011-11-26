package com.s3s.ssm.view.detail.contact;

import com.s3s.ssm.entity.contact.Bank;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditBankView extends AbstractDetailView<Bank> {
    private static final long serialVersionUID = 728867266827208141L;

    public EditBankView(Bank entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Bank entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
    }

}
