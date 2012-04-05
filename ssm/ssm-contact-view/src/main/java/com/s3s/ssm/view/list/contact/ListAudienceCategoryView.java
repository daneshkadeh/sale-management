package com.s3s.ssm.view.list.contact;

import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.view.detail.contact.EditAudienceCategoryView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListAudienceCategoryView extends AbstractListView<AudienceCategory> {

    /**
     * 
     */
    private static final long serialVersionUID = 1858438355493942660L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("name", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<AudienceCategory>> getEditViewClass() {
        return EditAudienceCategoryView.class;
    }

}
