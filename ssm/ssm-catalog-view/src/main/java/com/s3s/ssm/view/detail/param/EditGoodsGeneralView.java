package com.s3s.ssm.view.detail.param;

import java.util.Map;

import com.s3s.ssm.entity.catalog.Goods;
import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * General view to configure Goods
 * 
 * @author phamcongbang
 * 
 */
public class EditGoodsGeneralView extends EditProductGeneralView<Goods> {
    private static final long serialVersionUID = 1L;

    private static final String MANU_REF_ID = "2";
    private static final String UOM_REF_ID = "3";

    public EditGoodsGeneralView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, Goods entity, Map<String, Object> request) {
        super.initialPresentationView(detailDataModel, entity, request);
        detailDataModel.tab(ControlConfigUtils.getString("tab.EditGoodsGeneralView.sellInfo"), "Sell info", null);
        detailDataModel.addAttribute("originPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("baseSellPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("maintainPeriod", DetailFieldType.YEAR_MON_DAY);
        detailDataModel.addAttribute("minNumberOfStoredProduct", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mustSoldPeriod", DetailFieldType.YEAR_MON_DAY);
        detailDataModel.addAttribute("minNumberSoldInMonth", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("effectiveSoldInMonth", DetailFieldType.TEXTBOX);
    }

    @Override
    protected void addTabGeneral(DetailDataModel detailDataModel) {
        super.addTabGeneral(detailDataModel);
        detailDataModel.addAttribute("manufacturer", DetailFieldType.DROPDOWN).referenceDataId(MANU_REF_ID);
        detailDataModel.addAttribute("model", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("mainUom", DetailFieldType.DROPDOWN).referenceDataId(UOM_REF_ID);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Goods entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(MANU_REF_ID, getDaoHelper().getDao(Manufacturer.class).findAll(), null);
        refDataModel.putRefDataList(UOM_REF_ID, getDaoHelper().getDao(UnitOfMeasure.class).findAll(), null);

    }

    @Override
    protected ProductFamilyType getProductFamilyType() {
        return ProductFamilyType.GOODS;
    }

}
