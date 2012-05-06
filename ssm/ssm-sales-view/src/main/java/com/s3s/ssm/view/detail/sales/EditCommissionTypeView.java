package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.CommissionType;
import com.s3s.ssm.entity.sales.CommissionType.CommissionMethod;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCommissionTypeView extends AbstractSingleEditView<CommissionType> {

    private static final String REF_COM_METHOD = "REF_COM_METHOD";

    public EditCommissionTypeView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, CommissionType entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("commissionMethod", DetailFieldType.DROPDOWN).referenceDataId(REF_COM_METHOD);
        detailDataModel.addAttribute("percent", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("commissionMoney", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("active", DetailFieldType.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, CommissionType entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_COM_METHOD, CommissionMethod.values());
    }

}
