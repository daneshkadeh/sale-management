package com.s3s.ssm.view.list.param;

import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.view.detail.param.EditVoucherView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListVoucherView extends AbstractListView<Voucher> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("name", ListColumnType.TEXT);
        listDataModel.addColumn("type", ListColumnType.TEXT);
        listDataModel.addColumn("manufacturer", ListColumnType.TEXT);
        listDataModel.addColumn("model", ListColumnType.TEXT);
        listDataModel.addColumn("description", ListColumnType.TEXT);
        listDataModel.addColumn("mainUom", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Voucher>> getEditViewClass() {
        return EditVoucherView.class;
    }

}
