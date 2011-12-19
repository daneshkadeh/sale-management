package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.param.Currency;
import com.s3s.ssm.entity.param.UomCategory;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditCurrencyView;
import com.s3s.ssm.view.detail.param.EditUomCategoryView;

public class ListCurrencyView extends AbstractListView<Currency> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("symbol", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("isActive", FieldTypeEnum.DROPDOWN));

    }

    @Override
    protected Class<? extends AbstractDetailView<Currency>> getDetailViewClass() {
        return EditCurrencyView.class;
    }

}
