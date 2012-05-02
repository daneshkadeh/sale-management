/*
 * ListGroupExportStoreDataView
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

package com.s3s.ssm.view.list.report;

import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import com.s3s.ssm.dto.store.GroupDetailExportData;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.view.list.AListDataView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Phan Hong Phuc
 * @since May 1, 2012
 */
public class ListGroupExportStoreDataView extends AListDataView<GroupDetailExportData> {
    private static final long serialVersionUID = -496470452725423039L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("product.name", ListRendererType.TEXT);
        listDataModel.addColumn("item.sumUomName", ListRendererType.TEXT);
        listDataModel.addColumn("quantity", ListRendererType.TEXT);
    }

    @Override
    protected List<GroupDetailExportData> loadData(int fistIndex, int maxResults) {
        // return serviceProvider.getService(IStoreService.class).get...();
        GroupDetailExportData g1 = new GroupDetailExportData();
        Product p1 = new Product();
        p1.setName("product 1");
        Item i1 = new Item();
        i1.setSumUomName("sumUomName 1");
        g1.setProduct(p1);
        g1.setItem(i1);
        g1.setQuantity(1);

        GroupDetailExportData g2 = new GroupDetailExportData();
        Product p2 = new Product();
        p2.setName("product 2");
        Item i2 = new Item();
        i2.setSumUomName("sumUomName 2");
        g2.setProduct(p2);
        g2.setItem(i2);
        g2.setQuantity(1);

        return Arrays.asList(g1, g2);

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
