package com.s3s.ssm.view.detail;

import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import com.s3s.ssm.entity.Goods;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

/**
 * The detail screen of {@link Goods} entity.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class EditGoodsView extends AbstractDetailView<Goods> {
    private static final long serialVersionUID = 8247461633468843994L;
    private static final String TAX_REF_ID = "0";

    public EditGoodsView(Goods entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Goods goods) {
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("priceBeforeTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("tax", FieldTypeEnum.DROPDOWN).referenceDataId(TAX_REF_ID);
        detailDataModel.addAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("addQuantity", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("curQuantity", FieldTypeEnum.TEXTBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Goods entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Double> taxRefs = Arrays.asList(1.0, 2.0, 3.0);
        DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel cell = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (cellHasFocus) {
                    cell.setBackground(Color.GREEN);
                }

                if (new Double(1.0).equals(value)) {
                    cell.setBackground(Color.RED);
                    cell.setText("HPP");
                } else if (new Double(2.0).equals(value)) {
                    cell.setBackground(Color.YELLOW);
                } else if (new Double(3.0).equals(value)) {
                    cell.setBackground(Color.BLUE);
                }
                return cell;
            }
        };
        refDataModel.putRefDataList(TAX_REF_ID, refDataModel.new ReferenceData(taxRefs, listCellRenderer));
    }
}
