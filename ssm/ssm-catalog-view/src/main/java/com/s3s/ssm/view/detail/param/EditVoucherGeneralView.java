package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditVoucherGeneralView extends EditProductGeneralView<Voucher> {
    public EditVoucherGeneralView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void addTabGeneral(DetailDataModel detailDataModel) {
        super.addTabGeneral(detailDataModel);
        detailDataModel.addAttribute("minAmount", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
    }

    @Override
    protected ProductFamilyType getProductFamilyType() {
        return ProductFamilyType.VOUCHER;
    }
}
