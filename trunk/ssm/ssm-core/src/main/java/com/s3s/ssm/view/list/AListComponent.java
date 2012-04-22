/*
 * AbstractListComponent
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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.divxdede.swing.busy.DefaultBusyModel;
import org.divxdede.swing.busy.JBusyComponent;
import org.jdesktop.swingx.JXTable;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.SClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;

/**
 * The list component, turn off the break page function.
 * 
 * @author Phan Hong Phuc
 * @since Apr 21, 2012
 */
public abstract class AListComponent<T extends AbstractBaseIdObject> extends JPanel {

    private static final long serialVersionUID = -1311942671249671111L;
    // TODO It should get from the property "defPageRowNum" of BasicInformation in ssm-config
    private static final Log logger = LogFactory.getLog(AbstractListView.class);

    private JTabbedPane tabPane;
    protected JPanel contentPane;
    private SAdvanceTable mainTable;
    private JList<String> rowHeader;
    private JXTable tblFooter;
    private JBusyComponent<JScrollPane> busyPane;

    private AdvanceTableModel<T> mainTableModel;

    protected List<T> entities = new ArrayList<>();

    // This model is used by sub classes.
    protected ListDataModel listDataModel = new ListDataModel();
    private ReferenceDataModel refDataModel = new ReferenceDataModel();

    public AListComponent(Icon icon, String label, String tooltip) {
        initialPresentationView(listDataModel);
        this.refDataModel = initReferenceDataModel();

        tabPane = new JTabbedPane();
        contentPane = new JPanel(new MigLayout("wrap, ins 0, hidemode 2", "grow, fill", "[]0[]0[]0[][][]"));
        tabPane.addTab(label, icon, contentPane, tooltip);
        this.setLayout(new MigLayout("ins 0", "grow, fill", "grow, fill"));
        this.add(tabPane, "grow");

        addComponents();
        addKeyBindings();
    }

    protected void addKeyBindings() {
        // InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        //
        // KeyStroke addShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
        // KeyStroke deleteShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);
        //
        // inputMap.put(addShortkey, "addKeyAction");
        // inputMap.put(deleteShortkey, "deleteKeyAction");
        //
        // ActionMap actionMap = getActionMap();
        // actionMap.put("deleteKeyAction", deleteAction);
    }

    /**
     * Return the number of rows visibled. The default return value is {@link #getPageSize()}. It should be overrided.
     * 
     * @return a number of visible rows.
     */
    protected int getVisibleRowCount() {
        return 10;
    }

    @Override
    public boolean requestFocusInWindow() {
        return mainTable.requestFocusInWindow();
    }

    protected abstract void initialPresentationView(ListDataModel listDataModel);

    /**
     * The subclass should override this method to set data model for the editor component when the cell in edit mode.
     * 
     * @param rdm
     */
    protected ReferenceDataModel initReferenceDataModel() {
        return new ReferenceDataModel();
    }

    protected void addComponents() {
        // ///////////////// Init main table ////////////////////////////////
        mainTableModel = new AdvanceTableModel<T>(listDataModel, entities, getEntityClass(), true);
        mainTable = new SAdvanceTable(mainTableModel, listDataModel, refDataModel) {

            @Override
            public void changeSelection(int row, int column, boolean toggle, boolean extend) {
                if (row == getRowCount() - 1) {
                    ((AdvanceTableModel) getModel()).addRowAt(getRowCount() - 1, createNewEntity());
                }
                super.changeSelection(row, column, toggle, extend);
            }

        };
        mainTable.setVisibleRowCount(getVisibleRowCount());

        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // if () {
                // JXTable target = (JXTable)e.getSource();
                // int row = target.getSelectedRow();
                // int column = target.getSelectedColumn();
                // TODO Phuc
                // }
            }
        });

        // //////////////// Create footer table //////////////////////////////
        FooterTableModel footerModel = new FooterTableModel(mainTableModel);

        tblFooter = new JXTable(footerModel, mainTable.getColumnModel()) {
            private static final long serialVersionUID = -7685932666381447654L;

            /**
             * Sync column between 2 table when resize the column.
             * <p>
             * {@inheritDoc}
             */
            @Override
            public void columnMarginChanged(ChangeEvent event) {
                final TableColumnModel eventModel = (DefaultTableColumnModel) event.getSource();
                final TableColumnModel thisModel = getColumnModel();
                final int columnCount = eventModel.getColumnCount();

                for (int i = 0; i < columnCount; i++) {
                    thisModel.getColumn(i).setWidth(eventModel.getColumn(i).getWidth());
                }
                repaint();
            }
        };
        tblFooter.setTableHeader(null); // Remove table header.
        // Visible 1 row in footer table.
        // tblFooter.setPreferredScrollableViewportSize(new Dimension(
        // tblFooter.getPreferredScrollableViewportSize().width, tblFooter.getRowHeight()));
        tblFooter.setVisibleRowCount(1);
        tblFooter.setRowSelectionAllowed(false);
        tblFooter.setShowGrid(false);
        tblFooter.setFont(UIConstants.DEFAULT_BOLD_FONT);

        mainTable.getColumnModel().addColumnModelListener(tblFooter);

        // The rowHeader show the order number for the rows of the main table.
        rowHeader = new JList<String>(new AbstractListModel<String>() {
            private static final long serialVersionUID = -771503812711976068L;

            @Override
            public int getSize() {
                return entities.size() + 1;
            }

            @Override
            public String getElementAt(int index) {
                if (index == entities.size()) {
                    return "+";
                }
                return String.valueOf(index + 1);
            }
        });

        rowHeader.setCellRenderer(new RowHeaderRenderer(mainTable));
        rowHeader.setFixedCellWidth(UIConstants.DEFAULT_ROW_HEADER_WIDTH);
        rowHeader.setFixedCellHeight(mainTable.getRowHeight());
        mainTableModel.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                rowHeader.repaint();
                rowHeader.revalidate();
            }
        });

        JScrollPane mainScrollpane = new JScrollPane(mainTable);
        mainScrollpane.getViewport().setBackground(Color.WHITE);
        mainScrollpane.setRowHeaderView(rowHeader);
        busyPane = createBusyPane(mainScrollpane);

        contentPane.add(busyPane);
        JScrollPane footerScrollpane = new JScrollPane(tblFooter);

        // Show the footer if existing a column summarized.
        for (ColumnModel column : listDataModel.getColumns()) {
            if (column.isSummarized()) {
                contentPane.add(footerScrollpane);
                break;
            }
        }
        JPanel footerPanel = createFooterPanel(mainTableModel);
        if (footerPanel != null) {
            contentPane.add(footerPanel, "grow");
        }
    }

    /**
     * Override this method to add the footer panel to the list view.
     * 
     * @return
     */
    protected JPanel createFooterPanel(TableModel tableModel) {
        // The template method
        return new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Just create a panel with no size
    }

    private JBusyComponent<JScrollPane> createBusyPane(JScrollPane mainScrollpane) {
        JBusyComponent<JScrollPane> bp = new JBusyComponent<JScrollPane>(mainScrollpane);
        ((DefaultBusyModel) bp.getBusyModel()).setDescription(ControlConfigUtils.getString("label.loading"));
        return bp;
    }

    private void doDeleteRows() {
        int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this),
                "Are you sure want to delete the selected row?", "Confirm delete", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            int selectedRow = mainTable.getSelectedRow();
            int rowModelIndex = mainTable.convertRowIndexToModel(selectedRow);
            mainTableModel.deleteRows(rowModelIndex, rowModelIndex);
            rowHeader.repaint();
            rowHeader.revalidate();
        }
    }

    /**
     * Override this function to add some other attribute to the new entity. When new a row, a new entity is created and
     * initialize some field by this function.
     * 
     * @category template method
     */
    protected T createNewEntity() {
        // Template method
        try {
            return getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Fail to initialize new entity!");
        }

    }

    /**
     * Add the listener for the main table data changed.
     * 
     * @param tableModelListener
     */
    public void addTableModelListener(TableModelListener tableModelListener) {
        mainTableModel.addTableModelListener(tableModelListener);
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return (Class<T>) SClassUtils.getArgumentClass(getClass());
    }

    private class FooterTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final TableModel mainTableModel;

        public FooterTableModel(TableModel mainTableModel) {
            this.mainTableModel = mainTableModel;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == listDataModel.getColumns().size()) {
                return null;
            }
            ColumnModel column = listDataModel.getColumns().get(columnIndex); // decrease 1 because the hidden
            if (column.isSummarized()) {
                Class<?> fieldClass = SClassUtils.getClassOfField(column.getName(), getEntityClass());
                if (ClassUtils.isAssignable(fieldClass, Integer.class)) {
                    int sum = 0;
                    for (int i = 0; i < mainTableModel.getRowCount() - 1; i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum + ((value == null) ? 0 : (int) value);
                    }
                    return sum;
                }

                if (ClassUtils.isAssignable(fieldClass, Long.class)) {
                    long sum = 0L;
                    for (int i = 0; i < mainTableModel.getRowCount() - 1; i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum + ((value == null) ? 0 : (long) value);
                    }
                    return sum;
                }

                if (ClassUtils.isAssignable(fieldClass, Float.class)) {
                    float sum = 0f;
                    for (int i = 0; i < mainTableModel.getRowCount() - 1; i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum + ((value == null) ? 0 : (float) value);
                    }
                    return sum;
                }

                if (ClassUtils.isAssignable(fieldClass, Double.class)) {
                    double sum = 0d;
                    for (int i = 0; i < mainTableModel.getRowCount() - 1; i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum + ((value == null) ? 0 : (double) value);
                    }
                    return sum;
                }

                if (ClassUtils.isAssignable(fieldClass, Money.class)) {
                    String currencyCode = "VND"; // TODO Phuc: get from organizationContext later
                    Money sum = Money.zero(currencyCode);
                    for (int i = 0; i < mainTableModel.getRowCount() - 1; i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum.plus(((value == null) ? Money.zero(currencyCode) : (Money) value));
                    }
                    return sum;
                }

            }
            return null;
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return mainTableModel.getColumnCount();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return mainTableModel.getColumnClass(columnIndex);
        }

    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(Collection<T> entities) {
        mainTableModel.setEntities(entities);
    }

    public JTabbedPane getTabbedPane() {
        return tabPane;
    }
}
