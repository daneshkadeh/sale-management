package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPackageGeneralView extends AbstractSingleEditView<SPackage> {

    public EditPackageGeneralView(Map<String, Object> request) {
        super(request);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, SPackage entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
    }

}
