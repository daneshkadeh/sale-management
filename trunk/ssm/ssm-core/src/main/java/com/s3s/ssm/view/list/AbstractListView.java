/*
 * AbstractListView
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
import java.awt.Dialog;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.divxdede.swing.busy.DefaultBusyModel;
import org.divxdede.swing.busy.JBusyComponent;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.error.ErrorInfo;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.export.exporter.DefaultExporterFactory;
import com.s3s.ssm.export.exporter.Exporter;
import com.s3s.ssm.export.exporter.ExporterFactory;
import com.s3s.ssm.export.exporter.ExporterNotFoundException;
import com.s3s.ssm.export.exporter.ExportingException;
import com.s3s.ssm.export.view.ExportDialog;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomPermission;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.SClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.AbstractView;
import com.s3s.ssm.view.ISavedListener;
import com.s3s.ssm.view.IViewLazyLoadable;
import com.s3s.ssm.view.SavedEvent;
import com.s3s.ssm.view.component.ButtonTabComponent;
import com.s3s.ssm.view.component.IPageChangeListener;
import com.s3s.ssm.view.component.PagingNavigator;
import com.s3s.ssm.view.edit.AbstractEditView;

/**
 * This is an abstract view for list entities.
 * 
 * <p>
 * 
 * The list supports break page function with the follow methods:</br>
 * <ul>
 * <li> {@link #loadData(int)}
 * <li>{@link #getPageSize()}
 * </ul>
 * Note: when the data in current page is added or removed, the page size is not keep constantly. It's just
 * re-adjustment when we change the pageNumber.
 * 
 * @author phamcongbang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractListView<T extends AbstractBaseIdObject> extends AbstractView implements
        IPageChangeListener, IViewLazyLoadable, ISavedListener<T> {

    private static final long serialVersionUID = -1311942671249671111L;
    private static final String ADD_ACTION_KEY = "addAction";
    // TODO It should get from the property "defPageRowNum" of BasicInformation in ssm-config
    private static final int DEFAULT_PAGE_SIZE = 30;

    private static final Log logger = LogFactory.getLog(AbstractListView.class);

    private JTabbedPane tabPane;
    protected JPanel contentPane;
    private JToolBar toolbar;
    private JXTable tblListEntities;
    private JList<Integer> rowHeader;
    private JXTable tblFooter;
    private JBusyComponent<JScrollPane> busyPane;

    private AdvanceTableModel<T> mainTableModel;
    protected PagingNavigator pagingNavigator;
    // button toolbar
    protected JButton btnAdd;
    protected JButton btnDelete;
    protected JButton btnEdit;
    protected JButton btnExport;
    protected JButton btnPrint;
    protected JButton btnRefresh;

    // TODO use this flag temporarily to prevent init the view more than one time. --> Need to use the Proxy object
    // instead.
    public boolean isInitialized = false;

    protected final List<T> entities = new ArrayList<>();

    // This model is used by sub classes.
    protected final ListDataModel listDataModel = new ListDataModel();
    private ReferenceDataModel refDataModel = new ReferenceDataModel();

    private Action addAction;
    private Action editAction;
    private Action deleteAction;
    private Action exportAction;
    private Action printAction;

    // the service is used to get access rule
    private Set<CustomPermission> permissionSet;
    // export factory
    // TODO should get from service
    ExporterFactory exporterFactory = new DefaultExporterFactory();

    public AbstractListView() {
        this(new HashMap<String, Object>());
    }

    private Set<CustomPermission> getPermissionOfCurrentUser() {
        ACLResourceEnum aclResource = registerACLResource();
        // get permissions of current user
        return ConfigProvider.getInstance().getContextProvider().getPermissions(aclResource);
    }

    public AbstractListView(Icon icon, String label, String tooltip) {
        this(new HashMap<String, Object>(), icon, label, tooltip);
    }

    public AbstractListView(Map<String, Object> request) {
        this(request, null, null, null);
    }

    public AbstractListView(Map<String, Object> request, Icon icon, String label, String tooltip) {
        super(request);

        this.permissionSet = getPermissionOfCurrentUser();
        initialPresentationView(listDataModel);

        addAction = new AddAction();
        editAction = new EditAction();
        deleteAction = new DeleteAction();
        exportAction = new ExportAction();
        printAction = new PrintAction();

        tabPane = new JTabbedPane();
        contentPane = new JPanel(new MigLayout("wrap, ins 0, hidemode 2", "grow, fill", "[]0[]0[]0[][][]"));
        tabPane.addTab(label, icon, contentPane, tooltip);
        this.setLayout(new MigLayout("ins 0", "grow, fill", "grow, fill"));
        this.add(tabPane, "grow");

        addKeyBindings();
        addComponents();
        setAccessRule();
    }

    protected void addKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);

        KeyStroke addShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
        KeyStroke deleteShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);

        inputMap.put(addShortkey, ADD_ACTION_KEY);
        inputMap.put(deleteShortkey, "deleteKeyAction");

        ActionMap actionMap = getActionMap();
        actionMap.put(ADD_ACTION_KEY, addAction);
        actionMap.put("deleteKeyAction", deleteAction);
    }

    /**
     * List fields need to show on the view.
     * 
     * @param listDataModel
     * @param summaryFieldNames
     *            the fields want to show sum values in footer. They must be Number type.
     */
    protected abstract void initialPresentationView(ListDataModel listDataModel);

    /**
     * Load the data for the particular page which having current pageNumber.
     * 
     * @param pageNumber
     *            the number of the page, range from 1 to number of row in database.
     * @return all data shown on the view.
     */
    protected List<T> loadData(int pageNumber) {
        if (!isInitialized) {
            return new ArrayList<T>();
        }
        int firstIndex = (pageNumber - 1) * getPageSize();

        // Load necessary properties if they are lazing.
        DetachedCriteria dc = getCriteriaForView();

        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getDaoHelper().getDao(getEntityClass()).findByCriteria(dc, firstIndex, getPageSize());
    }

    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = getDaoHelper().getDao(getEntityClass()).getCriteria();
        List<ColumnModel> columns = listDataModel.getColumns();
        setFetchMode(dc, columns);
        setSortOrder(dc, columns);
        return dc;
    }

    private void setSortOrder(DetachedCriteria dc, List<ColumnModel> columns) {
        for (int i = 0; i < columns.size(); i++) {
            for (int j = 0; j < columns.size(); j++) {
                ColumnModel cm = columns.get(j);
                String name = cm.getName();
                if (cm.isSorted()) {
                    if (cm.getSortOrder() == SortOrder.ASCENDING) {
                        dc.addOrder(Order.asc(name));
                    } else {
                        dc.addOrder(Order.desc(name));
                    }
                }
            }
        }
    }

    private void setFetchMode(DetachedCriteria dc, List<ColumnModel> columns) {
        for (ColumnModel column : columns) {
            String pathName = column.getName();
            if (pathName.contains(".")) {
                // Not fetching the ending properties => remove it from the pathName
                String[] paths = StringUtils.split(pathName, '.');
                paths = (String[]) ArrayUtils.remove(paths, paths.length - 1);
                dc.setFetchMode(StringUtils.join(paths), FetchMode.JOIN);
            }
        }
    }

    /**
     * Load the data from a page to another page
     * 
     * @param firstPage
     *            the first page
     * @param lastPage
     *            the last page
     * 
     * @return all data from first page to last page
     */
    protected List<T> loadData(int firstPage, int lastPage) {
        if (!isInitialized || (firstPage > lastPage)) {
            return new ArrayList<T>();
        }
        int firstIndex = (firstPage - 1) * getPageSize();
        int recordTotal = (lastPage - firstPage) * getPageSize();

        DetachedCriteria dc = getCriteriaForView();

        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getDaoHelper().getDao(getEntityClass()).findByCriteria(dc, firstIndex, recordTotal);
    }

    /**
     * Return the number of rows visibled. The default return value is {@link #getPageSize()}. It should be overrided.
     * 
     * @return a number of visible rows.
     */
    protected int getVisibleRowCount() {
        return getPageSize();
    }

    /**
     * Return the number of rows of each pages. The default return value is {@link #DEFAULT_PAGE_SIZE} It should be
     * overrided.
     * 
     * @return the number rows of a page.
     */
    protected int getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    @Override
    public boolean requestFocusInWindow() {
        return tblListEntities.requestFocusInWindow();
    }

    protected void addComponents() {

        // //////////////////// Button panel /////////////////////////////////
        toolbar = createButtonToolBar();
        contentPane.add(toolbar, "grow x, split 2");
        // ///////////////////// Paging navigator ///////////////////////////////
        pagingNavigator = new PagingNavigator(calculateTotalPages());
        pagingNavigator.addPageChangeListener(this);
        contentPane.add(pagingNavigator);

        entities.removeAll(entities);
        entities.addAll(loadData(pagingNavigator.getCurrentPage()));
        refDataModel = initReferenceDataModel();

        // ///////////////// Init main table ////////////////////////////////
        mainTableModel = (AdvanceTableModel<T>) createTableModel();

        tblListEntities = new SAdvanceTable(mainTableModel, listDataModel, refDataModel);

        tblListEntities.setVisibleRowCount(getVisibleRowCount());

        // Show edit view when double on a such row.
        // check permissions before showing detail view
        tblListEntities.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // JXTable target = (JXTable)e.getSource();
                    // int row = target.getSelectedRow();
                    // int column = target.getSelectedColumn();
                    // // do some action
                    performEditAction();
                }
            }
        });

        // //////////////// Create footer table //////////////////////////////
        FooterTableModel footerModel = new FooterTableModel(mainTableModel);

        tblFooter = new JXTable(footerModel, tblListEntities.getColumnModel()) {
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
        // tblFooter.setEnabled(false);
        tblFooter.setShowGrid(false);
        tblFooter.setFont(UIConstants.DEFAULT_BOLD_FONT);

        tblListEntities.getColumnModel().addColumnModelListener(tblFooter);

        // The rowHeader show the order number for the rows of the main table.
        rowHeader = new JList<Integer>(new AbstractListModel<Integer>() {
            private static final long serialVersionUID = -771503812711976068L;

            @Override
            public int getSize() {
                return entities.size();
            }

            @Override
            public Integer getElementAt(int index) {
                return index + 1;
            }
        });

        rowHeader.setCellRenderer(new RowHeaderRenderer(tblListEntities));
        rowHeader.setFixedCellWidth(UIConstants.DEFAULT_ROW_HEADER_WIDTH);
        rowHeader.setFixedCellHeight(tblListEntities.getRowHeight());
        mainTableModel.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                rowHeader.repaint();
                rowHeader.revalidate();
            }
        });

        JScrollPane mainScrollpane = new JScrollPane(tblListEntities);
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

    /**
     * The subclass should override this method to set data model for the editor component when the cell in edit mode.
     * 
     * @param rdm
     */
    protected ReferenceDataModel initReferenceDataModel() {
        return new ReferenceDataModel();
    }

    private JBusyComponent<JScrollPane> createBusyPane(JScrollPane mainScrollpane) {
        JBusyComponent<JScrollPane> bp = new JBusyComponent<JScrollPane>(mainScrollpane);
        ((DefaultBusyModel) bp.getBusyModel()).setDescription(ControlConfigUtils.getString("label.loading"));
        return bp;
    }

    private int calculateTotalPages() {
        int numOfAllRows = getNumberOfAllRows();
        int totalPages = (numOfAllRows % DEFAULT_PAGE_SIZE == 0) ? (numOfAllRows / DEFAULT_PAGE_SIZE) : (numOfAllRows
                / DEFAULT_PAGE_SIZE + 1);
        return (totalPages == 0) ? 1 : totalPages; // totalPages must begin from 1.
    }

    /**
     * Get number of all of rows existing in database.
     */
    private int getNumberOfAllRows() {
        IBaseDao<T> dao = getDaoHelper().getDao(getEntityClass());
        return dao.findCountByCriteria(dao.getCriteria());
    }

    /**
     * Create model for the table. </br> <b>Pay attention:</b> if the child class override this method to change the
     * model of the table. It must ensure that the first hide column must be the id of the entities. This will warranty
     * that the edit view linked to edit view correctly.
     * 
     * @return the model of the table.
     */
    protected TableModel createTableModel() {
        return new AdvanceTableModel<T>(listDataModel, entities, getEntityClass(), false);
    }

    /**
     * The list of buttons to do actions on the table.
     * 
     * @return the panel containing the buttons.
     */
    protected JToolBar createButtonToolBar() {
        JToolBar buttonToolbar = new JToolBar();
        buttonToolbar.setRollover(true);
        buttonToolbar.setFloatable(false);
        btnAdd = new JButton(ImageUtils.getSmallIcon(ImageConstants.NEW_ICON));
        btnAdd.setToolTipText(ControlConfigUtils.getString("default.button.create"));
        btnAdd.addActionListener(addAction);

        btnDelete = new JButton(ImageUtils.getSmallIcon(ImageConstants.DELETE_ICON));
        btnDelete.setToolTipText(ControlConfigUtils.getString("default.button.delete"));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDeleteRows();
            }
        });

        btnEdit = new JButton(ImageUtils.getSmallIcon(ImageConstants.EDIT_ICON));
        btnEdit.setToolTipText(ControlConfigUtils.getString("default.button.edit"));
        btnEdit.addActionListener(editAction);

        btnExport = new JButton(ImageUtils.getSmallIcon(ImageConstants.EXPORT_ICON));
        btnExport.setToolTipText(ControlConfigUtils.getString("default.button.export"));
        btnExport.addActionListener(exportAction);

        btnPrint = new JButton(ImageUtils.getSmallIcon(ImageConstants.PRINT_ICON));
        btnPrint.setToolTipText(ControlConfigUtils.getString("default.button.print"));
        btnPrint.addActionListener(printAction);

        btnRefresh = new JButton(ImageUtils.getSmallIcon(ImageConstants.REFRESH_ICON));
        btnRefresh.setToolTipText(ControlConfigUtils.getString("default.button.refresh"));
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        buttonToolbar.add(btnAdd);
        buttonToolbar.add(btnEdit);
        buttonToolbar.add(btnDelete);
        buttonToolbar.add(btnExport);
        buttonToolbar.add(btnPrint);
        buttonToolbar.add(Box.createHorizontalGlue());
        buttonToolbar.add(btnRefresh);
        return buttonToolbar;
    }

    private void doDeleteRows() {
        int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AbstractListView.this),
                "Are you sure want to delete the selected row?", "Confirm delete", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            int[] selectedRows = tblListEntities.getSelectedRows();
            int[] selectedModelRows = new int[selectedRows.length];
            List<T> removedEntities = new ArrayList<>(selectedRows.length);
            for (int i = 0; i < selectedRows.length; i++) {
                int rowModelIndex = tblListEntities.convertRowIndexToModel(selectedRows[i]);
                removedEntities.add(entities.get(rowModelIndex));
                selectedModelRows[i] = rowModelIndex;
            }
            // Remove row in database
            getDaoHelper().getDao(getEntityClass()).deleteAll(removedEntities);
            // Remove row in view
            mainTableModel.deleteRows(selectedModelRows);
            rowHeader.repaint();
            rowHeader.revalidate();
        }
    }

    /**
     * Show the detail view with specific entity (when button Add or Edit is clicked).
     * 
     * @param entity
     *            the entity which the detail view display for. If <code>null</code>, new entity is displayed.
     */
    protected void showEditView(T entity, EditActionEnum action) {
        try {
            Map<String, Object> detailParams = new HashMap<>();
            detailParams.put(PARAM_ENTITY_ID, entity != null ? entity.getId() : null);
            detailParams.put(PARAM_ACTION, action);
            detailParams.put(PARAM_PARENT_ID, parentId);
            detailParams.put(PARAM_PARENT_CLASS, parentClass);
            detailParams.put(PARAM_LIST_VIEW, this);

            if (!preShowEditView(entity, action, detailParams)) {
                return;
            }

            // if existing a new tab --> select it.
            if (action == EditActionEnum.NEW) {
                int idx = tabPane.indexOfTab(AbstractEditView.NEW_TITLE);
                if (idx != -1) {
                    tabPane.setSelectedIndex(idx);
                    return;
                }
            }
            Class<? extends AbstractEditView<T>> detailViewClass = getEditViewClass();
            final AbstractEditView<T> detailView = detailViewClass.getConstructor(Map.class).newInstance(detailParams);

            String tabTitle = detailView.getTitle();

            int tabIndex = tabPane.indexOfTab(tabTitle);
            if (tabIndex == -1) {
                tabPane.addTab(tabTitle, detailView);
            }
            tabIndex = tabPane.indexOfTab(tabTitle);
            tabPane.setTabComponentAt(tabIndex, new ButtonTabComponent(tabPane));
            tabPane.setSelectedIndex(tabIndex);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    detailView.requestFocusInWindow();
                }
            });
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException("There are problems when init the detail view.");
        }
    }

    /**
     * The child class should override to perform action before showing the edit view.
     * 
     * @param entity
     *            the entity of the row selecting on the list.
     * @param action
     *            the action.
     * @param detailParams
     *            the param to initialize the detail view.
     * @return true if continues to show the edit view, false if otherwise.
     */
    protected boolean preShowEditView(T entity, EditActionEnum action, Map<String, Object> detailParams) {
        return true;
    }

    /**
     * Override this function in case of the list is editable. When new a row, a new entity is created and initialize
     * some field by this function.
     * 
     * @param entity
     *            an empty entity
     * @return the entity for the new row.
     * @category template method
     */
    protected T initEntity(T entity) {
        // Template method
        return entity;
    }

    /**
     * Add the listener for the main table data changed.
     * 
     * @param tableModelListener
     */
    public void addTableModelListener(TableModelListener tableModelListener) {
        mainTableModel.addTableModelListener(tableModelListener);
    }

    protected abstract Class<? extends AbstractEditView<T>> getEditViewClass();

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

    /**
     * Entity was saved on AbstractDetailView and sent to AbstractListView to refresh data.
     * 
     * @param entity
     * @param isNew
     */
    public void notifyFromDetailView(final T entity, final boolean isNew) {
        replaceEntity(entity);
        if (isNew) {
            entities.add(entity);
            int tabIndex = tabPane.indexOfTab(ControlConfigUtils.getString("label.tab.new"));

            // TODO Phuc: This is a bug, set the title of tab is by getDefaultTitle() from editView
            tabPane.setTitleAt(tabIndex, entity.getId().toString());
            mainTableModel.fireTableRowsInserted(entities.size() - 1, entities.size() - 1);
        } else {
            int index = entities.indexOf(entity);
            mainTableModel.fireTableRowsUpdated(index, index);
        }

        ((AbstractTableModel) tblFooter.getModel()).fireTableDataChanged();
        rowHeader.repaint();
        rowHeader.revalidate();
    }

    /**
     * Replace the element in entities having ID equal the entity parameter.
     * 
     * @param entity
     */
    private void replaceEntity(T entity) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(entity.getId())) {
                entities.set(i, entity);
            }
        }
    }

    public void performAddAction() {
        showEditView(null, EditActionEnum.NEW);
    }

    private void performEditAction() {
        if (permissionSet.contains(CustomPermission.ADMINISTRATION) || permissionSet.contains(CustomPermission.READ)) {
            int selectedRow = tblListEntities.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AbstractListView.this),
                        "Please select a row to edit", "Warning", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
            } else {
                int rowModel = tblListEntities.convertRowIndexToModel(selectedRow);
                showEditView(entities.get(rowModel), EditActionEnum.EDIT);
            }
        }
    }

    private void performExportAction() {
        try {
            Window parentContainer = (Window) SwingUtilities.getRoot(AbstractListView.this);

            ExportDialog exportDialog = new ExportDialog(parentContainer, Dialog.ModalityType.APPLICATION_MODAL);
            exportDialog.setVisible(true);
            if (exportDialog.APPROVE_OPTION == 1) {
                FileOutputStream outputStream = new FileOutputStream(exportDialog.getSelectedFile());
                // get field List
                List<String> fieldList = new ArrayList<String>();
                Map<String, String> labels = new HashMap<String, String>();
                for (ColumnModel column : listDataModel.getColumns()) {
                    String fieldName = column.getName();
                    fieldList.add(fieldName);
                    labels.put(fieldName,
                            ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "." + fieldName));
                }
                // get export list
                List<T> exportData = null;
                switch (exportDialog.getPageRange()) {
                case ExportDialog.CUR_PAGE:
                    exportData = entities;
                    break;
                case ExportDialog.ALL_PAGE:
                    exportData = getDaoHelper().getDao(getEntityClass()).findAll();
                    break;
                case ExportDialog.RANGE_PAGE:
                    exportData = loadData(ExportDialog.FIRST_PAGE, ExportDialog.LAST_PAGE);
                    break;
                default:
                    break;
                }
                Exporter exporter = exporterFactory.createExporter(exportDialog.getExportType(), fieldList, labels,
                        null, null);
                exporter.export(outputStream, exportData);
                outputStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ExportingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExporterNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void performPrintAction() {
        try {
            tblListEntities.print();
        } catch (PrinterException e) {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(this, "Having error when printing");
        }
    }

    protected void refreshData() {
        busyPane.setBusy(true);
        new SwingWorker<List<T>, Object>() {

            @Override
            protected List<T> doInBackground() throws Exception {
                return loadData(pagingNavigator.getCurrentPage());
            }

            @Override
            protected void done() {
                try {
                    // Retrieve the list from doInBackground()
                    List<T> refreshedList = get();
                    entities.removeAll(entities);
                    entities.addAll(refreshedList);
                    // fireTableDataChanged to re-render the table.
                    mainTableModel.fireTableDataChanged();
                    ((AbstractTableModel) tblFooter.getModel()).fireTableDataChanged();
                    rowHeader.repaint();
                    rowHeader.revalidate();

                    tblListEntities.packAll(); // resize all column fit to their contents
                } catch (InterruptedException | ExecutionException e) {
                    logger.error(e.getMessage());
                    isInitialized = false;
                    ErrorInfo errorInfo = new ErrorInfo(ControlConfigUtils.getString("error.title"),
                            ControlConfigUtils.getString("error.refreshData.message"), e.getMessage(), null, e, null,
                            null);
                    JXErrorPane.showDialog(AbstractListView.this, errorInfo);

                } finally {
                    busyPane.setBusy(false);
                }
            }
        }.execute();
    }

    private class AddAction extends AbstractAction {
        private static final long serialVersionUID = 3455983492968974921L;

        @Override
        public void actionPerformed(ActionEvent e) {
            performAddAction();
        }

    }

    private class EditAction extends AbstractAction {
        private static final long serialVersionUID = -7091407169970088286L;

        @Override
        public void actionPerformed(ActionEvent e) {
            performEditAction();
        }

    }

    private class DeleteAction extends AbstractAction {
        private static final long serialVersionUID = -7091407169970088286L;

        @Override
        public void actionPerformed(ActionEvent e) {
            doDeleteRows();
        }

    }

    private class ExportAction extends AbstractAction {
        private static final long serialVersionUID = 3365790306413388379L;

        @Override
        public void actionPerformed(ActionEvent e) {
            performExportAction();
        }

    }

    private class PrintAction extends AbstractAction {
        private static final long serialVersionUID = 3365790306413388379L;

        @Override
        public void actionPerformed(ActionEvent e) {
            performPrintAction();
        }

    }

    @Override
    public void doPageChanged(ChangeEvent e) {
        PagingNavigator pagingNavigator = (PagingNavigator) e.getSource();
        // Re-calculate the total pages and set to the pagingNavigator.
        pagingNavigator.setTotalPage(calculateTotalPages());
        refreshData();
    }

    /**
     * This function is intended to replace for {@link #notifyFromDetailView(AbstractIdOLObject, boolean)}.
     * {@inheritDoc}
     */
    @Override
    public void doSaved(SavedEvent<T> e) {
        // TODO Phuc: consider add abstractListView as a listerner of EditView to make a clear code.
        notifyFromDetailView(e.getEntity(), e.isNew());
    }

    public JTabbedPane getTabbedPane() {
        return tabPane;
    }

    @Override
    public void loadView() {
        if (!isInitialized) {
            isInitialized = true;
            refreshData();
        }
    }

    // TODO Hoang this method must implement under subclass
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.BASIC_INFORMATION;
    }

    public Set<CustomPermission> getPermissionSet() {
        return permissionSet;
    }

    protected void setAccessRule() {
        if (!permissionSet.contains(CustomPermission.ADMINISTRATION)) {
            // TODO Hoang should not use 'if' in this case.
            if (!permissionSet.contains(CustomPermission.CREATE)) {
                btnAdd.setVisible(false);
            }
            if (!permissionSet.contains(CustomPermission.WRITE)) {
                btnEdit.setVisible(false);
            }
            if (!permissionSet.contains(CustomPermission.DELETE)) {
                btnDelete.setVisible(false);
            }
        }
    }
}
