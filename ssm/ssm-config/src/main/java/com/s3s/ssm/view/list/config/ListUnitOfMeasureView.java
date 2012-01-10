package com.s3s.ssm.view.list.config;

import java.util.List;

import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.config.EditUnitOfMeasureView;

public class ListUnitOfMeasureView extends AbstractListView<UnitOfMeasure> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("uomCategory", FieldTypeEnum.DROPDOWN));
        listDataModel.add(new DetailAttribute("isBaseMeasure", FieldTypeEnum.DROPDOWN));
    }

    @Override
    protected Class<? extends AbstractDetailView<UnitOfMeasure>> getDetailViewClass() {
        return EditUnitOfMeasureView.class;
    }

    @Override
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.UOM;
    }
}
