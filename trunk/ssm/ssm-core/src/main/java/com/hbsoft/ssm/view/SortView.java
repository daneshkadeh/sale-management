package com.hbsoft.ssm.view;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;

import com.hbsoft.ssm.util.i18n.ControlConfigUtils;

/**
 * The panel contains the view for search function.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class SortView extends JPanel {
    private static final long serialVersionUID = -4257029787878899984L;

    // Table input
    private JTable table;

    // ComboBox column name
    private JComboBox cbbNames1;
    private JComboBox cbbNames2;
    private JComboBox cbbNames3;

    // ComboBox order
    private JComboBox cbbOrder1;
    private JComboBox cbbOrder2;
    private JComboBox cbbOrder3;

    /**
     * Instantiate sort panel with a specific table.
     * 
     * @param table
     *            The table need to sort.
     */
    public SortView(JTable table) {
        super();
        this.table = table;
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 3"));
        String[] colNames = getColumnNames(table);

        add(new JLabel(ControlConfigUtils.getString("SortView.Label.Key1")));
        cbbNames1 = new JComboBox(new DefaultComboBoxModel(colNames));

        add(new JLabel(ControlConfigUtils.getString("SortView.Label.Key2")));
        cbbNames2 = new JComboBox(new DefaultComboBoxModel(colNames));

        add(new JLabel(ControlConfigUtils.getString("SortView.Label.Key3")));
        cbbNames3 = new JComboBox(new DefaultComboBoxModel(colNames));

    }

    private String[] getColumnNames(JTable tbl) {
        int numOfCols = tbl.getColumnCount();
        String[] colNames = new String[numOfCols];
        for (int i = 0; i < numOfCols; i++) {
            colNames[i] = tbl.getColumnName(i);
        }
        return colNames;
    }
}
