package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.param.Item;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditItemView;

public class ListItemView extends AbstractListView<Item> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("product", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("sumUomName", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("baseSellPrice", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("currency", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<Item>> getDetailViewClass() {
        return EditItemView.class;
    }

}
