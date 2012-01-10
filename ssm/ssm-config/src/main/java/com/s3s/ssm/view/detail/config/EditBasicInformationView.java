package com.s3s.ssm.view.detail.config;

import com.s3s.ssm.entity.config.BasicInformation;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.config.UploadFile;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditBasicInformationView extends AbstractDetailView<BasicInformation> {
    private static final long serialVersionUID = 1L;
    private static final String CURRENCY_REF_ID = "1";

    public EditBasicInformationView(BasicInformation entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, BasicInformation entity) {
        // information of company
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("companyName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("agent", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("position", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("logo.data", FieldTypeEnum.IMAGE);
        detailDataModel.addAttribute("companyAddress", FieldTypeEnum.RICH_TEXTBOX);
        detailDataModel.addAttribute("tel", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("website", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("email", FieldTypeEnum.TEXTBOX);
        // information of bank
        detailDataModel.addAttribute("bankName", FieldTypeEnum.RICH_TEXTBOX);
        detailDataModel.addAttribute("bankAddress", FieldTypeEnum.RICH_TEXTBOX);
        detailDataModel.addAttribute("usdAcctNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("vndAcctNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("beneficeName", FieldTypeEnum.TEXTBOX);
        // general parameter
        detailDataModel.addAttribute("defCurrency", FieldTypeEnum.DROPDOWN).referenceDataId(CURRENCY_REF_ID);
        detailDataModel.addAttribute("defDetailInvNum", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("defPageRowNum", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("defPaymentMethod", FieldTypeEnum.TEXTBOX);
        // rule of code generation
        detailDataModel.addAttribute("orderInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("salesInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("salesRefundInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("purInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("purRefundInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("sponContractCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("movementInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("exportInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("importInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("paymentBillCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("receiptsCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("promotionCodeRule", FieldTypeEnum.TEXTBOX);
        // the path backup data
        detailDataModel.addAttribute("backupPath", FieldTypeEnum.FILE_CHOOSER);
        // sold on credit
        detailDataModel.addAttribute("digitAfterCommaQuan", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("digitAfterCommaPrice", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("digitAfterCommaRate", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("thousandsSeparator", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("oddSeparator", FieldTypeEnum.TEXTBOX);

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, BasicInformation entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(CURRENCY_REF_ID, getDaoHelper().getDao(SCurrency.class).findAll(), null);
    }

    @Override
    protected void loadForCreate(BasicInformation entity) {
        super.loadForCreate(entity);
        entity.setLogo(new UploadFile());
    }

    @Override
    protected void loadForEdit(BasicInformation entity) {
        super.loadForEdit(entity);
        if (entity.getLogo() == null) {
            entity.setLogo(new UploadFile());
        }
    }

    @Override
    protected void saveOrUpdate(BasicInformation entity) {
        // Save Image. TODO: check image changed or not before saving.
        entity.getLogo().setTitle(entity.getCode());
        getDaoHelper().getDao(UploadFile.class).saveOrUpdate(entity.getLogo());
        super.saveOrUpdate(entity);
    }
}
