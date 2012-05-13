package com.s3s.ssm.view.detail.param;

import java.util.Map;

import javax.swing.Icon;

import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.PackageLineItemPrice;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPackageLineItemPriceComponent extends AListComponent<PackageLineItemPrice> {
    private static final String REF_ITEM = "REF_ITEM";

    public ListPackageLineItemPriceComponent(Map<String, Object> params, Icon icon, String label, String tooltip) {
        super(params, icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_ITEM);
        listDataModel.addColumn("audienceCategory", ListRendererType.TEXT, ListEditorType.COMBOBOX).cacheDataId(
                CacheId.REF_LIST_AUDIENCE_CATE);
        listDataModel.addColumn("sellPrice", ListRendererType.TEXT, ListEditorType.MONEY).cacheDataId(
                CacheId.REF_LIST_CURRENCY);
    }

    @Override
    protected ReferenceDataModel initReferenceDataModel() {
        ReferenceDataModel refData = super.initReferenceDataModel();
        PackageLine parent = getParentObject();
        if (parent.getIsAllItem()) {
            refData.putRefDataList(REF_ITEM, parent.getProduct().getListItems());
        } else {
            refData.putRefDataList(REF_ITEM, parent.getExplicitLinkItems());
        }
        return refData;
    }

    public PackageLine getParentObject() {
        return (PackageLine) getRequest().get("parent");
    }

}
