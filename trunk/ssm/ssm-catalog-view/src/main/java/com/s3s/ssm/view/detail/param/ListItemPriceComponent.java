package com.s3s.ssm.view.detail.param;

import javax.swing.Icon;

import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListItemPriceComponent extends AListComponent<ItemPrice> {
    private static final String REF_AUDIENCE_CATE = "REF_AUDIENCE_CATE";
    private static final String REF_CURRENCY_ID = "REF_CURRENCY_ID";

    public ListItemPriceComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("audienceCategory", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_AUDIENCE_CATE);
        listDataModel.addColumn("sellPrice", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY_ID).width(200);
    }

    @Override
    protected ReferenceDataModel initReferenceDataModel() {
        ReferenceDataModel refDataModel = super.initReferenceDataModel();
        refDataModel.putRefDataList(REF_AUDIENCE_CATE, getDaoHelper().getDao(AudienceCategory.class).findAll(), null);
        refDataModel.putRefDataList(REF_CURRENCY_ID, getDaoHelper().getDao(SCurrency.class).findAll(), null);
        return refDataModel;
    }
}
