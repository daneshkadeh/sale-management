package com.s3s.ssm.view.list.contact;

import java.util.List;

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.contact.EditBankView;

public class ListBankView extends AbstractListView<Bank> {
    private static final long serialVersionUID = 1898147147716601668L;

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));

    }

    @Override
    protected Class<? extends AbstractDetailView<Bank>> getDetailViewClass() {
        return EditBankView.class;
    }

}
