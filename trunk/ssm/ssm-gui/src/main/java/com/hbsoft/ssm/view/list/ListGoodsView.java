package com.hbsoft.ssm.view.list;

import java.util.List;

import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.AbstractCommonListView;

/**
 * The common list screen of {@link Goods} entity.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class ListGoodsView extends AbstractCommonListView<Goods> {
    private static final long serialVersionUID = -8034885109793508234L;

    @Override
    protected void initialPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("addQuantity", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel("curQuantity", FieldTypeEnum.TEXT_BOX, true, true));
    }

    @Override
    protected List<Goods> loadData() {
        return ConfigProvider.getInstance().getGoodsService().findAll();
    }
}
