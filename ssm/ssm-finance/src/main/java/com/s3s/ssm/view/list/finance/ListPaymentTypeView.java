package com.s3s.ssm.view.list.finance;

import java.util.List;

import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.finance.EditPaymentTypeView;

public class ListPaymentTypeView extends AbstractListView<PaymentType> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("id", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("contentType", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("isReceived", FieldTypeEnum.CHECKBOX));

    }

    @Override
    protected Class<? extends AbstractDetailView<PaymentType>> getDetailViewClass() {
        return EditPaymentTypeView.class;
    }

}
