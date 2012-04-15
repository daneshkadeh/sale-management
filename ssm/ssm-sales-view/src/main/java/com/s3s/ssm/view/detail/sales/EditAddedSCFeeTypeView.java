package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.AddedSCFeeType;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditAddedSCFeeTypeView extends AbstractSingleEditView<AddedSCFeeType> {

    public EditAddedSCFeeTypeView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, AddedSCFeeType entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
    }

    @Override
    protected String getDefaultTitle(AddedSCFeeType entity) {
        return entity.getCode();
    }
}
