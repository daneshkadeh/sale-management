package com.hbsoft.ssm.view;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.springframework.util.StringUtils;

import sun.swing.table.DefaultTableCellHeaderRenderer;

import com.hbsoft.ssm.entity.AbstractBaseIdObject;
import com.hbsoft.ssm.model.DetailDataModel;
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
    private JScrollPane jScrollPane;

    // Class<T> clazz;
    protected List<T> entities;
    private final List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
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
        tblListEntities.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblListEntities.getSelectionModel().addListSelectionListener(new RowListener());

        // TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>) tblListEntities.getRowSorter();
        // tblListEntities.getModel().

        // Install sorting for table
        // SortedList<T> sortedEntities = new SortedList<T>(entities, new DefaultEntityComparator());
        // TableComparatorChooser<T> tableComparatorChooser = TableComparatorChooser.install(tblListEntities,
        // sortedEntities, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

        jScrollPane = new JScrollPane(tblListEntities);

        this.add(jScrollPane, "grow");

        JPanel pnlButton = createButtonPanel(tblListEntities);
        this.add(pnlButton);
    }

    /**
     * The list of buttons to do actions on the table.
     * 
     * @return the panel containing the buttons.
     */
    protected abstract JPanel createButtonPanel(JTable table);

    private void displayEntitiesList() {
        entities = loadData();
        // EventTableModel<T> tableModel = new EventTableModel<T>(entities, new BasicTableFormat());
        AdvanceTableModel tableModel = new AdvanceTableModel();
        tblListEntities.setModel(tableModel);
        selector = false;
    }

    /**
     * Return getter or setter method base on <code>getter</code> parameter.
     * 
     * @param fieldName
     *            the name of property
     * @param getter
     *            <code>true</code> getter method, <code>false</code> setter method.
     * @return
     */
    private Method getGetterSetterMethod(String fieldName, boolean getter) {
        try {
            String methodPrefix = getter ? "get" : "set";
            return getEntityClass().getMethod(methodPrefix + StringUtils.capitalize(fieldName));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("field " + fieldName + " or its setter/getter method does not exist in class "
                    + getEntityClass().getName());
        }
    }

    private Class<?> getClassOfField(String fieldName) {
        return getGetterSetterMethod(fieldName, true).getReturnType();
    }

    private DetailDataModel getDataModelFromGetMethod(String setMethodName) {
        for (DetailDataModel dataModel : listDataModel) {
            if (setMethodName.equals("get" + StringUtils.capitalize(dataModel.getFieldName()))) {
                return dataModel;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) controllerType;
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
        private List<T> currentEntities = entities;

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

        /**
         * {@inheritDoc}
         */
        @Override
        public int getRowCount() {
            return currentEntities.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            T entity = currentEntities.get(rowIndex);
            DetailDataModel dataModel = listDataModel.get(columnIndex);
            Method method = getGetterSetterMethod(dataModel.getFieldName(), true);
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

        /**
         * {@inheritDoc}
         */
        @Override
        public int getColumnCount() {
            return listDataModel.size();
        }

        @Override
        public String getColumnName(int column) {
            return ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + listDataModel.get(column).getFieldName());
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            T entity = currentEntities.get(rowIndex);
            DetailDataModel dataModel = listDataModel.get(columnIndex);
            Method method = getGetterSetterMethod(dataModel.getFieldName(), false);
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
            return getClassOfField(listDataModel.get(columnIndex).getFieldName());
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

    private class TableHeaderRenderer extends DefaultTableCellHeaderRenderer {

    }
}
