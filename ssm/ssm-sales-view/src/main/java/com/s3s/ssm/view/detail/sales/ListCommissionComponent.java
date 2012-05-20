package com.s3s.ssm.view.detail.sales;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.entity.sales.Commission;
import com.s3s.ssm.entity.sales.CommissionType;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.ServiceProvider;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * TODO: not implemented.
 * 
 * @author phamcongbang
 * 
 */
public class ListCommissionComponent extends AListComponent<Commission> {
    private static final long serialVersionUID = 5336927585382407587L;
    private static final String REF_CURRENCY = "REF_CURRENCY";
    private static final String REF_COM_TYPE = "REF_COM_TYPE";
    private Money sumCommissionAmount = Money.zero(CurrencyEnum.VND);

    public ListCommissionComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
        calSumCommission(getData());
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("type", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_COM_TYPE);
        listDataModel.addColumn("commissionMoney", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120).summarized();
        listDataModel.addColumn("remark", ListRendererType.TEXT).width(UIConstants.REMARK_COLUMN_WIDTH_300);

    }

    @Override
    protected void doRowUpdated(String attributeName, Commission entityUpdated, List<Commission> entities) {
        super.doRowUpdated(attributeName, entityUpdated, entities);
        if ("commissionMoney".equals(attributeName)) {
            calSumCommission(entities);
        } else if ("type".equals(attributeName)) {
            CommissionType commissionType = entityUpdated.getType();
            if (commissionType != null) {
                // TODO: If is percent, calculate commissionMoney base on DetailInvoices
                entityUpdated.setCommissionMoney(commissionType.getCommissionMoney());
            }
        }
    }

    @Override
    protected void doRowDelete(int[] deletedIndex) {
        super.doRowDelete(deletedIndex);
        calSumCommission(getData());
    }

    private void calSumCommission(List<Commission> entities) {
        sumCommissionAmount = Money.zero(CurrencyEnum.VND);
        for (Commission commission : entities) {
            sumCommissionAmount = sumCommissionAmount.plus(commission.getCommissionMoney());
        }

    }

    public Money getSumCommissionAmount() {
        return sumCommissionAmount;
    }

    @Override
    protected ReferenceDataModel initReferenceDataModel() {
        ReferenceDataModel refDataModel = super.initReferenceDataModel();
        ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();
        refDataModel.putRefDataList(REF_CURRENCY, serviceProvider.getService(IConfigService.class).getCurrencyCodes());
        refDataModel.putRefDataList(REF_COM_TYPE, getDaoHelper().getDao(CommissionType.class).findAllActive());
        return refDataModel;
    }

}
