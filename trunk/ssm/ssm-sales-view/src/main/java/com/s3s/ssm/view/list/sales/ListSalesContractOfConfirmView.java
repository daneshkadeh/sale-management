package com.s3s.ssm.view.list.sales;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.view.detail.sales.EditSalesContractView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * This is child view of EditSalesConfirmMultiView
 * 
 * @author phamcongbang
 * 
 */
public class ListSalesContractOfConfirmView extends AListEntityView<SalesContract> {

    public ListSalesContractOfConfirmView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("supplier", ListRendererType.TEXT);
        listDataModel.addColumn("dateContract", ListRendererType.TEXT);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<SalesContract>> getEditViewClass() {
        return EditSalesContractView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.add(Restrictions.eq("salesConfirm", getParentObject()));
        return dc;
    }
}
