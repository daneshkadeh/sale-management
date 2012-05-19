/*
 * Test
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
import java.awt.Cursor;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
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
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.divxdede.swing.busy.DefaultBusyModel;
import org.divxdede.swing.busy.JBusyComponent;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.entity.IActiveObject;
import com.s3s.ssm.export.exporter.DefaultExporterFactory;
import com.s3s.ssm.export.exporter.ExporterFactory;
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
import com.s3s.ssm.view.IViewLazyLoadable;
import com.s3s.ssm.view.component.IPageChangeListener;
import com.s3s.ssm.view.component.PagingNavigator;
import com.s3s.ssm.view.list.renderer.RowHeaderRenderer;

/**
 * @author Phan Hong Phuc
 * @since May 1, 2012
 */
public abstract class AListView<T> extends AbstractView implements IPageChangeListener, IViewLazyLoadable {
    private static final long serialVersionUID = -1311942671249671111L;
    private static final String ADD_ACTION_KEY = "addAction";
    // TODO It should get from the property "defPageRowNum" of BasicInformation in ssm-config
    protected static final int DEFAULT_PAGE_SIZE = 22;

    private static final Log logger = LogFactory.getLog(AListView.class);

    protected JTabbedPane tabPane;
    protected JPanel contentPane;
    protected JToolBar toolbar;
    protected JXTable tblListEntities;
    protected JList<Integer> rowHeader;
    protected JBusyComponent<JScrollPane> busyPane;

    protected AdvanceTableModel<T> mainTableModel;
    protected PagingNavigator pagingNavigator;
    // button toolbar
    protected JButton btnNew;
    protected JButton btnDelete;
    protected JButton btnEdit;
    protected JButton btnExport;
    protected JButton btnPrint;
    protected JButton btnRefresh;
    protected JButton btnActivate;
    protected JButton btnInactivate;

    // TODO use this flag temporarily to prevent init the view more than one time. --> Need to use the Proxy object
    // instead.
    public boolean isInitialized = false;

    protected final List<T> entities = new ArrayList<>();

    // This model is used by sub classes.
    protected final ListDataModel listDataModel = new ListDataModel();
    protected ReferenceDataModel refDataModel = new ReferenceDataModel();

    protected Action newAction;
    protected Action editAction;
    protected Action deleteAction;
    protected Action exportAction;
    protected Action printAction;

    // the service is used to get access rule
    protected Set<CustomPermission> permissionSet;
    // export factory
    // TODO should get from service
    ExporterFactory exporterFactory = new DefaultExporterFactory();

    public AListView() {
        this(new HashMap<String, Object>());
    }

    private Set<CustomPermission> getPermissionOfCurrentUser() {
        ACLResourceEnum aclResource = registerACLResource();
        // get permissions of current user
        return ConfigProvider.getInstance().getContextProvider().getPermissions(aclResource);
    }

    public AListView(Icon icon, String label, String tooltip) {
        this(new HashMap<String, Object>(), icon, label, tooltip);
    }

    public AListView(Map<String, Object> request) {
        this(request, null, null, null);
    }

    public AListView(Map<String, Object> request, Icon icon, String label, String tooltip) {
        super(request);

        this.permissionSet = getPermissionOfCurrentUser();
        initialPresentationView(listDataModel);

        newAction = new NewAction();
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
        KeyStroke refreshShortkey = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);

        inputMap.put(addShortkey, ADD_ACTION_KEY);
        inputMap.put(deleteShortkey, "deleteKeyAction");
        inputMap.put(refreshShortkey, "refreshKeyAction");

        ActionMap actionMap = getActionMap();
        if (isShowNewButton()) {
            actionMap.put(ADD_ACTION_KEY, newAction);
        }
        if (isShowDeleteButton()) {
            actionMap.put("deleteKeyAction", deleteAction);
        }
        actionMap.put("refreshKeyAction", new AbstractAction() {
            private static final long serialVersionUID = -710427087087670747L;

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

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
    protected abstract List<T> loadData(int firstIndex, int maxResults);

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
        addSearchPanel();
        // //////////////////// Button panel /////////////////////////////////
        toolbar = createButtonToolBar();
        contentPane.add(toolbar, "grow x, split 2");
        // ///////////////////// Paging navigator ///////////////////////////////
        pagingNavigator = new PagingNavigator(calculateTotalPages());
        pagingNavigator.addPageChangeListener(this);
        contentPane.add(pagingNavigator);

        entities.removeAll(entities);
        entities.addAll(loadData(0, getPageSize()));
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

    protected abstract int calculateTotalPages();

    /**
     * Create model for the table. </br> <b>Pay attention:</b> if the child class override this method to change the
     * model of the table. It must ensure that the first hide column must be the id of the entities. This will warranty
     * that the edit view linked to edit view correctly.
     * 
     * @return the model of the table.
     */
    protected TableModel createTableModel() {
        return new AdvanceTableModel<T>(listDataModel, entities, getGenericClass(), false, getVisibleRowCount(),
                new ICallbackAdvanceTableModel<T>() {

                    @Override
                    public Object getAttributeValueCallback(T entity, ColumnModel dataModel) {
                        return getAttributeValue(entity, dataModel);
                    }

                    @Override
                    public void setAttributeValueCallback(T entity, ColumnModel dataModel, Object aValue) {
                        setAttributeValue(entity, dataModel, aValue);
                    }
                });
    }

    public Object getAttributeValue(T entity, ColumnModel dataModel) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
        return dataModel.isRaw() ? dataModel.getValue() : beanWrapper.getPropertyValue(dataModel.getName());
    }

    public void setAttributeValue(T entity, ColumnModel dataModel, Object aValue) {
        // do not bind the property if it's raw. The sub class must bind this property manual
        if (!dataModel.isRaw()) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
            beanWrapper.setPropertyValue(dataModel.getName(), aValue);
        } else {
            dataModel.value(aValue);
        }
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
        btnNew = new JButton(ImageUtils.getSmallIcon(ImageConstants.NEW_ICON));
        btnNew.setToolTipText(ControlConfigUtils.getString("default.button.create"));
        btnNew.addActionListener(newAction);
        btnNew.setVisible(isShowNewButton());

        btnDelete = new JButton(ImageUtils.getSmallIcon(ImageConstants.DELETE_ICON));
        btnDelete.setToolTipText(ControlConfigUtils.getString("default.button.delete"));
        btnDelete.addActionListener(deleteAction);
        btnDelete.setVisible(isShowDeleteButton());

        btnEdit = new JButton(ImageUtils.getSmallIcon(ImageConstants.EDIT_ICON));
        btnEdit.setToolTipText(ControlConfigUtils.getString("default.button.edit"));
        btnEdit.addActionListener(editAction);
        btnEdit.setVisible(isShowEditButton());

        btnExport = new JButton(ImageUtils.getSmallIcon(ImageConstants.EXPORT_ICON));
        btnExport.setToolTipText(ControlConfigUtils.getString("default.button.export"));
        btnExport.addActionListener(exportAction);
        btnExport.setVisible(isShowExportButton());

        btnPrint = new JButton(ImageUtils.getSmallIcon(ImageConstants.PRINT_ICON));
        btnPrint.setToolTipText(ControlConfigUtils.getString("default.button.print"));
        btnPrint.addActionListener(printAction);
        btnPrint.setVisible(isShowPrintButton());

        btnRefresh = new JButton(ImageUtils.getSmallIcon(ImageConstants.REFRESH_ICON));
        btnRefresh.setToolTipText(ControlConfigUtils.getString("default.button.refresh"));
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        buttonToolbar.add(btnNew);
        buttonToolbar.add(btnEdit);
        buttonToolbar.add(btnDelete);
        buttonToolbar.add(btnExport);
        buttonToolbar.add(btnPrint);

        // TODO: check security for this button
        if (IActiveObject.class.isAssignableFrom(getGenericClass()) && isShowActivateButton()) {
            btnActivate = new JButton(ImageUtils.getSmallIcon(ImageConstants.ACTIVATE_ICON));
            btnActivate.setToolTipText(ControlConfigUtils.getString("default.button.activate"));
            btnActivate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performActivateData();
                }
            });
            buttonToolbar.add(btnActivate);

            btnInactivate = new JButton(ImageUtils.getSmallIcon(ImageConstants.INACTIVATE_ICON));
            btnInactivate.setToolTipText(ControlConfigUtils.getString("default.button.inactivate"));
            btnInactivate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performInactivateData();
                }
            });

            buttonToolbar.add(btnInactivate);
        }

        buttonToolbar.add(Box.createHorizontalGlue());
        buttonToolbar.add(btnRefresh);
        return buttonToolbar;
    }

    /**
     * Create the search panel.
     * 
     * @return the search panel, hide the search panel if return null.
     */
    protected abstract JPanel createSearchPanel();

    /**
     * Clear the criteria on the search panel.
     */
    protected abstract void clearCriteria();

    protected JPanel createSearchButtonsPanel() {
        JButton btnSearch = new JButton(ImageUtils.getSmallIcon(ImageConstants.SEARCH_ICON));
        btnSearch.setText(ControlConfigUtils.getString("button.text.search"));
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        btnSearch.setCursor(handCursor);

        JButton btnClear = new JButton(ImageUtils.getSmallIcon(ImageConstants.CLEAR_ICON));
        btnClear.setText(ControlConfigUtils.getString("button.text.clearCriteria"));
        btnClear.setToolTipText(ControlConfigUtils.getString("tooltip.clearCriteria"));
        btnClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clearCriteria();
            }
        });
        btnSearch.setCursor(handCursor);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(btnSearch);
        panel.add(btnClear);
        return panel;
    }

    protected void addSearchPanel() {
        if (createSearchPanel() == null) {
            return;
        }
        Color backgroundColor = new Color(200, 200, 255);
        JXTaskPane pane = new JXTaskPane();
        pane.setTitle(ControlConfigUtils.getString("label.search.searchTitle"));
        pane.setIcon(ImageUtils.getSmallIcon(ImageConstants.SEARCH_ICON));
        pane.setCollapsed(false);
        pane.getContentPane().setBackground(backgroundColor);
        JPanel searchPanel = createSearchPanel();
        searchPanel.setBackground(backgroundColor);
        pane.add(searchPanel);
        JPanel searchButtonsPanel = createSearchButtonsPanel();
        searchButtonsPanel.setBackground(backgroundColor);
        pane.add(searchButtonsPanel);
        contentPane.add(pane);
    }

    // //////////////// Show / Hide buttons /////////////////////
    protected boolean isShowActivateButton() {
        return true;
    }

    protected boolean isShowNewButton() {
        return true;
    }

    protected boolean isShowDeleteButton() {
        return true;
    }

    protected boolean isShowEditButton() {
        return true;
    }

    protected boolean isShowExportButton() {
        return true;
    }

    protected boolean isShowPrintButton() {
        return true;
    }

    // ////////////////////////////////////////////////////////

    protected void doDeleteRows() {
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

    @SuppressWarnings("unchecked")
    protected Class<T> getGenericClass() {
        return (Class<T>) SClassUtils.getArgumentClass(getClass());
    }

    protected void performNewAction() {
    }

    protected void performEditAction() {
    }

    protected void performActivateData() {
    }

    protected void performInactivateData() {
    }

    protected void performExportAction() {
    }

    protected void performPrintAction() {
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
                int firstIndex = (pagingNavigator.getCurrentPage() - 1) * getPageSize();
                return loadData(firstIndex, getPageSize());
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
                    rowHeader.repaint();
                    rowHeader.revalidate();

                    // resize all column fit to their contents.
                    tblListEntities.packAll();
                } catch (InterruptedException | ExecutionException e) {
                    logger.error(e.getMessage());
                    isInitialized = false;
                    ErrorInfo errorInfo = new ErrorInfo(ControlConfigUtils.getString("error.title"),
                            ControlConfigUtils.getString("error.refreshData.message"), e.getMessage(), null, e, null,
                            null);
                    JXErrorPane.showDialog(AListView.this, errorInfo);

                } finally {
                    busyPane.setBusy(false);
                }
            }
        }.execute();
    }

    private class NewAction extends AbstractAction {
        private static final long serialVersionUID = 3455983492968974921L;

        @Override
        public void actionPerformed(ActionEvent e) {
            performNewAction();
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
                btnNew.setVisible(false);
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
