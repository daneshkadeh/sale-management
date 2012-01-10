package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.entity.config.UploadFile;
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
        detailDataModel.addAttribute("symbol.data", FieldTypeEnum.IMAGE);
    }

    @Override
    protected void loadForCreate(Manufacturer entity) {
        super.loadForCreate(entity);
        entity.setSymbol(new UploadFile());
    }

    @Override
    protected void loadForEdit(Manufacturer entity) {
        super.loadForEdit(entity);
        if (entity.getSymbol() == null) {
            entity.setSymbol(new UploadFile());
        }
    }

    @Override
    protected void saveOrUpdate(Manufacturer entity) {
        // Save Image. TODO: check image changed or not before saving.
        entity.getSymbol().setTitle(entity.getCode());
        getDaoHelper().getDao(UploadFile.class).saveOrUpdate(entity.getSymbol());
        super.saveOrUpdate(entity);
    }
}
