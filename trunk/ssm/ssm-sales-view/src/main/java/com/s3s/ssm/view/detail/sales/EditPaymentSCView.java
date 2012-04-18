package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.PaymentSC;
import com.s3s.ssm.entity.sales.PaymentSC.PaymentSCType;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPaymentSCView extends AbstractSingleEditView<PaymentSC> {
    private static final String REF_TYPE = "REF_TYPE";

    public EditPaymentSCView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, PaymentSC entity,
            Map<String, Object> request) {
        detailDataModel.addRawAttribute("salesContract", DetailFieldType.LABEL).value(entity.getSalesContract());
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_TYPE);
        detailDataModel.addAttribute("amount", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("bank", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_BANK);
        detailDataModel.addAttribute("referenceCode", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("remark", DetailFieldType.TEXTAREA);
    }

    @Override
    protected PaymentSC loadForCreate(Map<String, Object> request) {
        PaymentSC entity = super.loadForCreate(request);
        entity.setSalesContract((SalesContract) getParentObject());
        return entity;
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, PaymentSC entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_TYPE, PaymentSCType.values());
    }

}
