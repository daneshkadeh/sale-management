/*
 * ListGoodsViewTest
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view.list;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.GoodsTest;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractSearchListView;
import com.s3s.ssm.view.detail.EditGoodsViewTest;

/**
 * The common list screen of {@link GoodsTest} entity.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class ListGoodsViewTest extends AbstractSearchListView<GoodsTest> {
    private static final long serialVersionUID = -8034885109793508234L;

    private JTextField txtGoodNameCriteria;

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("id", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("tax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("addQuantity", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("curQuantity", FieldTypeEnum.TEXTBOX));
        summaryFieldNames.add("tax");
        summaryFieldNames.add("curQuantity");
    }

    @Override
    protected List<GoodsTest> loadData(int pageNumber) {
        DetachedCriteria dc = getDaoHelper().getDao(GoodsTest.class).getCriteria();
        dc.add(Restrictions.like("name", txtGoodNameCriteria.getText(), MatchMode.ANYWHERE));
        return getDaoHelper().getDao(GoodsTest.class).findByCriteria(dc);
    }

    @Override
    protected Class<? extends AbstractEditView<GoodsTest>> getEditViewClass() {
        return EditGoodsViewTest.class;
    }

    @Override
    protected JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        txtGoodNameCriteria = new JTextField(20);
        panel.add(new JLabel("Good name"));
        panel.add(txtGoodNameCriteria);
        return panel;
    }

    @Override
    protected void clearCriteria() {
        txtGoodNameCriteria.setText(StringUtils.EMPTY);
    }
}