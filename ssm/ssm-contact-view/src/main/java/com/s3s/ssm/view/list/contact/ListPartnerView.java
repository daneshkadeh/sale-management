package com.s3s.ssm.view.list.contact;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListPartnerView<T extends Partner> extends AbstractListView<T> {

    /**
     * 
     */
    private static final long serialVersionUID = 3639982113208110252L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("name", ListColumnType.TEXT);
        listDataModel.addColumn("phone", ListColumnType.TEXT);
        listDataModel.addColumn("fax", ListColumnType.TEXT);
        listDataModel.addColumn("email", ListColumnType.TEXT);
        listDataModel.addColumn("website", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<T>> getEditViewClass() {
        return null;
    }

}
