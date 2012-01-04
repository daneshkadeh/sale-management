package com.s3s.ssm.view.list.config;

import java.util.List;

import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.config.EditUomCategoryView;

public class ListUomCategoryView extends AbstractListView<UomCategory> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));

    }

    @Override
    protected Class<? extends AbstractDetailView<UomCategory>> getDetailViewClass() {
        return EditUomCategoryView.class;
    }

}