package com.hbsoft.ssm.view.list;

import java.util.List;

import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.view.AbstractDetailView;
import com.hbsoft.ssm.view.AbstractListView;
import com.hbsoft.ssm.view.detail.EditMasterInvoiceView;

/**
 * Show list of invoices.
 * 
 * @author phamcongbang
 * 
 */
public class ListInvoiceView extends AbstractListView<Invoice> {
    private static final long serialVersionUID = -9117198848678726047L;

    @Override
    protected void initialPresentationView(List<DetailDataModel> listDataModel) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));

        DetailDataModel createdDateField = new DetailDataModel("createdDate", FieldTypeEnum.TEXT_BOX);
        listDataModel.add(createdDateField);

        listDataModel.add(new DetailDataModel("customerId", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("totalBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("taxTotal", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("totalAfterTax", FieldTypeEnum.TEXT_BOX));
    }

    @Override
    protected List<Invoice> loadData() {
        return ConfigProvider.getInstance().getInvoiceService().findAll();
    }

    @Override
    protected Class<? extends AbstractDetailView<Invoice>> getDetailViewClass() {
        return EditMasterInvoiceView.class;
    }

}