package com.s3s.ssm.view.detail;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.entity.DetailInvoice;
import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
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
    public void initialPresentationView(DetailDataModel detailDataModel, Invoice invoice) {
        invoice.setCreatedDate(new Date());
        detailDataModel.addAttribute("createdDate", FieldTypeEnum.TEXTBOX).editable(false);
        detailDataModel.addAttribute("customerId", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("totalBeforeTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("taxTotal", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("totalAfterTax", FieldTypeEnum.TEXTBOX);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("goodsId", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("goodsName", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("quantity", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("tax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX));

        listDataModel.add(new DetailAttribute("moneyBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("moneyOfTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX));

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
