package com.hbsoft.ssm.view;

import java.util.List;

import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.object.DetailDataModel;
import com.hbsoft.ssm.view.object.FieldTypeEnum;

public class EditGoodsView extends AbstractDetailView<Goods> {
    private static final long serialVersionUID = 8247461633468843994L;

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("addQuantity", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("curQuantity", FieldTypeEnum.TEXT_BOX, true, true));
    }

    @Override
    protected void saveOrUpdate(Goods goods) {
        ConfigProvider.getInstance().getGoodsService().save(goods);
    }

    // @Override
    // protected Class<Goods> getEntityClass() {
    // return Goods.class;
    // }

}
