package com.s3s.ssm.view.list.sales;

import java.util.List;

import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;

public class ListSalesContractView extends AbstractListView<SalesContract> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<SalesContract>> getDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
