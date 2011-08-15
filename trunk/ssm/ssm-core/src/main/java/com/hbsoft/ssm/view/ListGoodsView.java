package com.hbsoft.ssm.view;

import java.util.List;
import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.service.GoodsService;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.object.DetailDataModel;
import com.hbsoft.ssm.view.object.FieldType;

public class ListGoodsView<Goods> extends AbstractListView<Goods> {
//	private GoodsService goodsService = ConfigProvider.getInstance().getGoodsService();
	
	@Override
	protected void initialPresentationView(List<DetailDataModel> listDataModel) {
		listDataModel.add(new DetailDataModel("Goods Id", "id", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("Goods name", "name", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("PriceBT", "priceBeforeTax", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("Tax", "tax", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("PriceAT", "priceAfterTax", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("AddQuan", "addQuantity", FieldType.TEXT_BOX, true, true));
		listDataModel.add(new DetailDataModel("CurQuan", "curQuantity", FieldType.TEXT_BOX, true, true));
	}
	
	@Override
	protected List loadData() {
		return ConfigProvider.getInstance().getGoodsService().findAll();
	}
	
//	@Override
//	protected Class<com.hbsoft.ssm.entity.Goods> getCommandClass() {
//		return com.hbsoft.ssm.entity.Goods.class;
//	}
}
