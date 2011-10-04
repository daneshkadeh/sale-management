package com.hbsoft.ssm.view;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

import com.hbsoft.ssm.entity.AbstractBaseIdObject;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.util.Solution3sClassUtils;
import com.hbsoft.ssm.util.i18n.ControlConfigUtils;

/**
 * This is an abstract view for list entities.
 * 
 * @author phamcongbang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractListView<T extends AbstractBaseIdObject> extends JPanel {
    private static final Color HIGHLIGHT_ROW_COLOR = new Color(220, 220, 250);
    private static final long serialVersionUID = -1311942671249671111L;
    private static final Log logger = LogFactory.getLog(AbstractListView.class);

    private JXTable tblListEntities;

    // Class<T> clazz;
    protected List<T> entities = new ArrayList<T>();

    // This model is used by sub classes.
    protected final List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
    public boolean selector = false;

    public AbstractListView() {
        initialPresentationView(listDataModel);
        initComponents();
    }

    /**
     * List fields need to show on the view.
     * 
     * @param listDataModel
     */
    protected abstract void initialPresentationView(List<DetailDataModel> listDataModel);

    /**
     * 
     * @return all data should be show on the view.
     */
    protected List<T> loadData() {
        // TODO: all data must be get on this AbstractListView
        return new ArrayList<T>();
    }

    private void initComponents() {
        this.setLayout(new MigLayout("wrap", "grow, fill", "grow, fill"));

        tblListEntities = new JXTable();
        // Highlight the row when mouse over.
        tblListEntities
                .addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, HIGHLIGHT_ROW_COLOR, null));

        displayEntitiesList();

        // Hide the entity column by set width = 0
        tblListEntities.getColumnModel().getColumn(0).setMinWidth(0);
        tblListEntities.getColumnModel().getColumn(0).setMaxWidth(0);

        tblListEntities.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblListEntities.getSelectionModel().addListSelectionListener(new RowListener());

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

        // TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>) tblListEntities.getRowSorter();
        // tblListEntities.getModel().

        // Install sorting for table
        // SortedList<T> sortedEntities = new SortedList<T>(entities, new DefaultEntityComparator());
        // TableComparatorChooser<T> tableComparatorChooser = TableComparatorChooser.install(tblListEntities,
        // sortedEntities, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

        JScrollPane jScrollPane = new JScrollPane(tblListEntities);
        this.add(jScrollPane, "grow");

        JPanel pnlButton = createButtonPanel(tblListEntities);
        this.add(pnlButton);
    }

    /**
     * The list of buttons to do actions on the table.
     * 
     * @return the panel containing the buttons.
     */
    protected JPanel createButtonPanel(JTable table) {
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showDetailView(null);
            }

        });

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog((JFrame) SwingUtilities.getRoot(AbstractListView.this),
                        "Are you sure want to delete the selected row?", "Confirm delete", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    int[] selectedRows = tblListEntities.getSelectedRows();
                    for (int i : selectedRows) {
                        int rowModelIndex = tblListEntities.convertRowIndexToModel(i);
                        entities.remove(rowModelIndex);
                    }
                }
            }
        });

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performEditAction();
            }
        });

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

    protected void displayEntitiesList() {
        entities = loadData();
        // EventTableModel<T> tableModel = new EventTableModel<T>(entities, new BasicTableFormat());
        AdvanceTableModel tableModel = new AdvanceTableModel();

        tblListEntities.setModel(tableModel);
        selector = false;
    }

    private Class<?> getClassOfField(String fieldName) {
        return Solution3sClassUtils.getGetterMethod(getEntityClass(), fieldName).getReturnType();
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return (Class<T>) Solution3sClassUtils.getArgumentClass(getClass());
    }

    protected class RowListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            selector = true;
        }
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

        private Set<Integer> hiddenRows = new HashSet<Integer>();

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
            showDetailView((T) tblListEntities.getValueAt(selectedRow, 0));
        }
    }
}
