package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditPropertyElementVirtualView extends AbstractSingleEditView<ProductPropertyElement> {

    public EditPropertyElementVirtualView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ProductPropertyElement entity) {
        detailDataModel.addAttribute("value", DetailFieldType.TEXTBOX).mandatory(true);
    }

    @Override
    protected void saveOrUpdate(ProductPropertyElement entity) {
        // do nothing, wait for saved by parent view.
    }

}
