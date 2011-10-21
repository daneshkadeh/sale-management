package com.s3s.ssm.view.detail;

import java.util.Arrays;
import java.util.List;

import com.s3s.ssm.entity.Customer;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditCustomerView extends AbstractDetailView<Customer> {
    private static final long serialVersionUID = 2202186074068854009L;
    private static final String REF_TEST_MODEL = "0";

    public EditCustomerView(Customer entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel, Customer customer) {
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXTBOX));
        // DetailDataModel testModel = new DetailDataModel("testField", FieldTypeEnum.MULTI_SELECT_BOX);
        // testModel.setReferenceDataId(REF_TEST_MODEL);
        // listDataModel.add(testModel);
    }

    @Override
    protected void saveOrUpdate(Customer entity) {
        getDaoHelper().getDao(Customer.class).saveOrUpdate(entity);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Customer entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<String> refDataList = Arrays.asList("Role1", "Role2", "Role3", "Role4", "Role5", "Role6");
        refDataModel.putRefDataList(REF_TEST_MODEL, refDataModel.new ReferenceData(refDataList));
    }
}
