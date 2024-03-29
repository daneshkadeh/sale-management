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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import com.s3s.ssm.dto.store.GroupDetailImportData;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.util.IziJasperReportUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.list.AListDataView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListImportStoreReportDataView extends AListDataView<GroupDetailImportData> {
    private static final long serialVersionUID = -4763495750566322036L;

    private JTextField salesContactComp;
    private JXDatePicker fromDateComp;
    private JXDatePicker toDateComp;
    private JComboBox<Store> cbStore;
    private JComboBox<Product> cbProduct;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("importingDate", ListRendererType.DATE);
        listDataModel.addColumn("salesContractCode", ListRendererType.TEXT);
        listDataModel.addColumn("storeName", ListRendererType.TEXT);
        listDataModel.addColumn("supplierName", ListRendererType.TEXT);
        listDataModel.addColumn("productName", ListRendererType.TEXT);
        listDataModel.addColumn("itemName", ListRendererType.TEXT);
        // listDataModel.addColumn("storeName", ListRendererType.TEXT);
        listDataModel.addColumn("quantity", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH);
    }

    @Override
    protected List<GroupDetailImportData> loadData(int firstIndex, int maxResults) {
        Date fromDate = fromDateComp.getDate();
        Date toDate = toDateComp.getDate();
        String salesContractCode = salesContactComp.getText();
        Store selectedStore = (Store) cbStore.getSelectedItem();
        Product selectedProduct = (Product) cbProduct.getSelectedItem();
        // TODO Phuc pass firstIndex and maxResult to paging
        return serviceProvider.getService(IStoreService.class).getStatisticImportStoreData(salesContractCode,
                selectedStore == null ? "" : selectedStore.getCode(),
                selectedProduct == null ? "" : selectedProduct.getCode(), fromDate, toDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int calculateTotalPages() {
        Date fromDate = fromDateComp.getDate();
        Date toDate = toDateComp.getDate();
        String salesContractCode = salesContactComp.getText();
        Store selectedStore = (Store) cbStore.getSelectedItem();
        Product selectedProduct = (Product) cbProduct.getSelectedItem();
        return serviceProvider.getService(IStoreService.class).getStatisticImportStoreDataCount(salesContractCode,
                selectedStore == null ? "" : selectedStore.getCode(),
                selectedProduct == null ? "" : selectedProduct.getCode(), fromDate, toDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JPanel createSearchPanel() {
        List<Store> stores = serviceProvider.getService(IStoreService.class).getStores();
        stores.add(0, null);
        List<Product> products = serviceProvider.getService(ICatalogService.class).getListProducts();
        products.add(0, null);

        JPanel panel = new JPanel(new MigLayout("ins 0, fill", "grow"));
        salesContactComp = new JTextField();
        fromDateComp = new JXDatePicker();
        toDateComp = new JXDatePicker();
        cbStore = new JComboBox(stores.toArray());
        cbProduct = new JComboBox(products.toArray());

        panel.add(new JLabel(ControlConfigUtils.getString("label.GroupDetailImportData.productName")), "right");
        panel.add(cbProduct, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.GroupDetailImportData.storeName")), "right");
        panel.add(cbStore, "grow,wrap");

        panel.add(new JLabel(ControlConfigUtils.getString("label.GroupDetailImportData.fromDate")), "right");
        panel.add(fromDateComp, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.GroupDetailImportData.toDate")), "right");
        panel.add(toDateComp, "grow,wrap");
        panel.add(new JLabel(ControlConfigUtils.getString("label.GroupDetailImportData.salesContractCode")), "right");
        panel.add(salesContactComp, "grow");

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

    @Override
    protected void performPrintAction() {
        // TODO Phuc
        List<GroupDetailImportData> data = loadData(-1, -1);
        Map<String, Object> params = new HashMap<String, Object>();
        String productNameFilter = cbProduct.getSelectedItem() == null ? "" : ((Product) cbProduct.getSelectedItem())
                .getName();
        String storeNameFilter = cbStore.getSelectedItem() == null ? "" : ((Store) cbStore.getSelectedItem()).getName();
        params.put("PRODUCT_NAME", productNameFilter);
        params.put("STORE", storeNameFilter);
        params.put("CONTRACT_NUM", salesContactComp.getText());
        params.put("FROM_DATE", fromDateComp.getDate());
        params.put("TO_DATE", toDateComp.getDate());
        IziJasperReportUtils.showReportDialog(SwingUtilities.getWindowAncestor(this), "/reports/importStore.jasper",
                params, data);
    }
}
