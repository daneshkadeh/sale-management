package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import javax.swing.Icon;

import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInvoiceExportStoreComponentView extends AListComponent<ExportStoreForm> {

    public ListInvoiceExportStoreComponentView(Map<String, Object> params, Icon icon, String label, String tooltip) {
        super(params, icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("createdDate", ListRendererType.DATE);
        listDataModel.addColumn("transType", ListRendererType.TEXT);
        listDataModel.addColumn("store", ListRendererType.TEXT);
        listDataModel.addColumn("realQuanTotal", ListRendererType.TEXT);
    }

}
