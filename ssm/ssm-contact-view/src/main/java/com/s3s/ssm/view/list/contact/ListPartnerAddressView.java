package com.s3s.ssm.view.list.contact;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.contact.PartnerAddressLink;
import com.s3s.ssm.view.detail.contact.EditPartnerAddressView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * This view shows list addresses of a partner
 * 
 * @author phamcongbang
 * 
 */
public class ListPartnerAddressView extends AListEntityView<PartnerAddressLink> {

    /**
     * 
     */
    private static final long serialVersionUID = 4986549916138130505L;

    public ListPartnerAddressView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("address.name", ListRendererType.TEXT);
        listDataModel.addColumn("address.address", ListRendererType.TEXT);
        listDataModel.addColumn("address.district", ListRendererType.TEXT);
        listDataModel.addColumn("address.city", ListRendererType.TEXT);
        listDataModel.addColumn("address.postalCode", ListRendererType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<PartnerAddressLink>> getEditViewClass() {
        return EditPartnerAddressView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.add(Restrictions.eq("partner", getDaoHelper().getDao(getParentClass()).findById(getParentId())));
        return dc;
    }
}
