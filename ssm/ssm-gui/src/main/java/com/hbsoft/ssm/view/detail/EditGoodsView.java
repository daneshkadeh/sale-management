package com.hbsoft.ssm.view.detail;

import java.util.Arrays;
import java.util.List;

import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.model.ReferenceDataModel;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.AbstractDetailView;

/**
 * The detail screen of {@link Goods} entity.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class EditGoodsView extends AbstractDetailView<Goods> {
    private static final long serialVersionUID = 8247461633468843994L;
    private static final String TAX_REF_ID = "0";

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel, Goods goods) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("name", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXT_BOX));
        DetailDataModel taxDataModel = new DetailDataModel("tax", FieldTypeEnum.COMBO_BOX);
        taxDataModel.setReferenceDataId(TAX_REF_ID);
        listDataModel.add(taxDataModel);
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("addQuantity", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("curQuantity", FieldTypeEnum.TEXT_BOX));
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Goods entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Double> taxRefs = Arrays.asList(1.0, 2.0, 3.0);
        refDataModel.putRefDataList(TAX_REF_ID, taxRefs);
    }

    @Override
    protected void saveOrUpdate(Goods goods) {
        ConfigProvider.getInstance().getGoodsService().save(goods);
    }
}
