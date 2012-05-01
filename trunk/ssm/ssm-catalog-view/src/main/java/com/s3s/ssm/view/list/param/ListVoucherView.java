package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.view.detail.param.EditVoucherView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListVoucherView extends AListEntityView<Voucher> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("type", ListRendererType.TEXT);
        listDataModel.addColumn("description", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Voucher>> getEditViewClass() {
        return EditVoucherView.class;
    }

}
