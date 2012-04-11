package com.s3s.ssm.view.detail.param;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.PackageLineItemPrice;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;
import com.s3s.ssm.view.list.param.ListPackageLineView;

public class EditPackageLineView extends AbstractMasterDetailView<PackageLine, PackageLineItemPrice> {

    private static final String REF_ITEMS = "REF_ITEMS";

    public EditPackageLineView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("item", ListRendererType.TEXT);
        listDataModel.addColumn("audienceCategory", ListRendererType.TEXT);
        listDataModel.addColumn("sellPrice", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<PackageLineItemPrice>> getChildDetailViewClass() {
        return EditPackageLineItemPriceVirtualView.class;
    }

    @Override
    protected String getChildFieldName() {
        return "itemPrices";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, PackageLine entity,
            Map<String, Object> request) {
        detailDataModel.addRawAttribute("product", DetailFieldType.TEXTBOX).value(entity.getProduct());
        detailDataModel.addAttribute("optional", DetailFieldType.CHECKBOX);
        detailDataModel.addAttribute("isAllItem", DetailFieldType.CHECKBOX);
        detailDataModel.addAttribute("minItemAmount", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("maxItemAmount", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("explicitLinkItems", DetailFieldType.MULTI_SELECT_LIST_BOX).referenceDataId(
                REF_ITEMS);
    }

    @Override
    protected PackageLine loadForCreate() {
        PackageLine entity = super.loadForCreate();
        entity.setPackage((SPackage) getParentObject());
        entity.setProduct((Product) request.get(ListPackageLineView.PRODUCT_OF_PACKAGE_LINE));
        return entity;
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, PackageLine entity) {
        super.setReferenceDataModel(refDataModel, entity);
        DetachedCriteria dc = daoHelper.getDao(Item.class).getCriteria();
        dc.add(Restrictions.eq("product", entity.getProduct()));
        refDataModel.putRefDataList(REF_ITEMS, daoHelper.getDao(Item.class).findByCriteria(dc));
    }

    @Override
    protected String getDefaultTitle(PackageLine entity) {
        return ControlConfigUtils.getString("JTree.SubMenu.Package.PackageLine") + " - "
                + (entity.getId() != null ? entity.getId() : "New");
    }
}
