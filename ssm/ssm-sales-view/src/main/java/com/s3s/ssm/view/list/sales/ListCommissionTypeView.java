package com.s3s.ssm.view.list.sales;

import com.s3s.ssm.entity.sales.CommissionType;
import com.s3s.ssm.view.detail.sales.EditCommissionTypeView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListCommissionTypeView extends AListEntityView<CommissionType> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("commissionMethod", ListRendererType.TEXT);
        listDataModel.addColumn("percent", ListRendererType.TEXT);
        listDataModel.addColumn("commissionMoney", ListRendererType.TEXT);
        listDataModel.addColumn("active", ListRendererType.BOOLEAN);

    }

    @Override
    protected Class<? extends AbstractEditView<CommissionType>> getEditViewClass() {
        return EditCommissionTypeView.class;
    }
}
