package com.s3s.ssm.view.detail;

import java.util.Date;

import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

/**
 * This class is not used.
 * 
 * @author phamcongbang
 * 
 */
@Deprecated
public class EditInvoiceView extends AbstractDetailView<Invoice> {

    public EditInvoiceView(Invoice entity) {
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

}
