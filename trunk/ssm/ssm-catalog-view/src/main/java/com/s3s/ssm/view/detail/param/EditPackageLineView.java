package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.PackageLineItemPrice;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.list.ListDataModel;

public class EditPackageLineView extends AbstractMasterDetailView<PackageLine, PackageLineItemPrice> {

    public EditPackageLineView(Map<String, Object> entity) {
        super(entity);

    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractEditView<PackageLineItemPrice>> getChildDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getChildFieldName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void addDetailIntoMaster(PackageLine masterEntity, PackageLineItemPrice detailEntity) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, PackageLine entity,
            Map<String, Object> request) {
        // TODO Auto-generated method stub

    }

}
