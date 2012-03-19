package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.view.detail.param.EditProductProperty;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AbstractListView;

public class ListProductPropertyView extends AbstractListView<ProductProperty> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("type", DetailFieldType.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<ProductProperty>> getEditViewClass() {
        return EditProductProperty.class;
    }

}
