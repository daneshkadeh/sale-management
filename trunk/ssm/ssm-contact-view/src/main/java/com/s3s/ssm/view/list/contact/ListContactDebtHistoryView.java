package com.s3s.ssm.view.list.contact;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.contact.ContactDebtHistory;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * This is a child view of EditPartnerView
 * 
 * @author phamcongbang
 * 
 */
public class ListContactDebtHistoryView extends ANonSearchListEntityView<ContactDebtHistory> {
    private static final long serialVersionUID = 2097337075091174071L;

    public ListContactDebtHistoryView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("startDate", ListRendererType.DATE);
        listDataModel.addColumn("startDebtAmount", ListRendererType.TEXT);

        listDataModel.addColumn("endDate", ListRendererType.DATE);
        listDataModel.addColumn("endDebtAmount", ListRendererType.TEXT);

        listDataModel.addColumn("paidAmount", ListRendererType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<ContactDebtHistory>> getEditViewClass() {
        return null;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.add(Restrictions.eq("partner", (Partner) getParentObject()));
        return dc;
    }
}
