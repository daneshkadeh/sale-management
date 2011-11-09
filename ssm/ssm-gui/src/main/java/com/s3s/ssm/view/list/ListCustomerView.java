package com.s3s.ssm.view.list;

import java.util.List;

import com.s3s.ssm.entity.CustomerTest;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.EditCustomerView;

public class ListCustomerView extends AbstractListView<CustomerTest> {
    private static final long serialVersionUID = -8565742036667887785L;

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("id", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected List<CustomerTest> loadData() {
        return getDaoHelper().getDao(CustomerTest.class).findAll();
    }

    @Override
    protected Class<? extends AbstractDetailView<CustomerTest>> getDetailViewClass() {
        return EditCustomerView.class;
    }
}
