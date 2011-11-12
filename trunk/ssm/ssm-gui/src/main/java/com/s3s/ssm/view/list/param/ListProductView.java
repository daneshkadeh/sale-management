package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.param.Product;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditProductView;

public class ListProductView extends AbstractListView<Product> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("type", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("manufacturer", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("model", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("description", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("mainUom", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<Product>> getDetailViewClass() {
        return EditProductView.class;
    }

}
