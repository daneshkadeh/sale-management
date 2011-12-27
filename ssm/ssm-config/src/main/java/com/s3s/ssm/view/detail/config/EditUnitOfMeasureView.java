package com.s3s.ssm.view.detail.config;

import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditUnitOfMeasureView extends AbstractDetailView<UnitOfMeasure> {

    private static final String BOOL_REF_ID = "1";
    private static final String CATE_REF_ID = "2";

    public EditUnitOfMeasureView(UnitOfMeasure entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, UnitOfMeasure entity) {
        detailDataModel.addAttribute("uomCategory", FieldTypeEnum.DROPDOWN).referenceDataId(CATE_REF_ID);
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isBaseMeasure", FieldTypeEnum.DROPDOWN).referenceDataId(BOOL_REF_ID);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, UnitOfMeasure entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Boolean> boolList = Arrays.asList(Boolean.TRUE, Boolean.FALSE);
        refDataModel.putRefDataList(BOOL_REF_ID,
                refDataModel.new ReferenceData(boolList, new DefaultListCellRenderer()));
        List<UomCategory> cateList = getDaoHelper().getDao(UomCategory.class).findAll();
        refDataModel.putRefDataList(CATE_REF_ID,
                refDataModel.new ReferenceData(cateList, new DefaultListCellRenderer()));
    }
}
