package com.s3s.ssm.view.detail.param;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.list.param.ListPackageLineView;

// TODO: will change to SingleEditView
//public class EditPackageLineView extends AbstractSingleEditView<PackageLine> {
public class EditPackageLineView extends AbstractSingleEditView<PackageLine> {

    private static final String REF_ITEMS = "REF_ITEMS";

    public EditPackageLineView(Map<String, Object> entity) {
        super(entity);
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
        detailDataModel.addAttribute("itemPrices", DetailFieldType.LIST).componentInfo(
                createListPLItemPriceElementInfo(entity));
    }

    private IComponentInfo createListPLItemPriceElementInfo(PackageLine entity) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("parent", entity);
        ListPackageLineItemPriceComponent component = new ListPackageLineItemPriceComponent(params, null, null, null);
        return new ListComponentInfo(component, "packageLine");
    }

    @Override
    protected PackageLine loadForCreate(Map<String, Object> request) {
        PackageLine entity = super.loadForCreate(request);
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
