package com.s3s.ssm.view.detail.finance;

import java.util.Arrays;

import com.s3s.ssm.entity.finance.PaymentContentType;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditPaymentTypeView extends AbstractDetailView<PaymentType> {

    private static final String REF_CONTENT_TYPE = "contentType";

    public EditPaymentTypeView(PaymentType entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, PaymentType entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("contentType", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CONTENT_TYPE);
        detailDataModel.addAttribute("isReceived", FieldTypeEnum.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, PaymentType entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_CONTENT_TYPE, Arrays.asList(PaymentContentType.values()), null);
    }

}
