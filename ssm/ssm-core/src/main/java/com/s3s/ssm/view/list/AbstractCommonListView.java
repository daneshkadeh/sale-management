/*
 * AbstractCommonListView
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.SortView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;

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
public abstract class AbstractCommonListView<T extends AbstractIdOLObject> extends AbstractListView<T> {
    private static final long serialVersionUID = -7157596822400727053L;
    JFrame detailFrame = new JFrame();

    @Override
    protected JToolBar createButtonToolBar(final JTable table) {
        JToolBar pnlButton = new JToolBar();

        JButton btnDisplayAll = new JButton(ControlConfigUtils.getString("ListView.Common.Button.DisplayAll"));
        btnDisplayAll.addActionListener(new ActionListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e) {
                // AdvanceTableModel tableModel = (AdvanceTableModel) table.getModel();
                // tableModel.showAllRows();
            }
        });

        JButton btnDisplaySelectedRow = new JButton(
                ControlConfigUtils.getString("ListView.Common.Button.DisplaySelectedRow"));
        btnDisplaySelectedRow.addActionListener(new ActionListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e) {
                // AdvanceTableModel tableModel = (AdvanceTableModel) table.getModel();
                // tableModel.hideRows(getUnselectedRows(table));
            }
        });

        JButton btnSearch = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Search"));

        JButton btnSort = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Sort"));
        btnSort.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        showPanelInNewFrame(new SortView(table));
                    }
                });

            }
        });

        JButton btnPrint = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Print"));
        JButton btnImport = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Import"));
        JButton btnExport = new JButton(ControlConfigUtils.getString("ListView.Common.Button.Export"));
        JButton btnDeleteRow = new JButton(ControlConfigUtils.getString("ListView.Common.Button.DeleteSelectedRow"));

        JButton btnInsertRow = new JButton(ControlConfigUtils.getString("ListView.Common.Button.InsertRow"));
        btnInsertRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDetailView();
            }

        });

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

    protected AbstractSingleEditView<T> getDetailView() {
        return null;
    }

    protected void openDetailView() {
        AbstractSingleEditView detailView = getDetailView();
        if (detailView != null) {
            detailView.setEnabled(true);
            detailView.setListView(this);

            detailFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            detailFrame.setContentPane(detailView);
            detailFrame.setVisible(true);
            detailFrame.repaint();
            detailFrame.pack();
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void showPanelInNewFrame(JPanel panel) {
        // Create and set up the window.
        final JFrame frame = new JFrame("Sort");
        frame.setLayout(new MigLayout("", "grow, fill"));
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Get selected model indices of rows.
     * 
     * @param table
     * @return
     */
    private int[] getUnselectedRows(JTable table) {
        int unselectedRowCount = table.getRowCount() - table.getSelectedRowCount();
        int[] unselectedRows = new int[unselectedRowCount];
        int k = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            if (!table.getSelectionModel().isSelectedIndex(i)) {
                unselectedRows[k++] = table.convertRowIndexToModel(i);
            }
        }

        return unselectedRows;
    }

}
