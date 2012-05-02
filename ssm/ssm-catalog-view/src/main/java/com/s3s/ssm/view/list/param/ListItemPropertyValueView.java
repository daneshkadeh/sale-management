package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListItemPropertyValueView extends ANonSearchListEntityView<ItemPropertyValue> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("property", ListRendererType.TEXT);
        listDataModel.addColumn("element", ListRendererType.TEXT);
        listDataModel.addColumn("value", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ItemPropertyValue>> getEditViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
