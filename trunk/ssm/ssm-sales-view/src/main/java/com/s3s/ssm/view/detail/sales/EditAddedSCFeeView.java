package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.AddedSCFee;
import com.s3s.ssm.entity.sales.AddedSCFee.AddedSCFeeStatus;
import com.s3s.ssm.entity.sales.AddedSCFeeType;
import com.s3s.ssm.entity.sales.ImportationSC;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditAddedSCFeeView extends AbstractSingleEditView<AddedSCFee> {

    private static final String REF_SERVICE_PROVIDER = "REF_SERVICE_PROVIDER";
    private static final String REF_TYPE = "REF_TYPE";
    private static final String REF_STATUS = "REF_STATUS";

    public EditAddedSCFeeView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, AddedSCFee entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_TYPE);
        detailDataModel.addAttribute("referenceNumber", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("serviceProvider", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_SUPPLIER);
        detailDataModel.addAttribute("basePrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("unitPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("remark", DetailFieldType.TEXTAREA);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_STATUS);
    }

    @Override
    protected AddedSCFee loadForCreate(Map<String, Object> request) {
        AddedSCFee entity = super.loadForCreate(request);
        entity.setImportationSC((ImportationSC) getParentObject());
        return entity;
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, AddedSCFee entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_STATUS, AddedSCFeeStatus.values());
        refDataModel.putRefDataList(REF_TYPE, daoHelper.getDao(AddedSCFeeType.class).findAll());
    }
}
