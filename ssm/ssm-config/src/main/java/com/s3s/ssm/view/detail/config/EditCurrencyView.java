package com.s3s.ssm.view.detail.config;

import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditCurrencyView extends AbstractDetailView<SCurrency> {
    private static final long serialVersionUID = 1L;
    private static final String BOOL_REF_ID = "1";
    public EditCurrencyView(SCurrency entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, SCurrency entity) {
    	detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
    	detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("symbol", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isActive", FieldTypeEnum.DROPDOWN).referenceDataId(BOOL_REF_ID);
    }
    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, SCurrency entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Boolean> boolList = Arrays.asList(Boolean.TRUE, Boolean.FALSE);
        refDataModel.putRefDataList(BOOL_REF_ID,
                refDataModel.new ReferenceData(boolList, new DefaultListCellRenderer()));
    }
}
