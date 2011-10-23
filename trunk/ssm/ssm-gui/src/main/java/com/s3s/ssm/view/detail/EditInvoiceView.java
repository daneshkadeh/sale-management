package com.s3s.ssm.view.detail;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.FieldTypeEnum;
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

}