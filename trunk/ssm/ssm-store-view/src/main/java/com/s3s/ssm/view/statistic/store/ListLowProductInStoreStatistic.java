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

package com.s3s.ssm.view.statistic.store;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.s3s.ssm.dto.store.LowProductInStoreDTO;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.list.AListDataView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListLowProductInStoreStatistic extends AListDataView<LowProductInStoreDTO> {
    private static final long serialVersionUID = -1244668202484079391L;
    private JComboBox<Store> cbStore;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("goodsCode", ListRendererType.TEXT);
        listDataModel.addColumn("goodsName", ListRendererType.TEXT);
        listDataModel.addColumn("storeName", ListRendererType.TEXT);
        listDataModel.addColumn("quotaQty", ListRendererType.TEXT);
        listDataModel.addColumn("actualQty", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("varianceQty", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH);
    }

    @Override
    protected List<LowProductInStoreDTO> loadData(int fistIndex, int maxResults) {
        return serviceProvider.getService(IStoreService.class).statisticLowProductInStore();
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
        List<Store> stores = serviceProvider.getService(IStoreService.class).getStores();
        stores.add(0, null);
        List<Product> products = serviceProvider.getService(ICatalogService.class).getListProducts();

        // JPanel panel = new JPanel(new MigLayout("ins 0, fill", "grow"));
        JPanel panel = new JPanel(new MigLayout("ins 0"));
        cbStore = new JComboBox(stores.toArray());
        panel.add(new JLabel(ControlConfigUtils.getString("label.ProductInStoreDTO.store")), "right");
        panel.add(cbStore, "grow, wrap");
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void clearCriteria() {
        cbStore.addItem(null);
    }
}
