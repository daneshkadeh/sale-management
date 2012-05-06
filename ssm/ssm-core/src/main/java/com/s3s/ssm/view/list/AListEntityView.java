/*
 * AListEntityView1
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

import java.awt.Dialog;
import java.awt.Window;
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.IActiveObject;
import com.s3s.ssm.export.exporter.Exporter;
import com.s3s.ssm.export.exporter.ExporterNotFoundException;
import com.s3s.ssm.export.exporter.ExportingException;
import com.s3s.ssm.export.view.ExportDialog;
import com.s3s.ssm.security.CustomPermission;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.ISavedListener;
import com.s3s.ssm.view.SavedEvent;
import com.s3s.ssm.view.component.ButtonTabComponent;
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
public abstract class AListEntityView<T extends AbstractBaseIdObject> extends AListView<T> implements ISavedListener<T> {

    private static final long serialVersionUID = -1311942671249671111L;

    private static final Log logger = LogFactory.getLog(AListEntityView.class);

    public AListEntityView() {
        super();
    }

    public AListEntityView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    public AListEntityView(Map<String, Object> request) {
        super(request);
    }

    public AListEntityView(Map<String, Object> request, Icon icon, String label, String tooltip) {
        super(request, icon, label, tooltip);
    }

    /**
     * List fields need to show on the view.
     * 
     * @param listDataModel
     * @param summaryFieldNames
     *            the fields want to show sum values in footer. They must be Number type.
     */
    @Override
    protected abstract void initialPresentationView(ListDataModel listDataModel);

    @Override
    protected List<T> loadData(int firstIndex, int maxResults) {
        if (!isInitialized) {
            return new ArrayList<T>();
        }

        // Load necessary properties if they are lazing.
        DetachedCriteria dc = getCriteriaForView();

        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getDaoHelper().getDao(getGenericClass()).findByCriteria(dc, firstIndex, maxResults);
    }

    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = getDaoHelper().getDao(getGenericClass()).getCriteria();
        List<ColumnModel> columns = listDataModel.getColumns();
        setFetchMode(dc, columns);
        setSortOrder(dc, columns);
        return dc;
    }

    @Override
    protected JPanel createSearchPanel() {
        return null;
    }

    @Override
    protected void clearCriteria() {
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

    // /**
    // * Load the data from a page to another page
    // *
    // * @param firstPage
    // * the first page
    // * @param lastPage
    // * the last page
    // *
    // * @return all data from first page to last page
    // * @deprecated it should has 1 loadData function. Using
    // */
    // protected List<T> loadData(int firstPage, int lastPage) {
    // if (!isInitialized || (firstPage > lastPage)) {
    // return new ArrayList<T>();
    // }
    // int firstIndex = (firstPage - 1) * getPageSize();
    // int recordTotal = (lastPage - firstPage) * getPageSize();
    //
    // DetachedCriteria dc = getCriteriaForView();
    //
    // dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    // return getDaoHelper().getDao(getEntityClass()).findByCriteria(dc, firstIndex, recordTotal);
    // }

    @Override
    protected int calculateTotalPages() {
        int numOfAllRows = getNumberOfAllRows();
        int totalPages = (numOfAllRows % DEFAULT_PAGE_SIZE == 0) ? (numOfAllRows / DEFAULT_PAGE_SIZE) : (numOfAllRows
                / DEFAULT_PAGE_SIZE + 1);
        return (totalPages == 0) ? 1 : totalPages; // totalPages must begin from 1.
    }

    /**
     * Get number of all of rows existing in database.
     */
    private int getNumberOfAllRows() {
        IBaseDao<T> dao = getDaoHelper().getDao(getGenericClass());
        return dao.findCountByCriteria(dao.getCriteria());
    }

    @Override
    protected void doDeleteRows() {
        int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AListEntityView.this),
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
            getDaoHelper().getDao(getGenericClass()).deleteAll(removedEntities);
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
    @Override
    protected T initEntity(T entity) {
        // Template method
        return entity;
    }

    protected abstract Class<? extends AbstractEditView<T>> getEditViewClass();

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

    @Override
    public void performNewAction() {
        showEditView(null, EditActionEnum.NEW);
    }

    @Override
    protected void performEditAction() {
        if (permissionSet.contains(CustomPermission.ADMINISTRATION) || permissionSet.contains(CustomPermission.READ)) {
            int selectedRow = tblListEntities.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this), "Please select a row to edit", "Warning",
                        JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
            } else {
                int rowModel = tblListEntities.convertRowIndexToModel(selectedRow);
                showEditView(entities.get(rowModel), EditActionEnum.EDIT);
            }
        }
    }

    @Override
    protected void performActivateData() {
        int selectedRow = tblListEntities.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this), "Please select a row to activate", "Warning",
                    JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } else {
            int rowModel = tblListEntities.convertRowIndexToModel(selectedRow);
            IActiveObject activeObject = (IActiveObject) entities.get(rowModel);
            if (!activeObject.isActive()) {
                activeObject.setActive(true);
                getDaoHelper().getDao(getGenericClass()).saveOrUpdate(entities.get(rowModel));
                tblListEntities.repaint();
                tblListEntities.revalidate();
            } else {
                JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this),
                        "The selected object has been active already.", "Warning", JOptionPane.CLOSED_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    protected void performInactivateData() {
        int selectedRow = tblListEntities.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this), "Please select a row to inactivate", "Warning",
                    JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } else {
            int rowModel = tblListEntities.convertRowIndexToModel(selectedRow);
            IActiveObject activeObject = (IActiveObject) entities.get(rowModel);
            if (activeObject.isActive()) {
                activeObject.setActive(false);
                getDaoHelper().getDao(getGenericClass()).saveOrUpdate(entities.get(rowModel));
                tblListEntities.repaint();
                tblListEntities.revalidate();
            } else {
                JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this),
                        "The selected object has been inactive already.", "Warning", JOptionPane.CLOSED_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    @Override
    protected void performExportAction() {
        try {
            Window parentContainer = (Window) SwingUtilities.getRoot(this);

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
                    labels.put(fieldName, ControlConfigUtils.getString("label." + getGenericClass().getSimpleName()
                            + "." + fieldName));
                }
                // get export list
                List<T> exportData = null;
                switch (exportDialog.getPageRange()) {
                case ExportDialog.CUR_PAGE:
                    exportData = entities;
                    break;
                case ExportDialog.ALL_PAGE:
                    exportData = getDaoHelper().getDao(getGenericClass()).findAll();
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

    @Override
    protected void performPrintAction() {
        try {
            tblListEntities.print();
        } catch (PrinterException e) {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(this, "Having error when printing");
        }
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

}
