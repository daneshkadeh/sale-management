package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.param.Manufacturer;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditManufacturerView;

public class ListManufacturerView extends AbstractListView<Manufacturer> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<Manufacturer>> getDetailViewClass() {
        return EditManufacturerView.class;
    }

}
