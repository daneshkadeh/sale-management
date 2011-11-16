package com.s3s.ssm.view;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.S3sPageChangeListener;
import com.s3s.ssm.view.component.S3sPagingNavigator;

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
        S3sPageChangeListener {
    private static final long serialVersionUID = -1311942671249671111L;
    private static final String ADD_ACTION_KEY = "addAction";
    private static final Color HIGHLIGHT_ROW_COLOR = new Color(97, 111, 231);
    private static final int DEFAULT_PAGE_SIZE = 3;
    private static final Log logger = LogFactory.getLog(AbstractListView.class);

    private JXTable tblListEntities;
    private JXTable tblFooter;
    private S3sPagingNavigator pagingNavigator;

    private Class<? extends AbstractBaseIdObject> parentClass;
    private Long parentId;

    protected List<T> entities = new ArrayList<>();

    // This model is used by sub classes.
    protected final List<DetailAttribute> listDataModel = new ArrayList<>();
    protected final List<String> summaryFieldNames = new ArrayList<>();

    private Action addAction;
    private Action editAction;

    public AbstractListView() {
        this(null, null);
    }

    public AbstractListView(Long parentId, Class<? extends AbstractBaseIdObject> parentClass) {
        this.parentId = parentId;
        this.parentClass = parentClass;
        initialPresentationView(listDataModel, summaryFieldNames);

        addAction = new AddAction();
        editAction = new EditAction();
        setLayout(new MigLayout("wrap", "grow, fill", "[]0[]0[grow, fill]2[]0[][]"));

        addKeyBindings();
        addComponents();
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
     * Load the data for the particular page which having pageNumber currently.
     * 
     * @param pageNumber
     *            the number of page, range from 1 to number of row in database.
     * @return all data shown on the view.
     */
    protected List<T> loadData(int pageNumber) {
        int firstIndex = (pageNumber - 1) * getPageSize();
        DetachedCriteria dc = getDaoHelper().getDao(getEntityClass()).getCriteria();
        return getDaoHelper().getDao(getEntityClass()).findByCriteria(dc, firstIndex, getPageSize());
    }

    /**
     * Return the number of rows of each pages. It should be overrided if want change the page size.
     * 
     * @return the number rows of a page.
     */
    protected int getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    protected void addComponents() {
        // ///////////////////// Paging navigator ///////////////////////////////
        pagingNavigator = new S3sPagingNavigator(calculateTotalPages());
        pagingNavigator.addPageChangeListener(this);

        // ///////////////// Init main table ////////////////////////////////
        tblListEntities = new JXTable();

        tblListEntities.addHighlighter(HighlighterFactory.createSimpleStriping());
        // Highlight the row when mouse over.
        tblListEntities
                .addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, HIGHLIGHT_ROW_COLOR, null));

        final AdvanceTableModel mainTableModel = createTableModel();
        tblListEntities.setModel(mainTableModel);

        // Hide the entity column by set width = 0
        tblListEntities.getColumnModel().getColumn(0).setMinWidth(0);
        tblListEntities.getColumnModel().getColumn(0).setMaxWidth(0);

        tblListEntities.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblListEntities.setColumnControlVisible(true);

        // Show edit view when double on a such row.
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
        JScrollPane mainScrollpane = new JScrollPane(tblListEntities);
        this.add(mainScrollpane, "grow");

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
        tblFooter.setPreferredScrollableViewportSize(new Dimension(
                tblFooter.getPreferredScrollableViewportSize().width, tblFooter.getRowHeight()));
        tblFooter.setEnabled(false);
        tblFooter.setShowGrid(false);

        tblListEntities.getColumnModel().addColumnModelListener(tblFooter);

        JScrollPane footerScrollpane = new JScrollPane(tblFooter);

        if (CollectionUtils.isNotEmpty(summaryFieldNames)) {
            this.add(footerScrollpane, "grow");
        }

        this.add(pagingNavigator);
        // //////////////////// Button panel /////////////////////////////////
        JPanel pnlButton = createButtonPanel(tblListEntities);
        this.add(pnlButton);
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
    protected JPanel createButtonPanel(JTable table) {
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(addAction);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AbstractListView.this),
                        "Are you sure want to delete the selected row?", "Confirm delete", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
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

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(editAction);

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnAdd);
        return buttonPanel;
    }

    /**
     * Show the detail view with specific entity (when button Add or Edit is clicked).
     * 
     * @param entity
     *            the entity which the detail view display for. If <code>null</code>, new entity is displayed.
     */
    private void showDetailView(T entity) {
        Class<? extends AbstractDetailView<T>> detailViewClass = getDetailViewClass();
        Class<T> entityClass = getEntityClass();
        try {
            if (entity == null) {
                entity = entityClass.newInstance();
            }

            // TODO This call requires sub class override Constructor method! It's not good.
            AbstractDetailView<T> detailView = detailViewClass.getConstructor(entityClass).newInstance(entity);

            // TODO HPP consider to listen the event from AbstractDetailView (not set reference to it).
            detailView.setListView(AbstractListView.this);

            Window parentContainer = (Window) SwingUtilities.getRoot(AbstractListView.this);
            JDialog dialog = new JDialog(parentContainer);
            dialog.setContentPane(detailView);
            Dimension preferredSize = detailView.getPreferredSize();
            Dimension dialogSize = new Dimension(preferredSize.width + 20, preferredSize.height + 40);
            dialog.setMinimumSize(dialogSize);
            dialog.setLocationRelativeTo(parentContainer); // Display the dialog in the center.
            dialog.setModalityType(ModalityType.APPLICATION_MODAL);
            dialog.setVisible(true);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException("There are problems when init the detail view.");
        }
    }

    protected abstract Class<? extends AbstractDetailView<T>> getDetailViewClass();

    private Class<?> getClassOfField(String fieldName) {
        return Solution3sClassUtils.getGetterMethod(getEntityClass(), fieldName).getReturnType();
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

            // The hide extra column contain the entity ID
            if (columnIndex == 0) {
                return entity.getId();
            }

            DetailAttribute dataModel = listDataModel.get(columnIndex - 1);
            Method method = Solution3sClassUtils.getGetterMethod(getEntityClass(), dataModel.getName());
            try {
                return method.invoke(entity);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
                throw new RuntimeException();
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage());
                throw new RuntimeException();
            }
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
            DetailAttribute dataModel = listDataModel.get(columnIndex);
            Method method = Solution3sClassUtils.getSetterMethod(getEntityClass(), dataModel.getName());
            try {
                method.invoke(entity, aValue);
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
                throw new RuntimeException();
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
                throw new RuntimeException();
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage());
                throw new RuntimeException();
            }

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
        // Keep the selected row before the data of table is changed.
        int selectedRow = tblListEntities.getSelectedRow();
        if (isNew) {
            entities.add(entity);
            selectedRow = entities.size() - 1; // If add new entity, the selected row has the last index.
        }

        // fireTableDataChanged to rerender the table.
        ((AbstractTableModel) tblListEntities.getModel()).fireTableDataChanged();
        ((AbstractTableModel) tblFooter.getModel()).fireTableDataChanged();

        // After fireTableDataChanged() the selection is lost. We need to reselect it programmatically.
        tblListEntities.setRowSelectionInterval(selectedRow, selectedRow);
    }

    private void performEditAction() {
        int selectedRow = tblListEntities.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showConfirmDialog(SwingUtilities.getRoot(AbstractListView.this), "Please select a row to edit",
                    "Warning", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
        } else {
            int rowModel = tblListEntities.convertRowIndexToModel(selectedRow);
            showDetailView(entities.get(rowModel));
        }
    }

    protected void refreshData() {
        entities.removeAll(entities);
        entities.addAll(loadData(pagingNavigator.getCurrentPage()));
        // fireTableDataChanged to re-render the table.
        ((AbstractTableModel) tblListEntities.getModel()).fireTableDataChanged();
        ((AbstractTableModel) tblFooter.getModel()).fireTableDataChanged();
    }

    private class AddAction extends AbstractAction {
        private static final long serialVersionUID = 3455983492968974921L;

        @Override
        public void actionPerformed(ActionEvent e) {
            showDetailView(null);
        }
    }

    private class EditAction extends AbstractAction {
        private static final long serialVersionUID = -7091407169970088286L;

        @Override
        public void actionPerformed(ActionEvent e) {
            performEditAction();
        }

    }

    @Override
    public void doPageChanged(ChangeEvent e) {
        S3sPagingNavigator pagingNavigator = (S3sPagingNavigator) e.getSource();
        // Re-calculate the total pages and set to the pagingNavigator.
        pagingNavigator.setTotalPage(calculateTotalPages());
        refreshData();
    }

}
