package com.s3s.ssm.view.detail.security;

import com.s3s.ssm.entity.security.User;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditUserView extends AbstractDetailView<User> {
    private static final long serialVersionUID = 1L;

    public EditUserView(User entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, User entity) {
        detailDataModel.addAttribute("username", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("password", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isAccountNonExpired", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isAccountNonLocked", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isCredentialsNonExpired", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isEnabled", FieldTypeEnum.TEXTBOX);
    }

}
