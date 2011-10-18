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
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * This is an abstract view for list entities.
 * 
 * @author phamcongbang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractListView<T extends AbstractBaseIdObject> extends AbstractView {
    private static final String ADD_ACTION_KEY = "addAction";
    private static final Color HIGHLIGHT_ROW_COLOR = new Color(97, 111, 231);
    private static final long serialVersionUID = -1311942671249671111L;
    private static final Log logger = LogFactory.getLog(AbstractListView.class);

    private JXTable tblListEntities;

    private Class<? extends AbstractBaseIdObject> parentClass;
    private Long parentId;

    protected List<T> entities = new ArrayList<T>();

    // This model is used by sub classes.
    protected final List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
    protected final List<String> summaryFieldNames = new ArrayList<String>();

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
        setLayout(new MigLayout("wrap", "grow, fill", "[]0[]0[grow, fill]5[]0[]"));

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
            initialPresentationView(List<DetailDataModel> listDataModel, List<String> summaryFieldNames);

    /**
     * 
     * @return all data shown on the view.
     */
    protected List<T> loadData() {
        return getDaoHelper().getDao(getEntityClass()).findAll();
    }

    protected void addComponents() {
        tblListEntities = new JXTable();

        tblListEntities.addHighlighter(HighlighterFactory.createSimpleStriping());
        // Highlight the row when mouse over.
        tblListEntities
                .addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, HIGHLIGHT_ROW_COLOR, null));

        final AdvanceTableModel mainTableModel = createTableModel();
        tblListEntities.setModel(mainTableModel);

        // Hide the entity column by set width = 0
        tblListEntities.getColumnExt(0).setVisible(false);
        tblListEntities.getColumnExt(0).setVisible(false);
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

        // //////////////// Create footer table
        TableModel footerModel = new AbstractTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return null;
                }
                DetailDataModel detailDataModel = listDataModel.get(columnIndex - 1); // decrease 1 because the hidden
                                                                                      // entity column
                String fieldName = detailDataModel.getFieldName();
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
                return getClassOfField(listDataModel.get(columnIndex - 1).getFieldName());
            }

        };

        JXTable footerTable = new JXTable(footerModel, tblListEntities.getColumnModel()) {
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
        footerTable.setTableHeader(null); // Remove table header.
        // Visible 1 row in footer table.
        footerTable.setPreferredScrollableViewportSize(new Dimension(
                footerTable.getPreferredScrollableViewportSize().width, footerTable.getRowHeight()));
        footerTable.setEnabled(false);

        tblListEntities.getColumnModel().addColumnModelListener(footerTable);

        JScrollPane mainScrollpane = new JScrollPane(tblListEntities);
        JScrollPane footerScrollpane = new JScrollPane(footerTable);

        this.add(mainScrollpane, "grow");
        this.add(footerScrollpane, "grow");

        JPanel pnlButton = createButtonPanel(tblListEntities);
        this.add(pnlButton);
    }

    private AdvanceTableModel createTableModel() {
        entities = loadData();
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
                    for (int i : selectedRows) {
                        int rowModelIndex = tblListEntities.convertRowIndexToModel(i);
                        entities.remove(rowModelIndex);
                        ((AdvanceTableModel) tblListEntities.getModel()).fireTableDataChanged();
                    }
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
            logger.error(ex.getCause());
            logger.error(ex.getMessage());
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
     * 
     * @author Phan Hong Phuc
     * 
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

            // The hide extra column contain the entity
            if (columnIndex == 0) {
                return entity;
            }

            DetailDataModel dataModel = listDataModel.get(columnIndex - 1);
            Method method = Solution3sClassUtils.getGetterMethod(getEntityClass(), dataModel.getFieldName());
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
                    + listDataModel.get(column - 1).getFieldName());
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            T entity = currentEntities.get(rowIndex);
            DetailDataModel dataModel = listDataModel.get(columnIndex);
            Method method = Solution3sClassUtils.getSetterMethod(getEntityClass(), dataModel.getFieldName());
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
            return getClassOfField(listDataModel.get(columnIndex - 1).getFieldName());
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
        ((AdvanceTableModel) tblListEntities.getModel()).fireTableDataChanged();

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
            showDetailView((T) tblListEntities.getModel().getValueAt(rowModel, 0));
        }
    }

    protected void refreshData() {
        entities.removeAll(entities);
        entities.addAll(loadData());
        // fireTableDataChanged to rerender the table.
        ((AdvanceTableModel) tblListEntities.getModel()).fireTableDataChanged();
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
}
