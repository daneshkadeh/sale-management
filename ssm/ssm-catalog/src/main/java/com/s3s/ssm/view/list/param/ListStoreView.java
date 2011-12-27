package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.Store;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditStoreView;

public class ListStoreView extends AbstractListView<Store> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("address", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("storedAddress", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("importAddress", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("exportAddress", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("managerCode", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<Store>> getDetailViewClass() {
        return EditStoreView.class;
    }

}
