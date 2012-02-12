package com.s3s.ssm.view.detail.sales;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditDetailInvoiceVirtualView extends AbstractSingleEditView<DetailInvoice> {

    private static final String REF_ITEM = "item";
    private static final String REF_PACKLINE = "packageLine";
    private static final String REF_TYPE = "type";
    private static final String REF_STATUS = "status";

    public EditDetailInvoiceVirtualView(DetailInvoice entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, DetailInvoice entity) {
        detailDataModel.addAttribute("item", FieldTypeEnum.DROPDOWN).referenceDataId(REF_ITEM);
        detailDataModel.addAttribute("packageLine", FieldTypeEnum.DROPDOWN).referenceDataId(REF_PACKLINE);
        detailDataModel.addAttribute("amount", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("type", FieldTypeEnum.DROPDOWN).referenceDataId(REF_TYPE);
        detailDataModel.addAttribute("status", FieldTypeEnum.DROPDOWN).referenceDataId(REF_STATUS);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, DetailInvoice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_ITEM, getDaoHelper().getDao(Item.class).findAll(), null);
        refDataModel.putRefDataList(REF_PACKLINE, getDaoHelper().getDao(PackageLine.class).findAll(), null);
        refDataModel.putRefDataList(REF_TYPE, DetailInvoiceType.values());
        refDataModel.putRefDataList(REF_STATUS, DetailInvoiceStatus.values());
    }

    @Override
    protected void loadForCreate(DetailInvoice entity) {
        super.loadForCreate(entity);
        // TODO: work-arround. Invoice should be set from parent view
        entity.setInvoice(new Invoice());
    }

    @Override
    protected void saveOrUpdate(DetailInvoice entity) {
        // THU does not care about tax
        entity.setPriceBeforeTax(entity.getPriceAfterTax());
        entity.setMoneyBeforeTax(entity.getMoneyAfterTax());
        // Do not save. Will be save by parent view
    }

}
