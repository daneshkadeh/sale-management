package com.s3s.ssm.view.list.finance;

import java.util.List;

import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;

public class ListPaymentView extends AbstractListView<Payment> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<Payment>> getDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
