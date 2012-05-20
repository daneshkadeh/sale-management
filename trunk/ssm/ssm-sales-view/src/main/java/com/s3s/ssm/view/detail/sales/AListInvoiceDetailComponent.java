package com.s3s.ssm.view.detail.sales;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.collections.CollectionUtils;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.ServiceProvider;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.component.EntityDialog;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ColumnModel;
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

    public AListInvoiceDetailComponent(Map<String, Object> request, Icon icon, String label, String tooltip) {
        super(request, icon, label, tooltip);

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
        listDataModel.addColumn("package", ListRendererType.TEXT).setRaw(true);
        listDataModel.addColumn("packageLine", ListRendererType.TEXT).setRaw(true);
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
    public Object getAttributeValue(DetailInvoice entity, ColumnModel dataModel) {
        if ("package".equals(dataModel.getName())) {
            return entity.getPackage() != null ? entity.getPackage().getCode() : "";
        } else if ("packageLine".equals(dataModel.getName())) {
            return entity.getPackageLine() != null ? entity.getPackageLine().getId() : "";
        } else {
            return super.getAttributeValue(entity, dataModel);
        }
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
    protected void doRowDelete(int[] deletedIndex) {
        List<DetailInvoice> entities = getData();
        // TODO Bang: remove all deleted rows instead of the first row
        DetailInvoice entity = entities.get(deletedIndex[0]);
        if (entity != null && entity.getPackage() != null) {
            if (entity.getParent() != null) {
                entities.remove(entity.getParent());
                entities.removeAll(entity.getParent().getSubs());
            }
            if (CollectionUtils.isNotEmpty(entity.getSubs())) {
                entities.remove(entity);
                entities.removeAll(entity.getSubs());
            }
        } else {
            super.doRowDelete(deletedIndex);
        }
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

    @Override
    protected JToolBar createButtonsToolbar() {
        JToolBar tb = super.createButtonsToolbar();
        tb.add(new JSeparator(SwingConstants.VERTICAL));
        JButton packageBtn = new JButton(ControlConfigUtils.getString("AListInvoiceDetailComponent.packageBtn"));
        packageBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Open a popup to select package, then open sales package screen
                // after finish input sales package, add packageLines to the listEntities (this table)

                EntityDialog<SPackage> packageDialog = new EntityDialog<>(null, Dialog.ModalityType.APPLICATION_MODAL,
                        daoHelper.getDao(SPackage.class).findAll());
                packageDialog.setLocationRelativeTo(SwingUtilities.getRootPane(AListInvoiceDetailComponent.this));
                packageDialog.setVisible(true);

                Map<String, Object> detailParams = new HashMap<String, Object>();
                if (packageDialog.isPressedOK() == 1) {
                    SPackage selectedPackage = packageDialog.getSelectedEntity();
                    detailParams.put("package", selectedPackage);
                    detailParams.put("parentObject", getRequest().get("parentObject")); // parent object is invoice
                } else {
                    return; // do nothing
                }

                JDialog frame = new JDialog();
                detailParams.put("parentDialog", frame);
                EditDetailInvoicePackageView invoicePackageView = new EditDetailInvoicePackageView(detailParams);
                // frame.setLocationRelativeTo(SwingUtilities.getRootPane(AListInvoiceDetailComponent.this));
                frame.add(invoicePackageView);
                frame.pack();
                frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                frame.setVisible(true);
                if (invoicePackageView.isSaved()) {
                    // add list detail invoices to the invoice view
                    if (invoicePackageView.getEntity() != null) {
                        getMainTableModel().addRowAt(getMainTableModel().getData().size(),
                                invoicePackageView.getEntity());
                        for (DetailInvoice sub : invoicePackageView.getEntity().getSubs()) {
                            getMainTableModel().addRowAt(getMainTableModel().getData().size(), sub);
                        }
                    }
                }

                // Map<String, Object> params = new HashMap<>();
                // params.put("invoice", getEntity());
                // EditInvoicePaymentView invoicePaymentForm = new EditInvoicePaymentView(params);
                // JDialog frame = new JDialog();
                // frame.add(invoicePaymentForm);
                // frame.pack();
                // frame.setVisible(true);
            }
        });
        tb.add(packageBtn);
        return tb;
    }
}
