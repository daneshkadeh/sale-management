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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.SClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;

/**
 * @author Phan Hong Phuc
 * @since Apr 18, 2012
 */
public abstract class ASearchComponent<T extends AbstractBaseIdObject> extends JPanel implements DocumentListener {
    private static final int NUM_VISIBLE_ROWS = 10;
    private static final long serialVersionUID = -869806032147504253L;
    private static final int MAX_RESULT = 50;
    private JTextField textField;
    private JWindow popup;
    private JScrollPane tablePane;
    private JXTable table;
    private List<T> entities = new ArrayList<>();
    private String[] attributeColumns;
    private String[] searchOnAttributes;
    private String[] displayAttribute;
    private IBaseDao<T> dao;
    private EntityLoader worker = new EntityLoader();
    private T selectedEntity;
    private Class<T> entityClass;
    private JPanel suggestPanel;
    private boolean isTableShown = false;

    /**
     * Default constructor.
     */
    public ASearchComponent() {
        this.displayAttribute = getDisplayAttributes();
        this.attributeColumns = getAttributeColumns();
        this.searchOnAttributes = getSearchedOnAttributes();
        this.entityClass = (Class<T>) SClassUtils.getArgumentClass(getClass());
        this.dao = (IBaseDao<T>) ConfigProvider.getInstance().getDaoHelper().getDao(entityClass);
        suggestPanel = new JPanel();
        suggestPanel.setBackground(UIConstants.INFO_COLOR);
        JLabel tipLabel = new JLabel(ControlConfigUtils.getString("SearchComponent.suggestion"));
        tipLabel.setFont(UIConstants.DEFAULT_ITALIC_FONT);
        suggestPanel.add(tipLabel);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // ///////////// Table ///////////////////
        TableModel model = new MyTableModel();
        table = new JXTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setVisibleRowCount(NUM_VISIBLE_ROWS);
        tablePane = new JScrollPane(table);

        // ///////////// Text Field ////////////////
        textField = new JTextField(20);
        textField.getDocument().addDocumentListener(this);
        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                worker.cancel(true);
                if (selectedEntity == null) {
                    textField.setText(null);
                }
                popup.setVisible(false);
                isTableShown = false;

            }

            @Override
            public void focusGained(FocusEvent e) {
                if (StringUtils.isNotBlank(textField.getText())) {
                    showTipPanel();
                }
            }
        });
        // //////// PromptSupport for textField
        String promptText = createPromptText();
        PromptSupport.setPrompt(promptText, textField);
        PromptSupport.setFontStyle(Font.ITALIC, textField);
        PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, textField);

        // ////////// Key listener for textField
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

    /**
     * Get the attribute to display on the text box after selecting a value. Should be unique constraint.
     * 
     * @return
     */
    protected abstract String[] getDisplayAttributes();

    /**
     * Get the attributes to show information about the entity. Each attribute is shown on a column of the dropdown
     * table.
     */
    protected abstract String[] getAttributeColumns();

    /**
     * The attributes to search on when user type in textbox. </br> <b>Pay attention:</b> The attributes must be String
     * type, or you need to override the {@link #createSearchCriteria()}.
     */
    protected abstract String[] getSearchedOnAttributes();

    /**
     * @return
     */
    private String createPromptText() {
        StringBuilder sb = new StringBuilder();
        sb.append(ControlConfigUtils.getString("SearchComponent.input")).append(' ');
        List<String> attributes = new ArrayList<>(searchOnAttributes.length);
        for (String attribute : searchOnAttributes) {
            attributes.add(ControlConfigUtils.getString(entityClass.getSimpleName() + '.' + attribute));
        }
        sb.append(StringUtils.join(attributes, ", "));
        return sb.toString();
    }

    private class DownAction extends AbstractAction {
        private static final long serialVersionUID = 3977021150275942850L;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!isTableShown) {
                popup.setLocation(textField.getLocationOnScreen().x,
                        textField.getLocationOnScreen().y + textField.getPreferredSize().height);
                popup.remove(suggestPanel);
                popup.add(tablePane);
                popup.setVisible(true);
                isTableShown = true;
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
                setSelectedEntity(entities.get(currentRow));
                popup.setVisible(false);
            }
        }

    }

    private String buildDisplayString(String[] displayAttribute, BeanWrapper wrapper) {
        List<String> s = new ArrayList<>(displayAttribute.length);
        for (String atb : displayAttribute) {
            s.add((String) wrapper.getPropertyValue(atb));
        }
        return StringUtils.join(s, " - ");
    }

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
            return ControlConfigUtils.getString(entityClass.getSimpleName() + '.' + attributeColumns[column]);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return SClassUtils.getClassOfField(attributeColumns[columnIndex], entityClass);
        }

        @Override
        public int getColumnCount() {
            return attributeColumns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            BeanWrapper wrapper = new BeanWrapperImpl(entities.get(rowIndex));
            return wrapper.getPropertyValue(attributeColumns[columnIndex]);
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
        if (textField.hasFocus()) {
            if (popup.isVisible() && isTableShown) {
                // SwingWorker is only designed to be executed once. Executing a SwingWorker more than once will not
                // result in invoking the doInBackground method twice.
                // TODO Phuc wait 0.5 .....
                worker.cancel(true);
                worker = new EntityLoader();
                worker.run();
            } else {
                showTipPanel();
            }
        }
    }

    private void showTipPanel() {
        popup.remove(tablePane);
        isTableShown = false;
        popup.setLocation(textField.getLocationOnScreen().x,
                textField.getLocationOnScreen().y + textField.getPreferredSize().height);
        popup.add(suggestPanel);
        popup.setVisible(true);
        popup.pack();
    }

    protected DetachedCriteria createSearchCriteria() {
        DetachedCriteria criteria = dao.getCriteria();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criterion criterion = Restrictions.ilike(searchOnAttributes[0], textField.getText(), MatchMode.ANYWHERE);
        for (int i = 1; i < searchOnAttributes.length; i++) {
            criterion = Restrictions.or(criterion,
                    Restrictions.ilike(searchOnAttributes[i], textField.getText(), MatchMode.ANYWHERE));
        }
        criteria.add(criterion);
        return criteria;
    }

    private class EntityLoader extends SwingWorker<List<T>, T> {

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
                popup.pack();
            } catch (InterruptedException | ExecutionException | CancellationException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSelectedEntity(T entity) {
        selectedEntity = entity;
        if (entity != null) {
            BeanWrapper wrapper = new BeanWrapperImpl(selectedEntity);
            textField.setText(buildDisplayString(displayAttribute, wrapper));
        } else {
            textField.setText("");
        }
        fireValueChanged();
    }

    public T getSelectedEntity() {
        return selectedEntity;
    }

    // ///////// Fire event ///////////////
    private void fireValueChanged() {
        for (Object l : listenerList.getListenerList()) {
            ChangeEvent ce = new ChangeEvent(this);
            if (l instanceof IValueChangedListener) {
                ((IValueChangedListener) l).doValueChanged(ce);
            }
        }
    }

    public void addValueChangedListener(IValueChangedListener listener) {
        listenerList.add(IValueChangedListener.class, listener);
    }

    public void removeValueChangedListener(IValueChangedListener listener) {
        listenerList.remove(IValueChangedListener.class, listener);
    }
}
