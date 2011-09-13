package com.hbsoft.ssm.view.detail;

import java.util.List;

import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.AbstractDetailView;

public class EditCustomerView extends AbstractDetailView<Customer> {

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXT_BOX));
    }

    @Override
    protected void saveOrUpdate(Customer entity2) {
        ConfigProvider.getInstance().getCustomerSerice().save(entity2);
    }
}
