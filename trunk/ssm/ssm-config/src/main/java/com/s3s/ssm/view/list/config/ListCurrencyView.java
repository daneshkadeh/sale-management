package com.s3s.ssm.view.list.config;

import java.util.List;

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.config.EditCurrencyView;

public class ListCurrencyView extends AbstractListView<SCurrency> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("symbol", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("isActive", FieldTypeEnum.DROPDOWN));

    }

    @Override
    protected Class<? extends AbstractDetailView<SCurrency>> getDetailViewClass() {
        return EditCurrencyView.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.CURRENCY;
    }

}
