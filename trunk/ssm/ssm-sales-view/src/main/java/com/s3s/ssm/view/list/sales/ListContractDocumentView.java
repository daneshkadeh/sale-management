package com.s3s.ssm.view.list.sales;

import com.s3s.ssm.entity.sales.ContractDocument;
import com.s3s.ssm.view.detail.sales.EditContractDocumentView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListContractDocumentView extends AListEntityView<ContractDocument> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("documentPlace", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ContractDocument>> getEditViewClass() {
        return EditContractDocumentView.class;
    }

}
