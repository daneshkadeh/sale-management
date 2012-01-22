package com.s3s.ssm.view.detail.security;

import java.util.List;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.entity.security.User;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditUserView extends AbstractDetailView<User> {
    private static final long serialVersionUID = 1L;
    private static final String ROLE_REF_ID = "1";

    public EditUserView(User entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, User entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("username", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("password", FieldTypeEnum.PASSWORD).setMandatory(true);
        detailDataModel.addAttribute("roles", FieldTypeEnum.MULTI_SELECT_BOX).referenceDataId(ROLE_REF_ID);
        detailDataModel.addAttribute("isEnabled", FieldTypeEnum.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, User entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Role> roleList = getDaoHelper().getDao(Role.class).findAll();
        refDataModel.putRefDataList(ROLE_REF_ID,
                refDataModel.new ReferenceData(roleList, new DefaultListCellRenderer()));
    }
}
