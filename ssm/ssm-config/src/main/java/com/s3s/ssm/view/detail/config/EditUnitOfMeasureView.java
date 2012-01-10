package com.s3s.ssm.view.detail.config;

import java.util.List;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditUnitOfMeasureView extends AbstractDetailView<UnitOfMeasure> {

    private static final String CATE_REF_ID = "1";

    public EditUnitOfMeasureView(UnitOfMeasure entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, UnitOfMeasure entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("uomCategory", FieldTypeEnum.DROPDOWN).referenceDataId(CATE_REF_ID);
        detailDataModel.addAttribute("isBaseMeasure", FieldTypeEnum.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, UnitOfMeasure entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<UomCategory> cateList = getDaoHelper().getDao(UomCategory.class).findAll();
        refDataModel.putRefDataList(CATE_REF_ID,
                refDataModel.new ReferenceData(cateList, new DefaultListCellRenderer()));
    }

    @Override
    protected void saveOrUpdate(UnitOfMeasure entity) {
        if (entity.getIsBaseMeasure()) {
            List<UnitOfMeasure> uomList = getDaoHelper().getDao(UnitOfMeasure.class).findAll();
            uomList.remove(entity);
            for (UnitOfMeasure uom : uomList) {
                if (uom.getIsBaseMeasure() == true) {
                    uom.setIsBaseMeasure(false);
                    getDaoHelper().getDao(UnitOfMeasure.class).saveOrUpdate(uom);
                    break;
                }
            }
        }

        super.saveOrUpdate(entity);
    }
}
