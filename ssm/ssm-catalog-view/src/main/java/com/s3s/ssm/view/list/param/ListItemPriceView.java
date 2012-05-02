package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.view.detail.param.EditItemPriceVirtualView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListItemPriceView extends ANonSearchListEntityView<ItemPrice> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("audienceCategory", ListRendererType.TEXT);
        listDataModel.addColumn("sellPrice", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ItemPrice>> getEditViewClass() {
        return EditItemPriceVirtualView.class;
    }

}
