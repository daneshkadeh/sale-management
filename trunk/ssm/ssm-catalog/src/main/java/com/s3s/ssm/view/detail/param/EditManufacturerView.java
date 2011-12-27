package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditManufacturerView extends AbstractDetailView<Manufacturer> {
    private static final long serialVersionUID = 1L;

    public EditManufacturerView(Manufacturer entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Manufacturer entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);

    }

}
