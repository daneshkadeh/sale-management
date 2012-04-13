package com.s3s.ssm.view.list.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.ImportationSC;
import com.s3s.ssm.view.detail.sales.EditImportationSCView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListImportationSCView extends AbstractListView<ImportationSC> {
    public ListImportationSCView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("createdDate", ListRendererType.TEXT);
        listDataModel.addColumn("shipmentDate", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<ImportationSC>> getEditViewClass() {
        return EditImportationSCView.class;
    }

}
