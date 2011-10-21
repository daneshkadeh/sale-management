package com.s3s.ssm.view.detail;

import java.util.List;

import com.s3s.ssm.entity.DetailInvoice;
import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditDetailInvoiceView extends AbstractDetailView<DetailInvoice> {
    private static final long serialVersionUID = 4336242795515386078L;

    public EditDetailInvoiceView(DetailInvoice entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel, DetailInvoice detailInvoice) {
        listDataModel.add(new DetailDataModel("goodsId", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("goodsName", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("quantity", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXTBOX));

        listDataModel.add(new DetailDataModel("moneyBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("moneyOfTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailDataModel("moneyAfterTax", FieldTypeEnum.TEXTBOX));

        // listDataModel.add(new DetailDataModel("invoiceId", FieldTypeEnum.TEXT_BOX));
        // TODO: work-around to pass onBindAndValidate. InvoiceId will be set later.
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
