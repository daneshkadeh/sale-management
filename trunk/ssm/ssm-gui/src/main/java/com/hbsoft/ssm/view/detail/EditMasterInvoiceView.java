package com.hbsoft.ssm.view.detail;

import java.util.Date;
import java.util.List;

import com.hbsoft.ssm.entity.DetailInvoice;
import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.AbstractDetailView;
import com.hbsoft.ssm.view.AbstractMasterDetailView;

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
    protected void saveOrUpdate(Invoice invoice, List<DetailInvoice> listDetailInvoice) {
        ConfigProvider.getInstance().getInvoiceService().save(invoice);
        for (DetailInvoice detailInvoice : listDetailInvoice) {
            detailInvoice.setInvoice(invoice);
            ConfigProvider.getInstance().getDetailInvoiceService().save(detailInvoice);
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
