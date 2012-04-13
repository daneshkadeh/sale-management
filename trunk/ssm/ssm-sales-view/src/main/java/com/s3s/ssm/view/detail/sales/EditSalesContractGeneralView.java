package com.s3s.ssm.view.detail.sales;

import java.util.Map;

import com.s3s.ssm.entity.sales.ContractDocument;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.entity.sales.SalesContractStatus;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditSalesContractGeneralView extends AbstractSingleEditView<SalesContract> {

    private static final String REF_DOCUMENTS = "REF_DOCUMENTS";
    private static final String REF_CONTRACT_STATUS = "REF_CONTRACT_STATUS";

    public EditSalesContractGeneralView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, SalesContract entity,
            Map<String, Object> request) {
        detailDataModel.tab("General", "General info", null);
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("dateContract", DetailFieldType.DATE);
        detailDataModel.addAttribute("supplier", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_SUPPLIER);
        detailDataModel.addAttribute("moneyBeforeTax", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("moneyOfTax", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("moneyAfterTax", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_CONTRACT_STATUS);
        detailDataModel.tab("Article 1", "Article 1", null);
        detailDataModel.addAttribute("remarkQuantity", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("remarkPacking", DetailFieldType.TEXTBOX);

        detailDataModel.tab("Article 2", "Article 2", null);
        detailDataModel.addAttribute("remarkShipment", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("isPartialShipment", DetailFieldType.CHECKBOX);
        detailDataModel.addAttribute("discharginPort", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("loadingPort", DetailFieldType.TEXTBOX);

        detailDataModel.tab("Article 3", "Article 3", null);
        detailDataModel.addAttribute("remarkPayment", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("listDocuments", DetailFieldType.MULTI_SELECT_LIST_BOX).referenceDataId(
                REF_DOCUMENTS);
        detailDataModel.addAttribute("otherRequirement", DetailFieldType.TEXTAREA);

        detailDataModel.tab("Article 4", "Article 4", null);
        detailDataModel.addAttribute("importLicence", DetailFieldType.TEXTAREA);

        detailDataModel.tab("Article 5", "Article 5", null);
        detailDataModel.addAttribute("forceMajeure", DetailFieldType.TEXTAREA);

        detailDataModel.tab("Article 6", "Article 6", null);
        detailDataModel.addAttribute("arbitrarion", DetailFieldType.TEXTAREA);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, SalesContract entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_CONTRACT_STATUS, SalesContractStatus.values());
        // TODO: Maybe only find IN_FOREIGN?
        refDataModel.putRefDataList(REF_DOCUMENTS, daoHelper.getDao(ContractDocument.class).findAll());
    }
}
