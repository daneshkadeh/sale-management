package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;

public class ListItemPropertyValueView extends AbstractListView<ItemPropertyValue> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("property", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("element", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("value", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<ItemPropertyValue>> getEditViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
