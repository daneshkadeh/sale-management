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
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.DaoHelper;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.SClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.list.renderer.FooterRenderer;
import com.s3s.ssm.view.list.renderer.RowHeaderRenderer;

/**
 * The list component, turn off the break page function.
 * 
 * @author Phan Hong Phuc
 * @since Apr 21, 2012
 */
public abstract class AListComponent<T> extends JPanel implements TableModelListener {
    private static final long serialVersionUID = -1311942671249671111L;
    private static final int NUM_ROW_VISIBLE = 10;
    private static final Log logger = LogFactory.getLog(AListComponent.class);

    protected DaoHelper daoHelper = ConfigProvider.getInstance().getDaoHelper();

    private SAdvanceTable mainTable;
    private JList<String> rowHeader;
    private JXTable tblFooter;
    private JBusyComponent<JScrollPane> busyPane;

    private Action insAction;
    private Action deleteAction;

    private boolean isInsertRowAllowed = true;
    private boolean isDeleteRowAllowed = true;

    private JButton insBtn;
    private JButton delBtn;

    private AdvanceTableModel<T> mainTableModel;

    private List<ChangeListener> listeners = new ArrayList<>();

    // This model is used by sub classes.
    protected ListDataModel listDataModel = new ListDataModel();
    private ReferenceDataModel refDataModel = new ReferenceDataModel();

    public AListComponent(Icon icon, String label, String tooltip) {
        initialPresentationView(listDataModel);
        this.refDataModel = initReferenceDataModel();

        this.setLayout(new MigLayout("ins 0, wrap", "grow, fill", "[]0[]0[]0[]"));
        // setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Test", TitledBorder.LEFT,
        // TitledBorder.TOP));
        insAction = new InsertRowAction();
        deleteAction = new DeleteRowAction();

        addComponents();
        addKeyBindings();
    }

    protected void addKeyBindings() {
        InputMap inputMap = mainTable.getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        KeyStroke addShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK);
        KeyStroke deleteShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);

        inputMap.put(addShortkey, "insKeyAction");
        inputMap.put(deleteShortkey, "delKeyAction");

        ActionMap actionMap = mainTable.getActionMap();
        actionMap.put("insKeyAction", insAction);
        actionMap.put("delKeyAction", deleteAction);
    }

    private class InsertRowAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isInsertRowAllowed) {
                mainTableModel.addRowAt(mainTableModel.getData().size(), createNewEntity());
            }
        }
    }

    private class DeleteRowAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isDeleteRowAllowed) {
                int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AListComponent.this),
                        "Are you sure want to delete the selected row?", "Confirm delete",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    int[] selectedRows = mainTable.getSelectedRows();
                    int[] selectedModelRows = new int[selectedRows.length];
                    for (int i = 0; i < selectedRows.length; i++) {
                        int rowModelIndex = mainTable.convertRowIndexToModel(selectedRows[i]);
                        selectedModelRows[i] = rowModelIndex;
                    }
                    mainTableModel.deleteRows(selectedRows);
                }
            }
        }
    }

    /**
     * Return the number of rows visibled. The default return value is {@link #getPageSize()}. It should be overrided.
     * 
     * @return a number of visible rows.
     */
    protected int getVisibleRowCount() {
        return NUM_ROW_VISIBLE;
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

        add(createButtonsPanel());

        mainTableModel = new AdvanceTableModel<T>(listDataModel, new ArrayList<T>(), getEntityClass(), true,
                getVisibleRowCount());
        mainTable = new SAdvanceTable(mainTableModel, listDataModel, refDataModel);
        mainTable.setVisibleRowCount(getVisibleRowCount());

        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JMenuItem menuItem;

                    // Create the popup menu.
                    JPopupMenu popup = new JPopupMenu();
                    menuItem = new JMenuItem("A popup menu item");
                    // menuItem.addActionListener(this);
                    popup.add(menuItem);
                    menuItem = new JMenuItem("Another popup menu item");
                    // menuItem.addActionListener(this);
                    popup.add(menuItem);
                    popup.show(mainTable, e.getX(), e.getY());
                }
                // if () {
                // JXTable target = (JXTable)e.getSource();
                // int row = target.getSelectedRow();
                // int column = target.getSelectedColumn();
                // TODO Phuc
                // }
            }
        });

        mainTableModel.addTableModelListener(this);

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
                super.columnMarginChanged(event);
                TableColumnModel eventModel = (DefaultTableColumnModel) event.getSource();
                TableColumnModel thisModel = getColumnModel();
                int columnCount = eventModel.getColumnCount();

                for (int i = 0; i < columnCount; i++) {
                    thisModel.getColumn(i).setWidth(eventModel.getColumn(i).getWidth());
                }
                repaint();
            }
        };

        tblFooter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblFooter.setTableHeader(null); // Remove table header.
        tblFooter.setVisibleRowCount(1);
        tblFooter.setRowSelectionAllowed(false);
        tblFooter.setGridColor(Color.LIGHT_GRAY);
        tblFooter.setFont(UIConstants.DEFAULT_BOLD_FONT);
        tblFooter.setRowHeight(mainTable.getRowHeight());
        tblFooter.setDefaultRenderer(Money.class, new FooterRenderer());
        tblFooter.setDefaultRenderer(Number.class, new FooterRenderer());
        tblFooter.setDefaultRenderer(Object.class, new FooterRenderer());

        mainTable.getColumnModel().addColumnModelListener(tblFooter);
        // The rowHeader show the order number for the rows of the main table.
        rowHeader = new JList<String>(new AbstractListModel<String>() {
            private static final long serialVersionUID = -771503812711976068L;

            @Override
            public int getSize() {
                return mainTableModel.getRowCount();
            }

            @Override
            public String getElementAt(int index) {
                int numOfEntities = mainTableModel.getData().size();
                if (index >= numOfEntities) {
                    return "x";
                }
                return String.valueOf(index + 1);
            }
        });

        rowHeader.setCellRenderer(new RowHeaderRenderer(mainTable));
        rowHeader.setFixedCellWidth(UIConstants.DEFAULT_ROW_HEADER_WIDTH);
        rowHeader.setFixedCellHeight(mainTable.getRowHeight());

        JScrollPane mainScrollpane = new JScrollPane(mainTable);
        mainScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainScrollpane.getViewport().setBackground(Color.WHITE);
        mainScrollpane.setRowHeaderView(rowHeader);
        busyPane = createBusyPane(mainScrollpane);

        add(busyPane);
        JScrollPane footerScrollpane = new JScrollPane(tblFooter);
        footerScrollpane.getHorizontalScrollBar().setModel(mainScrollpane.getHorizontalScrollBar().getModel());
        footerScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        footerScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // The vertical dummy bar for footer table.
        JScrollBar dummyBar = new JScrollBar() {
            private static final long serialVersionUID = 8512878657569440966L;

            @Override
            public void paint(Graphics g) {
            }
        };
        dummyBar.setPreferredSize(footerScrollpane.getVerticalScrollBar().getPreferredSize());
        footerScrollpane.setVerticalScrollBar(dummyBar);

        JLabel sumLabel = new JLabel();
        sumLabel.setPreferredSize(new Dimension(UIConstants.DEFAULT_ROW_HEADER_WIDTH, tblFooter.getRowHeight()));
        // sumLabel.setOpaque(true);
        // sumLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        footerScrollpane.setRowHeaderView(sumLabel);

        // Show the footer if existing a column summarized.
        boolean isFooterShown = false;
        for (ColumnModel column : listDataModel.getColumns()) {
            if (column.isSummarized()) {
                add(footerScrollpane);
                isFooterShown = true;
                break;
            }
        }
        if (!isFooterShown) {
            mainScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            mainScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
        JPanel footerPanel = createFooterPanel(mainTableModel);
        if (footerPanel != null) {
            add(footerPanel, "growx");
        }
    }

    /**
     * @return
     */
    private JToolBar createButtonsPanel() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        insBtn = new JButton(insAction);
        insBtn.setIcon(ImageUtils.getSmallIcon(ImageConstants.INSERT_ROW_ICON));
        insBtn.setText(ControlConfigUtils.getString("AListComponent.insertRow"));
        insBtn.setToolTipText(ControlConfigUtils.getString("AListComponent.insertRow") + " (Ctrl+I)");
        delBtn = new JButton(deleteAction);
        delBtn.setIcon(ImageUtils.getSmallIcon(ImageConstants.DEL_ROW_ICON));
        delBtn.setText(ControlConfigUtils.getString("AListComponent.delRow"));
        delBtn.setToolTipText(ControlConfigUtils.getString("AListComponent.delRow") + " (Ctrl+D)");
        tb.add(insBtn);
        tb.add(delBtn);
        return tb;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        List<T> entities = mainTableModel.getData();
        if (e.getType() == TableModelEvent.UPDATE) {
            if (e.getColumn() == TableModelEvent.ALL_COLUMNS) {
                return;
            }
            String attributeName = listDataModel.getColumn(e.getColumn()).getName();
            doRowUpdated(attributeName, entities.get(e.getFirstRow()), entities);
        } else if (e.getType() == TableModelEvent.INSERT) {
            doRowInsert(entities.get(e.getFirstRow()), entities);
        } else if (e.getType() == TableModelEvent.DELETE) {
            doRowDelete(entities);
        }
        fireStateChange();
        mainTable.repaint();
        mainTable.revalidate();
        rowHeader.repaint();
        rowHeader.revalidate();
    }

    /**
     * Perform after a row is inserted.
     * 
     * @param rowIndex
     * @param entities
     */
    protected void doRowInsert(T entityInserted, List<T> entities) {
        // Template method
    }

    /**
     * Perform after a row is updated.
     * 
     * @param attributeName
     *            the name of
     * @param entityUpdated
     * @param entities
     */
    protected void doRowUpdated(String attributeName, T entityUpdated, List<T> entities) {
        // Template method
    }

    /**
     * Perform after a row is deleted.
     * 
     * @param entities
     */
    protected void doRowDelete(List<T> entities) {
        // Template method
    }

    public void setInsertRowAllowed(boolean allowed) {
        isInsertRowAllowed = allowed;
        insBtn.setVisible(allowed);
    }

    public void setDeleteRowAllowed(boolean allowed) {
        isDeleteRowAllowed = allowed;
        delBtn.setVisible(allowed);
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

    private void fireStateChange() {
        ChangeEvent e = new ChangeEvent(this);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(e);
        }
    }

    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
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
            ColumnModel column = listDataModel.getColumns().get(columnIndex);
            if (column.isSummarized()) {
                Class<?> fieldClass = SClassUtils.getClassOfField(column.getName(), getEntityClass());
                if (ClassUtils.isAssignable(fieldClass, Integer.class)) {
                    int sum = 0;
                    for (int i = 0; i < mainTableModel.getRowCount(); i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum + ((value == null) ? 0 : (int) value);
                    }
                    return sum;
                }

                if (ClassUtils.isAssignable(fieldClass, Long.class)) {
                    long sum = 0L;
                    for (int i = 0; i < mainTableModel.getRowCount(); i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum + ((value == null) ? 0 : (long) value);
                    }
                    return sum;
                }

                if (ClassUtils.isAssignable(fieldClass, Float.class)) {
                    float sum = 0f;
                    for (int i = 0; i < mainTableModel.getRowCount(); i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum + ((value == null) ? 0 : (float) value);
                    }
                    return sum;
                }

                if (ClassUtils.isAssignable(fieldClass, Double.class)) {
                    double sum = 0d;
                    for (int i = 0; i < mainTableModel.getRowCount(); i++) {
                        Object value = mainTableModel.getValueAt(i, columnIndex);
                        sum = sum + ((value == null) ? 0 : (double) value);
                    }
                    return sum;
                }

                if (ClassUtils.isAssignable(fieldClass, Money.class)) {
                    CurrencyEnum currencyCode = CurrencyEnum.VND; // TODO Phuc: get from organizationContext later
                    Money sum = Money.zero(currencyCode);
                    for (int i = 0; i < mainTableModel.getRowCount(); i++) {
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

    public void setEditable(boolean editable) {
        mainTable.setEditable(editable);
        setInsertRowAllowed(editable);
        setDeleteRowAllowed(editable);
    }

    public List<T> getData() {
        return mainTableModel.getData();
    }

    public void setData(Collection<T> data) {
        mainTableModel.setData(data);
        mainTable.packAll();
    }

    public DaoHelper getDaoHelper() {
        return daoHelper;
    }
}
