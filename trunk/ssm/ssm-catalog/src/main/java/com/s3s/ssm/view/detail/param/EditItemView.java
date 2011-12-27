package com.s3s.ssm.view.detail.param;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.internal.runtime.Product;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.config.CurrencyEnum;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractMasterDetailView;

/**
 * This view is only used to TEST. A list items should be shown on 1 product config. The entity tree view is required
 * for this case.
 * 
 * @author phamcongbang
 * 
 */
public class EditItemView extends AbstractMasterDetailView<Item, ItemPrice> {

    private static final String REF_PRODUCT_ID = "REF_PRODUCT_ID";
    private static final String REF_CURRENCY_ID = "REF_CURRENCY_ID";
    private static final String REF_UOM_ID = "REF_UOM_ID";

    public EditItemView(Item entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Item entity) {
        detailDataModel.addAttribute("product", FieldTypeEnum.DROPDOWN).referenceDataId(REF_PRODUCT_ID);
        detailDataModel.addAttribute("sumUomName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("baseSellPrice", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("currency", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CURRENCY_ID);
        detailDataModel.addAttribute("listUom", FieldTypeEnum.MULTI_SELECT_BOX).referenceDataId(REF_UOM_ID);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Item entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PRODUCT_ID, getDaoHelper().getDao(Product.class).findAll(), null);
        refDataModel.putRefDataList(REF_CURRENCY_ID, Arrays.asList(CurrencyEnum.values()), null);
        refDataModel.putRefDataList(REF_UOM_ID, getDaoHelper().getDao(UnitOfMeasure.class).findAll(), null);
        // refDataModel.putRefDataList(REF_UOM_ID, Arrays.asList("0", "1", "2"), null);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("contactType", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("sellPrice", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("currency", FieldTypeEnum.TEXTBOX));

    }

    @Override
    protected Class<? extends AbstractDetailView<ItemPrice>> getChildDetailViewClass() {
        return EditItemPriceVirtualView.class;
    }

    @Override
    protected String getChildFieldName() {
        return "listItemPrices";
    }

    @Override
    protected void addDetailIntoMaster(Item masterEntity, ItemPrice detailEntity) {
        masterEntity.addItemPrice(detailEntity);
    }

}
