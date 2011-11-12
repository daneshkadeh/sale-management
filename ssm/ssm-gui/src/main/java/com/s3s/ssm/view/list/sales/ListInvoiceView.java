package com.s3s.ssm.view.list.sales;

import java.util.List;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;

public class ListInvoiceView extends AbstractListView<Invoice> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<Invoice>> getDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
