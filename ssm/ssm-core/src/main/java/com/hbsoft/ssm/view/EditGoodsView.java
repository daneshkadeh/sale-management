package com.hbsoft.ssm.view;

import java.util.List;

import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.util.i18n.ControlConfiguration;
import com.hbsoft.ssm.view.object.DetailDataModel;
import com.hbsoft.ssm.view.object.FieldTypeEnum;

public class EditGoodsView extends AbstractDetailView<Goods> {
    private static final long serialVersionUID = 8247461633468843994L;

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.id"), "id",
                FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.name"), "name",
                FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.priceBeforeTax"),
                "priceBeforeTax", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.tax"), "tax",
                FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.priceAfterTax"),
                "priceAfterTax", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.addQuantity"),
                "addQuantity", FieldTypeEnum.TEXT_BOX, true, true));
        listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.curQuantity"),
                "curQuantity", FieldTypeEnum.TEXT_BOX, true, true));
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
