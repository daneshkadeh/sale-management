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
package com.s3s.ssm.view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Event;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.export.exporter.DefaultExporterFactory;
import com.s3s.ssm.export.exporter.Exporter;
import com.s3s.ssm.export.exporter.ExporterFactory;
import com.s3s.ssm.export.exporter.ExporterNotFoundException;
import com.s3s.ssm.export.exporter.ExportingException;
import com.s3s.ssm.export.view.ExportDialog;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomPermission;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.component.IPageChangeListener;
import com.s3s.ssm.view.component.PagingNavigator;

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
public abstract class AbstractListView<T extends AbstractIdOLObject> extends AbstractView implements
        IPageChangeListener, IViewLazyLoadable {

    private static final long serialVersionUID = -1311942671249671111L;
    private static final String ADD_ACTION_KEY = "addAction";
    private static final Color HIGHLIGHT_ROW_COLOR = new Color(97, 111, 231);
    // TODO It should get from the property "defPageRowNum" of BasicInformation in ssm-config
    private static final int DEFAULT_PAGE_SIZE = 25;

    private static final String CHOOSER_DIALOG_TITLE = "Choose Directory";
    private static final Log logger = LogFactory.getLog(AbstractListView.class);

    private JXTable tblListEntities;
    private JList<Integer> rowHeader;
    private JXTable tblFooter;
    private AdvanceTableModel mainTableModel;
    private PagingNavigator pagingNavigator;
    // button toolbar
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnEdit;
    private JButton btnExport;
    private JButton btnPrint;

    // TODO use this flag temporarily to prevent init the view more than one time. --> Need to use the Proxy object
    // instead.
    public boolean isInitialized = false;

    private Class<? extends AbstractIdOLObject> parentClass;
    private Long parentId;

    protected List<T> entities = new ArrayList<>();

    // This model is used by sub classes.
    protected final List<DetailAttribute> listDataModel = new ArrayList<>();
    protected final List<String> summaryFieldNames = new ArrayList<>();

    private Action addAction;
    private Action editAction;
    private Action exportAction;
    private Action printAction;

    private BeanWrapper beanWrapper;
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

    private Long getParentIdParam(Map<String, Object> params) {
        if (params.get(PARAM_PARENT_ID) != null) {
            return (Long) params.get(PARAM_PARENT_ID);
        } else {
            return null;
        }
    }

    private Class<?> getParentClassParam(Map<String, Object> params) {
        if (params.get(PARAM_PARENT_CLASS) != null) {
            return (Class<?>) params.get(PARAM_PARENT_CLASS);
        } else {
            return null;
        }
    }

    public AbstractListView(Map<String, Object> request) {
        super(request);

        this.parentId = getParentIdParam(request);
        this.parentClass = (Class) request.get("parentClass");
        this.permissionSet = getPermissionOfCurrentUser();
        initialPresentationView(listDataModel, summaryFieldNames);

        addAction = new AddAction();
        editAction = new EditAction();
        exportAction = new ExportAction();
        printAction = new PrintAction();

        setLayout(new MigLayout("wrap", "grow, fill", "[]0[]0[]0[]2[][]"));

        addKeyBindings();
        addComponents();
        setAccessRule();
    }

    protected void addKeyBindings() {
        // Key binding
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);

        // Ctrl-A to add new row
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
        inputMap.put(key, ADD_ACTION_KEY);

        ActionMap actionMap = getActionMap();
        actionMap.put(ADD_ACTION_KEY, addAction);

    }

    /**
     * List fields need to show on the view.
     * 
     * @param listDataModel
     * @param summaryFieldNames
     *            the fields want to show sum values in footer. They must be Number type.
     */
    protected abstract void
            initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames);

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

        return getDaoHelper().getDao(getEntityClass()).findByCriteria(dc, firstIndex, getPageSize());
    }

    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = getDaoHelper().getDao(getEntityClass()).getCriteria();
        for (DetailAttribute attribute : listDataModel) {
            String pathName = attribute.getName();
            if (pathName.contains(".")) {
                // Not fetching the ending properties => remove it from the pathName
                String[] paths = StringUtils.split(pathName, '.');
                paths = (String[]) ArrayUtils.remove(paths, paths.length - 1);
                dc.setFetchMode(StringUtils.join(paths), FetchMode.JOIN);
            }
        }
        return dc;
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

    protected void addComponents() {
        // //////////////////// Button panel /////////////////////////////////
        JToolBar pnlButton = createButtonToolBar(tblListEntities);
        this.add(pnlButton);

        // ///////////////////// Paging navigator ///////////////////////////////
        pagingNavigator = new PagingNavigator(calculateTotalPages());
        pagingNavigator.addPageChangeListener(this);

        // ///////////////// Init main table ////////////////////////////////
        tblListEntities = new JXTable();
        tblListEntities.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblListEntities.addHighlighter(HighlighterFactory.createSimpleStriping());
        // Highlight the row when mouse over.
        tblListEntities
                .addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, HIGHLIGHT_ROW_COLOR, null));

        mainTableModel = createTableModel();
        tblListEntities.setModel(mainTableModel);

        // Hide the entity column by set width = 0
        tblListEntities.getColumnModel().getColumn(0).setMinWidth(0);
        tblListEntities.getColumnModel().getColumn(0).setMaxWidth(0);

        // /////// Hack at here: when number of column
        tblListEntities.setVisibleColumnCount(0);

        tblListEntities.setVisibleRowCount(getVisibleRowCount());
        tblListEntities.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblListEntities.setColumnControlVisible(true);

        // Show edit view when double on a such row.
        // check permissions before showing detail view
        if (permissionSet.contains(CustomPermission.ADMINISTRATION) || permissionSet.contains(CustomPermission.READ)) {
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
        }
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
        tblFooter.setEnabled(false);
        tblFooter.setShowGrid(false);

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
        JScrollPane mainScrollpane = new JScrollPane(tblListEntities);
        mainScrollpane.setRowHeaderView(rowHeader);
        // mainScrollpane.getViewport().setBackground(Color.WHITE);

        this.add(mainScrollpane);
        JScrollPane footerScrollpane = new JScrollPane(tblFooter);

        if (CollectionUtils.isNotEmpty(summaryFieldNames)) {
            this.add(footerScrollpane);
        }

        this.add(pagingNavigator);
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

    private AdvanceTableModel createTableModel() {
        entities = loadData(pagingNavigator.getCurrentPage());
        return new AdvanceTableModel();
    }

    /**
     * The list of buttons to do actions on the table.
     * 
     * @return the panel containing the buttons.
     */
    protected JToolBar createButtonToolBar(JTable table) {
        JToolBar buttonToolbar = new JToolBar();
        buttonToolbar.setRollover(true);
        buttonToolbar.setFloatable(false);
        btnAdd = new JButton(ControlConfigUtils.getString("default.button.create"));
        btnAdd.addActionListener(addAction);

        btnDelete = new JButton(ControlConfigUtils.getString("default.button.delete"));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AbstractListView.this),
                        "Are you sure want to delete the selected row?", "Confirm delete",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    int[] selectedRows = tblListEntities.getSelectedRows();
                    List<T> removedEntities = new ArrayList<>(selectedRows.length);
                    for (int i : selectedRows) {
                        int rowModelIndex = tblListEntities.convertRowIndexToModel(i);
                        removedEntities.add(entities.get(rowModelIndex));
                    }
                    // Remove row in database
                    getDaoHelper().getDao(getEntityClass()).deleteAll(removedEntities);
                    // Remove row in view
                    entities.removeAll(removedEntities);
                    ((AdvanceTableModel) tblListEntities.getModel()).fireTableDataChanged();
                }
            }
        });

        btnEdit = new JButton(ControlConfigUtils.getString("default.button.edit"));
        btnEdit.addActionListener(editAction);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        btnExport = new JButton(ControlConfigUtils.getString("default.button.export"));
        btnExport.addActionListener(exportAction);

        btnPrint = new JButton(ControlConfigUtils.getString("default.button.print"));
        btnPrint.addActionListener(printAction);

        buttonToolbar.add(btnEdit);
        buttonToolbar.add(btnDelete);
        buttonToolbar.add(btnAdd);
        buttonToolbar.add(btnExport);
        buttonToolbar.add(btnPrint);
        buttonToolbar.add(Box.createHorizontalGlue());
        buttonToolbar.add(btnRefresh);
        return buttonToolbar;
    }

    /**
     * Show the detail view with specific entity (when button Add or Edit is clicked).
     * 
     * @param entity
     *            the entity which the detail view display for. If <code>null</code>, new entity is displayed.
     */
    protected void showEditView(T entity, String action) {
        Class<? extends AbstractEditView<T>> detailViewClass = getEditViewClass();
        try {
            Map<String, Object> detailParams = new HashMap<>();
            detailParams.put(PARAM_ENTITY_ID, entity != null ? entity.getId() : null);
            detailParams.put(PARAM_ACTION, action);
            detailParams.put(PARAM_PARENT_ID, parentId);
            detailParams.put(PARAM_PARENT_CLASS, parentClass);
            detailParams.put(PARAM_LIST_VIEW, this);

            // TODO This call requires sub class override Constructor method! It's not good.
            AbstractEditView<T> detailView = detailViewClass.getConstructor(Map.class).newInstance(detailParams);
            // TODO HPP consider to listen the event from AbstractDetailView (not set reference to it).
            // detailView.setListView(this);
            JScrollPane scrollPane = new JScrollPane(detailView);

            Window parentContainer = (Window) SwingUtilities.getRoot(AbstractListView.this);
            JDialog dialog = new JDialog(parentContainer);
            // dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setContentPane(scrollPane);
            dialog.setSize(detailView.getFitSize());
            dialog.setLocationRelativeTo(parentContainer); // Display the dialog in the center.
            dialog.setModalityType(ModalityType.APPLICATION_MODAL);
            dialog.setVisible(true);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException("There are problems when init the detail view.");
        }
    }

    protected abstract Class<? extends AbstractEditView<T>> getEditViewClass();

    private Class<?> getClassOfField(String fieldName) {
        String[] paths = StringUtils.split(fieldName, '.');
        Class<?> clazz = getEntityClass(); // original class is class of current entity.
        for (String path : paths) {
            clazz = Solution3sClassUtils.getGetterMethod(clazz, path).getReturnType();
        }
        return clazz;
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return (Class<T>) Solution3sClassUtils.getArgumentClass(getClass());
    }

    /**
     * Model support for table can be hide rows. The supported methods:</br> <code>hideRows()</code> and
     * <code>showAllRows()</code>
     */
    public class AdvanceTableModel extends AbstractTableModel {
        private static final long serialVersionUID = -4720974982417224609L;

        private final Set<Integer> hiddenRows = new HashSet<Integer>();

        /**
         * The remaining entities after hiding rows.
         */
        private List<T> currentEntities;

        public AdvanceTableModel() {
            if (entities == null) {
                currentEntities = new ArrayList<T>();
            } else {
                currentEntities = entities;
            }
        }

        /**
         * Hide rows.
         * 
         * @param rowIndices
         *            Index of rows which will be hidden
         */
        public void hideRows(int[] rowIndices) {
            hiddenRows.clear();
            for (int i : rowIndices) {
                hiddenRows.add(i);
            }
            currentEntities = getVisibleEntities();
            fireTableDataChanged();
        }

        /**
         * Show all rows being hidden.
         */
        public void showAllRows() {
            hiddenRows.clear();
            currentEntities = entities;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return currentEntities.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            T entity = currentEntities.get(rowIndex);
            beanWrapper = new BeanWrapperImpl(entity);
            // The hide extra column contain the entity ID
            if (columnIndex == 0) {
                return entity.getId();
            }

            DetailAttribute dataModel = listDataModel.get(columnIndex - 1);
            return beanWrapper.getPropertyValue(dataModel.getName());
        }

        @Override
        public int getColumnCount() {
            return listDataModel.size() + 1; // Add a more column contain entity value.
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "";
            }
            return ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + listDataModel.get(column - 1).getName());
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            T entity = currentEntities.get(rowIndex);
            beanWrapper = new BeanWrapperImpl(entity);
            DetailAttribute dataModel = listDataModel.get(columnIndex);
            beanWrapper.setPropertyValue(dataModel.getName(), aValue);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) {
                return getEntityClass();
            }
            return getClassOfField(listDataModel.get(columnIndex - 1).getName());
        }

        private List<T> getVisibleEntities() {
            List<T> visibleEntities = new ArrayList<T>(getRowCount());
            for (int i = 0; i < currentEntities.size(); i++) {
                if (!hiddenRows.contains(i)) {
                    visibleEntities.add(currentEntities.get(i));
                }
            }
            return visibleEntities;
        }
    }

    private class FooterTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final AdvanceTableModel mainTableModel;

        public FooterTableModel(AdvanceTableModel mainTableModel) {
            this.mainTableModel = mainTableModel;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return null;
            }
            DetailAttribute detailDataModel = listDataModel.get(columnIndex - 1); // decrease 1 because the hidden
                                                                                  // entity column
            String fieldName = detailDataModel.getName();
            // Check exists summaryFieldName is fieldName or not.
            for (String sfName : summaryFieldNames) {
                if (fieldName.equals(sfName)) {
                    Class<?> fieldClass = getClassOfField(fieldName);
                    if (ClassUtils.isAssignable(fieldClass, Integer.class)) {
                        int sum = 0;
                        for (int i = 0; i < mainTableModel.getRowCount(); i++) {
                            sum = sum + (Integer) mainTableModel.getValueAt(i, columnIndex);
                        }
                        return sum;
                    }

                    if (ClassUtils.isAssignable(fieldClass, Double.class)) {
                        Double sum = 0d;
                        for (int i = 0; i < mainTableModel.getRowCount(); i++) {
                            sum = sum + (Double) mainTableModel.getValueAt(i, columnIndex);
                        }
                        return sum;
                    }

                    throw new RuntimeException("Just support sum for Double and Integer type");

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
            if (columnIndex == 0) {
                return getEntityClass();
            }
            return getClassOfField(listDataModel.get(columnIndex - 1).getName());
        }

    }

    /**
     * Entity was saved on AbstractDetailView and sent to AbstractListView to refresh data.
     * 
     * @param entity
     * @param isNew
     */
    public void notifyFromDetailView(T entity, boolean isNew) {
        replaceEntity(entity);
        // Keep the selected row before the data of table is changed.
        int selectedRow = tblListEntities.getSelectedRow();
        if (isNew) {
            entities.add(entity);
            selectedRow = entities.size() - 1; // If add new entity, the selected row has the last index.
        }

        // fireTableDataChanged to rerender the table.
        ((AbstractTableModel) tblListEntities.getModel()).fireTableDataChanged();
        ((AbstractTableModel) tblFooter.getModel()).fireTableDataChanged();
        rowHeader.repaint();
        rowHeader.revalidate();

        // After fireTableDataChanged() the selection is lost. We need to reselect it programmatically.
        tblListEntities.setRowSelectionInterval(selectedRow, selectedRow);
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
        showEditView(null, ACTION_NEW);
    }

    private void performEditAction() {
        int selectedRow = tblListEntities.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AbstractListView.this), "Please select a row to edit",
                    "Warning", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
        } else {
            int rowModel = tblListEntities.convertRowIndexToModel(selectedRow);
            showEditView(entities.get(rowModel), ACTION_EDIT);
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
                for (DetailAttribute detailAtribute : listDataModel) {
                    String fieldName = detailAtribute.getName();
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
        entities.removeAll(entities);
        entities.addAll(loadData(pagingNavigator.getCurrentPage()));
        // fireTableDataChanged to re-render the table.
        ((AbstractTableModel) tblListEntities.getModel()).fireTableDataChanged();
        ((AbstractTableModel) tblFooter.getModel()).fireTableDataChanged();
        rowHeader.repaint();
        rowHeader.revalidate();

        tblListEntities.packAll(); // resize all column fit to their contents
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

    public Class<? extends AbstractIdOLObject> getParentClass() {
        return parentClass;
    }

    public Long getParentId() {
        return parentId;
    }
}
