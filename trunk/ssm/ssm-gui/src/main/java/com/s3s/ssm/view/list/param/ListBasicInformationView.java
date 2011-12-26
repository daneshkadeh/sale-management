package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.config.BasicInformation;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditBasicInformationView;

public class ListBasicInformationView extends AbstractListView<BasicInformation> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("companyName", FieldTypeEnum.TEXTBOX));

    }

    @Override
    protected Class<? extends AbstractDetailView<BasicInformation>> getDetailViewClass() {
        return EditBasicInformationView.class;
    }

}
