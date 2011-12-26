package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditUomCategoryView extends AbstractDetailView<UomCategory> {
    private static final long serialVersionUID = 1L;

    public EditUomCategoryView(UomCategory entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, UomCategory entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);

    }

}
