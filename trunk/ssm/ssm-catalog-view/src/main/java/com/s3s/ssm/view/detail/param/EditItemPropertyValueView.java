package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditItemPropertyValueView extends AbstractSingleEditView<ItemPropertyValue> {

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ItemPropertyValue entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("item", DetailFieldType.TEXTBOX).editable(false);
        detailDataModel.addAttribute("property", DetailFieldType.DROPDOWN);

    }

    @Override
    protected ItemPropertyValue loadForCreate(Map<String, Object> request) {
        ItemPropertyValue value = super.loadForCreate(request);
        value.setItem((Item) getDaoHelper().getDao(this.getParentClass()).findById(this.getParentId()));
        return value;
    }

}
