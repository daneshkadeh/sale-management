package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.view.detail.param.EditItemPriceVirtualView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AbstractListView;

public class ListItemPriceView extends AbstractListView<ItemPrice> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("partnerCategory", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("sellPrice", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("currency", DetailFieldType.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<ItemPrice>> getEditViewClass() {
        return EditItemPriceVirtualView.class;
    }

}
