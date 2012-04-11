package com.s3s.ssm.view.list.param;

import java.awt.Dialog;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.view.component.EntityDialog;
import com.s3s.ssm.view.detail.param.EditPackageLineView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPackageLineView extends AbstractListView<PackageLine> {

    public static final String PRODUCT_OF_PACKAGE_LINE = "PRODUCT_OF_PACKAGE_LINE";

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

    @Override
    protected boolean preShowEditView(PackageLine entity, EditActionEnum action, Map<String, Object> detailParams) {
        super.preShowEditView(entity, action, detailParams);
        if (action == EditActionEnum.NEW) {
            List<Product> products = daoHelper.getDao(Product.class).findAll();
            EntityDialog<Product> entityDialog = new EntityDialog<Product>(null, Dialog.ModalityType.APPLICATION_MODAL,
                    products);
            entityDialog.setLocationRelativeTo(SwingUtilities.getRootPane(this));
            entityDialog.setVisible(true);
            Product selectedProduct = null;
            if (entityDialog.isPressedOK() == 1) {
                selectedProduct = (Product) entityDialog.getSelectedEntity();
                detailParams.put(PRODUCT_OF_PACKAGE_LINE, selectedProduct);
            } else {
                return false;
            }
        }
        return true;
    }
}
