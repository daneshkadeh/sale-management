package com.s3s.ssm.view.list.sales;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.sales.AddedSCFee;
import com.s3s.ssm.entity.sales.ImportationSC;
import com.s3s.ssm.view.detail.sales.EditAddedSCFeeView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * This list inside EditImporationSCMultiView
 * 
 * @author phamcongbang
 * 
 */
public class ListAddedSCFeeView extends AListEntityView<AddedSCFee> {

    public ListAddedSCFeeView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("type", ListRendererType.TEXT);
        listDataModel.addColumn("referenceNumber", ListRendererType.TEXT);
        listDataModel.addColumn("serviceProvider", ListRendererType.TEXT);
        listDataModel.addColumn("basePrice", ListRendererType.TEXT);
        listDataModel.addColumn("unitPrice", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.add(Restrictions.eq("importationSC", (ImportationSC) getParentObject()));
        return dc;
    }

    @Override
    protected Class<? extends AbstractEditView<AddedSCFee>> getEditViewClass() {
        return EditAddedSCFeeView.class;
    }

}
