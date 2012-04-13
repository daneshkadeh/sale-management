package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.DetailSalesContract;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditDetailSalesContractView extends AbstractSingleEditView<DetailSalesContract> {

    public EditDetailSalesContractView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, DetailSalesContract entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("product", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_PRODUCT);
        detailDataModel.addAttribute("stockCode", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("description", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("quantity", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("unitPrice", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("totalPrice", DetailFieldType.TEXTBOX);

    }

    @Override
    protected DetailSalesContract loadForCreate(Map<String, Object> request) {
        DetailSalesContract entity = super.loadForCreate(request);
        entity.setSalesContract((SalesContract) getParentObject());
        return entity;
    }

}
