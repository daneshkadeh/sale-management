/*
 * SearchComponent
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

package com.s3s.ssm.view.component;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.swingx.JXTable;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.Solution3sClassUtils;

/**
 * @author Phan Hong Phuc
 * @since Apr 18, 2012
 */
public abstract class SearchComponent<T extends AbstractBaseIdObject> extends JPanel implements DocumentListener {
    private static final long serialVersionUID = -869806032147504253L;
    private static final int MAX_RESULT = 50;
    private JTextField textField;
    private JWindow popup;
    private JScrollPane tablePane;
    private JXTable table;
    private List<T> entities = new ArrayList<>();
    private List<String> attributeColumns = new ArrayList<>();
    private List<String> searchOnAttributes = new ArrayList<>();
    private IBaseDao<T> dao = (IBaseDao<T>) ConfigProvider.getInstance().getDaoHelper()
            .getDao(Solution3sClassUtils.getArgumentClass(getClass()));
    private MyWorker currentWorker = new MyWorker();
    private T selectedEntity;

    public SearchComponent() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        addAttributeColumns(attributeColumns);
        addSearchOnAttributes(searchOnAttributes);

        // ///////////// Table ///////////////////
        TableModel model = new MyTableModel();
        table = new JXTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePane = new JScrollPane(table);

        // ///////////// Text Field ////////////////
        textField = new JTextField(20);
        textField.getDocument().addDocumentListener(this);
        textField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                popup.setVisible(false);
                // TODO Phuc: base on the text, find the selectedEntiy.
            }

        });

        InputMap inputMap = textField.getInputMap(WHEN_FOCUSED);
        KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        inputMap.put(downKey, "downAction");
        inputMap.put(upKey, "upAction");
        inputMap.put(enterKey, "enterAction");

        ActionMap actionMap = textField.getActionMap();
        actionMap.put("downAction", new DownAction());
        actionMap.put("upAction", new UpAction());
        actionMap.put("enterAction", new EnterAction());
        add(textField);

        // /////////////// Window ////////////////
        popup = new JWindow();
        popup.add(tablePane);
    }

    private class DownAction extends AbstractAction {
        private static final long serialVersionUID = 3977021150275942850L;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!popup.isVisible()) {
                doChangeText();
            }
            if (!entities.isEmpty()) {
                int currentRow = table.getSelectedRow();
                if (currentRow == table.getRowCount() - 1) {
                    currentRow = 0;
                } else {
                    currentRow++;
                }
                table.setRowSelectionInterval(currentRow, currentRow);
            }
        }
    }

    private class UpAction extends AbstractAction {
        private static final long serialVersionUID = -6870345786236419863L;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!popup.isVisible()) {
                return;
            }
            if (!entities.isEmpty()) {
                int currentRow = table.getSelectedRow();
                if (currentRow == 0) {
                    currentRow = table.getRowCount() - 1;
                } else {
                    currentRow--;
                }
                table.setRowSelectionInterval(currentRow, currentRow);
            }
        }
    }

    private class EnterAction extends AbstractAction {
        private static final long serialVersionUID = 4596446039663232049L;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!popup.isVisible()) {
                return;
            }
            if (!entities.isEmpty()) {
                int currentRow = table.getSelectedRow();
                selectedEntity = entities.get(currentRow);
                BeanWrapper wrapper = new BeanWrapperImpl(selectedEntity);
                textField.setText((String) wrapper.getPropertyValue(getDisplayField()));
                popup.setVisible(false);
            }
        }
    }

    protected abstract void addAttributeColumns(List<String> attributeColumns);

    protected abstract void addSearchOnAttributes(List<String> searchOnColumns);

    protected abstract String getDisplayField();

    @Override
    public boolean requestFocusInWindow() {
        return textField.requestFocusInWindow();
    }

    // TODO Phuc rename later
    private class MyTableModel extends AbstractTableModel {
        private static final long serialVersionUID = -7789476798175848307L;

        @Override
        public int getRowCount() {
            return entities.size();
        }

        @Override
        public String getColumnName(int column) {
            // TODO Phuc: name of column need to get as SearchComponent.<entity class name>.<Field name>
            return super.getColumnName(column);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return Solution3sClassUtils.getClassOfField(attributeColumns.get(columnIndex),
                    Solution3sClassUtils.getArgumentClass(SearchComponent.this.getClass()));
        }

        @Override
        public int getColumnCount() {
            return attributeColumns.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            BeanWrapper wrapper = new BeanWrapperImpl(entities.get(rowIndex));
            return wrapper.getPropertyValue(attributeColumns.get(columnIndex));
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        doChangeText();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        doChangeText();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        doChangeText();
    }

    /**
     * Perform when insert, remove or change the text in textField
     */
    protected void doChangeText() {
        currentWorker.cancel(true);
        // SwingWorker is only designed to be executed once. Executing a SwingWorker more than once will not result
        // in invoking the doInBackground method twice.
        currentWorker = new MyWorker();
        currentWorker.run();
        popup.setLocation(textField.getLocationOnScreen().x,
                textField.getLocationOnScreen().y + textField.getPreferredSize().height);
        popup.pack();
        popup.setVisible(true);
    }

    protected DetachedCriteria createSearchCriteria() {
        DetachedCriteria criteria = dao.getCriteria();
        Criterion criterion = Restrictions.ilike(searchOnAttributes.get(0), textField.getText(), MatchMode.ANYWHERE);
        for (int i = 1; i < searchOnAttributes.size(); i++) {
            criterion = Restrictions.or(criterion,
                    Restrictions.ilike(searchOnAttributes.get(i), textField.getText(), MatchMode.ANYWHERE));
        }
        criteria.add(criterion);
        return criteria;
    }

    class MyWorker extends SwingWorker<List<T>, T> {

        @Override
        protected List<T> doInBackground() throws Exception {
            return (List<T>) dao.findByCriteria(createSearchCriteria(), 0, MAX_RESULT);
        }

        @Override
        protected void done() {
            try {
                entities.removeAll(entities);
                entities.addAll(get());
                ((MyTableModel) table.getModel()).fireTableDataChanged();
                table.packAll();
            } catch (InterruptedException | ExecutionException | CancellationException e) {
                e.printStackTrace();
            }
        }
    }
}
