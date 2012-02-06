package com.s3s.ssm.view.detail.param;

import java.util.Arrays;
import java.util.List;

import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductProperty.PropertyType;
import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractMasterDetailView;

public class EditProductProperty extends AbstractMasterDetailView<ProductProperty, ProductPropertyElement> {

    private static final String REF_PROPERTY_TYPE = "REF_PROPERTY_TYPE";

    public EditProductProperty(ProductProperty entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("id", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("value", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<ProductPropertyElement>> getChildDetailViewClass() {
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
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("type", FieldTypeEnum.DROPDOWN).setMandatory(true)
                .referenceDataId(REF_PROPERTY_TYPE);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ProductProperty entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PROPERTY_TYPE, Arrays.asList(PropertyType.values()), null);
    }

}
