package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.Service;
import com.s3s.ssm.view.detail.param.EditServiceView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListServiceView extends AbstractListView<Service> {
    private static final long serialVersionUID = 1L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("type", ListRendererType.TEXT);
        listDataModel.addColumn("description", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Service>> getEditViewClass() {
        return EditServiceView.class;
    }

}
