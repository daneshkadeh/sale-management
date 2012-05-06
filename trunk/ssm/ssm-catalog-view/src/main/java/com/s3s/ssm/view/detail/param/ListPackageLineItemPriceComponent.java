package com.s3s.ssm.view.detail.param;

import javax.swing.Icon;

import com.s3s.ssm.entity.catalog.PackageLineItemPrice;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPackageLineItemPriceComponent extends AListComponent<PackageLineItemPrice> {

    public ListPackageLineItemPriceComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.COMBOBOX);
        listDataModel.addColumn("audienceCategory", ListRendererType.TEXT, ListEditorType.COMBOBOX);
        listDataModel.addColumn("sellPrice", ListRendererType.TEXT, ListEditorType.MONEY);
    }

}
