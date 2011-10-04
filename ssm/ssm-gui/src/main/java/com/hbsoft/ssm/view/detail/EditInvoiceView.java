package com.hbsoft.ssm.view.detail;

import java.util.Date;
import java.util.List;

import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.view.AbstractDetailView;

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
        // listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));

        DetailDataModel createdDateField = new DetailDataModel("createdDate", FieldTypeEnum.TEXT_BOX);
        createdDateField.setEditable(false);
        listDataModel.add(createdDateField);
        invoice.setCreatedDate(new Date());

        listDataModel.add(new DetailDataModel("customerId", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("totalBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("taxTotal", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("totalAfterTax", FieldTypeEnum.TEXT_BOX));
    }

}
