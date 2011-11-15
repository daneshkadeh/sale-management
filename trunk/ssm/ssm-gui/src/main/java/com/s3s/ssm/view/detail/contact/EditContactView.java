package com.s3s.ssm.view.detail.contact;

import java.util.List;

import com.s3s.ssm.entity.contact.Bank;
import com.s3s.ssm.entity.contact.BankAccount;
import com.s3s.ssm.entity.contact.Contact;
import com.s3s.ssm.entity.contact.ContactShop;
import com.s3s.ssm.entity.contact.ContactType;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractMasterDetailView;

public class EditContactView extends AbstractMasterDetailView<Contact, ContactShop> {

    private static final String REF_CONTACT_TYPE = "0";
    private static final String REF_BANK = "1";

    public EditContactView(Contact entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("contact", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("address", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("phone", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("fixPhone", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("fax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("email", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("remark", FieldTypeEnum.TEXTBOX));

    }

    @Override
    protected Class<? extends AbstractDetailView<ContactShop>> getChildDetailViewClass() {
        return EditContactShopView.class;
    }

    @Override
    protected String getChildFieldName() {
        return "listShops";
    }

    @Override
    protected void saveOrUpdate(Contact masterEntity, List<ContactShop> detailEntities) {
        // TODO: not work, it's just crazy, detailEntities is always empty.
        for (ContactShop contactShop : detailEntities) {
            masterEntity.addShop(contactShop);
        }

        // TODO: check bankAccount not updated and do not update in database.
        getDaoHelper().getDao(BankAccount.class).saveOrUpdate(masterEntity.getBankAccount());
        getDaoHelper().getDao(Contact.class).saveOrUpdate(masterEntity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Contact entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fullName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("contactType", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CONTACT_TYPE);
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("phone", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fixPhone", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("email", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("taxCode", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("bank", FieldTypeEnum.DROPDOWN).referenceDataId(REF_BANK);
        detailDataModel.addAttribute("accountNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("accountName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("maximumDayDebt", FieldTypeEnum.TEXTBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Contact entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_BANK, getDaoHelper().getDao(Bank.class).findAll(), null);
        refDataModel.putRefDataList(REF_CONTACT_TYPE, getDaoHelper().getDao(ContactType.class).findAll(), null);
    }

}
