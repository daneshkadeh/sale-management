package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditItemPropertyValueView extends AbstractSingleEditView<ItemPropertyValue> {

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ItemPropertyValue entity) {
        detailDataModel.addAttribute("item", DetailFieldType.TEXTBOX).editable(false);
        detailDataModel.addAttribute("property", DetailFieldType.DROPDOWN);

    }

    @Override
    protected ItemPropertyValue loadForCreate() {
        ItemPropertyValue value = super.loadForCreate();
        value.setItem((Item) getDaoHelper().getDao(this.getParentClass()).findById(this.getParentId()));
        return value;
    }

}
