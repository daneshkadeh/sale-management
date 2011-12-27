package com.s3s.ssm.view.list.finance;

import java.util.List;

import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;

public class ListPaymentTypeView extends AbstractListView<PaymentType> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<PaymentType>> getDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
