package com.s3s.ssm.view.detail;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.entity.DetailInvoice;
import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractMasterDetailView;

/**
 * Edit invoice with list details.
 * 
 * @author phamcongbang
 * 
 */
public class EditMasterInvoiceView extends AbstractMasterDetailView<Invoice, DetailInvoice> {
    private static final long serialVersionUID = -8901891656724849010L;

    /**
     * The default constructor.
     * 
     * @param entity
     */
    public EditMasterInvoiceView(Invoice entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel, Invoice invoice) {
        DetailDataModel createdDateField = new DetailDataModel("createdDate", FieldTypeEnum.TEXTBOX);
        createdDateField.setEditable(false);
        listDataModel.add(createdDateField);
        invoice.setCreatedDate(new Date());

        listDataModel.add(new DetailDataModel("customerId", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("totalBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("taxTotal", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("totalAfterTax", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("goodsId", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("goodsName", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("quantity", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXTBOX));

        listDataModel.add(new DetailDataModel("moneyBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("moneyOfTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("moneyAfterTax", FieldTypeEnum.TEXTBOX));

    }

    @Override
    protected void saveOrUpdate(Invoice invoice, List<DetailInvoice> listDetailInvoice) {
        // ConfigProvider.getInstance().getInvoiceService().save(invoice);
        for (DetailInvoice detailInvoice : listDetailInvoice) {
            detailInvoice.setInvoice(invoice);
            // ConfigProvider.getInstance().getDetailInvoiceService().save(detailInvoice);
        }
    }

    @Override
    protected String getChildFieldName() {
        return "detailInvoices";
    }

    @Override
    protected Class<? extends AbstractDetailView<DetailInvoice>> getChildDetailViewClass() {
        return EditDetailInvoiceView.class;
    }
}
