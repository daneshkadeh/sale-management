package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AbstractListView;

public class ListItemPropertyValueView extends AbstractListView<ItemPropertyValue> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("property", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("element", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("value", DetailFieldType.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<ItemPropertyValue>> getEditViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
