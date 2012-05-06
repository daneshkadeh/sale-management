package com.s3s.ssm.view.detail.param;

import javax.swing.Icon;

import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListProductPropertyElementComponent extends AListComponent<ProductPropertyElement> {

    public ListProductPropertyElementComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        // listDataModel.addColumn("id", ListRendererType.TEXT).notEditable();
        listDataModel.addColumn("value", ListRendererType.TEXT).width(200);
    }

}
