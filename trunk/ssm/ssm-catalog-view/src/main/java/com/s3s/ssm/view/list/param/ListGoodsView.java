/*
 * ListProductView
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
package com.s3s.ssm.view.list.param;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.Goods;
import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.entity.catalog.ProductType;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.detail.param.EditGoodsView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListGoodsView extends AListEntityView<Goods> {

    private JTextField codeField;
    private JTextField nameField;
    private JTextField modelField;
    private JComboBox<ProductType> typeField;
    private JComboBox<Manufacturer> manufacturerField; // TODO: change to search component if there are a lot.

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("type", ListRendererType.TEXT);
        listDataModel.addColumn("manufacturer", ListRendererType.TEXT);
        listDataModel.addColumn("model", ListRendererType.TEXT);
        listDataModel.addColumn("description", ListRendererType.TEXT);
        listDataModel.addColumn("mainUom", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Goods>> getEditViewClass() {
        return EditGoodsView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        if (StringUtils.isNotBlank(codeField.getText())) {
            dc.add(Restrictions.ilike("code", codeField.getText(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(nameField.getText())) {
            dc.add(Restrictions.ilike("name", nameField.getText(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(modelField.getText())) {
            dc.add(Restrictions.ilike("model", modelField.getText(), MatchMode.ANYWHERE));
        }
        if (typeField.getSelectedItem() != null) {
            dc.add(Restrictions.eq("type", typeField.getSelectedItem()));
        }
        if (manufacturerField.getSelectedItem() != null) {
            dc.add(Restrictions.eq("manufacturer", manufacturerField.getSelectedItem()));
        }

        return dc;
    }

    @Override
    protected JPanel createSearchPanel() {
        codeField = new JTextField();
        nameField = new JTextField();
        modelField = new JTextField();
        List<ProductType> productTypes = getDaoHelper().getDao(ProductType.class).findAll();
        productTypes.add(0, null);
        typeField = new JComboBox<ProductType>(productTypes.toArray(new ProductType[productTypes.size()]));

        List<Manufacturer> manufacturers = getDaoHelper().getDao(Manufacturer.class).findAll();
        manufacturers.add(0, null);
        manufacturerField = new JComboBox<Manufacturer>(manufacturers.toArray(new Manufacturer[manufacturers.size()]));

        JPanel panel = new JPanel(new MigLayout("ins 0, fill", "grow"));
        panel.add(new JLabel(ControlConfigUtils.getString("label.Goods.code")), "right");
        panel.add(codeField, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Goods.name")), "right");
        panel.add(nameField, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Goods.model")), "right");
        panel.add(modelField, "grow, wrap");

        panel.add(new JLabel(ControlConfigUtils.getString("label.Goods.type")), "right");
        panel.add(typeField, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Goods.manufacturer")), "right");
        panel.add(manufacturerField, "grow");

        return panel;
    }

    @Override
    protected void clearCriteria() {
        codeField.setText(null);
        nameField.setText(null);
        modelField.setText(null);
        typeField.setSelectedItem(null);
        manufacturerField.setSelectedItem(null);
    }

}
