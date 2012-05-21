/*
 * ListImportStoreReportDataView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.statistic.store;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import com.s3s.ssm.dto.finance.SupDebtHistoryDTO;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.interfaces.contact.IContactService;
import com.s3s.ssm.interfaces.finance.IFinanceService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.list.AListDataView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;
import com.s3s.ssm.view.util.FinanceViewHelper;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListSupDebtView extends AListDataView<SupDebtHistoryDTO> {
    private JXDatePicker fromDateComp;
    private JXDatePicker toDateComp;
    private JComboBox<Partner> cbPartner;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("hisDate", ListRendererType.DATE);
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("contentType", ListRendererType.TEXT);
        listDataModel.addColumn("content", ListRendererType.TEXT);
        listDataModel.addColumn("amt", ListRendererType.NUMBER).width(UIConstants.AMT_COLUMN_WIDTH);
        listDataModel.addColumn("payAmt", ListRendererType.NUMBER);
        listDataModel.addColumn("advanceAmt", ListRendererType.NUMBER);
        listDataModel.addColumn("debtAmt", ListRendererType.NUMBER).width(UIConstants.AMT_COLUMN_WIDTH);
    }

    @Override
    protected List<SupDebtHistoryDTO> loadData(int fistIndex, int maxResults) {
        Date fromDate = fromDateComp.getDate();
        Date toDate = toDateComp.getDate();
        Partner partner = (Partner) cbPartner.getSelectedItem();
        if (partner == null) {
            return Collections.EMPTY_LIST;
        }
        String partnerCode = "";
        List debtHistoryDTOList = serviceProvider.getService(IFinanceService.class).getDebtHistory(partnerCode,
                fromDate, toDate);
        FinanceViewHelper.transformDebtHistory(debtHistoryDTOList, partner, fromDate, toDate);
        return debtHistoryDTOList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int calculateTotalPages() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JPanel createSearchPanel() {
        List<Partner> partners = (List<Partner>) serviceProvider.getService(IContactService.class).getSuppliers();
        partners.add(0, null);
        JPanel panel = new JPanel(new MigLayout("ins 0, fill", "grow"));
        cbPartner = new JComboBox(partners.toArray());
        fromDateComp = new JXDatePicker();
        fromDateComp.setDate(new Date());

        toDateComp = new JXDatePicker();
        toDateComp.setDate(new Date());
        panel.add(new JLabel(ControlConfigUtils.getString("label.SupDebtHistoryDTO.partner")), "right");
        panel.add(cbPartner, "grow,wrap");
        panel.add(new JLabel(ControlConfigUtils.getString("label.SupDebtHistoryDTO.fromDate")), "right");
        panel.add(fromDateComp, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.SupDebtHistoryDTO.toDate")), "right");
        panel.add(toDateComp, "grow");
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void clearCriteria() {
        fromDateComp.setDate(null);
        toDateComp.setDate(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isShowPrintButton() {
        return true;
    }

}
