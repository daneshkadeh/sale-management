package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditItemPriceVirtualView;

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
