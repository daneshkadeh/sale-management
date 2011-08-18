package com.hbsoft.ssm.view;

import java.util.List;

import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.util.i18n.ControlConfiguration;
import com.hbsoft.ssm.view.object.DetailDataModel;
import com.hbsoft.ssm.view.object.FieldType;

public class ListGoodsView<Goods> extends AbstractListView<Goods> {
	// private GoodsService goodsService = ConfigProvider.getInstance().getGoodsService();
	private static final long serialVersionUID = -8034885109793508234L;

	@Override
	protected void initialPresentationView(List<DetailDataModel> listDataModel) {
		listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.id"), "id",
				FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.name"), "name",
				FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.priceBeforeTax"),
				"priceBeforeTax", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.tax"), "tax",
				FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.priceAfterTax"),
				"priceAfterTax", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.addQuantity"),
				"addQuantity", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel(ControlConfiguration.getText("label.EditGoodsView.curQuantity"),
				"curQuantity", FieldType.TEXT_BOX, true, true));
	}

	@Override
	protected List loadData() {
		return ConfigProvider.getInstance().getGoodsService().findAll();
	}

	// @Override
	// protected Class<com.hbsoft.ssm.entity.Goods> getCommandClass() {
	// return com.hbsoft.ssm.entity.Goods.class;
	// }
}
