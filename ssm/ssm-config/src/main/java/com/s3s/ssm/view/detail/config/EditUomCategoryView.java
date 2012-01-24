package com.s3s.ssm.view.detail.config;

import java.util.List;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditUomCategoryView extends AbstractDetailView<UomCategory> {
    private static final long serialVersionUID = 1L;
    private static final String CATE_REF_ID = "1";

    public EditUomCategoryView(UomCategory entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, UomCategory entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("parentUomCategory", FieldTypeEnum.DROPDOWN).referenceDataId(CATE_REF_ID);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, UomCategory entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<UomCategory> cateList = getDaoHelper().getDao(UomCategory.class).findAll();
        cateList.remove(entity);
        refDataModel.putRefDataList(CATE_REF_ID,
                refDataModel.new ReferenceData(cateList, new DefaultListCellRenderer()));
    }
}
