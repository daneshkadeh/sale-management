package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.view.detail.param.EditPackageView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPackageView extends AbstractListView<SPackage> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<SPackage>> getEditViewClass() {
        return EditPackageView.class;
    }

}
