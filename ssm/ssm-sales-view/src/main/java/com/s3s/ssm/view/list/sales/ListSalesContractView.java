/*
 * ListSalesContractView
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
package com.s3s.ssm.view.list.sales;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.swingx.JXDatePicker;

import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.entity.sales.SalesContractStatus;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.detail.sales.EditSalesContractView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListSalesContractView extends AListEntityView<SalesContract> {

    private JTextField contractCode;
    private JTextField supplierCode; // TODO: will be changed to searchComponent
    private JComboBox<SalesContractStatus> contractStatus;
    private JXDatePicker startDateContract;
    private JXDatePicker endDateContract;

    @Override
    protected JPanel createSearchPanel() {
        contractCode = new JTextField();
        supplierCode = new JTextField();
        // TODO: the null value should have a label. Should use an enum util to translate from enum to i18n.
        SalesContractStatus[] fullStatus = (SalesContractStatus[]) ArrayUtils
                .add(SalesContractStatus.values(), 0, null);
        contractStatus = new JComboBox<SalesContractStatus>(fullStatus);
        startDateContract = new JXDatePicker();
        endDateContract = new JXDatePicker();

        JPanel panel = new JPanel(new MigLayout("ins 0, fill", "grow"));
        panel.add(new JLabel(ControlConfigUtils.getString("label.SalesContract.code")), "right");
        panel.add(contractCode, "grow");

        panel.add(new JLabel(ControlConfigUtils.getString("label.SalesContract.dateContract") + " >"), "right");
        panel.add(startDateContract, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.SalesContract.dateContract") + " <"), "right");
        panel.add(endDateContract, "grow, wrap");

        panel.add(new JLabel(ControlConfigUtils.getString("label.SalesContract.supplier")), "right");
        panel.add(supplierCode, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.SalesContract.status")), "right");
        panel.add(contractStatus, "grow, wrap");

        return panel;
    }

    @Override
    protected void clearCriteria() {
        contractCode.setText(null);
        contractStatus.setSelectedItem(null);
        startDateContract.setDate(null);
        endDateContract.setDate(null);
        supplierCode.setText(null);

    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria criteria = super.getCriteriaForView();
        if (StringUtils.isNotBlank(contractCode.getText())) {
            criteria.add(Restrictions.ilike("code", contractCode.getText(), MatchMode.ANYWHERE));
        }
        if (contractStatus.getSelectedItem() != null) {
            criteria.add(Restrictions.eq("status", contractStatus.getSelectedItem()));
        }
        if (startDateContract.getDate() != null) {
            criteria.add(Restrictions.ge("dateContract", startDateContract.getDate()));
        }
        if (endDateContract.getDate() != null) {
            criteria.add(Restrictions.le("dateContract", endDateContract.getDate()));
        }
        if (StringUtils.isNotBlank(supplierCode.getText())) {
            criteria.createCriteria("supplier").add(Restrictions.eq("code", supplierCode.getText()));
        }
        return criteria;
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        // listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("supplier", ListRendererType.TEXT);
        listDataModel.addColumn("dateContract", ListRendererType.TEXT);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<SalesContract>> getEditViewClass() {
        return EditSalesContractView.class;
    }

}
