package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import javax.swing.JDialog;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.Goods;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.DaoHelperImpl;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditDetailInvoicePackageView extends AbstractSingleEditView<DetailInvoice> {
    private static final String REF_ITEM = "item";
    private static final String REF_PACKLINE = "packageLine";
    private static final String REF_TYPE = "type";
    private static final String REF_STATUS = "status";
    private DetailInvoice createdDetailInvoice;
    private boolean isSaved = false;

    public EditDetailInvoicePackageView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, DetailInvoice entity,
            Map<String, Object> request) {
        // entity is the parent detailInvoice in this case
        for (PackageLine line : entity.getPackage().getPackageLines()) {
            DetailInvoice subDetailInvoice = getSubDetailInvoice(line, entity);
            detailDataModel.startGroup(ControlConfigUtils.getString("label.SPackage.PackageLine.group") + " "
                    + line.getId());
            detailDataModel.addRawAttribute("productPackageLine" + line.getId(), DetailFieldType.TEXTBOX)
                    .value(line.getProduct())
                    .label(ControlConfigUtils.getString("label.DetailInvoice.productPackageLine"));
            detailDataModel.addRawAttribute("itemPackageLine" + line.getId(), DetailFieldType.DROPDOWN)
                    .referenceDataId("REF_ITEM" + line.getId()).value(subDetailInvoice.getItem())
                    .label(ControlConfigUtils.getString("label.DetailInvoice.itemPackageLine"));
            detailDataModel.addRawAttribute("lineAmount" + line.getId(), DetailFieldType.TEXTBOX)
                    .value(subDetailInvoice.getAmount())
                    .label(ControlConfigUtils.getString("label.DetailInvoice.lineAmount"));
            detailDataModel.addRawAttribute("linePriceAfterTax" + line.getId(), DetailFieldType.MONEY)
                    .cacheDataId(CacheId.REF_LIST_CURRENCY).value(subDetailInvoice.getPriceAfterTax())
                    .label(ControlConfigUtils.getString("label.DetailInvoice.linePriceAfterTax"));
            detailDataModel.addRawAttribute("lineMoneyAfterTax" + line.getId(), DetailFieldType.MONEY)
                    .cacheDataId(CacheId.REF_LIST_CURRENCY).value(subDetailInvoice.getMoneyAfterTax())
                    .label(ControlConfigUtils.getString("label.DetailInvoice.lineMoneyAfterTax"));
            detailDataModel.endGroup();
        }

        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_TYPE);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_STATUS);
    }

    private DetailInvoice getSubDetailInvoice(PackageLine line, DetailInvoice entity) {
        // if (!entity.isPersisted()) {
        for (DetailInvoice subDetail : entity.getSubs()) {
            if (line.equals(subDetail.getPackageLine())) {
                return subDetail;
            }
        }
        return createSubDetailInvoiceForPack(line, entity);
        // }
        // DetachedCriteria dc = getDaoHelper().getDao(DetailInvoice.class).getCriteria();
        // dc.add(Restrictions.eq("parent", entity));
        // dc.add(Restrictions.eq("packageLine", line));
        // List<DetailInvoice> list = getDaoHelper().getDao(DetailInvoice.class).findByCriteria(dc);
        // // Work-around to always return a detailInvoice.
        // DetailInvoice detail = null;
        // if (CollectionUtils.isEmpty(list)) {
        // detail = createSubDetailInvoiceForPack(line, entity);
        // } else {
        // detail = list.get(0);
        // }
        // return detail;
    }

    private DetailInvoice createSubDetailInvoiceForPack(PackageLine line, DetailInvoice entity) {
        DetailInvoice detail;
        detail = new DetailInvoice();
        detail.setInvoice(entity.getInvoice());
        detail.setProduct(line.getProduct());
        detail.setPackageLine(line);
        detail.setPackage(line.getPackage());
        detail.setParent(entity);

        // TODO: Price must get from Item and contact. (not only Goods, but also Service)
        if (line.getProduct().getType().getProductFamilyType() == ProductFamilyType.GOODS) {
            Goods goods = DaoHelperImpl.downCast(Goods.class, line.getProduct());
            detail.setPriceAfterTax(goods.getBaseSellPrice());
            detail.setMoneyAfterTax(goods.getBaseSellPrice());
        }

        entity.getSubs().add(detail);
        return detail;
    }

    @Override
    protected void bindingValue(DetailInvoice entity, String name, Object value, DetailAttribute detailAttribute) {
        if (name.startsWith("itemPackageLine")) {
            Long lineId = Long.valueOf(name.replaceFirst("itemPackageLine", ""));
            DetailInvoice sub = getSubDetailInvoice(daoHelper.getDao(PackageLine.class).findById(lineId), entity);
            Item item = (Item) value;
            if (item != null) {
                sub.setItem(item);
                sub.setUom(item.getUom());
                sub.setBaseUom(item.getUom());
            }
        } else if (name.startsWith("lineAmount")) {
            Long lineId = Long.valueOf(name.replaceFirst("lineAmount", ""));
            DetailInvoice sub = getSubDetailInvoice(daoHelper.getDao(PackageLine.class).findById(lineId), entity);
            sub.setAmount(Integer.valueOf(value.toString()));
        } else if (name.startsWith("linePriceAfterTax")) {
            Long lineId = Long.valueOf(name.replaceFirst("linePriceAfterTax", ""));
            DetailInvoice sub = getSubDetailInvoice(daoHelper.getDao(PackageLine.class).findById(lineId), entity);
            sub.setPriceAfterTax((Money) value);
        } else if (name.startsWith("lineMoneyAfterTax")) {
            Long lineId = Long.valueOf(name.replaceFirst("lineMoneyAfterTax", ""));
            DetailInvoice sub = getSubDetailInvoice(daoHelper.getDao(PackageLine.class).findById(lineId), entity);
            sub.setMoneyAfterTax((Money) value);
        } else {
            super.bindingValue(entity, name, value, detailAttribute);
        }

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, DetailInvoice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        DetachedCriteria dc = getDaoHelper().getDao(Item.class).getCriteria();
        dc.add(Restrictions.eq("product", entity.getProduct()));
        refDataModel.putRefDataList(REF_ITEM, getDaoHelper().getDao(Item.class).findByCriteria(dc));
        refDataModel.putRefDataList(REF_PACKLINE, getDaoHelper().getDao(PackageLine.class).findAll());
        refDataModel.putRefDataList(REF_TYPE, DetailInvoiceType.values());
        refDataModel.putRefDataList(REF_STATUS, DetailInvoiceStatus.values());

        SPackage pack = (SPackage) request.get("package");
        for (PackageLine line : pack.getPackageLines()) {
            refDataModel.putRefDataList("REF_ITEM" + line.getId(), line.getIsAllItem() ? line.getProduct()
                    .getListItems() : line.getExplicitLinkItems());
        }

    }

    @Override
    protected DetailInvoice loadForCreate(Map<String, Object> request) {
        DetailInvoice detail = super.loadForCreate(request);
        SPackage pack = (SPackage) request.get("package");
        detail.setPackage(pack);
        detail.setInvoice((Invoice) request.get("parentObject"));
        return detail;
    }

    @Override
    protected void saveOrUpdate(DetailInvoice entity) {
        // THU does not care about tax
        entity.setPriceBeforeTax(entity.getPriceAfterTax());
        entity.setMoneyBeforeTax(entity.getMoneyAfterTax());
        // TODO: close the popup and return to EditInvoiceView2
        ((JDialog) request.get("parentDialog")).dispose();
        createdDetailInvoice = entity;
        isSaved = true;
        // super.saveOrUpdate(entity);
        // if (!entity.getSubs().isEmpty()) {
        // getDaoHelper().getDao(DetailInvoice.class).saveOrUpdateAll(entity.getSubs());
        // }
    }

    public DetailInvoice getCreatedDetailInvoice() {
        return createdDetailInvoice;
    }

    public boolean isSaved() {
        return isSaved;
    }
}
