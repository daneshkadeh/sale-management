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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
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

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.model.ReferenceDataModel.ReferenceData;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.EntityChooser;
import com.s3s.ssm.view.component.FileChooser;
import com.s3s.ssm.view.component.ImageChooser;
import com.s3s.ssm.view.component.MultiSelectionBox;
import com.s3s.ssm.view.component.RadioButtonsGroup;
import com.s3s.ssm.view.component.SaleTargetComp;
import com.s3s.ssm.view.component.SaleTargetModel;

/**
 * @author Pham Cong Bang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */

public abstract class AbstractDetailView<T extends AbstractBaseIdObject> extends AbstractView {
    private static final long serialVersionUID = 1L;

    private final Log logger = LogFactory.getLog(AbstractDetailView.class);

    // TODO HPP consider to remove this reference. We should fire event to
    // listView instead of keep a reference.
    protected AbstractListView<T> listView;

    protected DetailDataModel detailDataModel = new DetailDataModel();
    protected Map<DetailAttribute, JComponent> mapFields = new HashMap<>();

    private JButton btnSave;
    private JButton btnCancel;
    protected T entity;
    protected BeanWrapper beanWrapper;

    private final ReferenceDataModel refDataModel = new ReferenceDataModel();

    private final static int DEFAULT_TEXTFIELD_COLUMN = 20;
    private final static int DEFAULT_RICH_TEXT_ROWS = 4;

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
    public AbstractDetailView(T entity) {
        this.entity = entity;
        loadForEdit(entity);
        contructView(entity);
    }

    private void contructView(T entity) {
        beanWrapper = new BeanWrapperImpl(entity);
        initialPresentationView(detailDataModel, entity);
        setReferenceDataModel(refDataModel, entity);
        initComponents();
    }

    public void setListView(AbstractListView<T> view) {
        this.listView = view;
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void initComponents() {
        // Layout the screen
        setLayout(new MigLayout("wrap, fill"));

        JToolBar toolbar = createToolBar();
        add(toolbar, "top");
        // if(detailDataModel.getTabList().isEmpty())

        JPanel pnlEdit = new JPanel(new MigLayout("wrap 4, debug", "[][grow, fill]20[][grow, fill]"));
        for (int i = 0; i < detailDataModel.getDetailAttributes().size(); i++) {
            DetailAttribute attribute = detailDataModel.getDetailAttributes().get(i);
            String label = ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + attribute.getName());
            String wrap = attribute.isWrap() ? "wrap" : "";
            if (attribute.isMandatory()) {
                label += " (*)";
            }
            JLabel lblLabel = new JLabel(label);
            JComponent dataField;
            Object value = beanWrapper.getPropertyValue(attribute.getName());
            ReferenceData referenceData = refDataModel.getRefDataListMap().get(attribute.getReferenceDataId());
            switch (attribute.getType()) {
            case TEXTBOX:
                // Class<?> propertyReturnType = getPropertyReturnType(entity, attribute);
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
                ((JFormattedTextField) dataField).setColumns(DEFAULT_TEXTFIELD_COLUMN);
                // ((JFormattedTextField) dataField).setInputVerifier(new NotEmptyValidator(SwingUtilities
                // .getWindowAncestor(this), dataField, "The field must be not empty."));

                dataField.setEnabled(attribute.isEnable());
                ((JFormattedTextField) dataField).setValue(value);
                pnlEdit.add(lblLabel);
                break;
            case TEXTAREA:
                dataField = new JTextArea(DEFAULT_RICH_TEXT_ROWS, DEFAULT_TEXTFIELD_COLUMN);
                ((JTextArea) dataField).setLineWrap(true);
                ((JTextArea) dataField).setWrapStyleWord(true);
                ((JTextArea) dataField).setEditable(true);
                String txtValue = value != null ? StringUtils.trimToEmpty(String.valueOf(value)) : "";
                ((JTextArea) dataField).setText(txtValue);
                pnlEdit.add(lblLabel, "top");
                break;
            case PASSWORD:
                dataField = new JPasswordField(DEFAULT_TEXTFIELD_COLUMN);
                ((JPasswordField) dataField).setEditable(attribute.isEditable());
                dataField.setEnabled(attribute.isEnable());
                ((JTextField) dataField).setText(ObjectUtils.toString(value));
                pnlEdit.add(lblLabel);
                break;
            case DROPDOWN:
                // get the referenceDataList from ReferenceDataModel using
                // referenceDataId of column.
                dataField = new JComboBox<>(referenceData.getValues().toArray());
                ((JComboBox<?>) dataField).setRenderer(referenceData.getRenderer());
                ((JComboBox<?>) dataField).setSelectedItem(value);
                pnlEdit.add(lblLabel);
                break;
            case MULTI_SELECT_BOX:
                List desValues = value != null ? new ArrayList((Set) value) : Collections.EMPTY_LIST;
                List scrValues = new ArrayList<>(ListUtils.removeAll(referenceData.getValues(), desValues));
                dataField = new MultiSelectionBox<>(scrValues, desValues, referenceData.getRenderer());
                pnlEdit.add(lblLabel, "top");
                break;
            case DATE:
                Date date = (Date) value;
                dataField = new JXDatePicker();
                if (date != null) {
                    ((JXDatePicker) dataField).setDate(date);
                }
                pnlEdit.add(lblLabel, "top");
                break;
            case RADIO_BUTTON_GROUP:
                dataField = new RadioButtonsGroup<>(referenceData.getValue2LabelMap(), value);
                pnlEdit.add(lblLabel, "top");
                break;
            case IMAGE:
                byte[] bytes = (byte[]) value;
                dataField = new ImageChooser(bytes);
                pnlEdit.add(lblLabel, "top");
                break;
            case FILE_CHOOSER:
                String filePath = (String) value;
                dataField = new FileChooser(filePath);
                pnlEdit.add(lblLabel, "top");
                break;
            case CHECKBOX:
                Boolean isSelected = BooleanUtils.isTrue((Boolean) value);
                dataField = new JCheckBox("", isSelected);
                pnlEdit.add(lblLabel);
                break;
            case ENTITY_CHOOSER:
                dataField = new EntityChooser<>(referenceData.getValues(), value);
                pnlEdit.add(lblLabel, "top");
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
                pnlEdit.add(lblLabel, "top");
                break;
            default:
                throw new RuntimeException("FieldType does not supported!");
            }
            pnlEdit.add(dataField, wrap);
            mapFields.put(attribute, dataField);
        }

        add(pnlEdit, "grow");
    }

    private JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        toolbar.setFloatable(false);
        btnSave = new JButton(ControlConfigUtils.getString("default.button.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel = new JButton(ControlConfigUtils.getString("default.button.cancel"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        toolbar.add(btnSave);
        toolbar.add(btnCancel);
        return toolbar;
    }

    protected void btnOKActionPerformed(ActionEvent evt) {
        Set<ConstraintViolation<T>> validateResult = bindAndValidate(entity);
        if (CollectionUtils.isEmpty(validateResult)) {
            boolean isNew = (entity.getId() == null);
            saveOrUpdate(entity);
            listView.notifyFromDetailView(entity, isNew);
            // TODO HPP find another way to close dialog.
            SwingUtilities.getRoot(this).setVisible(false);
        } else {
            for (ConstraintViolation<T> violation : validateResult) {
                logger.error(violation.getMessage());
            }
            JOptionPane.showMessageDialog(this, "Some fields are invalid!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    protected void saveOrUpdate(T entity) {
        getDaoHelper().getDao(getEntityClass()).saveOrUpdate(entity);
    }

    protected Set<ConstraintViolation<T>> bindAndValidate(T entity) {
        try {
            for (DetailAttribute attribute : detailDataModel.getDetailAttributes()) {
                JComponent component = mapFields.get(attribute);
                Class<?> paramClass = beanWrapper.getPropertyType(attribute.getName());
                switch (attribute.getType()) {
                case TEXTBOX:
                    JFormattedTextField txtField = (JFormattedTextField) component;
                    beanWrapper.setPropertyValue(attribute.getName(), paramClass.cast(txtField.getValue()));
                    break;
                case TEXTAREA:
                    JTextArea rtxtField = (JTextArea) component;
                    beanWrapper.setPropertyValue(attribute.getName(),
                            paramClass.cast(StringUtils.trimToEmpty(rtxtField.getText())));
                    break;
                case PASSWORD:
                    JPasswordField pwdField = (JPasswordField) component;
                    beanWrapper.setPropertyValue(attribute.getName(), paramClass.cast(pwdField.getText()));
                    break;
                case CHECKBOX:
                    JCheckBox chkField = (JCheckBox) component;
                    beanWrapper.setPropertyValue(attribute.getName(), paramClass.cast(chkField.isSelected()));
                    break;
                case DATE:
                    JXDatePicker dateField = (JXDatePicker) component;
                    beanWrapper.setPropertyValue(attribute.getName(), dateField.getDate());
                    break;
                case DROPDOWN:
                    JComboBox<?> comboBox = (JComboBox<?>) component;
                    beanWrapper.setPropertyValue(attribute.getName(), paramClass.cast(comboBox.getSelectedItem()));
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
                    beanWrapper.setPropertyValue(attribute.getName(),
                            paramClass.cast(radioBtnGroupField.getSelectedValue()));
                    break;
                case IMAGE:
                    ImageChooser imageField = (ImageChooser) component;
                    beanWrapper.setPropertyValue(attribute.getName(), paramClass.cast(imageField.getImageData()));
                    break;
                case FILE_CHOOSER:
                    FileChooser fileField = (FileChooser) component;
                    beanWrapper.setPropertyValue(attribute.getName(), paramClass.cast(fileField.getFilePath()));
                    break;
                case ENTITY_CHOOSER:
                    EntityChooser entityField = (EntityChooser) component;
                    beanWrapper.setPropertyValue(attribute.getName(), paramClass.cast(entityField.getSelectedEntity()));
                    break;
                case SALE_TARGET:
                    SaleTargetComp saleTargetField = (SaleTargetComp) component;
                    beanWrapper.setPropertyValue(attribute.getName(),
                            paramClass.cast(new HashSet<>(saleTargetField.getSaleTargetList())));
                    break;
                default:
                    throw new RuntimeException("Do not support FieldTypeEnum " + attribute.getType());
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getCause());
            throw new RuntimeException("Problem happens when bindAndValidate detailView:" + e.getMessage());
        }

        // TODO: must validate object before saving
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();

        Validator validator = factory.getValidator();
        return validator.validate(entity);
    }

    protected void btnCancelActionPerformed(ActionEvent evt) {
        SwingUtilities.getRoot(this).setVisible(false);
    }
}
