package com.s3s.ssm.view.detail.sales;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.ServiceProvider;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class AListInvoiceDetailComponent extends AListComponent<DetailInvoice> {

    private static final String REF_PACKLINE = "REF_PACKLINE";
    private static final String REF_CURRENCY = "REF_CURRENCY";
    private static final String REF_D_INVOICE_TYPE = "REF_D_INVOICE_TYPE";
    private static final String REF_D_INVOICE_STATUS = "REF_D_INVOICE_STATUS";
    private static final String REF_ITEM = "REF_ITEM";

    public AListInvoiceDetailComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        // TODO: must be search component for product or item?
        listDataModel.addColumn("item", ListRendererType.SEARCH_COMPONENT, ListEditorType.SEARCH_COMPONENT)
                .componentInfo(ComponentFactory.createItemComponentInfo());
        // listDataModel.addColumn("product.name", ListRendererType.TEXT).notEditable();
        // listDataModel.addColumn("item.sumUomName", ListRendererType.TEXT).notEditable();
        listDataModel.addColumn("productName", ListRendererType.TEXT).notEditable();

        // TODO: first work on item, do not apply package.
        // listDataModel.addColumn("package", ListRendererType.TEXT);
        // listDataModel.addColumn("packageLine", ListRendererType.TEXT, ListEditorType.COMBOBOX)
        // .referenceDataId(REF_PACKLINE).notEditable();
        listDataModel.addColumn("amount", ListRendererType.TEXT);
        listDataModel.addColumn("priceAfterTax", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120);
        listDataModel.addColumn("type", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_D_INVOICE_TYPE);
        listDataModel.addColumn("status", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_D_INVOICE_STATUS);
        listDataModel.addColumn("totalAmount", ListRendererType.TEXT).notEditable();

    }

    @Override
    protected void doRowUpdated(String attributeName, DetailInvoice entityUpdated, List<DetailInvoice> entities) {
        super.doRowUpdated(attributeName, entityUpdated, entities);
        if ("item".equals(attributeName)) {
            Item item = entityUpdated.getItem();
            entityUpdated.setProduct(item.getProduct());
            entityUpdated.setProductName(entityUpdated.getProduct().getName() + "-" + item.getSumUomName());
            entityUpdated.setPriceAfterTax(item.getBaseSellPrice());
            entityUpdated.setPriceBeforeTax(item.getBaseSellPrice());

            // TODO: sell price must base on Partner, and ItemPrices
            entityUpdated.setMoneyAfterTax(item.getBaseSellPrice());
            entityUpdated.setMoneyBeforeTax(item.getBaseSellPrice());
            entityUpdated.setBaseUom(item.getUom());
            entityUpdated.setUom(item.getUom());

        }
    }

    @Override
    protected ReferenceDataModel initReferenceDataModel() {
        ReferenceDataModel refDataModel = super.initReferenceDataModel();
        ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();
        refDataModel.putRefDataList(REF_ITEM, getDaoHelper().getDao(Item.class).findAll());
        refDataModel.putRefDataList(REF_PACKLINE, getDaoHelper().getDao(PackageLine.class).findAll());
        refDataModel.putRefDataList(REF_D_INVOICE_STATUS, DetailInvoiceStatus.values());
        refDataModel.putRefDataList(REF_D_INVOICE_TYPE, DetailInvoiceType.values());
        refDataModel.putRefDataList(REF_CURRENCY, serviceProvider.getService(IConfigService.class).getCurrencyCodes());
        return refDataModel;
    }
}
