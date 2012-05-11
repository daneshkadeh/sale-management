package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.Service;
import com.s3s.ssm.view.edit.DetailDataModel;

public class EditServiceGeneralView extends EditProductGeneralView<Service> {
    private static final long serialVersionUID = 1L;

    public EditServiceGeneralView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void addTabGeneral(DetailDataModel detailDataModel) {
        super.addTabGeneral(detailDataModel);

    }

    @Override
    protected ProductFamilyType getProductFamilyType() {
        return ProductFamilyType.SERVICE;
    }
}
