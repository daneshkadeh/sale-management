package com.s3s.ssm.view.detail.store;

import java.util.List;

import com.s3s.ssm.entity.store.DetailExchangeStore;
import com.s3s.ssm.entity.store.ExchangeStoreForm;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractMasterDetailView;

public class EditExchangeStoreFormView extends AbstractMasterDetailView<ExchangeStoreForm, DetailExchangeStore> {

    public EditExchangeStoreFormView(ExchangeStoreForm entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<DetailExchangeStore>> getChildDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getChildFieldName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void saveOrUpdate(ExchangeStoreForm masterEntity, List<DetailExchangeStore> detailEntities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ExchangeStoreForm entity) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void addDetailIntoMaster(ExchangeStoreForm masterEntity, DetailExchangeStore detailEntity) {
        // TODO Auto-generated method stub

    }

}