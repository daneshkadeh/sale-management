package com.s3s.ssm.view.detail.param;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductProperty.PropertyType;
import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditProductProperty extends AbstractMasterDetailView<ProductProperty, ProductPropertyElement> {
    private static final long serialVersionUID = 1L;
    private static final String REF_PROPERTY_TYPE = "REF_PROPERTY_TYPE";

    public EditProductProperty(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("id", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("value", DetailFieldType.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<ProductPropertyElement>> getChildDetailViewClass() {
        return EditPropertyElementVirtualView.class;
    }

    @Override
    protected String getChildFieldName() {
        return "elements";
    }

    @Override
    protected void addDetailIntoMaster(ProductProperty masterEntity, ProductPropertyElement detailEntity) {
        masterEntity.addElement(detailEntity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ProductProperty entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).mandatory(true).referenceDataId(REF_PROPERTY_TYPE);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ProductProperty entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PROPERTY_TYPE, Arrays.asList(PropertyType.values()), null);
    }

}
