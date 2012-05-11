package com.s3s.ssm.view.detail.param;

import java.util.Arrays;
import java.util.Map;

import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductProperty.PropertyType;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class EditProductProperty extends AbstractSingleEditView<ProductProperty> {
    private static final long serialVersionUID = 1L;
    private static final String REF_PROPERTY_TYPE = "REF_PROPERTY_TYPE";
    private static final String REF_PRODUCT_FAMILY_TYPE = "REF_PRODUCT_FAMILY_TYPE";

    public EditProductProperty(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ProductProperty entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        // Only support list property now
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(REF_PROPERTY_TYPE).editable(false);
        detailDataModel.addAttribute("productFamilyType", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(REF_PRODUCT_FAMILY_TYPE);
        detailDataModel.addAttribute("elements", DetailFieldType.LIST).componentInfo(
                createListProductPropertyElementInfo());
    }

    private IComponentInfo createListProductPropertyElementInfo() {
        ListProductPropertyElementComponent component = new ListProductPropertyElementComponent(null, null, null);
        return new ListComponentInfo(component, "property");
    }

    @Override
    protected ProductProperty loadForCreate(Map<String, Object> request) {
        ProductProperty entity = super.loadForCreate(request);
        entity.setType(PropertyType.LIST);
        return entity;
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ProductProperty entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PROPERTY_TYPE, Arrays.asList(PropertyType.values()), null);
        refDataModel.putRefDataList(REF_PRODUCT_FAMILY_TYPE, Arrays.asList(ProductFamilyType.values()), null);
    }

    @Override
    protected String getDefaultTitle(ProductProperty entity) {
        return entity.getCode();
    }
}
