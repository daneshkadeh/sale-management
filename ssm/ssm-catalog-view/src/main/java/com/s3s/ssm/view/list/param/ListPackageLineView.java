package com.s3s.ssm.view.list.param;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.view.detail.param.EditPackageLineView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPackageLineView extends AbstractListView<PackageLine> {

    public ListPackageLineView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("product", ListRendererType.TEXT);
        listDataModel.addColumn("minItemAmount", ListRendererType.TEXT);
        listDataModel.addColumn("maxItemAmount", ListRendererType.TEXT);
        listDataModel.addColumn("isAllItem", ListRendererType.BOOLEAN);
        listDataModel.addColumn("optional", ListRendererType.BOOLEAN);
    }

    @Override
    protected Class<? extends AbstractEditView<PackageLine>> getEditViewClass() {
        return EditPackageLineView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.add(Restrictions.eq("package", getDaoHelper().getDao(getParentClass()).findById(getParentId())));
        return dc;
    }

}
