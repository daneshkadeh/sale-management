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

import com.s3s.ssm.dto.sales.UnsoldProductByDayDTO;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.view.list.AListDataView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListUnsoldProductByDayStatistic extends AListDataView<UnsoldProductByDayDTO> {
    private static final long serialVersionUID = -2880770791519071704L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("goodsCode", ListRendererType.TEXT);
        listDataModel.addColumn("goodsName", ListRendererType.TEXT);
        listDataModel.addColumn("latestSellDate", ListRendererType.DATE);
        listDataModel.addColumn("unsoldDayNum", ListRendererType.NUMBER);
        listDataModel.addColumn("mustSoldPeriod", ListRendererType.NUMBER);
    }

    @Override
    protected List<UnsoldProductByDayDTO> loadData(int fistIndex, int maxResults) {
        return serviceProvider.getService(InvoiceService.class).statisticUnsoldProductByDay();
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
