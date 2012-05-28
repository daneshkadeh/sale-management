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

package com.s3s.ssm.view.statistic.sales;

import java.util.List;

import javax.swing.JPanel;

import com.s3s.ssm.dto.sales.TopProductInMonthDTO;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.view.list.AListDataView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListTopProductInMonthStatistic extends AListDataView<TopProductInMonthDTO> {
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("goodsCode", ListRendererType.TEXT);
        listDataModel.addColumn("goodsName", ListRendererType.TEXT);
        listDataModel.addColumn("quotaQty", ListRendererType.NUMBER);
        listDataModel.addColumn("soldQty", ListRendererType.NUMBER);
        listDataModel.addColumn("rate", ListRendererType.NUMBER);
    }

    @Override
    protected List<TopProductInMonthDTO> loadData(int fistIndex, int maxResults) {
        return serviceProvider.getService(InvoiceService.class).statisticTopProductInMonth();
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void clearCriteria() {
        // TODO Auto-generated method stub

    }
}
