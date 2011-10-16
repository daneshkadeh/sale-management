package com.hbsoft.ssm.view.list;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.math.RandomUtils;

import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.view.AbstractDetailView;
import com.hbsoft.ssm.view.AbstractSearchListView;
import com.hbsoft.ssm.view.detail.EditGoodsView;

/**
 * The common list screen of {@link Goods} entity.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class ListGoodsView extends AbstractSearchListView<Goods> {
    private static final long serialVersionUID = -8034885109793508234L;

    private JTextField txtGoodNameCriteria;

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

    @Override
    protected JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        txtGoodNameCriteria = new JTextField();
        panel.add(txtGoodNameCriteria);
        return panel;
    }

    @Override
    protected void clearCriteria() {
        // TODO Auto-generated method stub

    }
}
