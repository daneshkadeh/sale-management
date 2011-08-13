package com.hbsoft.ssm.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.object.DetailDataModel;
import com.hbsoft.ssm.view.object.FieldType;

public class EditGoodsView<goods> extends AbstractDetailView<Goods> {

	@Override
	public void initialPresentationView(List<DetailDataModel> listDataModel) {
		listDataModel.add(new DetailDataModel("Goods Id", "id", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("Goods name", "name", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("PriceBT", "priceBeforeTax", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("Tax", "tax", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("PriceAT", "priceAfterTax", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("AddQuan", "addQuantity", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("CurQuan", "curQuantity", FieldType.TEXT_BOX, true, true));
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
