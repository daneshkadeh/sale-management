package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.ContractDocument;
import com.s3s.ssm.entity.sales.ContractDocument.DocumentPlaceEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditContractDocumentView extends AbstractSingleEditView<ContractDocument> {

    private static final String REF_DOCUMENT_PLACE = "REF_DOCUMENT_PLACE";

    public EditContractDocumentView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ContractDocument entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("documentPlace", DetailFieldType.DROPDOWN).referenceDataId(REF_DOCUMENT_PLACE);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ContractDocument entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_DOCUMENT_PLACE, DocumentPlaceEnum.values());
    }
}
