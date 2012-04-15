package com.s3s.ssm.view.detail.param;

import java.util.Arrays;
import java.util.Map;

import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductProperty.PropertyType;
import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class EditProductProperty extends AbstractMasterDetailView<ProductProperty, ProductPropertyElement> {
    private static final long serialVersionUID = 1L;
    private static final String REF_PROPERTY_TYPE = "REF_PROPERTY_TYPE";

    public EditProductProperty(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("id", ListRendererType.TEXT);
        listDataModel.addColumn("value", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ProductPropertyElement>> getChildDetailViewClass() {
        return EditPropertyElementVirtualView.class;
    }

    @Override
    protected String getParentFieldName() {
        return "property";
    }

    @Override
    protected String getChildFieldName() {
        return "elements";
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ProductProperty entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        // Only support list property now
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(REF_PROPERTY_TYPE).editable(false);
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
    }

    @Override
    protected String getDefaultTitle(ProductProperty entity) {
        return entity.getCode();
    }
}
