package com.hbsoft.ssm.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;

import com.hbsoft.ssm.util.i18n.ControlConfigUtils;

/**
 * The panel contains the view for sort function.
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

    // Checkbox
    private JCheckBox cbxSaveSortCriteria;
    private JCheckBox cbxSaveResultOfSort;

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
        setLayout(new MigLayout("insets 20 20 20 20, wrap 3"));

        List<String> colNamesList = getColumnNames(table);
        // Add a null value at head of list to display a blank item in combobox
        colNamesList.add(0, StringUtils.EMPTY);

        SortOrder[] orderValues = new SortOrder[] { SortOrder.ASCENDING, SortOrder.DESCENDING };
        cbbNames1 = new JComboBox(new DefaultComboBoxModel(colNamesList.toArray()));
        cbbNames2 = new JComboBox(new DefaultComboBoxModel(colNamesList.toArray()));
        cbbNames3 = new JComboBox(new DefaultComboBoxModel(colNamesList.toArray()));

        ComboboxOrderRenderer orderRenderer = new ComboboxOrderRenderer();
        cbbOrder1 = new JComboBox(orderValues);
        cbbOrder1.setRenderer(orderRenderer);
        cbbOrder2 = new JComboBox(orderValues);
        cbbOrder2.setRenderer(orderRenderer);
        cbbOrder3 = new JComboBox(orderValues);
        cbbOrder3.setRenderer(orderRenderer);
        initAndAddKeyRow("SortView.Label.Key1", cbbNames1, cbbOrder1);
        initAndAddKeyRow("SortView.Label.Key2", cbbNames2, cbbOrder2);
        initAndAddKeyRow("SortView.Label.Key3", cbbNames3, cbbOrder3);

        // Delete and Advance button
        JPanel pnl1 = new JPanel();
        JButton btnDelete = createDeleteButton();
        JButton btnAdvance = createAdvanceButton();
        pnl1.add(btnDelete);
        pnl1.add(btnAdvance);
        add(pnl1, "grow, spanx");

        // Checkbox
        cbxSaveSortCriteria = new JCheckBox(ControlConfigUtils.getString("SortView.Checkbox.SaveSortCriteria"));
        cbxSaveResultOfSort = new JCheckBox(ControlConfigUtils.getString("SortView.Checkbox.SaveResultOfSort"));
        add(cbxSaveSortCriteria, "spanx");
        add(cbxSaveResultOfSort, "spanx");

        // Separator
        add(new JSeparator(), "grow, spanx");

        // Cancel and sort button
        JPanel pnl2 = new JPanel(new MigLayout("right"));
        JButton btnCancel = createCancelButton();
        JButton btnSort = createSortButton();
        pnl2.add(btnCancel);
        pnl2.add(btnSort);
        add(pnl2, "grow, spanx");
    }

    private JButton createSortButton() {
        JButton sortButton = new JButton(ControlConfigUtils.getString("SortView.Button.Sort"));
        sortButton.addActionListener(new ActionListener() {
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
                if (StringUtils.isNotBlank((String) cbbNames1.getSelectedItem())) {
                    sortKeys.add(createSortKey(table, cbbNames1, cbbOrder1));
                }
                if (StringUtils.isNotBlank((String) cbbNames2.getSelectedItem())) {
                    sortKeys.add(createSortKey(table, cbbNames2, cbbOrder2));
                }
                if (StringUtils.isNotBlank((String) cbbNames3.getSelectedItem())) {
                    sortKeys.add(createSortKey(table, cbbNames3, cbbOrder3));
                }
                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
                sorter.setSortKeys(sortKeys);
                table.setRowSorter(sorter);

            }
        });
        return sortButton;
    }

    private SortKey createSortKey(JTable tbl, JComboBox cbbNames, JComboBox cbbOrder) {
        int columnIndex = getColumnIndex(tbl, (String) cbbNames.getSelectedItem());
        SortOrder sortOrder = (SortOrder) cbbOrder.getSelectedItem();
        return new SortKey(columnIndex, sortOrder);
    }

    private JButton createCancelButton() {
        JButton cancelButton = new JButton(ControlConfigUtils.getString("SortView.Button.Cancel"));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        return cancelButton;
    }

    private JButton createAdvanceButton() {
        JButton btnAdv = new JButton(ControlConfigUtils.getString("SortView.Button.Advance"));
        btnAdv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

        return btnAdv;
    }

    private JButton createDeleteButton() {
        JButton btnDel = new JButton(ControlConfigUtils.getString("SortView.Button.Delete"));
        btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cbbNames1.setSelectedItem(StringUtils.EMPTY);
                cbbNames2.setSelectedItem(StringUtils.EMPTY);
                cbbNames3.setSelectedItem(StringUtils.EMPTY);
            }
        });

        return btnDel;
    }

    private void initAndAddKeyRow(String stringCode, JComboBox cbbNames, JComboBox cbbOrder) {
        add(new JLabel(ControlConfigUtils.getString(stringCode)));
        add(cbbNames);
        add(cbbOrder);
    }

    private List<String> getColumnNames(JTable tbl) {
        int numOfCols = tbl.getColumnCount();
        List<String> colNames = new ArrayList<String>(numOfCols);
        for (int i = 0; i < numOfCols; i++) {
            colNames.add(tbl.getColumnName(i));
        }
        return colNames;
    }

    private int getColumnIndex(JTable tbl, String columnName) {
        for (int i = 0; i < tbl.getColumnCount(); i++) {
            if (columnName.equals(tbl.getColumnName(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Renderer for the comboBox order.
     * 
     * @author Phan Hong Phuc
     * 
     */
    private class ComboboxOrderRenderer implements ListCellRenderer {
        /**
         * {@inheritDoc}
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            SortOrder order = (SortOrder) value;
            switch (order) {
            case ASCENDING:
                return new JLabel("<");
            case DESCENDING:
                return new JLabel(">");
            default:
                break;
            }
            return null;
        }

    }
}
