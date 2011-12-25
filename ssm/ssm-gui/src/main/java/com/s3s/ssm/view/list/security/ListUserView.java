package com.s3s.ssm.view.list.security;

import java.util.List;

import com.s3s.ssm.entity.security.User;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.security.EditUserView;

public class ListUserView extends AbstractListView<User> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
    	listDataModel.add(new DetailAttribute("username", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("isEnabled", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<User>> getDetailViewClass() {
    	return EditUserView.class;
    }

}