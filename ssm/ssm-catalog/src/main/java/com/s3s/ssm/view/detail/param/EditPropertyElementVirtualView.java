package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditPropertyElementVirtualView extends AbstractDetailView<ProductPropertyElement> {

    public EditPropertyElementVirtualView(ProductPropertyElement entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ProductPropertyElement entity) {
        detailDataModel.addAttribute("value", FieldTypeEnum.TEXTBOX).mandatory(true);
    }

    @Override
    protected void saveOrUpdate(ProductPropertyElement entity) {
        // do nothing, wait for saved by parent view.
    }

}
