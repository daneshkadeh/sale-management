package com.s3s.ssm.view.list.sales;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.sales.PaymentSC;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.view.detail.sales.EditPaymentSCView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPaymentSCOfContract extends ANonSearchListEntityView<PaymentSC> {
    public ListPaymentSCOfContract(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("type", ListRendererType.TEXT);
        listDataModel.addColumn("amount", ListRendererType.TEXT);
        listDataModel.addColumn("bank", ListRendererType.TEXT);
        listDataModel.addColumn("referenceCode", ListRendererType.TEXT);
        listDataModel.addColumn("remark", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<PaymentSC>> getEditViewClass() {
        return EditPaymentSCView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.add(Restrictions.eq("salesContract", (SalesContract) getParentObject()));
        return dc;
    }
}
