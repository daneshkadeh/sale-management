package com.hbsoft.ssm.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
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
import org.springframework.util.StringUtils;

import com.hbsoft.ssm.util.i18n.ControlConfigUtils;
import com.hbsoft.ssm.view.object.DetailDataModel;

/**
 * This is an abstract view for list entities. </br> Subclasses must override {@link #initialPresentationView(List)} and
 * {@link #loadData()} to show.
 * 
 * 
 * @author phamcongbang
 * 
 * @param <T>
 */
public abstract class AbstractListView<T> extends JFrame {
    private static final long serialVersionUID = -1311942671249671111L;
    Log logger = LogFactory.getLog(getClass());

    JButton btnSearch;
    JButton btnSort;
    JTable tblListEntities;
    JScrollPane jScrollPane;

    // Class<T> clazz;
    protected List<T> entities;
    List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
    public boolean selector = false;

    public AbstractListView() {
        // TODO: should get command class from T
        // clazz = getCommandClass();
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

    // protected Class<T> getCommandClass() {
    // return null;
    // }

    private void initComponents() {
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        btnSort = new JButton("Sort");
        btnSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortActionPerformed(evt);
            }
        });

        tblListEntities = new JTable();
        displayEntitiesList();
        tblListEntities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblListEntities.getSelectionModel().addListSelectionListener(new RowListener());

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(tblListEntities);

        Container container = getContentPane();
        container.setLayout(new MigLayout("fillx,insets 1, width :1000:"));

        JPanel pnlListEntities = new JPanel(new MigLayout());
        pnlListEntities.add(jScrollPane, "wrap");
        container.add(pnlListEntities, "wrap");

        JPanel pnlButton = new JPanel(new MigLayout());
        pnlButton.add(btnSearch);
        pnlButton.add(btnSort);
        container.add(pnlButton, "wrap");
        pack();
    }

    private void displayEntitiesList() {
        entities = loadData();
        int numOfCols = listDataModel.size();
        String[] tableHeaders = new String[numOfCols];
        Class<?>[] tableClasses = new Class<?>[numOfCols];
        Object[][] tableData = new Object[numOfCols][entities.size()];

        for (int i = 0; i < numOfCols; i++) {
            String label = ControlConfigUtils.getText("label." + getEntityClass().getSimpleName() + "."
                    + listDataModel.get(i).getFieldName());
            tableHeaders[i] = label;
            tableClasses[i] = getFieldClass(listDataModel.get(i).getFieldName());
        }

        int iRow = 0;
        for (T entity : entities) {
            // Vector<Object> oneRow = new Vector<Object>();
            Object[] rowData = new Object[listDataModel.size()];
            for (int i = 0; i < numOfCols; i++) {
                DetailDataModel dataModel = listDataModel.get(i);
                Method method = null;
                try {
                    method = getEntityClass().getMethod("get" + StringUtils.capitalize(dataModel.getFieldName()));
                } catch (Exception e) {
                    throw new RuntimeException("Can not find GET method for field " + dataModel.getFieldName());
                }

                try {
                    // TODO: should handle cell in CellEditor
                    rowData[i] = method.invoke(entity);
                } catch (Exception e) {
                    throw new RuntimeException("Can not invoke method " + method.getName());
                }
            }

            tableData[iRow] = rowData;
            iRow++;
        }
        tblListEntities.setModel(new DefaultTableModel(tableData, tableHeaders));
        selector = false;

    }

    private Class<?> getFieldClass(String fieldName) {
        try {
            Method method = getEntityClass().getMethod("get" + StringUtils.capitalize(fieldName));
            return method.getReturnType();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Can not find fieldName " + fieldName, e);
        }
    }

    public static void main(String args[]) {

    }

    private DetailDataModel getDataModelFromGetMethod(String setMethodName) {
        for (DetailDataModel dataModel : listDataModel) {
            if (setMethodName.equals("get" + StringUtils.capitalize(dataModel.getFieldName()))) {
                return dataModel;
            }
        }
        return null;
    }

    protected Class<T> getEntityClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) controllerType;
    }

    protected void btnSortActionPerformed(ActionEvent evt) {
        // TODO Auto-generated method stub
    }

    protected void btnSearchActionPerformed(ActionEvent evt) {
        // TODO Auto-generated method stub

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
