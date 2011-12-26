package com.s3s.ssm.view.detail.param;

import java.util.Arrays;

import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.ProductType;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditProductTypeView extends AbstractDetailView<ProductType> {

    private static final String REF_PRODUCT_FAMILY = "0";

    public EditProductTypeView(ProductType entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ProductType entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("productFamilyType", FieldTypeEnum.DROPDOWN).referenceDataId(REF_PRODUCT_FAMILY);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ProductType entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PRODUCT_FAMILY, Arrays.asList(ProductFamilyType.values()), null);
    }

}
