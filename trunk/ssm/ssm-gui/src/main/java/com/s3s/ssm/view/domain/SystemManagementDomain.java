/*
 * SystemManagementDomain
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
package com.s3s.ssm.view.domain;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.list.config.ListBankView;
import com.s3s.ssm.view.list.config.ListCurrencyView;
import com.s3s.ssm.view.list.config.ListExchangeRateView;
import com.s3s.ssm.view.list.config.ListInstitutionView;
import com.s3s.ssm.view.list.config.ListOrganizationView;
import com.s3s.ssm.view.list.config.ListUnitOfMeasureView;
import com.s3s.ssm.view.list.config.ListUomCategoryView;
import com.s3s.ssm.view.list.operator.ListOperatorView;
import com.s3s.ssm.view.list.operator.ListStallView;
import com.s3s.ssm.view.list.param.ListGoodsView;
import com.s3s.ssm.view.list.param.ListManufacturerView;
import com.s3s.ssm.view.list.param.ListPackageView;
import com.s3s.ssm.view.list.param.ListProductPropertyView;
import com.s3s.ssm.view.list.param.ListProductTypeView;
import com.s3s.ssm.view.list.param.ListServiceView;
import com.s3s.ssm.view.list.param.ListVoucherView;
import com.s3s.ssm.view.list.security.ListRoleView;

/**
 * Define master object in the system: user, product, supplier.
 * 
 * @author phamcongbang
 * 
 */
public class SystemManagementDomain extends AbstractDomain {
    private static final long serialVersionUID = -3556085153191024424L;

    public SystemManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.SystemManagement"));
        setIcon(ImageUtils.getMediumIcon(ImageConstants.SYSTEM_ICON));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        // User management
        ImageIcon userIcon = ImageUtils.getSmallIcon(ImageConstants.USER_ICON);
        TreeNodeWithView userManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement"), userIcon);
        TreeNodeWithView userNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.UserManagement.User"),
                new ListOperatorView());
        TreeNodeWithView profilesNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement.Profiles"), new ListRoleView(null,
                        ControlConfigUtils.getString("label.Role.list.title"), null));
        // TreeNodeWithView exceptionPrivilegeNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.UserManagement.ExceptionPrivilege"));
        TreeNodeWithView operatorNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement.Stall"), new ListStallView());
        rootNode.add(userManagementEntry);
        userManagementEntry.add(userNode);
        userManagementEntry.add(profilesNode);
        // userManagementEntry.add(exceptionPrivilegeNode);
        userManagementEntry.add(operatorNode);

        // Manufacturer management
        TreeNodeWithView mfManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.Manufacturer"), new ListManufacturerView());
        rootNode.add(mfManagementEntry);

        // Product management
        TreeNodeWithView productManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.ProductManagement"));
        TreeNodeWithView uomCategoryNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.UomCategory"), new ListUomCategoryView());

        TreeNodeWithView uomNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.UnitOfMeasure"),
                new ListUnitOfMeasureView(null, ControlConfigUtils.getString("label.UnitOfMeasure.list.title"), null));

        TreeNodeWithView productPropertyNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.ProductProperty"), new ListProductPropertyView());

        TreeNodeWithView productGroupNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.ProductGroup"), new ListProductTypeView());
        TreeNodeWithView productNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Goods"),
                new ListGoodsView());
        TreeNodeWithView voucherNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Voucher"),
                new ListVoucherView());

        TreeNodeWithView serviceProductNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.Service"), new ListServiceView());

        TreeNodeWithView packageNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Package"),
                new ListPackageView());

        // TreeNodeWithView itemNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Item"),
        // new ListItemView());
        // Basic Information Management
        // TreeNodeWithView basicInformationEntry = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.System.BasicInformation"), new ListBasicInformationView());
        TreeNodeWithView institutionEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.Institution"), new ListInstitutionView());
        rootNode.add(institutionEntry);
        TreeNodeWithView orgEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Organization"),
                new ListOrganizationView());
        rootNode.add(orgEntry);
        // TODO: ListTaxGroupView
        TreeNodeWithView taxGroupNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.TaxGroup"));
        rootNode.add(productManagementEntry);
        productManagementEntry.add(uomCategoryNode);
        productManagementEntry.add(uomNode);
        productManagementEntry.add(productPropertyNode);
        productManagementEntry.add(productGroupNode);
        productManagementEntry.add(productNode);
        productManagementEntry.add(voucherNode);
        productManagementEntry.add(serviceProductNode);
        productManagementEntry.add(packageNode);
        // productManagementEntry.add(itemNode);
        productManagementEntry.add(taxGroupNode);

        // Bank
        ImageIcon bankIcon = ImageUtils.getSmallIcon(ImageConstants.BANK_ICON);
        String bankTitle = ControlConfigUtils.getString("JTree.System.Bank");
        TreeNodeWithView bankEntry = new TreeNodeWithView(bankTitle, new ListBankView(bankIcon, null, bankTitle),
                bankIcon);
        rootNode.add(bankEntry);

        // Currency management
        // TODO: ListCurrencyView
        ImageIcon moneyIcon = ImageUtils.getSmallIcon(ImageConstants.MONEY_ICON);
        TreeNodeWithView currencyManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.CurrencyManagement"), moneyIcon);

        ImageIcon currencyIcon = ImageUtils.getSmallIcon(ImageConstants.CURRENCY_ICON);
        String currencyTitle = ControlConfigUtils.getString("JTree.System.CurrencyManagement.Currencies");
        TreeNodeWithView currenciesNode = new TreeNodeWithView(currencyTitle, new ListCurrencyView(currencyIcon, null,
                currencyTitle), currencyIcon);
        // Exchange rating
        ImageIcon exRateIcon = ImageUtils.getSmallIcon(ImageConstants.EXCHANGE_RATING_ICON);
        String exRateTitle = ControlConfigUtils.getString("JTree.System.CurrencyManagement.ExchangeRate");
        TreeNodeWithView exchangeRateNode = new TreeNodeWithView(exRateTitle, new ListExchangeRateView(exRateIcon,
                null, exRateTitle), exRateIcon);
        rootNode.add(currencyManagementEntry);
        currencyManagementEntry.add(currenciesNode);
        currencyManagementEntry.add(exchangeRateNode);
    }
}
