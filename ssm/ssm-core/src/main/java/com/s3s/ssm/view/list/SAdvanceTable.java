/*
 * SAdvanceTable
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

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.hssf.record.formula.functions.T;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.hyperlink.AbstractHyperlinkAction;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.HyperlinkProvider;
import org.jdesktop.swingx.table.DatePickerCellEditor;

import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.model.ReferenceDataModel.ReferenceData;
import com.s3s.ssm.view.component.MoneyComponent;

/**
 * @author Phan Hong Phuc
 * @since Apr 7, 2012
 */
public class SAdvanceTable extends JXTable {
    private static final long serialVersionUID = 6141561051329763125L;
    private static final int EDITOR_HEIGHT = 25;
    private ListDataModel listDataModel;
    private ReferenceDataModel refDataModel;

    public SAdvanceTable(TableModel tableModel, ListDataModel listDataModel, ReferenceDataModel refDataModel) {
        super(tableModel);
        this.listDataModel = listDataModel;
        this.refDataModel = refDataModel;
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        addHighlighter(HighlighterFactory.createSimpleStriping());
        // Highlight the row when mouse over.
        addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, UIManager.getColor("Table.dropLineColor"),
                null));

        int selectionMode = listDataModel.isEditable() ? ListSelectionModel.SINGLE_SELECTION
                : ListSelectionModel.SINGLE_INTERVAL_SELECTION;
        setSelectionMode(selectionMode);
        setColumnControlVisible(true);
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        setRenderer();
        setEditor();

        // The table is already sorted by Hibernate (done in AbstractListView)
        // setSorter();

        if (listDataModel.isEditable()) {
            setRowHeight(EDITOR_HEIGHT);
        }
    }

    @Override
    public void packAll() {
        super.packAll();
        setColumnWidth();
    }

    private void setColumnWidth() {
        for (int i = 0; i < listDataModel.getColumns().size(); i++) {
            TableColumn tc = getColumnModel().getColumn(i);
            ColumnModel cm = listDataModel.getColumn(i);
            if (cm.getWidth() != null) {
                tc.setPreferredWidth(cm.getWidth());
                tc.setWidth(cm.getWidth());
            }
            if (cm.getMaxWidth() != null) {
                tc.setMaxWidth(cm.getMaxWidth());
            }
            if (cm.getMinWidth() != null) {
                tc.setMinWidth(cm.getMinWidth());
            }
        }
    }

    /**
     * @param ldm
     * @param mainTable
     */
    private void setSorter() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(getModel());
        // Swing API: The precedence of the columns in the sort is indicated by the order of the sort keys in the sort
        // key list.
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        List<ColumnModel> columnModels = listDataModel.getColumns();
        for (int i = 0; i < columnModels.size(); i++) {
            for (int j = 0; j < columnModels.size(); j++) {
                ColumnModel cm = columnModels.get(j);
                if (cm.isSorted() && (cm.getPrecedence() == i)) {
                    sortKeys.add(new RowSorter.SortKey(j, cm.getSortOrder()));
                }
            }
        }
        sorter.setSortKeys(sortKeys);
        setRowSorter(sorter);
    }

    private void setRenderer() {
        for (int i = 0; i < listDataModel.getColumns().size(); i++) {
            final ColumnModel columnModel = listDataModel.getColumns().get(i);
            TableColumn column = getColumnModel().getColumn(i);
            switch (columnModel.getRendererType()) {
            case LINK:
                column.setCellRenderer(new DefaultTableRenderer(new HyperlinkProvider(new AbstractHyperlinkAction<T>() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Phuc
                        JOptionPane.showConfirmDialog(SAdvanceTable.this, "Test");
                        int selectedRow = getSelectedRow();
                        int rowModelIndex = convertRowIndexToModel(selectedRow);
                        if (columnModel.isRaw()) {

                        }
                    }
                })));
                break;
            default:
                break;
            }
        }
    }

    private void setEditor() {
        List<ColumnModel> columnModels = listDataModel.getColumns();
        for (int i = 0; i < columnModels.size(); i++) {
            TableColumn tc = getColumnModel().getColumn(i);
            ColumnModel cm = columnModels.get(i);
            TableCellEditor editor = null;
            switch (cm.getEditorType()) {
            case TEXTFIELD:
                editor = new DefaultCellEditor(new JFormattedTextField());
                break;
            case CHECKBOX:
                editor = new DefaultCellEditor(new JCheckBox());
                break;
            case DATE_PICKER:
                editor = new DatePickerCellEditor();
                break;
            case COMBOBOX:
                Object[] listData = refDataModel.getRefDataListMap().get(cm.getReferenceDataId()).getValues().toArray();
                JComboBox<?> comboBox = new JComboBox<>(listData);
                editor = new DefaultCellEditor(comboBox);
                break;
            case MONEY:
                ReferenceData refData = refDataModel.getRefDataListMap().get(cm.getReferenceDataId());
                MoneyComponent moneyCom = new MoneyComponent(Money.zero(String.valueOf(refData.getValues().get(0))),
                        refData.getValues());
                editor = new MoneyCellEditor(moneyCom);
                break;
            default:
                break;
            }
            tc.setCellEditor(editor);
        }
    }

    @Override
    public void changeSelection(int row, int column, boolean toggle, boolean extend) {
        if (listDataModel.isEditable() && row == getRowCount() - 1) {
            ((AdvanceTableModel) getModel()).addNewRowAt(getRowCount() - 1);
        }
        super.changeSelection(row, column, toggle, extend);
        // Place cell in edit mode when it 'gains focus'
        if (listDataModel.isEditable() && listDataModel.getColumns().get(column).isEditable()
                && editCellAt(row, column)) {
            getEditorComponent().requestFocusInWindow();
        }
    }

    protected void configureColumnControl() {
        Container p = getParent();
        if (p instanceof JViewport) {
            Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) gp;
                // Make certain we are the viewPort's view and not, for
                // example, the rowHeaderView of the scrollPane -
                // an implementor of fixed columns might do this.
                JViewport viewport = scrollPane.getViewport();
                if (viewport == null || viewport.getView() != this) {
                    return;
                }
                if (isColumnControlVisible()) {
                    scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, getColumnControl());
                }
            }
        }
    }
}
