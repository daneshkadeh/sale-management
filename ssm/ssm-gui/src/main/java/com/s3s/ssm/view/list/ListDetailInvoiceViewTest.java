/*
 * ListDetailInvoiceViewTest
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view.list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.s3s.ssm.entity.DetailInvoiceTest;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractCommonListView;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.detail.EditDetailInvoiceViewTest;

@Deprecated
public class ListDetailInvoiceViewTest extends AbstractCommonListView<DetailInvoiceTest> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("id", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("goodsId", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("goodsName", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("quantity", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("tax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX));

        listDataModel.add(new DetailAttribute("moneyBeforeTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("moneyOfTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected JToolBar createButtonToolBar(JTable table) {
        JToolBar pnlButton = new JToolBar();
        JButton btnInsertRow = new JButton(ControlConfigUtils.getString("ListView.Common.Button.InsertRow"));
        btnInsertRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDetailView();
            }

        });
        pnlButton.add(btnInsertRow);
        return pnlButton;
    }

    @Override
    protected Class<? extends AbstractEditView<DetailInvoiceTest>> getEditViewClass() {
        // TODO Auto-generated method stub
        return EditDetailInvoiceViewTest.class;
    }

}
