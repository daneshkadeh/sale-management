package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.SalesConfirm;
import com.s3s.ssm.entity.sales.SalesConfirm.SalesConfirmStatus;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditSalesConfirmView extends AbstractSingleEditView<SalesConfirm> {

    private static final String REF_STATUS = "REF_STATUS";

    public EditSalesConfirmView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, SalesConfirm entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("supplier", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_SUPPLIER);
        detailDataModel.addAttribute("expectedQtySC", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_STATUS);
        detailDataModel.addAttribute("description", DetailFieldType.TEXTAREA);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, SalesConfirm entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_STATUS, SalesConfirmStatus.values());
    }

}
