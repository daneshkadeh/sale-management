package com.s3s.ssm.view.detail;

import com.s3s.ssm.entity.DetailInvoice;
import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditDetailInvoiceView extends AbstractDetailView<DetailInvoice> {
    private static final long serialVersionUID = 4336242795515386078L;

    public EditDetailInvoiceView(DetailInvoice entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, DetailInvoice detailInvoice) {
        detailDataModel.addAttribute("goodsId", FieldTypeEnum.TEXTBOX).enable(true);
        detailDataModel.addAttribute("goodsName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("quantity", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("priceBeforeTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("tax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX);

        detailDataModel.addAttribute("moneyBeforeTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("moneyOfTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX);

        // listDataModel.add(new DetailDataModel("invoiceId",
        // FieldTypeEnum.TEXT_BOX));
        // TODO: work-around to pass onBindAndValidate. InvoiceId will be set
        // later.
        detailInvoice.setInvoice(new Invoice());

    }

    @Override
    protected void saveOrUpdate(DetailInvoice entity2) {
        // Do nothing. Wait for saveOrUpdate by EditMasterInvoiceView
        // Set a fake id if add a new entity, pretend the entity is saved.
        if (entity2.getId() == null) {
            entity2.setId(-1L);
        }
    }

}
