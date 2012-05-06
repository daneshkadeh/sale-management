package com.s3s.ssm.view.domain;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.list.config.ListUnitOfMeasureView;
import com.s3s.ssm.view.list.config.ListUomCategoryView;
import com.s3s.ssm.view.list.param.ListGoodsView;
import com.s3s.ssm.view.list.param.ListPackageView;
import com.s3s.ssm.view.list.param.ListProductPropertyView;
import com.s3s.ssm.view.list.param.ListProductTypeView;
import com.s3s.ssm.view.list.param.ListServiceView;
import com.s3s.ssm.view.list.param.ListVoucherView;

public class CatalogManagementDomain extends AbstractDomain {

    public CatalogManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.CatalogDomain"));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        // TODO: ListTaxGroupView
        // TreeNodeWithView taxGroupNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.System.TaxGroup"));

        // Product management
        TreeNodeWithView productManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.ProductManagement"));
        // UOM Category
        ImageIcon uomCateIcon = ImageUtils.getSmallIcon(ImageConstants.UOM_CATE_ICON);
        TreeNodeWithView uomCategoryNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.UomCategory"), new ListUomCategoryView(uomCateIcon,
                        ControlConfigUtils.getString("label.UnitOfMeasure.list.title"), null), uomCateIcon);
        // UOM
        ImageIcon uomIcon = ImageUtils.getSmallIcon(ImageConstants.UOM_ICON);
        TreeNodeWithView uomNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.System.UnitOfMeasure"),
                new ListUnitOfMeasureView(uomIcon, ControlConfigUtils.getString("label.UnitOfMeasure.list.title"), null),
                uomIcon);

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
        rootNode.add(productManagementEntry);
        rootNode.add(uomCategoryNode);
        rootNode.add(uomNode);
        productManagementEntry.add(productPropertyNode);
        productManagementEntry.add(productGroupNode);
        productManagementEntry.add(productNode);
        productManagementEntry.add(voucherNode);
        productManagementEntry.add(serviceProductNode);
        productManagementEntry.add(packageNode);
        // productManagementEntry.add(itemNode);
        // rootNode.add(taxGroupNode);

    }

}
