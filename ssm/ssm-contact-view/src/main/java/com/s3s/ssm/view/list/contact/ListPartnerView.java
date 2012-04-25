package com.s3s.ssm.view.list.contact;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPartnerView<T extends Partner> extends AbstractListView<T> {

    /**
     * 
     */
    private static final long serialVersionUID = 3639982113208110252L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("phone", ListRendererType.TEXT);
        listDataModel.addColumn("fax", ListRendererType.TEXT);
        listDataModel.addColumn("email", ListRendererType.TEXT);
        listDataModel.addColumn("website", ListRendererType.TEXT);
        listDataModel.addColumn("active", ListRendererType.BOOLEAN);
    }

    @Override
    protected Class<? extends AbstractEditView<T>> getEditViewClass() {
        return null;
    }

}
