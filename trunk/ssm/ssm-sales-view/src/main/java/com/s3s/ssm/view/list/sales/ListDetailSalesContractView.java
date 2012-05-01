package com.s3s.ssm.view.list.sales;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.sales.DetailSalesContract;
import com.s3s.ssm.view.detail.sales.EditDetailSalesContractView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * List details of a Contract. This list should be editable (but not available now because of missing component).
 * 
 * @author phamcongbang
 * 
 */
public class ListDetailSalesContractView extends AListEntityView<DetailSalesContract> {

    public ListDetailSalesContractView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("product", ListRendererType.TEXT);
        listDataModel.addColumn("stockCode", ListRendererType.TEXT, ListEditorType.TEXTFIELD);
        listDataModel.addColumn("description", ListRendererType.TEXT, ListEditorType.TEXTFIELD);
        listDataModel.addColumn("quantity", ListRendererType.TEXT, ListEditorType.TEXTFIELD);
        listDataModel.addColumn("unitPrice", ListRendererType.TEXT, ListEditorType.TEXTFIELD);
        listDataModel.addColumn("totalPrice", ListRendererType.TEXT, ListEditorType.TEXTFIELD);
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.add(Restrictions.eq("salesContract", getParentObject()));
        return dc;
    }

    @Override
    protected Class<? extends AbstractEditView<DetailSalesContract>> getEditViewClass() {
        return EditDetailSalesContractView.class;
    }

}
