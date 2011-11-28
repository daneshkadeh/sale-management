package com.s3s.ssm.view.domain;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.component.TreeNodeWithView;
import com.s3s.ssm.view.component.TreeView;
import com.s3s.ssm.view.list.contact.ListBankView;
import com.s3s.ssm.view.list.param.ListItemView;
import com.s3s.ssm.view.list.param.ListManufacturerView;
import com.s3s.ssm.view.list.param.ListProductTypeView;
import com.s3s.ssm.view.list.param.ListProductView;
import com.s3s.ssm.view.list.param.ListSupplierView;
import com.s3s.ssm.view.list.param.ListUnitOfMeasureView;
import com.s3s.ssm.view.list.param.ListUomCategoryView;

/**
 * Define master object in the system: user, product, supplier.
 * 
 * @author phamcongbang
 * 
 */
public class SystemManagementDomain extends AbstractDomain {

    public SystemManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.SystemManagement"));
    }

    @Override
    protected void constructTreeView(TreeView treeView) {
        TreeNodeWithView systemEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.SystemManagement"));
        // User management
        TreeNodeWithView userManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement"));
        TreeNodeWithView userNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.UserManagement.User"));
        TreeNodeWithView profilesNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement.Profiles"));
        TreeNodeWithView exceptionPrivilegeNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.UserManagement.ExceptionPrivilege"));
        systemEntry.add(userManagementEntry);
        userManagementEntry.add(userNode);
        userManagementEntry.add(profilesNode);
        userManagementEntry.add(exceptionPrivilegeNode);

        // Manufacturer management
        TreeNodeWithView mfManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.Manufacturer"), new ListManufacturerView());
        systemEntry.add(mfManagementEntry);

        // Supplier
        TreeNodeWithView supplierEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Supplier"),
                new ListSupplierView());
        systemEntry.add(supplierEntry);

        // Product management
        TreeNodeWithView productManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.ProductManagement"));
        TreeNodeWithView uomCategoryNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.UomCategory"), new ListUomCategoryView());

        TreeNodeWithView uomNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.UnitOfMeasure"),
                new ListUnitOfMeasureView());

        TreeNodeWithView productGroupNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.ProductGroup"), new ListProductTypeView());
        TreeNodeWithView productNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Product"),
                new ListProductView());

        TreeNodeWithView itemNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Item"),
                new ListItemView());

        // TODO: ListTaxGroupView
        TreeNodeWithView taxGroupNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.TaxGroup"));
        systemEntry.add(productManagementEntry);
        productManagementEntry.add(uomCategoryNode);
        productManagementEntry.add(uomNode);
        productManagementEntry.add(productGroupNode);
        productManagementEntry.add(productNode);
        productManagementEntry.add(itemNode);
        productManagementEntry.add(taxGroupNode);

        // Bank
        TreeNodeWithView bankEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.Bank"),
                new ListBankView());
        systemEntry.add(bankEntry);

        // Currency management
        // TODO: ListCurrencyView
        TreeNodeWithView currencyManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.CurrencyManagement"));
        TreeNodeWithView currenciesNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.CurrencyManagement.Currencies"));

        TreeNodeWithView exchangeRateNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.CurrencyManagement.ExchangeRate"));
        systemEntry.add(currencyManagementEntry);
        currencyManagementEntry.add(currenciesNode);
        currencyManagementEntry.add(exchangeRateNode);

        treeView.setModel(new DefaultTreeModel(systemEntry));
    }

}
