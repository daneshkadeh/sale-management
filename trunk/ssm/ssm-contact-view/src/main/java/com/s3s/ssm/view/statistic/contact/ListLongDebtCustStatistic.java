/*
 * ListImportStoreReportDataView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.statistic.contact;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.s3s.ssm.dto.contact.LongDebtCustDTO;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.interfaces.contact.IContactService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.list.AListDataView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListLongDebtCustStatistic extends AListDataView<LongDebtCustDTO> {

    private static final long serialVersionUID = -3980949070617308236L;
    private JComboBox<Partner> cbCustomer;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("custCode", ListRendererType.TEXT);
        listDataModel.addColumn("custName", ListRendererType.TEXT);
        listDataModel.addColumn("quotaAmt", ListRendererType.TEXT);
        listDataModel.addColumn("debtAmt", ListRendererType.TEXT);
        listDataModel.addColumn("remainingAmt", ListRendererType.TEXT);
    }

    @Override
    protected List<LongDebtCustDTO> loadData(int fistIndex, int maxResults) {
        Partner selCustomer = (Partner) cbCustomer.getSelectedItem();
        return serviceProvider.getService(IContactService.class).statisticLongDebtCust(selCustomer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int calculateTotalPages() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JPanel createSearchPanel() {
        List<Partner> customers = serviceProvider.getService(IContactService.class).getCustomers();
        customers.add(0, null);

        JPanel panel = new JPanel(new MigLayout("ins 0, fill"));
        cbCustomer = new JComboBox(customers.toArray());

        panel.add(new JLabel(ControlConfigUtils.getString("label.LongDebtCustDTO.customer")), "left");
        panel.add(cbCustomer, "grow");

        return panel;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void clearCriteria() {
        cbCustomer.setSelectedIndex(0);
    }
}
