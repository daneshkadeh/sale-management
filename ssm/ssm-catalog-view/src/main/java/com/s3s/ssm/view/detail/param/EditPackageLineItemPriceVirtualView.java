package com.s3s.ssm.view.detail.param;

import java.util.ArrayList;
import java.util.Map;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.PackageLineItemPrice;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPackageLineItemPriceVirtualView extends AbstractSingleEditView<PackageLineItemPrice> {

    private static final String REF_ITEMS = "REF_ITEMS";

    public EditPackageLineItemPriceVirtualView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, PackageLineItemPrice entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("item", DetailFieldType.DROPDOWN).referenceDataId(REF_ITEMS);
        detailDataModel.addAttribute("audienceCategory", DetailFieldType.DROPDOWN).cacheDataId(
                CacheId.REF_LIST_AUDIENCE_CATE);
        detailDataModel.addAttribute("sellPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PackageLineItemPrice loadForCreate(Map<String, Object> request) {
        PackageLineItemPrice entity = super.loadForCreate(request);
        entity.setPackageLine((PackageLine) getParentObject());
        return entity;
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, PackageLineItemPrice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        PackageLine parent = (PackageLine) getParentObject();
        if (parent.getIsAllItem()) {
            refDataModel.putRefDataList(REF_ITEMS, new ArrayList<Item>(parent.getProduct().getListItems()));
        } else {
            refDataModel.putRefDataList(REF_ITEMS, new ArrayList<Item>(parent.getExplicitLinkItems()));
        }

    }

}
