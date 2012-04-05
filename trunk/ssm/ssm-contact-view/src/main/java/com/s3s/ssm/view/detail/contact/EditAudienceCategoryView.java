package com.s3s.ssm.view.detail.contact;

import java.util.Map;

import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditAudienceCategoryView extends AbstractSingleEditView<AudienceCategory> {

    /**
     * 
     */
    private static final long serialVersionUID = 5888349885055311292L;

    public EditAudienceCategoryView(Map<String, Object> request) {
        super(request);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, AudienceCategory entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
    }

}
