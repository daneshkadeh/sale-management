package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditDetailInvoiceVirtualView extends AbstractSingleEditView<DetailInvoice> {

    private static final String REF_ITEM = "item";
    private static final String REF_PACKLINE = "packageLine";
    private static final String REF_TYPE = "type";
    private static final String REF_STATUS = "status";

    public EditDetailInvoiceVirtualView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, DetailInvoice entity) {
        detailDataModel.addAttribute("item", DetailFieldType.DROPDOWN).referenceDataId(REF_ITEM);
        detailDataModel.addAttribute("packageLine", DetailFieldType.DROPDOWN).referenceDataId(REF_PACKLINE);
        detailDataModel.addAttribute("amount", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("priceAfterTax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("moneyAfterTax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_TYPE);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_STATUS);
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
    protected DetailInvoice loadForCreate() {
        DetailInvoice detail = super.loadForCreate();
        // TODO: work-arround. Invoice should be set from parent view
        detail.setInvoice(new Invoice());
        return detail;
    }

    @Override
    protected void saveOrUpdate(DetailInvoice entity) {
        // THU does not care about tax
        entity.setPriceBeforeTax(entity.getPriceAfterTax());
        entity.setMoneyBeforeTax(entity.getMoneyAfterTax());
        super.saveOrUpdate(entity);
    }

}
