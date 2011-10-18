package com.s3s.ssm.view.list;

import java.util.List;

import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.EditMasterInvoiceView;

/**
 * Show list of invoices.
 * 
 * @author phamcongbang
 * 
 */
public class ListInvoiceView extends AbstractListView<Invoice> {
    private static final long serialVersionUID = -9117198848678726047L;

    @Override
    protected void initialPresentationView(List<DetailDataModel> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));

        DetailDataModel createdDateField = new DetailDataModel("createdDate", FieldTypeEnum.TEXT_BOX);
        listDataModel.add(createdDateField);

        listDataModel.add(new DetailDataModel("customerId", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("totalBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("taxTotal", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("totalAfterTax", FieldTypeEnum.TEXT_BOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<Invoice>> getDetailViewClass() {
        return EditMasterInvoiceView.class;
    }

}
