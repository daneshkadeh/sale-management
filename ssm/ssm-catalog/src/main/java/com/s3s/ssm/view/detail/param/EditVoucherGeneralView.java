package com.s3s.ssm.view.detail.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;

public class EditVoucherGeneralView extends EditProductGeneralView<Voucher> {

    private static final String REF_CURRENCY = "currency";

    public EditVoucherGeneralView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void addTabGeneral(DetailDataModel detailDataModel) {
        super.addTabGeneral(detailDataModel);
        detailDataModel.addAttribute("minAmount", FieldTypeEnum.MONEY).referenceDataId(REF_CURRENCY);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Product entity) {
        super.setReferenceDataModel(refDataModel, entity);
        // TODO: we will move list supported currencies to contextProvider
        List<SCurrency> listCurrencies = getDaoHelper().getDao(SCurrency.class).findAll();
        List<String> currencyCodes = new ArrayList<>();
        for (SCurrency currency : listCurrencies) {
            currencyCodes.add(currency.getCode());
        }
        refDataModel.putRefDataList(REF_CURRENCY, currencyCodes, null);
    }

}
