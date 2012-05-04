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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import com.s3s.ssm.dto.store.UnsoldProductDTO;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.component.MultiSelectionListBox;
import com.s3s.ssm.view.list.AListDataView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListUnsoldProductStatisticView extends AListDataView<UnsoldProductDTO> {
    private static final long serialVersionUID = -1244668202484079391L;
    private MultiSelectionListBox mulStListBox;
    private JXDatePicker fromDateComp;
    private JXDatePicker toDateComp;
    private JComboBox<Store> cbStore;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("productCode", ListRendererType.TEXT);
        listDataModel.addColumn("productName", ListRendererType.TEXT);
        listDataModel.addColumn("barcode", ListRendererType.TEXT);
        listDataModel.addColumn("uom", ListRendererType.TEXT);
        listDataModel.addColumn("firstQty", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("importQty", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("exportQty", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("lastQty", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("priceUnit", ListRendererType.NUMBER).width(UIConstants.AMT_COLUMN_WIDTH);
        listDataModel.addColumn("priceUnitTotal", ListRendererType.NUMBER).width(UIConstants.AMT_COLUMN_WIDTH);
    }

    @Override
    protected List<UnsoldProductDTO> loadData(int fistIndex, int maxResults) {
        Date fromDate = fromDateComp.getDate();
        Date toDate = toDateComp.getDate();
        Store selStore = (Store) cbStore.getSelectedItem();
        List<Product> selProducts = mulStListBox.getDestinationValues();
        List<UnsoldProductDTO> result = serviceProvider.getService(IStoreService.class).statisticUnsoldProduct(
                selProducts, selStore, fromDate, toDate);
        // return serviceProvider.getService(IStoreService.class).statisticImportStoreData(salesContractCode,
        // selectedStore.getCode(), selectedProduct.getCode(), fromDate, toDate);
        return result;
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
        mulStListBox = new MultiSelectionListBox<>(products, Collections.EMPTY_LIST);
        fromDateComp = new JXDatePicker();
        fromDateComp.setLocale(new Locale("vi"));
        fromDateComp.setDate(new Date());
        toDateComp = new JXDatePicker();
        toDateComp.setLocale(new Locale("vi"));
        toDateComp.setDate(new Date());
        cbStore = new JComboBox(stores.toArray());

        panel.add(new JLabel(ControlConfigUtils.getString("label.UnsoldProductDTO.store")), "right");
        panel.add(cbStore, "grow, wrap");
        panel.add(new JLabel(ControlConfigUtils.getString("label.UnsoldProductDTO.fromDate")), "right");
        panel.add(fromDateComp, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.UnsoldProductDTO.toDate")), "right");
        panel.add(toDateComp, "grow,wrap");
        panel.add(new JLabel(ControlConfigUtils.getString("label.UnsoldProductDTO.product")), "right");
        panel.add(mulStListBox, "grow");

        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void clearCriteria() {
        fromDateComp.setDate(null);
        toDateComp.setDate(null);
    }
}
