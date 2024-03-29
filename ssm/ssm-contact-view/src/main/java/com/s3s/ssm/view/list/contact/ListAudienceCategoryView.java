package com.s3s.ssm.view.list.contact;

import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.view.detail.contact.EditAudienceCategoryView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListAudienceCategoryView extends ANonSearchListEntityView<AudienceCategory> {

    /**
     * 
     */
    private static final long serialVersionUID = 1858438355493942660L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<AudienceCategory>> getEditViewClass() {
        return EditAudienceCategoryView.class;
    }

}
