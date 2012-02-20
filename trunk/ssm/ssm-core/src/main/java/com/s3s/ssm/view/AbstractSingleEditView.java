/*
 * AbstractDetailView
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
package com.s3s.ssm.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.GroupInfoData;
import com.s3s.ssm.model.DetailDataModel.TabInfoData;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.model.ReferenceDataModel.ReferenceData;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.util.view.WindowUtilities;
import com.s3s.ssm.view.NotifyPanel.NotifyKind;
import com.s3s.ssm.view.component.EntityChooser;
import com.s3s.ssm.view.component.FileChooser;
import com.s3s.ssm.view.component.ImageChooser;
import com.s3s.ssm.view.component.MultiSelectionBox;
import com.s3s.ssm.view.component.RadioButtonsGroup;
import com.s3s.ssm.view.component.SaleTargetComp;
import com.s3s.ssm.view.component.SaleTargetModel;

/**
 * The edit view with single view.
 * 
 * @author Pham Cong Bang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */

public abstract class AbstractSingleEditView<T extends AbstractIdOLObject> extends AbstractEditView<T> {
    private static final long serialVersionUID = 1L;

    private final Log logger = LogFactory.getLog(AbstractSingleEditView.class);

    protected DetailDataModel detailDataModel = new DetailDataModel();
    protected Map<String, AttributeComponent> name2AttributeComponent = new HashMap<>();

    private JButton btnSave;
    // private JButton btnSaveClose;
    private JButton btnSaveNew;
    private JButton btnNew;
    private JButton btnExit;
    protected T entity;
    protected BeanWrapper beanWrapper;

    private final ReferenceDataModel refDataModel = new ReferenceDataModel();

    private NotifyPanel notifyPanel;

    /**
     * The class includes the components to render an attribute. Like label, component, errorIcon...
     */
    public class AttributeComponent {
        private JLabel label;
        private JComponent component;
        private JLabel errorIcon;

        public AttributeComponent(JLabel label, JComponent component, JLabel errorIcon) {
            super();
            this.label = label;
            this.component = component;
            this.errorIcon = errorIcon;
        }

        public JLabel getLabel() {
            return label;
        }

        public void setLabel(JLabel label) {
            this.label = label;
        }

        public JComponent getComponent() {
            return component;
        }

        public void setComponent(JComponent component) {
            this.component = component;
        }

        public JLabel getErrorIcon() {
            return errorIcon;
        }

        public void setErrorIcon(JLabel errorIcon) {
            this.errorIcon = errorIcon;
        }
    }

    // /**
    // * The default constructor, init the detail view with new entity. TODO: Suspended for not confusing.
    // */
    // public AbstractDetailView() {
    // try {
    // T entity = getEntityClass().newInstance();
    // loadForCreate(entity);
    // contructView(entity);
    // } catch (InstantiationException | IllegalAccessException e) {
    // logger.error("There is a problem when init the detail view");
    // throw new RuntimeException(e.getCause());
    // }
    //
    // }

    /**
     * Append default attributes for entity.
     * 
     * @param entity
     */
    protected void loadForCreate(T entity) {

    }

    /**
     * Append missing attributes for entity.
     * 
     * @param entity
     */
    protected void loadForEdit(T entity) {

    }

    /**
     * Initialize the detail view. TODO: concrete classes should not override this constructor.
     * 
     * @param entity
     *            the entity of detail view.
     */
    public AbstractSingleEditView(T entity) {
        this(entity, null, null);
    }

    public AbstractSingleEditView(T entity, Long parentId, Class<? extends AbstractBaseIdObject> parentClass) {
        super(entity, parentId, parentClass);
        this.entity = entity;
        beanWrapper = new BeanWrapperImpl(entity);
        loadForEdit(entity);
        contructView(entity);
    }

    private void contructView(T entity) {
        initialPresentationView(detailDataModel, entity);
        setReferenceDataModel(refDataModel, entity);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return (Class<T>) Solution3sClassUtils.getArgumentClass(getClass());
    }

    public abstract void initialPresentationView(DetailDataModel detailDataModel, T entity);

    /**
     * Set data for ComboBox, MultiSelectBox.
     * 
     * @param refDataModel
     * @param entity
     */
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, T entity) {
    }

    protected void initComponents() {
        // Layout the screen
        setLayout(new MigLayout("hidemode 2, wrap, fillx"));

        // Toolbar
        JToolBar toolbar = createToolBar();
        add(toolbar, "growx, top");

        // Information panel
        notifyPanel = new NotifyPanel();
        notifyPanel.setVisible(false);
        add(notifyPanel, "top, growx");

        // Fields panel
        List<TabInfoData> tabList = detailDataModel.getTabList();
        int numOfAttributes = detailDataModel.getDetailAttributes().size();
        if (CollectionUtils.isNotEmpty(tabList)) {
            JTabbedPane tabPane = createTabPane(tabList, numOfAttributes);
            add(tabPane, "grow");
        } else {
            JPanel fieldsPanel = createFieldsPanel(0, numOfAttributes);
            add(fieldsPanel, "grow");
        }

        customizeComponents(name2AttributeComponent, entity);
    }

    /**
     * Customize the components.
     * 
     * @param name2AttributeComponent
     */
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, T entity) {
        // Template method
    };

    private JTabbedPane createTabPane(List<TabInfoData> tabList, int numOfAttributes) {
        Assert.isTrue(tabList.get(0).getStartIndex() == 0, "Tab must be added before attribute");
        JTabbedPane tabPane = new JTabbedPane();
        for (int i = 0; i < tabList.size() - 1; i++) {
            TabInfoData tab = tabList.get(i);
            JPanel pane = createFieldsPanel(tab.getStartIndex(), tabList.get(i + 1).getStartIndex());
            tabPane.addTab(tab.getName(), tab.getIcon(), pane, tab.getTooltip());
        }
        TabInfoData endTab = tabList.get(tabList.size() - 1);
        JPanel pane = createFieldsPanel(endTab.getStartIndex(), numOfAttributes);
        tabPane.addTab(endTab.getName(), endTab.getIcon(), pane, endTab.getTooltip());
        return tabPane;
    }

    private JPanel createFieldsPanel(int beginTabIndex, int endTabIndex) {
        JPanel fieldsPanel = new JPanel(new MigLayout("ins 0"));
        int i = beginTabIndex;
        for (GroupInfoData g : detailDataModel.getGroupList()) {
            if (g.getStartGroupIndex() >= beginTabIndex && g.getEndGroupIndex() <= endTabIndex) {
                addFields(fieldsPanel, i, g.getStartGroupIndex(), null);
                addFields(fieldsPanel, g.getStartGroupIndex(), g.getEndGroupIndex(), g.getName());
                i = g.getEndGroupIndex();
            }
        }
        addFields(fieldsPanel, i, endTabIndex, null);
        return fieldsPanel;
    }

    /**
     * 
     * @param fieldsPanel
     *            the panel which fields add to.
     * @param startIndex
     *            the inclusive index in detailDataModel.getDetailAttribute()
     * @param endIndex
     *            the exclusive index in detailDataModel.getDetailAttribute()
     * @param name
     *            the name of panel. If not null -> The panel render with title border.
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private JPanel addFields(JPanel fieldsPanel, int startIndex, int endIndex, String name) {
        JPanel pnlEdit = fieldsPanel;
        if (name != null) { // in case of group of fields.
            pnlEdit = new JPanel(new MigLayout("ins 0"));
            fieldsPanel.add(pnlEdit, "newline, spanx");
            pnlEdit.setBorder(new TitledBorder(name));
        }
        for (int i = startIndex; i < endIndex; i++) {
            DetailAttribute attribute = detailDataModel.getDetailAttributes().get(i);
            String label = ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + attribute.getName());
            String newline = attribute.isNewColumn() ? "gapleft 20, " : "newline, ";
            int width = attribute.getWidth() == 0 ? UIConstants.DEFAULT_TEXTFIELD_COLUMN : attribute.getWidth();
            if (attribute.isMandatory()) {
                label += " (*)";
            }
            JLabel lblLabel = new JLabel(label);
            JComponent dataField;
            Object value = beanWrapper.getPropertyValue(attribute.getName());
            ReferenceData referenceData = refDataModel.getRefDataListMap().get(attribute.getReferenceDataId());
            switch (attribute.getType()) {
            case TEXTBOX:
                Class<?> propertyReturnType = beanWrapper.getPropertyType(attribute.getName());
                if (ClassUtils.isAssignable(propertyReturnType, Number.class)) {
                    NumberFormatter numFormatter = new NumberFormatter();
                    numFormatter.setValueClass(propertyReturnType);
                    dataField = new JFormattedTextField(numFormatter);
                    ((JFormattedTextField) dataField).setHorizontalAlignment(JFormattedTextField.RIGHT);
                } else {
                    // The format type is String
                    dataField = new JFormattedTextField("");
                }
                ((JFormattedTextField) dataField).setEditable(attribute.isEditable());
                ((JFormattedTextField) dataField).setColumns(width);

                dataField.setEnabled(attribute.isEnable());
                ((JFormattedTextField) dataField).setValue(value);
                pnlEdit.add(lblLabel, newline);
                break;
            case TEXTAREA:
                JTextArea ta = new JTextArea(UIConstants.DEFAUL_TEXTAREA_ROWS, width);
                ta.setLineWrap(true);
                ta.setEditable(true);
                String txtValue = value != null ? StringUtils.trimToEmpty(String.valueOf(value)) : "";
                ta.setText(txtValue);
                dataField = new JScrollPane(ta);
                pnlEdit.add(lblLabel, newline + "top");
                break;
            case PASSWORD:
                dataField = new JPasswordField(width);
                ((JPasswordField) dataField).setEditable(attribute.isEditable());
                dataField.setEnabled(attribute.isEnable());
                ((JTextField) dataField).setText(ObjectUtils.toString(value));
                pnlEdit.add(lblLabel);
                break;
            case DROPDOWN:
                dataField = new JComboBox<>(referenceData.getValues().toArray());
                ((JComboBox<?>) dataField).setPreferredSize(new Dimension(width * getColumnWidth(), dataField
                        .getHeight()));
                ((JComboBox<?>) dataField).setRenderer(referenceData.getRenderer());
                ((JComboBox<?>) dataField).setSelectedItem(value);
                pnlEdit.add(lblLabel, newline);
                break;
            case MULTI_SELECT_BOX:
                List desValues = value != null ? new ArrayList((Collection<?>) value) : Collections.EMPTY_LIST;
                List scrValues = new ArrayList<>(ListUtils.removeAll(referenceData.getValues(), desValues));
                dataField = new MultiSelectionBox<>(scrValues, desValues, referenceData.getRenderer());
                pnlEdit.add(lblLabel, newline + "top");
                break;
            case DATE:
                Date date = (Date) value;
                dataField = new JXDatePicker();
                if (date != null) {
                    ((JXDatePicker) dataField).setDate(date);
                }
                pnlEdit.add(lblLabel, newline);
                break;
            case RADIO_BUTTON_GROUP:
                dataField = new RadioButtonsGroup<>(referenceData.getValue2LabelMap(), value);
                pnlEdit.add(lblLabel, newline);
                break;
            case IMAGE:
                byte[] bytes = (byte[]) value;
                dataField = new ImageChooser(bytes);
                pnlEdit.add(lblLabel, newline + "top");
                break;
            case FILE_CHOOSER:
                String filePath = (String) value;
                dataField = new FileChooser(filePath);
                pnlEdit.add(lblLabel, newline + "top");
                break;
            case CHECKBOX:
                Boolean isSelected = BooleanUtils.isTrue((Boolean) value);
                dataField = new JCheckBox("", isSelected);
                pnlEdit.add(lblLabel, newline);
                break;
            case ENTITY_CHOOSER:
                dataField = new EntityChooser<>(referenceData.getValues(), value);
                pnlEdit.add(lblLabel, newline + "top");
                break;
            case SALE_TARGET:
                Calendar now = Calendar.getInstance();
                Integer curyear = now.get(Calendar.YEAR);
                List<SaleTargetModel> saleTargetList = value != null ? new ArrayList<SaleTargetModel>((Set) value)
                        : Collections.EMPTY_LIST;
                if (saleTargetList.size() == 0) {
                    saleTargetList = referenceData.getValues();
                }
                // add sale target of current year
                boolean hasCurYear = false;
                for (SaleTargetModel saleTargetModel : saleTargetList) {
                    if (saleTargetModel.getYear().equals(curyear)) {
                        hasCurYear = true;
                        break;
                    }
                }
                if (hasCurYear == false) {
                    saleTargetList.addAll(referenceData.getValues());
                }
                dataField = new SaleTargetComp(curyear, saleTargetList);
                pnlEdit.add(lblLabel, newline + "top");
                break;
            default:
                throw new RuntimeException("FieldType does not supported!");
            }
            pnlEdit.add(dataField);
            JLabel errorIcon = new JLabel(ImageUtils.getImageIcon(ImageConstants.ERROR_ICON));
            pnlEdit.add(errorIcon);
            errorIcon.setVisible(false);
            name2AttributeComponent.put(attribute.getName(), new AttributeComponent(lblLabel, dataField, errorIcon));
        }
        return pnlEdit;
    }

    private JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        toolbar.setFloatable(false);
        btnSave = new JButton(ImageUtils.getImageIcon(ImageConstants.SAVE_ICON));
        btnSave.setToolTipText(ControlConfigUtils.getString("default.button.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doSave();
            }
        });

        // btnSaveClose = new JButton("Lưu và đóng");
        // btnSaveClose.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // if (doSave()) {
        // doClose();
        // }
        // }
        // });

        btnSaveNew = new JButton(ImageUtils.getImageIcon(ImageConstants.SAVE_NEW_ICON));
        btnSaveNew.setToolTipText(ControlConfigUtils.getString("edit.button.saveNew"));
        btnSaveNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doSave()) {
                    doNew();
                }
            }
        });

        btnNew = new JButton(ImageUtils.getImageIcon(ImageConstants.NEW_ICON));
        btnNew.setToolTipText(ControlConfigUtils.getString("edit.button.new"));
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCloseOrNewWithDirtyCheck(true);
            }
        });

        btnExit = new JButton(ImageUtils.getImageIcon(ImageConstants.EXIT_ICON));
        btnExit.setToolTipText(ControlConfigUtils.getString("edit.button.exit"));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doCloseOrNewWithDirtyCheck(false);
            }
        });
        
        JButton btnFullScreen = new JButton(ImageUtils.getImageIcon(ImageConstants.FULLSCREEN_ICON));
        btnExit.setToolTipText(ControlConfigUtils.getString("edit.button.fullscreen"));
        btnFullScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = (Window) SwingUtilities.getRoot(AbstractSingleEditView.this);
                window.setSize(WindowUtilities.getFullScreenSize());
                WindowUtilities.centerOnScreen(window);
            }
        });

        toolbar.add(btnNew);
        toolbar.add(btnSave);
        toolbar.add(btnSaveNew);
        // toolbar.add(btnSaveClose);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(btnFullScreen);
        toolbar.add(btnExit);
        return toolbar;
    }

    /**
     * <b>This function is same as JTextArea.getColumnWidth()</b> TODO move to the util.
     * 
     * @return the column width >= 1
     */
    protected int getColumnWidth() {
        FontMetrics metrics = getFontMetrics(getFont());
        return metrics.charWidth('m');
    }

    protected void btnSaveActionPerformed(ActionEvent evt) {
        doSave();
    }

    private boolean doSave() {
        Set<ConstraintViolation<T>> validateResult = bindAndValidate(entity);
        if (CollectionUtils.isEmpty(validateResult) && StringUtils.isBlank(notifyPanel.getMessage())) {
            try {
                boolean isNew = (entity.getId() == null);
                saveOrUpdate(entity);
                if (listView != null) {
                    listView.notifyFromDetailView(entity, isNew);
                }

                // Clear all the error on the screen
                for (AttributeComponent at : name2AttributeComponent.values()) {
                    at.getLabel().setForeground(Color.BLACK);
                    at.getErrorIcon().setVisible(false);
                }

                // Show information save success.
                notifyPanel.setNotifyKind(NotifyKind.INFORMATION);
                notifyPanel.setMessage(ControlConfigUtils.getString("edit.message.saveSuccess"));
                notifyPanel.setVisible(true);
                return true;
            } catch (Exception e) {
                notifyPanel.setNotifyKind(NotifyKind.ERROR);
                notifyPanel.setMessage(e.getCause().toString());
                notifyPanel.setVisible(true);
                return false;
            }
        } else {
            // Before show errors, clear all the errors existing on the screen.
            for (AttributeComponent at : name2AttributeComponent.values()) {
                at.getLabel().setForeground(Color.BLACK);
                at.getErrorIcon().setVisible(false);
            }

            notifyPanel.setNotifyKind(NotifyKind.ERROR);
            // Show the errors
            if (StringUtils.isBlank(notifyPanel.getMessage())) {
                notifyPanel.setMessage(ControlConfigUtils.getString("message.error"));
            }
            notifyPanel.setVisible(true);
            for (ConstraintViolation<T> violation : validateResult) {
                AttributeComponent attributeComponent = name2AttributeComponent.get(violation.getPropertyPath()
                        .toString());
                JLabel label = attributeComponent.getLabel();
                label.setForeground(Color.RED);
                JLabel errorIcon = attributeComponent.getErrorIcon();
                errorIcon.setToolTipText(violation.getMessage());
                errorIcon.setVisible(true);
                logger.error(violation.getMessage());
            }
            return false;
        }
    }

    protected void saveOrUpdate(T entity) {
        getDaoHelper().getDao(getEntityClass()).saveOrUpdate(entity);
    }

    protected Set<ConstraintViolation<T>> bindAndValidate(T entity) {
        for (DetailAttribute attribute : detailDataModel.getDetailAttributes()) {
            JComponent component = name2AttributeComponent.get(attribute.getName()).getComponent();
            switch (attribute.getType()) {
            case TEXTBOX:
                JFormattedTextField txtField = (JFormattedTextField) component;
                beanWrapper.setPropertyValue(attribute.getName(), txtField.getValue());
                break;
            case TEXTAREA:
                JTextArea rtxtField = (JTextArea) ((JScrollPane) component).getViewport().getView();
                beanWrapper.setPropertyValue(attribute.getName(), StringUtils.trimToNull(rtxtField.getText()));
                break;
            case PASSWORD:
                JPasswordField pwdField = (JPasswordField) component;
                beanWrapper.setPropertyValue(attribute.getName(), pwdField.getText());
                break;
            case CHECKBOX:
                JCheckBox chkField = (JCheckBox) component;
                beanWrapper.setPropertyValue(attribute.getName(), chkField.isSelected());
                break;
            case DATE:
                JXDatePicker dateField = (JXDatePicker) component;
                beanWrapper.setPropertyValue(attribute.getName(), dateField.getDate());
                break;
            case DROPDOWN:
                JComboBox<?> comboBox = (JComboBox<?>) component;
                beanWrapper.setPropertyValue(attribute.getName(), comboBox.getSelectedItem());
                break;
            case MULTI_SELECT_BOX:
                MultiSelectionBox<?> multiBox = (MultiSelectionBox<?>) component;
                // List<?> unselected = multiBox.getSourceValues();
                List<?> selected = multiBox.getDestinationValues();
                beanWrapper.setPropertyValue(attribute.getName(), selected);
                // List<>

                // TODO: set selected value into entity
                // for each item in listData of entity, remove, then set
                // selected into entity. (prevent
                // hibernate mapping issue).

                break;
            case RADIO_BUTTON_GROUP:
                RadioButtonsGroup<?> radioBtnGroupField = (RadioButtonsGroup<?>) component;
                beanWrapper.setPropertyValue(attribute.getName(), radioBtnGroupField.getSelectedValue());
                break;
            case IMAGE:
                ImageChooser imageField = (ImageChooser) component;
                beanWrapper.setPropertyValue(attribute.getName(), imageField.getImageData());
                break;
            case FILE_CHOOSER:
                FileChooser fileField = (FileChooser) component;
                beanWrapper.setPropertyValue(attribute.getName(), fileField.getFilePath());
                break;
            case ENTITY_CHOOSER:
                EntityChooser<?> entityField = (EntityChooser<?>) component;
                beanWrapper.setPropertyValue(attribute.getName(), entityField.getSelectedEntity());
                break;
            case SALE_TARGET:
                SaleTargetComp<?> saleTargetField = (SaleTargetComp<?>) component;
                beanWrapper.setPropertyValue(attribute.getName(), new HashSet<>(saleTargetField.getSaleTargetList()));
                break;
            default:
                throw new RuntimeException("Do not support FieldTypeEnum " + attribute.getType());
            }
        }

        validateMethods(entity);

        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();

        Validator validator = factory.getValidator();
        return validator.validate(entity);
    }

    /**
     * Validate the methods of entity which are marked {@link com.s3s.ssm.view.annotations.Validation}.
     * 
     * @param entity
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void validateMethods(T entity) {
        try {
            // Clear all existing message before validate.
            notifyPanel.clearMessage();
            for (Method m : entity.getClass().getMethods()) {
                for (Annotation a : m.getAnnotations()) {
                    if (ClassUtils.isAssignable(a.annotationType(), com.s3s.ssm.view.annotations.Validation.class)) {
                        boolean isSuccess;
                        isSuccess = (boolean) m.invoke(entity);
                        if (!isSuccess) {
                            notifyPanel.addMessage(ControlConfigUtils.getString("error."
                                    + getEntityClass().getSimpleName() + "." + m.getName()));
                        }
                    }
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("Error when validateMethods");
        }
    }

    /**
     * Check dirty before close or create new entity.
     * 
     * @param isNew
     *            <code>true</code> if create new entity, <code>false</code> close the current entity.
     */
    protected void doCloseOrNewWithDirtyCheck(boolean isNew) {
        if (isDirty()) {
            int option = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this),
                    ControlConfigUtils.getString("edit.warning.cancel.message"),
                    ControlConfigUtils.getString("edit.warning.cancel.title"), JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE, null);
            if (option == JOptionPane.YES_OPTION) {
                doSave();
                doCloseOrNew(isNew);
            } else if (option == JOptionPane.NO_OPTION) {
                doCloseOrNew(isNew);
            }
        } else {
            doCloseOrNew(isNew);
        }
    }

    private void doCloseOrNew(boolean isNew) {
        if (isNew) {
            doNew();
        } else {
            doClose();
        }
    }

    private void doClose() {
        SwingUtilities.getRoot(this).setVisible(false);
    }

    private void doNew() {
        SwingUtilities.getRoot(this).setVisible(false);
        listView.performAddAction();
    }

    protected boolean isDirty() {
        for (DetailAttribute attribute : detailDataModel.getDetailAttributes()) {
            JComponent component = name2AttributeComponent.get(attribute.getName()).getComponent();
            Object oldValue = beanWrapper.getPropertyValue(attribute.getName());
            Object newValue = null;
            switch (attribute.getType()) {
            case TEXTBOX:
                JFormattedTextField txtField = (JFormattedTextField) component;
                newValue = txtField.getValue();
                break;
            case TEXTAREA:
                JTextArea rtxtField = (JTextArea) ((JScrollPane) component).getViewport().getView();
                newValue = StringUtils.trimToNull(rtxtField.getText());
                break;
            case PASSWORD:
                JPasswordField pwdField = (JPasswordField) component;
                newValue = pwdField.getText();
                break;
            case CHECKBOX:
                JCheckBox chkField = (JCheckBox) component;
                newValue = chkField.isSelected();
                break;
            case DATE:
                JXDatePicker dateField = (JXDatePicker) component;
                newValue = dateField.getDate();
                break;
            case DROPDOWN:
                JComboBox<?> comboBox = (JComboBox<?>) component;
                newValue = comboBox.getSelectedItem();
                break;
            case MULTI_SELECT_BOX:
                MultiSelectionBox<?> multiBox = (MultiSelectionBox<?>) component;
                List<?> selected = multiBox.getDestinationValues();

                // TODO Phuc: test this case
                newValue = selected;
                break;
            case RADIO_BUTTON_GROUP:
                RadioButtonsGroup<?> radioBtnGroupField = (RadioButtonsGroup<?>) component;
                newValue = radioBtnGroupField.getSelectedValue();
                break;
            case IMAGE:
                ImageChooser imageField = (ImageChooser) component;
                newValue = imageField.getImageData();
                break;
            case FILE_CHOOSER:
                FileChooser fileField = (FileChooser) component;
                // TODO Hoang: do this component check dirty?
                newValue = fileField.getFilePath();
                break;
            case ENTITY_CHOOSER:
                EntityChooser<?> entityField = (EntityChooser<?>) component;
                newValue = entityField.getSelectedEntity();
                break;
            case SALE_TARGET:
                SaleTargetComp<?> saleTargetField = (SaleTargetComp<?>) component;
                // TODO Hoang: check dirty for this.
                newValue = new HashSet<>(saleTargetField.getSaleTargetList());
                break;
            default:
                throw new RuntimeException("Do not support FieldTypeEnum " + attribute.getType());
            }
            if (!ObjectUtils.equals(oldValue, newValue)) {
                return true;
            }
        }
        return false;
    }
}
