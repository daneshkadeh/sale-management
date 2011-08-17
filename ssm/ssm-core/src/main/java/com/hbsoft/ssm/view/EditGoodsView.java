package com.hbsoft.ssm.view;

import java.util.List;

import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.util.i18n.ControlConfiguration;
import com.hbsoft.ssm.view.object.DetailDataModel;
import com.hbsoft.ssm.view.object.FieldType;

public class EditGoodsView<goods> extends AbstractDetailView<Goods> {

	@Override
	public void initialPresentationView(List<DetailDataModel> listDataModel) {
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
	protected void saveOrUpdate(Goods entity2) {
		ConfigProvider.getInstance().getGoodsService().save(entity2);
	}

	@Override
	protected Class<Goods> getCommandClass() {
		return Goods.class;
	}

}
