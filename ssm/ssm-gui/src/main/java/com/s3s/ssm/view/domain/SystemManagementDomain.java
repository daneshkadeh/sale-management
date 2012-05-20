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

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.list.config.ListBankView;
import com.s3s.ssm.view.list.config.ListCurrencyView;
import com.s3s.ssm.view.list.config.ListExchangeRateView;
import com.s3s.ssm.view.list.config.ListInstitutionView;
import com.s3s.ssm.view.list.config.ListOrganizationView;
import com.s3s.ssm.view.list.operator.ListOperatorView;
import com.s3s.ssm.view.list.operator.ListStallView;
import com.s3s.ssm.view.list.param.ListManufacturerView;
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
        setIcon(IziImageUtils.getMediumIcon(IziImageConstants.SYSTEM_ICON));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        // User management
        ImageIcon userIcon = IziImageUtils.getMediumIcon(IziImageConstants.USER_ICON);
        TreeNodeWithView userManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement"), userIcon);
        ImageIcon userNodeIcon = IziImageUtils.getMediumIcon(IziImageConstants.USER_NODE_ICON);
        TreeNodeWithView userNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.UserManagement.User"),
                new ListOperatorView(userNodeIcon, ControlConfigUtils.getString("label.Operator.list.title"), null),
                userNodeIcon);
        // Role
        ImageIcon roleIcon = IziImageUtils.getMediumIcon(IziImageConstants.ROLE_ICON);
        TreeNodeWithView profilesNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement.Profiles"), new ListRoleView(roleIcon,
                        ControlConfigUtils.getString("label.Role.list.title"), null), roleIcon);
        // TreeNodeWithView exceptionPrivilegeNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.UserManagement.ExceptionPrivilege"));
        // Stall
        ImageIcon stallIcon = IziImageUtils.getMediumIcon(IziImageConstants.STALL_ICON);
        TreeNodeWithView operatorNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement.Stall"), new ListStallView(stallIcon,
                        ControlConfigUtils.getString("label.Stall.list.title"), null), stallIcon);
        rootNode.add(userManagementEntry);
        userManagementEntry.add(userNode);
        userManagementEntry.add(profilesNode);
        // userManagementEntry.add(exceptionPrivilegeNode);
        userManagementEntry.add(operatorNode);

        // Manufacturer management
        ImageIcon manufacturerIcon = IziImageUtils.getMediumIcon(IziImageConstants.MANUFACTURER_ICON);
        TreeNodeWithView mfManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.Manufacturer"), new ListManufacturerView(manufacturerIcon,
                        ControlConfigUtils.getString("label.Manufacturer.list.title"), null), manufacturerIcon);
        rootNode.add(mfManagementEntry);

        // Institution
        ImageIcon institutionIcon = IziImageUtils.getMediumIcon(IziImageConstants.INSTITUTION_ICON);
        TreeNodeWithView institutionEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.Institution"), new ListInstitutionView(institutionIcon,
                        ControlConfigUtils.getString("label.Institution.list.title"), null), institutionIcon);
        rootNode.add(institutionEntry);
        // Organization
        ImageIcon orgIcon = IziImageUtils.getMediumIcon(IziImageConstants.ORGANIZATION_ICON);
        TreeNodeWithView orgEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Organization"),
                new ListOrganizationView(orgIcon, ControlConfigUtils.getString("label.Organization.list.title"), null),
                orgIcon);
        rootNode.add(orgEntry);

        // Bank
        ImageIcon bankIcon = IziImageUtils.getMediumIcon(IziImageConstants.BANK_ICON);
        String bankTitle = ControlConfigUtils.getString("JTree.System.Bank");
        TreeNodeWithView bankEntry = new TreeNodeWithView(bankTitle, new ListBankView(bankIcon, null, bankTitle),
                bankIcon);
        rootNode.add(bankEntry);

        // Currency management
        // TODO: ListCurrencyView
        ImageIcon moneyIcon = IziImageUtils.getMediumIcon(IziImageConstants.MONEY_ICON);
        TreeNodeWithView currencyManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.CurrencyManagement"), moneyIcon);

        ImageIcon currencyIcon = IziImageUtils.getMediumIcon(IziImageConstants.CURRENCY_ICON);
        String currencyTitle = ControlConfigUtils.getString("JTree.System.CurrencyManagement.Currencies");
        TreeNodeWithView currenciesNode = new TreeNodeWithView(currencyTitle, new ListCurrencyView(currencyIcon, null,
                currencyTitle), currencyIcon);
        // Exchange rating
        ImageIcon exRateIcon = IziImageUtils.getMediumIcon(IziImageConstants.EXCHANGE_RATING_ICON);
        String exRateTitle = ControlConfigUtils.getString("JTree.System.CurrencyManagement.ExchangeRate");
        TreeNodeWithView exchangeRateNode = new TreeNodeWithView(exRateTitle, new ListExchangeRateView(exRateIcon,
                null, exRateTitle), exRateIcon);
        rootNode.add(currencyManagementEntry);
        currencyManagementEntry.add(currenciesNode);
        currencyManagementEntry.add(exchangeRateNode);
    }
}
