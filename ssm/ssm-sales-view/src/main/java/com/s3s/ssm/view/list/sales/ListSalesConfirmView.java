package com.s3s.ssm.view.list.sales;

import com.s3s.ssm.entity.sales.SalesConfirm;
import com.s3s.ssm.view.detail.sales.EditSalesConfirmMultiView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListSalesConfirmView extends AbstractListView<SalesConfirm> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("createdDate", ListRendererType.DATE);
        listDataModel.addColumn("supplier", ListRendererType.TEXT);
        listDataModel.addColumn("expectedQtySC", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
        listDataModel.addColumn("description", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<SalesConfirm>> getEditViewClass() {
        return EditSalesConfirmMultiView.class;
    }

}
