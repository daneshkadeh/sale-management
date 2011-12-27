package com.s3s.ssm.view.detail.contact;

import com.s3s.ssm.entity.contact.ContactShop;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditContactShopVirtualView extends AbstractDetailView<ContactShop> {

    public EditContactShopVirtualView(ContactShop entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ContactShop entity) {
        // detailDataModel.addAttribute("contact", FieldTypeEnum.TEXTBOX).editable(false);
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("phone", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fixPhone", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("email", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("remark", FieldTypeEnum.TEXTBOX);
    }

    @Override
    protected void saveOrUpdate(ContactShop entity) {
        // Fake id
        // TODO: How to put this entity to the listContactShop in EditContactView?
        // if (entity.getId() == null) {
        // entity.setId(-1L);
        // }
    }

}
