package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditVoucherView;

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
