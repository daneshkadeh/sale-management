package com.s3s.ssm.view.component;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.s3s.ssm.util.HBStringUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Le Thanh Hoang
 * 
 */
public class SaleTargetComp<T extends SaleTargetModel> extends JPanel {

    private static final long serialVersionUID = 1L;
    private Integer curYear;
    private List<JTextField> tfdMonthList = new ArrayList<JTextField>(12);
    private List<Double> dataMonthList = new ArrayList<Double>(12);
    private List<T> saleTargetList;
    private static int DEFAULT_TEXTFIELD_COLUMN = 10;

    JButton btnPre;
    JButton btnNext;
    JTextField txtYear;

    Container container = new Container();
    Container container1 = new Container();
    Container container2 = new Container();

    public SaleTargetComp(Integer year, List<T> saleTargetList) {
        this.curYear = year;
        this.saleTargetList = saleTargetList;
        initComponent();
    }

    void initComponent() {
        container.setLayout(new MigLayout());
        container1.setLayout(new MigLayout());
        container2.setLayout(new MigLayout("insets 10,gap 10, wrap 4, center"));

        btnPre = new JButton("<");
        btnNext = new JButton(">");
        txtYear = new JTextField();
        setDataMonthList();
        btnPre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int preYear = curYear - 1;
                if (validateYearOfSaleTarget(preYear)) {
                    // update some changes into current sale target
                    updateSaleTargetEntity(curYear);
                    curYear = preYear;
                    setDataMonthList();
                    for (int i = 0; i < 12; i++) {
                        tfdMonthList.get(i).setText(dataMonthList.get(i).toString());
                    }
                    txtYear.setText(curYear.toString());

                    SaleTargetComp.this.repaint();
                }
            }
        });

        btnNext.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Integer nextYear = curYear + 1;
                if (validateYearOfSaleTarget(nextYear)) {
                    // update some changes into current sale target
                    updateSaleTargetEntity(curYear);
                    curYear = nextYear;
                    setDataMonthList();
                    for (int i = 0; i < 12; i++) {
                        tfdMonthList.get(i).setText(dataMonthList.get(i).toString());
                    }
                    txtYear.setText(curYear.toString());

                    SaleTargetComp.this.repaint();
                }
            }
        });

        container1.add(btnPre, "center,gap 2");
        container1.add(txtYear, "center,gap 2");
        container1.add(btnNext, "wrap ,center,gap 2");
        txtYear.setText(curYear.toString());

        constructTargetView();

        container.add(container1, "wrap, center");
        container.add(container2);
        add(container);
    }

    void constructTargetView() {

        String labelMonth;
        JLabel lblMonth;

        for (int i = 0; i < 4; i++) {
            labelMonth = ControlConfigUtils.getString("SaleTargetComp.label.month" + (i + 1));
            lblMonth = new JLabel(labelMonth);
            container2.add(lblMonth, "center");
        }
        for (int i = 0; i < 4; i++) {
            JTextField field = new JTextField(dataMonthList.get(i).toString());
            field.setColumns(DEFAULT_TEXTFIELD_COLUMN);
            tfdMonthList.add(field);
            container2.add(field);
        }

        for (int i = 4; i < 8; i++) {
            labelMonth = ControlConfigUtils.getString("SaleTargetComp.label.month" + (i + 1));
            lblMonth = new JLabel(labelMonth);
            container2.add(lblMonth, "center");
        }

        for (int i = 4; i < 8; i++) {
            JTextField field = new JTextField(dataMonthList.get(i).toString());
            tfdMonthList.add(field);
            field.setColumns(DEFAULT_TEXTFIELD_COLUMN);
            container2.add(field);
        }

        for (int i = 8; i < 12; i++) {
            labelMonth = ControlConfigUtils.getString("SaleTargetComp.label.month" + (i + 1));
            lblMonth = new JLabel(labelMonth);
            container2.add(lblMonth, "center");
        }

        for (int i = 8; i < 12; i++) {
            JTextField field = new JTextField(dataMonthList.get(i).toString());
            field.setColumns(DEFAULT_TEXTFIELD_COLUMN);
            tfdMonthList.add(field);
            container2.add(field);
        }
    }

    private T findSaleTargetByYear(int year) {
        for (T saleTarget : saleTargetList) {
            if (saleTarget.getYear() == year) {
                return saleTarget;
            }
        }
        return null;
    }

    private boolean validateYearOfSaleTarget(int year) {
        for (T saleTarget : saleTargetList) {
            if (saleTarget.getYear() == year) {
                return true;
            }
        }
        return false;
    }

    private void setDataMonthList() {
        T saletarget = findSaleTargetByYear(curYear);
        dataMonthList.add(saletarget.getMonth1());
        dataMonthList.add(saletarget.getMonth2());
        dataMonthList.add(saletarget.getMonth3());
        dataMonthList.add(saletarget.getMonth4());
        dataMonthList.add(saletarget.getMonth5());
        dataMonthList.add(saletarget.getMonth6());
        dataMonthList.add(saletarget.getMonth7());
        dataMonthList.add(saletarget.getMonth8());
        dataMonthList.add(saletarget.getMonth9());
        dataMonthList.add(saletarget.getMonth10());
        dataMonthList.add(saletarget.getMonth11());
        dataMonthList.add(saletarget.getMonth12());
    }

    private void updateSaleTargetEntity(Integer year) {
        T saletarget = findSaleTargetByYear(year);
        saletarget.setMonth1(HBStringUtils.safeParseDouble(tfdMonthList.get(0).getText()));
        saletarget.setMonth2(HBStringUtils.safeParseDouble(tfdMonthList.get(1).getText()));
        saletarget.setMonth3(HBStringUtils.safeParseDouble(tfdMonthList.get(2).getText()));
        saletarget.setMonth4(HBStringUtils.safeParseDouble(tfdMonthList.get(3).getText()));
        saletarget.setMonth5(HBStringUtils.safeParseDouble(tfdMonthList.get(4).getText()));
        saletarget.setMonth6(HBStringUtils.safeParseDouble(tfdMonthList.get(5).getText()));
        saletarget.setMonth7(HBStringUtils.safeParseDouble(tfdMonthList.get(6).getText()));
        saletarget.setMonth8(HBStringUtils.safeParseDouble(tfdMonthList.get(7).getText()));
        saletarget.setMonth9(HBStringUtils.safeParseDouble(tfdMonthList.get(8).getText()));
        saletarget.setMonth10(HBStringUtils.safeParseDouble(tfdMonthList.get(9).getText()));
        saletarget.setMonth11(HBStringUtils.safeParseDouble(tfdMonthList.get(10).getText()));
        saletarget.setMonth12(HBStringUtils.safeParseDouble(tfdMonthList.get(11).getText()));
    }

    public List<T> getSaleTargetList() {
        // update current save target before returning the result
        updateSaleTargetEntity(curYear);
        return saleTargetList;
    }

}