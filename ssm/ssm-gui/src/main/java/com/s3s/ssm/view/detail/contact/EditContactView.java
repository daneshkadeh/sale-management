package com.s3s.ssm.view.detail.contact;

import java.util.List;

import com.s3s.ssm.entity.contact.Contact;
import com.s3s.ssm.entity.contact.ContactShop;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractMasterDetailView;

public class EditContactView extends AbstractMasterDetailView<Contact, ContactShop> {

    public EditContactView(Contact entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<ContactShop>> getChildDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getChildFieldName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void saveOrUpdate(Contact masterEntity, List<ContactShop> detailEntities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Contact entity) {
        // TODO Auto-generated method stub

    }

}
