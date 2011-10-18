package com.s3s.ssm.view.list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.s3s.ssm.entity.DetailInvoice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.FieldTypeEnum;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractCommonListView;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.detail.EditDetailInvoiceView;

@Deprecated
public class ListDetailInvoiceView extends AbstractCommonListView<DetailInvoice> {

    @Override
    protected void initialPresentationView(List<DetailDataModel> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailDataModel("id", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("goodsId", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("goodsName", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("quantity", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("tax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("priceAfterTax", FieldTypeEnum.TEXT_BOX));

        listDataModel.add(new DetailDataModel("moneyBeforeTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("moneyOfTax", FieldTypeEnum.TEXT_BOX));
        listDataModel.add(new DetailDataModel("moneyAfterTax", FieldTypeEnum.TEXT_BOX));
    }

    @Override
    protected JPanel createButtonPanel(JTable table) {
        JPanel pnlButton = new JPanel();
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
    protected List<DetailInvoice> loadData() {
        return super.loadData();
    }

    @Override
    protected Class<? extends AbstractDetailView<DetailInvoice>> getDetailViewClass() {
        // TODO Auto-generated method stub
        return EditDetailInvoiceView.class;
    }

}
