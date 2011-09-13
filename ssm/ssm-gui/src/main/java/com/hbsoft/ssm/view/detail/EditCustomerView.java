package com.hbsoft.ssm.view.detail;

import java.util.Arrays;
import java.util.List;

import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.model.ReferenceDataList;
import com.hbsoft.ssm.model.ReferenceDataModel;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.AbstractDetailView;

public class EditCustomerView extends AbstractDetailView<Customer> {

    private static final String REF_TEST_MODEL = "0";

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXT_BOX));
        DetailDataModel testModel = new DetailDataModel("testField", FieldTypeEnum.MULTI_SELECT_BOX);
        testModel.setReferenceDataId(REF_TEST_MODEL);
        listDataModel.add(testModel);
    }

    @Override
    protected void saveOrUpdate(Customer entity2) {
        ConfigProvider.getInstance().getCustomerSerice().save(entity2);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Customer entity) {
        super.setReferenceDataModel(refDataModel, entity);
        ReferenceDataList<String> refDataList = new ReferenceDataList<String>(Arrays.asList("Role1", "Role2", "Role3",
                "Role4", "Role5", "Role6"));
        refDataModel.putRefDataList(REF_TEST_MODEL, refDataList);
    }
}
