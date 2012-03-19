package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.view.detail.param.EditVoucherView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AbstractListView;

public class ListVoucherView extends AbstractListView<Voucher> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("type", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("manufacturer", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("model", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("description", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("mainUom", DetailFieldType.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<Voucher>> getEditViewClass() {
        return EditVoucherView.class;
    }

}
