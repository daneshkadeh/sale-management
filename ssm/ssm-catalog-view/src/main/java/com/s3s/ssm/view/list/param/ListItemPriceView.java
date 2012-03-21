package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.view.detail.param.EditItemPriceVirtualView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListItemPriceView extends AbstractListView<ItemPrice> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("partnerCategory", ListColumnType.TEXT);
        listDataModel.addColumn("sellPrice", ListColumnType.TEXT);
        listDataModel.addColumn("currency", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ItemPrice>> getEditViewClass() {
        return EditItemPriceVirtualView.class;
    }

}
