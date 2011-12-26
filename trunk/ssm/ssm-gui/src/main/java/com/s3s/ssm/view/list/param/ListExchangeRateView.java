package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.config.ExchangeRate;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditExchangeRateView;

public class ListExchangeRateView extends AbstractListView<ExchangeRate> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("updateDate", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("rate", FieldTypeEnum.DROPDOWN));

    }

    @Override
    protected Class<? extends AbstractDetailView<ExchangeRate>> getDetailViewClass() {
        return EditExchangeRateView.class;
    }

}
