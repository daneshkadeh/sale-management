package com.hbsoft.ssm.view.detail;

import java.util.List;

import com.hbsoft.ssm.entity.DetailInvoice;
import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.view.AbstractDetailView;

public class EditDetailInvoiceView extends AbstractDetailView<DetailInvoice> {
    private static final long serialVersionUID = 4336242795515386078L;

    public EditDetailInvoiceView(DetailInvoice entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(List<DetailDataModel> listDataModel, DetailInvoice detailInvoice) {
        listDataModel.add(new DetailDataModel("goodsId", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("goodsName", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("quantity", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXT_BOX));

        listDataModel.add(new DetailDataModel("moneyBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("moneyOfTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("moneyAfterTax", FieldTypeEnum.TEXT_BOX));

        // listDataModel.add(new DetailDataModel("invoiceId", FieldTypeEnum.TEXT_BOX));
        // TODO: work-around to pass onBindAndValidate. InvoiceId will be set later.
        detailInvoice.setInvoice(new Invoice());

    }

    @Override
    protected void saveOrUpdate(DetailInvoice entity2) {
        // Do nothing. Wait for saveOrUpdate by EditMasterInvoiceView
    }

}
