package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.config.ExchangeRate;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditExchangeRateView extends AbstractDetailView<ExchangeRate> {
    private static final long serialVersionUID = 1L;
    private static final String CURRENCY_REF_ID = "1";
    public EditExchangeRateView(ExchangeRate entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ExchangeRate entity) {
    	detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
    	detailDataModel.addAttribute("updateDate", FieldTypeEnum.DATE);
    	detailDataModel.addAttribute("currency", FieldTypeEnum.DROPDOWN).referenceDataId(CURRENCY_REF_ID);
    	detailDataModel.addAttribute("rate", FieldTypeEnum.TEXTBOX);
    }
    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ExchangeRate entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(CURRENCY_REF_ID, getDaoHelper().getDao(SCurrency.class).findAll(), null);
    }
}
