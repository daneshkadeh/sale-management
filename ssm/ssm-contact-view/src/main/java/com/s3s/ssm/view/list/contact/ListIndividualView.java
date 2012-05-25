package com.s3s.ssm.view.list.contact;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.contact.Individual;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.view.detail.contact.EditIndividualView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * This list shows individual of a Partner
 * 
 * @author phamcongbang
 * 
 */
public class ListIndividualView extends AListEntityView<Individual> {

    public ListIndividualView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("title", ListRendererType.TEXT);
        listDataModel.addColumn("fullName", ListRendererType.TEXT);
        listDataModel.addColumn("position", ListRendererType.TEXT);
        listDataModel.addColumn("role", ListRendererType.TEXT);
    }

    @Override
    protected List<Individual> loadData(int firstIndex, int maxResults) {
        DetachedCriteria dc = getDaoHelper().getDao(Individual.class).getCriteria();
        dc.add(Restrictions.eq("partner", (Partner) getParentObject()));
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getDaoHelper().getDao(Individual.class).findByCriteria(dc, firstIndex, maxResults);
    }

    @Override
    protected Class<? extends AbstractEditView<Individual>> getEditViewClass() {
        return EditIndividualView.class;
    }

}
