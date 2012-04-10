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
package com.s3s.ssm.view.edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatter;
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
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.model.ReferenceDataModel.ReferenceData;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.ISavedListener;
import com.s3s.ssm.view.SavedEvent;
import com.s3s.ssm.view.component.EntityChooser;
import com.s3s.ssm.view.component.FileChooser;
import com.s3s.ssm.view.component.IPageChangeListener;
import com.s3s.ssm.view.component.ImageChooser;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.component.MultiSelectableTreeNode;
import com.s3s.ssm.view.component.MultiSelectionListBox;
import com.s3s.ssm.view.component.MultiSelectionTreeBox;
import com.s3s.ssm.view.component.RadioButtonsGroup;
import com.s3s.ssm.view.component.SaleTargetComp;
import com.s3s.ssm.view.component.SaleTargetModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.DetailDataModel.GroupInfoData;
import com.s3s.ssm.view.edit.DetailDataModel.TabInfoData;
import com.s3s.ssm.view.edit.NotifyPanel.NotifyKind;

/**
 * The edit view with single view.
 * 
 * @author Pham Cong Bang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */

public abstract class AbstractSingleEditView<T extends AbstractBaseIdObject> extends AbstractEditView<T> {
    /**
     * 
     */
    private static final int MAX_WIDTH_LABEL = 150;

    private static final long serialVersionUID = 1L;

    private final Log logger = LogFactory.getLog(AbstractSingleEditView.class);

    private DetailDataModel detailDataModel = new DetailDataModel();
    private Map<String, AttributeComponent> name2AttributeComponent = new HashMap<>();

    private BeanWrapper beanWrapper;

    protected final ReferenceDataModel refDataModel = new ReferenceDataModel();

    private NotifyPanel notifyPanel;

    private List<ISavedListener<T>> savedListeners = new ArrayList<>();

    protected JToolBar toolbar;
    private JButton btnSave;
    // private JButton btnSaveClose;
    private JButton btnSaveNew;
    private JButton btnNew;
    private JButton btnExit;

    public AbstractSingleEditView() {
        this(null);
    }

    public AbstractSingleEditView(Map<String, Object> request) {
        super(request);
        beanWrapper = new BeanWrapperImpl(this.entity);
        contructView(this.entity);
    }

    private void contructView(T entity) {
        initialPresentationView(detailDataModel, entity);
        setReferenceDataModel(refDataModel, entity);
        initComponents();
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
        setLayout(new MigLayout("hidemode 2, wrap, fillx, ins 0 10 0 10", "grow"));

        toolbar = createToolBar();
        toolbar.setVisible(!isReadOnly());
        // Toolbar
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

    protected JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        toolbar.setFloatable(false);
        btnSave = new JButton(ImageUtils.getIcon(ImageConstants.SAVE_ICON));
        btnSave.setToolTipText(ControlConfigUtils.getString("default.button.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doSave();
            }
        });

        // btnSaveClose = new JButton("Luu va dong");
        // btnSaveClose.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // if (doSave()) {
        // doClose();
        // }
        // }
        // });

        btnSaveNew = new JButton(ImageUtils.getIcon(ImageConstants.SAVE_NEW_ICON));
        btnSaveNew.setToolTipText(ControlConfigUtils.getString("edit.button.saveNew"));
        btnSaveNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doSave()) {
                    doNew();
                }
            }
        });

        btnNew = new JButton(ImageUtils.getIcon(ImageConstants.NEW_ICON));
        btnNew.setEnabled(entity.isPersisted());
        btnNew.setToolTipText(ControlConfigUtils.getString("edit.button.new"));
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doNew();
            }
        });

        btnExit = new JButton(ImageUtils.getIcon(ImageConstants.EXIT_ICON));
        btnExit.setToolTipText(ControlConfigUtils.getString("edit.button.exit"));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doClose();
            }
        });

        // JButton btnFullScreen = new JButton(ImageUtils.getIcon(ImageConstants.FULLSCREEN_ICON));
        // btnFullScreen.setToolTipText(ControlConfigUtils.getString("edit.button.fullscreen"));
        // btnFullScreen.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // setSizeParentWindoẉ̣(WindowUtilities.getFullScreenSize());
        // }
        // });
        //
        // JButton btnMinimize = new JButton(ImageUtils.getIcon(ImageConstants.MINIMIZE_ICON));
        // btnMinimize.setToolTipText(ControlConfigUtils.getString("edit.button.minimize"));
        // btnMinimize.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // setSizeParentWindoẉ̣(getFitSize());
        // }
        // });

        toolbar.add(btnNew);
        toolbar.add(btnSave);
        toolbar.add(btnSaveNew);
        // toolbar.add(btnSaveClose);
        toolbar.add(Box.createHorizontalGlue());
        // toolbar.add(btnMinimize);
        // toolbar.add(btnFullScreen);
        toolbar.add(btnExit);
        return toolbar;
    }

    /**
     * Customize the components.
     * 
     * @param name2AttributeComponent
     */
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, T entity) {
        // Template method
    };

    public boolean focus() {
        String firstAttributeName = detailDataModel.getDetailAttributes().get(0).getName();
        return name2AttributeComponent.get(firstAttributeName).getComponent().requestFocusInWindow();
    }

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
                addFields(fieldsPanel, g.getStartGroupIndex(), g.getEndGroupIndex(), g);
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
     * @param g
     *            the group info data of panel. If not null -> The panel render on the group panel.
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private JPanel addFields(JPanel fieldsPanel, int startIndex, int endIndex, GroupInfoData g) {
        JPanel pnlEdit = null;
        if (g != null) { // in case of group of fields.
            pnlEdit = new JXTaskPane();
            ((JXTaskPane) pnlEdit).setTitle(g.getName());
            ((JXTaskPane) pnlEdit).setIcon(g.getIcon());
            pnlEdit.setLayout(new MigLayout("ins 0"));
            fieldsPanel.add(pnlEdit, "newline, spanx");
        } else {
            pnlEdit = fieldsPanel;
        }
        for (int i = startIndex; i < endIndex; i++) {
            final DetailAttribute attribute = detailDataModel.getDetailAttributes().get(i);
            String label = null;
            if (attribute.getLabel() != null) {
                label = attribute.getLabel();
            } else {
                label = ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                        + attribute.getName());
            }

            String newline = attribute.isNewColumn() ? "right, gapleft 10, " : "right, newline, ";
            int width = attribute.getWidth() == 0 ? UIConstants.DEFAULT_WIDTH : attribute.getWidth();

            if (attribute.isMandatory()) {
                label += " (*)";
            }
            JLabel lblLabel = createLabel(label);
            JComponent dataField = null;
            boolean isRaw = attribute.isRaw();
            boolean editable = attribute.isEditable();
            Object value = isRaw ? attribute.getValue() : beanWrapper.getPropertyValue(attribute.getName());

            ReferenceData referenceData = null;
            // cacheDataId is priority than referenceDataId
            if (attribute.getCacheDataId() != null) {
                referenceData = refDataModel.new ReferenceData(cacheDataService.getReferenceDataList(attribute
                        .getCacheDataId()), null);
                refDataModel.putRefDataList("CACHE_ID_" + attribute.getCacheDataId(), referenceData);
            } else {
                referenceData = refDataModel.getRefDataListMap().get(attribute.getReferenceDataId());
            }

            switch (attribute.getType()) {
            case LABEL:
                dataField = new JLabel((String) value);
                dataField.setPreferredSize(new Dimension(width, dataField.getPreferredSize().height));
                pnlEdit.add(lblLabel, newline);
                break;
            case TEXTBOX:
                Class<?> propertyReturnType = null;
                if (isRaw) {
                    Assert.isTrue(value != null, "The value for the raw attribute must be set for TEXTBOX type");
                    propertyReturnType = value.getClass();
                } else {
                    propertyReturnType = beanWrapper.getPropertyType(attribute.getName());
                }
                if (ClassUtils.isAssignable(propertyReturnType, Number.class)) {
                    NumberFormatter numFormatter = new NumberFormatter();
                    numFormatter.setValueClass(propertyReturnType);
                    dataField = new JFormattedTextField(numFormatter);
                    ((JFormattedTextField) dataField).setHorizontalAlignment(JFormattedTextField.RIGHT);
                } else {
                    // The format type is String
                    dataField = new JFormattedTextField("");
                    ((DefaultFormatter) ((JFormattedTextField) dataField).getFormatter()).setOverwriteMode(false);

                }
                ((JFormattedTextField) dataField).setEditable(editable);
                dataField.setPreferredSize(new Dimension(width, dataField.getHeight()));

                dataField.setEnabled(attribute.isEnable());
                ((JFormattedTextField) dataField).setValue(value);
                pnlEdit.add(lblLabel, newline);
                break;
            case TEXTAREA:
                JTextArea ta = new JTextArea();
                ta.setRows(UIConstants.DEFAUL_TEXTAREA_ROWS);
                ta.setLineWrap(true);
                ta.setEditable(true);
                String txtValue = value != null ? StringUtils.trimToEmpty(String.valueOf(value)) : "";
                ta.setText(txtValue);
                ta.setEnabled(!isReadOnly());
                dataField = new JScrollPane(ta);
                dataField.setPreferredSize(new Dimension(width, dataField.getPreferredSize().height));
                pnlEdit.add(lblLabel, newline + "top ");
                break;
            case PASSWORD:
                dataField = new JPasswordField();
                dataField.setPreferredSize(new Dimension(width, dataField.getPreferredSize().height));
                ((JPasswordField) dataField).setEditable(editable);
                dataField.setEnabled(attribute.isEnable());
                ((JTextField) dataField).setText(ObjectUtils.toString(value));
                pnlEdit.add(lblLabel);
                break;
            case DROPDOWN:
                dataField = createDropdownComponent(attribute, width, value, referenceData);
                pnlEdit.add(lblLabel, newline);
                break;
            case DROPDOWN_AUTOCOMPLETE:
                dataField = createDropdownComponent(attribute, width, value, referenceData);
                AutoCompleteDecorator.decorate((JComboBox) dataField);
                pnlEdit.add(lblLabel, newline);
                break;
            case MULTI_SELECT_LIST_BOX:
                List desValues = value != null ? new ArrayList((Collection<?>) value) : Collections.EMPTY_LIST;
                List scrValues = new ArrayList<>(ListUtils.removeAll(referenceData.getValues(), desValues));
                dataField = new MultiSelectionListBox<>(scrValues, desValues, referenceData.getRenderer());
                dataField.setPreferredSize(new Dimension(width, dataField.getPreferredSize().height));
                pnlEdit.add(lblLabel, newline + "top");
                break;
            case MULTI_SELECT_TREE_BOX:
                MultiSelectableTreeNode root = new MultiSelectableTreeNode("Root", null, false);
                MultiSelectableTreeNode a1 = new MultiSelectableTreeNode("a1", null, false);
                MultiSelectableTreeNode b1 = new MultiSelectableTreeNode("b1", null, false);
                MultiSelectableTreeNode a2 = new MultiSelectableTreeNode("a2", null, false);
                MultiSelectableTreeNode a3 = new MultiSelectableTreeNode("a3", null, false);
                MultiSelectableTreeNode b2 = new MultiSelectableTreeNode("b2", null, false);
                root.add(a1);
                root.add(b1);
                a1.add(a2);
                a1.add(a3);
                b1.add(b2);
                b2.setState(true);
                dataField = new MultiSelectionTreeBox(root);
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
            case MONEY:
                Money money = null;
                if (value == null) {
                    money = Money.zero((String) referenceData.getValues().get(0)); // TODO: get default currency from
                                                                                   // context provider
                } else {
                    money = (Money) value;
                }
                dataField = new MoneyComponent(money, referenceData.getValues());
                dataField.setPreferredSize(new Dimension(width, dataField.getPreferredSize().height));
                pnlEdit.add(lblLabel, newline);
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
                pnlEdit.add(lblLabel, newline);
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

            // Validate the field when lost focus.
            dataField.addFocusListener(new ValidationListener(attribute));
            dataField.setEnabled(!isReadOnly());
            pnlEdit.add(dataField, "split 2");
            JLabel errorIcon = new JLabel(ImageUtils.getIcon(ImageConstants.ERROR_ICON));
            pnlEdit.add(errorIcon);
            errorIcon.setVisible(false);
            name2AttributeComponent.put(attribute.getName(), new AttributeComponent(lblLabel, dataField, errorIcon));
        }
        return pnlEdit;
    }

    private JComponent createDropdownComponent(final DetailAttribute attribute, int width, Object value,
            ReferenceData referenceData) {
        JComponent dataField;
        if (!attribute.isMandatory()) {
            referenceData.getValues().add(0, null);
        }
        dataField = new JComboBox<>(referenceData.getValues().toArray());
        dataField.setPreferredSize(new Dimension(width, dataField.getPreferredSize().height));
        ((JComboBox<?>) dataField).setRenderer(referenceData.getRenderer());
        ((JComboBox<?>) dataField).setSelectedItem(value);
        return dataField;
    }

    private boolean isReadOnly() {
        return Boolean.TRUE.equals(request.get(PARAM_READONLY));
    }

    private JLabel createLabel(String label) {
        JLabel lb = new JLabel(label);
        Dimension preferredSize = lb.getPreferredSize();
        // Use JXLabel to wrap the label, if just using the JXLable --> It is not work for Group panel, so I work around
        // by using the JLabel to measure and set the min width for JXTable.
        if (preferredSize.width > MAX_WIDTH_LABEL) {
            JXLabel jxlbl = new JXLabel(label);
            jxlbl.setLineWrap(true);
            jxlbl.setMinimumSize(new Dimension(MAX_WIDTH_LABEL, preferredSize.height));
            jxlbl.setMaxLineSpan(MAX_WIDTH_LABEL);
            return jxlbl;
        } else {
            return lb;
        }
    }

    protected void btnSaveActionPerformed(ActionEvent evt) {
        doSave();
    }

    protected boolean doSave() {
        Set<ConstraintViolation<T>> validateResult = bindAndValidate(entity);
        if (CollectionUtils.isEmpty(validateResult) && StringUtils.isBlank(notifyPanel.getMessage())) {
            try {
                boolean isNew = (entity.getId() == null);
                // entity.setVersion(1L);
                saveOrUpdate(entity);
                fireSavedListener(isNew);
                if (getListView() != null) {
                    getListView().notifyFromDetailView(entity, isNew);
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

                btnNew.setEnabled(true);
                return true;
            } catch (Exception e) {
                for (StackTraceElement st : e.getStackTrace()) {
                    logger.error(st.toString());
                }
                logger.error(e.getMessage());
                notifyPanel.setNotifyKind(NotifyKind.ERROR);
                notifyPanel.setMessage("<html>" + ControlConfigUtils.getString("error.technicalerror") + "</br>"
                        + e.getMessage() + "</html>");
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
            boolean isRaw = attribute.isRaw();
            Object value = getComponentValue(component, attribute.getType());
            bindingValue(entity, attribute.getName(), isRaw, value);
        }

        validateMethods(entity);

        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();

        Validator validator = factory.getValidator();
        return validator.validate(entity);
    }

    /**
     * Binding value to the entity. Default implementation is binding value for main attributes. The child class should
     * override this method to binding for the raw attributes.
     * 
     * @param entity
     *            the entity binded.
     * @param name
     *            the name of attribute
     * @param isRaw
     *            is raw attribute or not
     * @param value
     *            the value of the component.
     */
    protected void bindingValue(T entity, String name, boolean isRaw, Object value) {
        // The child class should override this
        if (!isRaw) {
            beanWrapper.setPropertyValue(name, value);
        }
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
                        boolean isSuccess = (boolean) m.invoke(entity);
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
                if (doSave()) {
                    doCloseOrNew(isNew);
                }
            } else if (option == JOptionPane.NO_OPTION) {
                doCloseOrNew(isNew);
            }
        } else {
            doCloseOrNew(isNew);
        }
    }

    protected void doNew() {
        doCloseOrNewWithDirtyCheck(true);
    }

    protected void doClose() {
        doCloseOrNewWithDirtyCheck(false);
    }

    private void doCloseOrNew(boolean isNew) {
        if (isNew) {
            getListView().performAddAction();
        } else {
            JTabbedPane tabbedPane = getListView().getTabbedPane();
            tabbedPane.remove(tabbedPane.indexOfComponent(this));
        }
    }

    protected boolean isDirty() {
        for (DetailAttribute attribute : detailDataModel.getDetailAttributes()) {
            JComponent component = name2AttributeComponent.get(attribute.getName()).getComponent();
            Object oldValue = attribute.isRaw() ? attribute.getValue() : beanWrapper.getPropertyValue(attribute
                    .getName());
            Object newValue = getComponentValue(component, attribute.getType());

            if (!ObjectUtils.equals(oldValue, newValue)) {
                // TODO not right in all case of field type. Check again.
                return true;
            }
        }
        return false;
    }

    private Object getComponentValue(JComponent component, DetailFieldType type) {
        switch (type) {
        case LABEL:
            JLabel lbl = (JLabel) component;
            return lbl.getText();
        case TEXTBOX:
            JFormattedTextField txtField = (JFormattedTextField) component;
            return txtField.getValue();
        case TEXTAREA:
            JTextArea rtxtField = (JTextArea) ((JScrollPane) component).getViewport().getView();
            return StringUtils.trimToNull(rtxtField.getText());
        case PASSWORD:
            JPasswordField pwdField = (JPasswordField) component;
            return pwdField.getText();
        case CHECKBOX:
            JCheckBox chkField = (JCheckBox) component;
            return chkField.isSelected();
        case DATE:
            JXDatePicker dateField = (JXDatePicker) component;
            return dateField.getDate();
        case DROPDOWN:
            JComboBox<?> comboBox = (JComboBox<?>) component;
            return comboBox.getSelectedItem();
        case MULTI_SELECT_LIST_BOX:
            MultiSelectionListBox<?> multiBox = (MultiSelectionListBox<?>) component;
            // TODO Phuc: test this case
            return multiBox.getDestinationValues();
        case MULTI_SELECT_TREE_BOX:
            MultiSelectionTreeBox multiTreeBox = (MultiSelectionTreeBox) component;
            return multiTreeBox.getSelectedValues();
        case RADIO_BUTTON_GROUP:
            RadioButtonsGroup<?> radioBtnGroupField = (RadioButtonsGroup<?>) component;
            return radioBtnGroupField.getSelectedValue();
        case IMAGE:
            ImageChooser imageField = (ImageChooser) component;
            return imageField.getImageData();
        case MONEY:
            MoneyComponent moneyField = (MoneyComponent) component;
            return moneyField.getMoney();
        case FILE_CHOOSER:
            FileChooser fileField = (FileChooser) component;
            // TODO Hoang: do this component check dirty?
            return fileField.getFilePath();
        case ENTITY_CHOOSER:
            EntityChooser<?> entityField = (EntityChooser<?>) component;
            return entityField.getSelectedEntity();
        case SALE_TARGET:
            SaleTargetComp<?> saleTargetField = (SaleTargetComp<?>) component;
            // TODO Hoang: check dirty for this.
            return new HashSet<>(saleTargetField.getSaleTargetList());
        default:
            throw new RuntimeException("Do not support FieldTypeEnum " + type);
        }
    }

    public boolean requestFocusInWindow() {
        return name2AttributeComponent.get(detailDataModel.getDetailAttributes().get(0).getName()).getComponent()
                .requestFocusInWindow();
    }

    // ////////////////////////////////
    private void fireSavedListener(boolean isNew) {
        SavedEvent<T> e = new SavedEvent<>(this, entity, isNew);
        for (ISavedListener<T> sl : savedListeners) {
            sl.doSaved(e);
        }
    }

    public void addSavedListener(ISavedListener<T> listener) {
        savedListeners.add(listener);
    }

    public void removePageChangeListener(IPageChangeListener listener) {
        savedListeners.remove(listener);
    }

    /**
     * Validation for the field when it lost or gain focus.
     * 
     * @author Phan Hong Phuc
     * @since Apr 5, 2012
     */
    private final class ValidationListener implements FocusListener {
        /**
         * 
         */
        private final DetailAttribute attribute;

        /**
         * @param attribute
         */
        private ValidationListener(DetailAttribute attribute) {
            this.attribute = attribute;
        }

        @Override
        public void focusLost(FocusEvent e) {
            AttributeComponent attributeComponent = name2AttributeComponent.get(attribute.getName());
            JComponent component = attributeComponent.getComponent();
            if (attribute.getType() == DetailFieldType.TEXTBOX) {
                try {
                    ((JFormattedTextField) component).commitEdit();
                } catch (ParseException e1) {
                    UIManager.getLookAndFeel().provideErrorFeedback(component);
                }
            }

            boolean isRaw = attribute.isRaw();
            Object value = getComponentValue(component, attribute.getType());
            bindingValue(entity, attribute.getName(), isRaw, value);
            if (!isRaw) {
                Configuration<?> config = Validation.byDefaultProvider().configure();
                ValidatorFactory factory = config.buildValidatorFactory();

                Validator validator = factory.getValidator();
                Set<ConstraintViolation<T>> validateResult = validator.validateProperty(entity, attribute.getName());
                JLabel label = attributeComponent.getLabel();
                JLabel errorIcon = attributeComponent.getErrorIcon();
                if (CollectionUtils.isNotEmpty(validateResult)) {
                    label.setForeground(Color.RED);
                    errorIcon.setVisible(true);
                    errorIcon.setToolTipText(validateResult.iterator().next().getMessage());
                } else {
                    label.setForeground(Color.BLACK);
                    errorIcon.setVisible(false);
                }
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
        }
    }

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
}
