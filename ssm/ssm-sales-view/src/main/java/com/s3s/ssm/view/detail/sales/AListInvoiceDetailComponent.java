package com.s3s.ssm.view.detail.sales;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.ServiceProvider;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class AListInvoiceDetailComponent extends AListComponent<DetailInvoice> {
    private static final long serialVersionUID = 7509501589404634796L;
    private static final String REF_PACKLINE = "REF_PACKLINE";
    private static final String REF_CURRENCY = "REF_CURRENCY";
    private static final String REF_D_INVOICE_TYPE = "REF_D_INVOICE_TYPE";
    private static final String REF_D_INVOICE_STATUS = "REF_D_INVOICE_STATUS";
    private static final String REF_ITEM = "REF_ITEM";
    private static final String REF_UOM = "REF_UOM";
    private Money totalAmounts = Money.zero(CurrencyEnum.VND);

    public AListInvoiceDetailComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);

        // TODO: need method document on ready. When first time load invoice view, totalAmounts is not init
        calcuSumAmount(getData());
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
        listDataModel.addColumn("amount", ListRendererType.TEXT).summarized();
        listDataModel.addColumn("uom", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_UOM)
                .notEditable();
        listDataModel.addColumn("priceAfterTax", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120);
        listDataModel.addColumn("type", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_D_INVOICE_TYPE);
        listDataModel.addColumn("status", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_D_INVOICE_STATUS);
        listDataModel.addColumn("totalAmount", ListRendererType.TEXT).notEditable().summarized();

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

        } else if ("amount".equals(attributeName)) {
            calcuSumAmount(entities);
        }
    }

    private void calcuSumAmount(List<DetailInvoice> entities) {
        Money sum = Money.zero(CurrencyEnum.VND);
        for (DetailInvoice detailInvoice : entities) {
            sum = sum.plus(detailInvoice.getTotalAmount());
        }
        totalAmounts = sum;
    }

    @Override
    protected void doRowDelete(List<DetailInvoice> entities) {
        super.doRowDelete(entities);
        calcuSumAmount(entities);
    }

    public Money getTotalAmounts() {
        return totalAmounts;
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
        refDataModel.putRefDataList(REF_UOM, serviceProvider.getService(IConfigService.class).getUnitUom());
        return refDataModel;
    }
}