package com.hbsoft.ssm.view.list;

import java.util.List;

import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.AbstractCommonListView;
import com.hbsoft.ssm.view.AbstractDetailView;
import com.hbsoft.ssm.view.detail.EditCustomerView;

public class ListCustomerView extends AbstractCommonListView<Customer> {

    @Override
    protected void initialPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXT_BOX));
    }

    @Override
    protected List<Customer> loadData() {
        return ConfigProvider.getInstance().getCustomerSerice().findAll();
    }

    @Override
    protected AbstractDetailView getDetailView() {
        return new EditCustomerView();
    }
}
