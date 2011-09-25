package com.hbsoft.ssm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.hbsoft.ssm.entity.AbstractBaseIdObject;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.model.ReferenceDataModel;
import com.hbsoft.ssm.util.i18n.ControlConfigUtils;
import com.hbsoft.ssm.view.component.MultiSelectionBox;

/**
 * @author Pham Cong Bang
 * 
 * @param <T>
 */
public abstract class AbstractDetailView<T extends AbstractBaseIdObject> extends JPanel {
    private static final long serialVersionUID = 1L;

    private Log logger = LogFactory.getLog(AbstractDetailView.class);

    protected AbstractCommonListView listView;

    protected List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
    protected Map<DetailDataModel, JComponent> mapFields = new HashMap<DetailDataModel, JComponent>();

    private JButton btnOK;
    private JButton btnCancel;
    private Class<T> clazz;
    private T entity;

    private ReferenceDataModel refDataModel = new ReferenceDataModel();

    private Integer JTEXTFIELD_SIZE = 20;
    private Integer JPASSWORDFIELD_SIZE = 20;

    public AbstractDetailView() {
        clazz = getEntityClass();
        try {
            entity = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        initialPresentationView(listDataModel, entity);
        setReferenceDataModel(refDataModel, entity);
        try {
            initComponents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setListView(AbstractCommonListView view) {
        this.listView = view;
    }

    protected Class<T> getEntityClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) controllerType;
    }

    public abstract void initialPresentationView(List<DetailDataModel> listDataModel, T entity);

    /**
     * Set data for ComboBox, MultiSelectBox.
     * 
     * @param refDataModel
     * @param entity
     */
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, T entity) {
    }

    protected void initComponents() throws Exception {
        // Layout the screen
        setLayout(new MigLayout("wrap"));

        add(createButtonPanel());

        JPanel pnlEdit = new JPanel(new MigLayout("wrap 2", "[][fill, grow]"));
        for (DetailDataModel dataModel : listDataModel) {
            String label = ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + dataModel.getFieldName());
            JLabel lblLabel = new JLabel(label);
            JComponent dataField;

            Method getMethod = entity.getClass().getMethod("get" + StringUtils.capitalize(dataModel.getFieldName()));
            Object value;
            switch (dataModel.getFieldType()) {
            case TEXT_BOX:
                Class<?> propertyReturnType = getPropertyReturnType(entity, dataModel);
                if (ClassUtils.isAssignable(propertyReturnType, Number.class)) {
                    NumberFormatter numFormatter = new NumberFormatter();
                    numFormatter.setAllowsInvalid(false);
                    if (ClassUtils.isAssignable(propertyReturnType, Integer.class)) {
                        numFormatter.setValueClass(Integer.class);
                    } else {
                        numFormatter.setValueClass(Double.class);
                    }
                    dataField = new JFormattedTextField(numFormatter);
                } else {
                    dataField = new JFormattedTextField();
                }
                ((JFormattedTextField) dataField).setEditable(dataModel.isEditable());
                dataField.setEnabled(dataModel.isEnable());
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);

                value = getMethod.invoke(entity);
                if (value != null) {
                    ((JTextField) dataField).setText(String.valueOf(value));
                }
                break;
            case PASSWORD:
                dataField = new JPasswordField(JPASSWORDFIELD_SIZE);
                ((JPasswordField) dataField).setEditable(dataModel.isEditable());
                dataField.setEnabled(dataModel.isEnable());
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);

                value = getMethod.invoke(entity);
                if (value != null) {
                    ((JTextField) dataField).setText(String.valueOf(value));
                }
                break;
            case COMBO_BOX:
                // get the referenceDataList from ReferenceDataModel using referenceDataId of column.
                // TODO: getRenderer to render data using class of DataList
                dataField = new JComboBox(new DefaultComboBoxModel(refDataModel.getRefDataListMap()
                        .get(dataModel.getReferenceDataId()).toArray()));
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);
                break;
            case MULTI_SELECT_BOX:
                List<Object> refDataList = refDataModel.getRefDataListMap().get(dataModel.getReferenceDataId());
                dataField = new MultiSelectionBox(refDataList, new ArrayList<Object>());
                pnlEdit.add(lblLabel, "top");
                pnlEdit.add(dataField);
                break;
            default:
                throw new RuntimeException("FieldType does not supported!");
            }
            mapFields.put(dataModel, dataField);
        }

        add(pnlEdit, "grow");

        btnOK = new JButton("OK");
        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        JPanel pnlButton = new JPanel();
        pnlButton.add(btnOK);
        pnlButton.add(btnCancel);
        add(pnlButton, "center");
    }

    private JPanel createButtonPanel() {
        // TODO hpp: move btnOK and Cancel to here, add other button.
        return new JPanel();
    }

    protected void btnOKActionPerformed(ActionEvent evt) {
        Set<ConstraintViolation<T>> validateResult = bindAndValidate(entity);
        if (CollectionUtils.isEmpty(validateResult)) {
            saveOrUpdate(entity);
            listView.notifyFromDetailView(entity, true);
        } else {
            for (ConstraintViolation<T> violation : validateResult) {
                logger.error(violation.getMessage());
            }
            JOptionPane.showMessageDialog(this, "Some fields are invalid!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    protected void saveOrUpdate(T entity) {
        // TODO Should abtract this method also. getDao(T).saveOrUpdate(entity)

    }

    protected Set<ConstraintViolation<T>> bindAndValidate(T entity) {
        for (Method method : entity.getClass().getMethods()) {
            DetailDataModel dataModel = getDataModelFromSetMethod(method.getName());
            if (dataModel != null) {
                JComponent component = mapFields.get(dataModel);
                if (!dataModel.isEditable()) {
                    continue;
                }

                try {
                    if (dataModel.getFieldType() == FieldTypeEnum.TEXT_BOX) {
                        JFormattedTextField textComponent = (JFormattedTextField) component;

                        Class<?> paramClass = getPropertyReturnType(entity, dataModel);
                        if (textComponent.getText().isEmpty()) {
                            method.invoke(entity, (Object) null);
                        } else if (paramClass.equals(Double.class)) {
                            method.invoke(entity, (Double) textComponent.getValue());
                        } else if (paramClass.equals(Integer.class)) {
                            method.invoke(entity, (Integer) textComponent.getValue());
                        } else if (paramClass.equals(Long.class)) {
                            method.invoke(entity, (Long) textComponent.getValue());
                        } else if (paramClass.equals(String.class)) {
                            method.invoke(entity, textComponent.getText());
                        } else {
                            throw new RuntimeException("Do not support class " + paramClass.getCanonicalName());
                        }
                    } else if (dataModel.getFieldType() == FieldTypeEnum.PASSWORD) {
                        JPasswordField textComponent = (JPasswordField) component;

                        Class<?> paramClass = getPropertyReturnType(entity, dataModel);
                        if (textComponent.getText().isEmpty()) {
                            method.invoke(entity, (Object) null);
                        } else if (paramClass.equals(Double.class)) {
                            method.invoke(entity, Double.valueOf(textComponent.getText()));
                        } else if (paramClass.equals(Integer.class)) {
                            method.invoke(entity, Integer.valueOf(textComponent.getText()));
                        } else if (paramClass.equals(String.class)) {
                            method.invoke(entity, textComponent.getText());
                        } else {
                            throw new RuntimeException("Do not support class " + paramClass.getCanonicalName());
                        }
                    } else if (dataModel.getFieldType() == FieldTypeEnum.COMBO_BOX) {
                        JComboBox comboBox = (JComboBox) component;
                        String id = comboBox.getSelectedItem().toString();
                        Class<?> paramClass = getPropertyReturnType(entity, dataModel);

                        // TODO: duplicate code with FieldType TEXT_BOX
                        if (paramClass.equals(Double.class)) {
                            method.invoke(entity, Double.valueOf(id));
                        } else if (paramClass.equals(Integer.class)) {
                            method.invoke(entity, Integer.valueOf(id));
                        } else if (paramClass.equals(String.class)) {
                            method.invoke(entity, id);
                        } else {
                            throw new RuntimeException("Do not support class " + paramClass.getCanonicalName());
                        }
                    } else if (dataModel.getFieldType() == FieldTypeEnum.MULTI_SELECT_BOX) {
                        MultiSelectionBox multiBox = (MultiSelectionBox) component;
                        List<Object> unselected = multiBox.getSourceValues();
                        List<Object> selected = multiBox.getDestinationValues();
                        // TODO: set selected value into entity
                        // for each item in listData of entity, remove, then set selected into entity. (prevent
                        // hibernate mapping issue).

                    } else {
                        throw new RuntimeException("Do not support FieldTypeEnum " + dataModel.getFieldType());
                    }
                } catch (Exception e) {
                    logger.error("Technical error.", e);
                    throw new RuntimeException(e);
                }
            }
        }

        // TODO: must validate object before saving
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();

        Validator validator = factory.getValidator();
        return validator.validate(entity);
    }

    /**
     * Get type of property from dataModel and getter of entity. The getter must follow convention get+FieldName. TODO:
     * This returnType could be set directly to DetailDataModel?
     * 
     * @param entity
     * @param dataModel
     * @return
     * @throws NoSuchMethodException
     */
    private Class<?> getPropertyReturnType(T entity, DetailDataModel dataModel) {
        try {
            Method getMethod = entity.getClass().getMethod("get" + StringUtils.capitalize(dataModel.getFieldName()));
            Class<?> paramClass = getMethod.getReturnType();
            return paramClass;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private DetailDataModel getDataModelFromSetMethod(String setMethodName) {
        for (DetailDataModel dataModel : listDataModel) {
            if (setMethodName.equals("set" + StringUtils.capitalize(dataModel.getFieldName()))) {
                return dataModel;
            }
        }
        return null;
    }

    protected void btnCancelActionPerformed(ActionEvent evt) {
    }
}
