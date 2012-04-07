package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.view.detail.param.EditProductProperty;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListProductPropertyView extends AbstractListView<ProductProperty> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("type", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ProductProperty>> getEditViewClass() {
        return EditProductProperty.class;
    }

}
