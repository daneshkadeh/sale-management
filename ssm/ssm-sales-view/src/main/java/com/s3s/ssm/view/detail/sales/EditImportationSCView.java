package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.ImportationSC;
import com.s3s.ssm.entity.sales.ImportationSC.ImportationSCStatus;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditImportationSCView extends AbstractSingleEditView<ImportationSC> {

    private static final String REF_STATUS = "REF_STATUS";

    public EditImportationSCView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ImportationSC entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("shipmentDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_STATUS);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ImportationSC entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_STATUS, ImportationSCStatus.values());
    }

    @Override
    protected ImportationSC loadForCreate(Map<String, Object> request) {
        ImportationSC entity = super.loadForCreate(request);
        entity.setSalesContract((SalesContract) getParentObject());
        return entity;
    }
}
