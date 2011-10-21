package com.s3s.ssm.view.list;

import java.util.List;

import com.s3s.ssm.entity.Customer;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.EditCustomerView;

public class ListCustomerView extends AbstractListView<Customer> {
    private static final long serialVersionUID = -8565742036667887785L;

    @Override
    protected void initialPresentationView(List<DetailDataModel> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected List<Customer> loadData() {
        return getDaoHelper().getDao(Customer.class).findAll();
    }

    @Override
    protected Class<? extends AbstractDetailView<Customer>> getDetailViewClass() {
        return EditCustomerView.class;
    }
}
