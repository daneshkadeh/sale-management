package com.s3s.ssm.view.list.contact;

import java.util.List;

import com.s3s.ssm.entity.contact.Contact;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;

public class ListContactView extends AbstractListView<Contact> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<Contact>> getDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
