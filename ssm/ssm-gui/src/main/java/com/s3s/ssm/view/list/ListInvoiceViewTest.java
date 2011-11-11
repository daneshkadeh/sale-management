package com.s3s.ssm.view.list;

import java.util.List;

import com.s3s.ssm.entity.InvoiceTest;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.EditMasterInvoiceViewTest;

/**
 * Show list of invoices.
 * 
 * @author phamcongbang
 * 
 */
public class ListInvoiceViewTest extends AbstractListView<InvoiceTest> {
    private static final long serialVersionUID = -9117198848678726047L;

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("id", FieldTypeEnum.TEXTBOX));

        DetailAttribute createdDateField = new DetailAttribute("createdDate", FieldTypeEnum.TEXTBOX);
        listDataModel.add(createdDateField);

        listDataModel.add(new DetailAttribute("customerId", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("totalBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("taxTotal", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("totalAfterTax", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<InvoiceTest>> getDetailViewClass() {
        return EditMasterInvoiceViewTest.class;
    }

}
