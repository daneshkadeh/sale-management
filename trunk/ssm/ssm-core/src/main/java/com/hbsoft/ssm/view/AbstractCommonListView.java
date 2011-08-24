package com.hbsoft.ssm.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.hbsoft.ssm.util.i18n.ControlConfigUtils;

/**
 * The list view containing buttons of the common list screen:
 * <ul>
 * <li>Display all</li>
 * <li>Display selected row</li>
 * <li>Search</li>
 * <li>Sort</li>
 * <li>Print</li>
 * <li>Import</li>
 * <li>Export</li>
 * <li>Delete selected row</li>
 * <li>Insert row</li>
 * <li>Close</li>
 * </ul>
 * 
 * @see AbstractStatisticListView
 * @see AbstractListView
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractCommonListView<T> extends AbstractListView<T> {
    private static final long serialVersionUID = -7157596822400727053L;

    @Override
    protected JPanel createButtonPanel(JTable table) {
        JPanel pnlButton = new JPanel();
        JButton btnDisplayAll = new JButton(ControlConfigUtils.getString("ListView.Common.Button.DisplayAll"));
        JButton btnDisplaySelectedRow = new JButton(
                ControlConfigUtils.getString("ListView.Common.Button.DisplaySelectedRow"));
        JButton btnSearch = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Search"));
        JButton btnSort = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Sort"));
        JButton btnPrint = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Print"));
        JButton btnImport = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Import"));
        JButton btnExport = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Export"));
        JButton btnDeleteRow = new JButton(ControlConfigUtils.getString("ListView.Common.Button.DeleteSelectedRow"));
        JButton btnInsertRow = new JButton(ControlConfigUtils.getString("ListView.Common.Button.InsertRow"));
        JButton btnClose = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Close"));

        pnlButton.add(btnDisplayAll);
        pnlButton.add(btnDisplaySelectedRow);
        pnlButton.add(btnSearch);
        pnlButton.add(btnSort);
        pnlButton.add(btnPrint);
        pnlButton.add(btnImport);
        pnlButton.add(btnExport);
        pnlButton.add(btnDeleteRow);
        pnlButton.add(btnInsertRow);
        pnlButton.add(btnClose);

        return pnlButton;
    }

}
