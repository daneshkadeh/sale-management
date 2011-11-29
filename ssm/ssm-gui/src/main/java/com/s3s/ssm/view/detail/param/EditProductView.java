package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.param.Manufacturer;
import com.s3s.ssm.entity.param.Product;
import com.s3s.ssm.entity.param.ProductType;
import com.s3s.ssm.entity.param.UnitOfMeasure;
import com.s3s.ssm.entity.param.UploadFile;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditProductView extends AbstractDetailView<Product> {

    private static final String TYPE_REF_ID = "1";
    private static final String MANU_REF_ID = "2";
    private static final String UOM_REF_ID = "3";

    public EditProductView(Product entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Product entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("type", FieldTypeEnum.DROPDOWN).referenceDataId(TYPE_REF_ID);
        detailDataModel.addAttribute("manufacturer", FieldTypeEnum.DROPDOWN).referenceDataId(MANU_REF_ID);
        detailDataModel.addAttribute("model", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("description", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("mainUom", FieldTypeEnum.DROPDOWN).referenceDataId(UOM_REF_ID);
        detailDataModel.addAttribute("uploadFile.data", FieldTypeEnum.IMAGE);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Product entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(TYPE_REF_ID, getDaoHelper().getDao(ProductType.class).findAll(), null);
        refDataModel.putRefDataList(MANU_REF_ID, getDaoHelper().getDao(Manufacturer.class).findAll(), null);
        refDataModel.putRefDataList(UOM_REF_ID, getDaoHelper().getDao(UnitOfMeasure.class).findAll(), null);
    }

    @Override
    protected void loadForCreate(Product entity) {
        super.loadForCreate(entity);
        entity.setUploadFile(new UploadFile());
    }

    @Override
    protected void loadForEdit(Product entity) {
        super.loadForEdit(entity);
        if (entity.getUploadFile() == null) {
            entity.setUploadFile(new UploadFile());
        }
    }

    @Override
    protected void saveOrUpdate(Product entity) {
        // Save Image. TODO: check image changed or not before saving.
        entity.getUploadFile().setTitle(entity.getCode());
        getDaoHelper().getDao(UploadFile.class).saveOrUpdate(entity.getUploadFile());
        super.saveOrUpdate(entity);
    }

}
