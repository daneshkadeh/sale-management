package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListItemPropertyValueView extends AbstractListView<ItemPropertyValue> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("property", ListColumnType.TEXT);
        listDataModel.addColumn("element", ListColumnType.TEXT);
        listDataModel.addColumn("value", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ItemPropertyValue>> getEditViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
