package com.s3s.ssm.view.detail.param;

import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.contact.PartnerCategory;
import com.s3s.ssm.helper.CatalogHelper;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditItemPriceVirtualView extends AbstractDetailView<ItemPrice> {
    private static final String REF_CONTACT_TYPE = "REF_CONTACT_TYPE";
    private static final String REF_CURRENCY_ID = "REF_CURRENCY_ID";

    public EditItemPriceVirtualView(ItemPrice entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ItemPrice entity) {
        detailDataModel.addAttribute("partnerCategory", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CONTACT_TYPE);
        detailDataModel.addAttribute("sellPrice", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("currency", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CURRENCY_ID);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ItemPrice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_CONTACT_TYPE, getDaoHelper().getDao(PartnerCategory.class).findAll(), null);
        refDataModel.putRefDataList(REF_CURRENCY_ID, CatalogHelper.getCurrenciesCode(getDaoHelper()), null);
    }

    @Override
    protected void saveOrUpdate(ItemPrice entity) {
        // Do nothing, wait for saved by master entity Item.
    }
}
