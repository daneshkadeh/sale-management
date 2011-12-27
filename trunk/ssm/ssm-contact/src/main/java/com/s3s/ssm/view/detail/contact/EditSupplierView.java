package com.s3s.ssm.view.detail.contact;

import org.apache.commons.lang.StringUtils;

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditSupplierView extends AbstractDetailView<Supplier> {

    private static final String REF_BANK = "REF_BANK";

    public EditSupplierView(Supplier entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Supplier entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        // detailDataModel.addAttribute("mainContact", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("phoneNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fixPhoneNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("faxNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("bankAccount.bank", FieldTypeEnum.DROPDOWN).referenceDataId(REF_BANK);
        detailDataModel.addAttribute("bankAccount.accountNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("bankAccount.accountName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("note", FieldTypeEnum.RICH_TEXTBOX);
    }

    @Override
    protected void loadForCreate(Supplier entity) {
        super.loadForCreate(entity);
        entity.setBankAccount(new BankAccount());
    }

    @Override
    protected void loadForEdit(Supplier entity) {
        super.loadForEdit(entity);
        if (entity.getBankAccount() == null) {
            entity.setBankAccount(new BankAccount());
        }
    }

    @Override
    protected void saveOrUpdate(Supplier entity) {
        // TODO: Must save bank account before supplier. How to avoid it?
        if (StringUtils.isNotEmpty(entity.getBankAccount().getAccountNumber())) {
            getDaoHelper().getDao(BankAccount.class).saveOrUpdate(entity.getBankAccount());
        }
        super.saveOrUpdate(entity);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Supplier entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_BANK, getDaoHelper().getDao(Bank.class).findAll(), null);
    }

}
