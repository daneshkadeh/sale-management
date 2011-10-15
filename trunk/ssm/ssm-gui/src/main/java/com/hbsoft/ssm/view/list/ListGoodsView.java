package com.hbsoft.ssm.view.list;

import java.util.List;

import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.view.AbstractDetailView;
import com.hbsoft.ssm.view.AbstractListView;
import com.hbsoft.ssm.view.detail.EditGoodsView;

/**
 * The common list screen of {@link Goods} entity.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class ListGoodsView extends AbstractListView<Goods> {
    private static final long serialVersionUID = -8034885109793508234L;

    @Override
    protected void initialPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("addQuantity", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("curQuantity", FieldTypeEnum.TEXT_BOX));
    }

    @Override
    protected List<Goods> loadData() {
        return getDaoHelper().getDao(Goods.class).findAll();
    }

    @Override
    protected Class<? extends AbstractDetailView<Goods>> getDetailViewClass() {
        return EditGoodsView.class;
    }
}
