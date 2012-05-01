package com.s3s.ssm.view.list.sales;

import com.s3s.ssm.entity.sales.AddedSCFeeType;
import com.s3s.ssm.view.detail.sales.EditAddedSCFeeTypeView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListAddedSCFeeTypeView extends AListEntityView<AddedSCFeeType> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<AddedSCFeeType>> getEditViewClass() {
        return EditAddedSCFeeTypeView.class;
    }

}
