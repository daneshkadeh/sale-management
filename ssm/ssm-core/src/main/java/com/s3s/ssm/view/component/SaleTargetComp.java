package com.s3s.ssm.view.component;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Thanh Hoang
 * 
 */
public class SaleTargetComp extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String[] MONTHS = { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
            "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" };
    private Date date;
    JButton btnPre;
    JButton btnBack = new JButton(">");
    JTextField txtYear;

    public SaleTargetComp() {
        this(new Date());
    }

    public SaleTargetComp(Date date) {
        this.date = date;
        initComponent();
    }

    void initComponent() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String strYear = simpleDateFormat.format(this.date);

        Container container = new Container();
        Container container1 = new Container();
        Container container2 = new Container();

        container.setLayout(new MigLayout());

        container1.setLayout(new MigLayout());

        btnPre = new JButton("<");
        btnBack = new JButton(">");
        txtYear = new JTextField(strYear);

        container1.add(btnPre, "center,gap 2");
        container1.add(txtYear, "center,gap 2");
        container1.add(btnBack, "wrap ,center,gap 2");

        btnPre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // ToDo: Implement the function for preBtn

            }
        });

        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // ToDo: Implement the function for preBtn

            }
        });

        container2.setLayout(new MigLayout("insets 10,gap 10, wrap 4, center"));

        JLabel l[];
        l = new JLabel[MONTHS.length];

        for (int i = 0; i < 4; i++) {
            l[i] = new JLabel(MONTHS[i]);
            container2.add(l[i], "center");
        }
        for (int i = 0; i < 4; i++) {
            container2.add(new JTextField(10));
        }
        for (int i = 4; i < 8; i++) {
            l[i] = new JLabel(MONTHS[i]);
            container2.add(l[i], "center");
        }

        for (int i = 0; i < 4; i++) {
            container2.add(new JTextField(10));
        }

        for (int i = 8; i < 12; i++) {
            l[i] = new JLabel(MONTHS[i]);
            container2.add(l[i], "center");
        }

        for (int i = 0; i < 4; i++) {
            container2.add(new JTextField(10));
        }

        container.add(container1, "wrap, center");
        container.add(container2);
        this.add(container);
    }

}