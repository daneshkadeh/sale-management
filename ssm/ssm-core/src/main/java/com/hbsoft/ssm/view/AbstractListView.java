package com.hbsoft.ssm.view;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.springframework.util.StringUtils;

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
public abstract class AbstractListView<T> extends JPanel {
    private static final long serialVersionUID = -1311942671249671111L;
    private static final Log logger = LogFactory.getLog(AbstractListView.class);

    private JTable tblListEntities;
    private JScrollPane jScrollPane;

    // Class<T> clazz;
    protected List<T> entities;
    private List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
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
        ((JXTable) tblListEntities).addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, null,
                Color.RED));

        displayEntitiesList();
        tblListEntities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblListEntities.getSelectionModel().addListSelectionListener(new RowListener());

        jScrollPane = new JScrollPane(tblListEntities);

        this.add(jScrollPane, "grow");

        // JPanel pnlButton = new JPanel(new MigLayout());
        // pnlButton.add(btnSearch);
        // pnlButton.add(btnSort);
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
        DefaultTableModel tableModel = createTableModel();
        tblListEntities.setModel(tableModel);
        selector = false;
    }

    private DefaultTableModel createTableModel() {
        int numOfCols = listDataModel.size();

        // Create names and clazzes for columns in tables.
        String[] tableHeaders = new String[numOfCols];
        Class<?>[] tableClasses = new Class<?>[numOfCols];
        for (int i = 0; i < numOfCols; i++) {
            String label = ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + listDataModel.get(i).getFieldName());
            tableHeaders[i] = label;
            tableClasses[i] = getClassOfField(listDataModel.get(i).getFieldName());
        }

        // Create data for table.s
        Object[][] tableData = new Object[numOfCols][entities.size()];
        int iRow = 0;
        try {
            for (T entity : entities) {
                Object[] rowData = new Object[listDataModel.size()];
                for (int i = 0; i < numOfCols; i++) {
                    DetailDataModel dataModel = listDataModel.get(i);
                    Method method = getGetterMethoḍ̣̣̣̣(dataModel.getFieldName());
                    // TODO: should handle cell in CellEditor
                    rowData[i] = method.invoke(entity);
                }
                tableData[iRow] = rowData;
                iRow++;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getMessage());
        }

        return new DefaultTableModel(tableData, tableHeaders);
    }

    private Method getGetterMethoḍ̣̣̣̣(String fieldName) {
        try {
            return getEntityClass().getMethod("get" + StringUtils.capitalize(fieldName));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("field " + fieldName + " or its getter method does not exist in class "
                    + getEntityClass().getName());
        }
    }

    private Class<?> getClassOfField(String fieldName) {
        return getGetterMethoḍ̣̣̣̣(fieldName).getReturnType();
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
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            try {
                selector = true;
            } catch (Exception e) {
            }
        }
    }
}
