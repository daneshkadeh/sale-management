package com.hbsoft.ssm.view.detail;

import java.util.Date;
import java.util.List;

import com.hbsoft.ssm.entity.DetailInvoice;
import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.AbstractMasterDetailView;

/**
 * Edit invoice with list details.
 * 
 * @author phamcongbang
 * 
 */
public class EditMasterInvoiceView extends AbstractMasterDetailView<Invoice, DetailInvoice> {

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel, Invoice invoice) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));

        DetailDataModel createdDateField = new DetailDataModel("createdDate", FieldTypeEnum.TEXT_BOX);
        createdDateField.setEditable(false);
        listDataModel.add(createdDateField);
        invoice.setCreatedDate(new Date());

        listDataModel.add(new DetailDataModel("customerId", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("totalBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("taxTotal", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("totalAfterTax", FieldTypeEnum.TEXT_BOX));
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("goodsId", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("goodsName", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("quantity", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXT_BOX));

        listDataModel.add(new DetailDataModel("moneyBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("moneyOfTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("moneyAfterTax", FieldTypeEnum.TEXT_BOX));

    }

    @Override
    protected void initialEditDetailPresentationView(List<DetailDataModel> listDataModel, DetailInvoice detailInvoice) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("goodsId", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("goodsName", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("quantity", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXT_BOX));

        listDataModel.add(new DetailDataModel("moneyBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("moneyOfTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("moneyAfterTax", FieldTypeEnum.TEXT_BOX));

        // listDataModel.add(new DetailDataModel("invoiceId", FieldTypeEnum.TEXT_BOX));
        // TODO: work-around to pass onBindAndValidate. InvoiceId will be set later.
        detailInvoice.setInvoiceId(0);
    }

    @Override
    protected void saveOrUpdate(Invoice invoice, List<DetailInvoice> listDetailInvoice) {
        ConfigProvider.getInstance().getInvoiceService().save(invoice);
        for (DetailInvoice detailInvoice : listDetailInvoice) {
            detailInvoice.setInvoiceId(invoice.getId());
            ConfigProvider.getInstance().getDetailInvoiceService().save(detailInvoice);
        }
    }
}
