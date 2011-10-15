package com.hbsoft.ssm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
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
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.hbsoft.ssm.entity.AbstractBaseIdObject;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.model.ReferenceDataModel;
import com.hbsoft.ssm.util.Solution3sClassUtils;
import com.hbsoft.ssm.util.i18n.ControlConfigUtils;
import com.hbsoft.ssm.view.component.MultiSelectionBox;

/**
 * @author Pham Cong Bang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractDetailView<T extends AbstractBaseIdObject> extends AbstractView {
    private static final long serialVersionUID = 1L;

    private Log logger = LogFactory.getLog(AbstractDetailView.class);

    // TODO HPP consider to remove this reference. We should fire event to listView instead of keep a reference.
    protected AbstractListView<T> listView;

    protected List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
    protected Map<DetailDataModel, JComponent> mapFields = new HashMap<DetailDataModel, JComponent>();

    private JButton btnOK;
    private JButton btnCancel;
    protected T entity;

    private ReferenceDataModel refDataModel = new ReferenceDataModel();

    private Integer JPASSWORDFIELD_SIZE = 20;

    /**
     * Initialize the detail view.
     * 
     * @param entity
     *            the entity of detail view.
     */
    public AbstractDetailView(T entity) {
        this.entity = entity;
        initialPresentationView(listDataModel, entity);
        setReferenceDataModel(refDataModel, entity);
        try {
            initComponents();
        } catch (Exception e) {
            logger.error(e.getCause());
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setListView(AbstractListView<T> view) {
        this.listView = view;
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return (Class<T>) Solution3sClassUtils.getArgumentClass(getClass());
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
        setLayout(new MigLayout("wrap", "fill, grow"));

        add(createButtonPanel());

        JPanel pnlEdit = new JPanel(new MigLayout("wrap 2", "[][fill, grow]"));
        for (DetailDataModel dataModel : listDataModel) {
            String label = ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + dataModel.getFieldName());
            JLabel lblLabel = new JLabel(label);
            JComponent dataField;

            Method getMethod = entity.getClass().getMethod("get" + StringUtils.capitalize(dataModel.getFieldName()));
            Object value = getMethod.invoke(entity);
            switch (dataModel.getFieldType()) {
            case TEXT_BOX:
                Class<?> propertyReturnType = getPropertyReturnType(entity, dataModel);
                if (ClassUtils.isAssignable(propertyReturnType, Number.class)) {
                    NumberFormatter numFormatter = new NumberFormatter();
                    numFormatter.setValueClass(propertyReturnType);
                    dataField = new JFormattedTextField(numFormatter);
                    ((JFormattedTextField) dataField).setHorizontalAlignment(JFormattedTextField.RIGHT);
                } else {
                    // The format type is String
                    dataField = new JFormattedTextField("");
                }
                ((JFormattedTextField) dataField).setEditable(dataModel.isEditable());
                dataField.setEnabled(dataModel.isEnable());
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);

                ((JFormattedTextField) dataField).setValue(value);
                break;
            case PASSWORD:
                dataField = new JPasswordField(JPASSWORDFIELD_SIZE);
                ((JPasswordField) dataField).setEditable(dataModel.isEditable());
                dataField.setEnabled(dataModel.isEnable());
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);

                ((JTextField) dataField).setText(ObjectUtils.toString(value));
                break;
            case COMBO_BOX:
                // get the referenceDataList from ReferenceDataModel using referenceDataId of column.
                // TODO: getRenderer to render data using class of DataList
                dataField = new JComboBox(new DefaultComboBoxModel(refDataModel.getRefDataListMap()
                        .get(dataModel.getReferenceDataId()).toArray()));
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);

                ((JComboBox) dataField).setSelectedItem(value);
                break;
            case MULTI_SELECT_BOX:
                List refDataList = refDataModel.getRefDataListMap().get(dataModel.getReferenceDataId());
                dataField = new MultiSelectionBox(refDataList, new ArrayList());
                pnlEdit.add(lblLabel, "top");
                pnlEdit.add(dataField);

                // ((MultiSelectionBox) dataField)

                break;
            default:
                throw new RuntimeException("FieldType does not supported!");
            }
            mapFields.put(dataModel, dataField);
        }

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
        pnlEdit.add(pnlButton, "span, center, grow");

        add(pnlEdit, "grow");
    }

    private JPanel createButtonPanel() {
        // TODO hpp: move btnOK and Cancel to here, add other button.
        return new JPanel();
    }

    protected void btnOKActionPerformed(ActionEvent evt) {
        Set<ConstraintViolation<T>> validateResult = bindAndValidate(entity);
        if (CollectionUtils.isEmpty(validateResult)) {
            boolean isNew = (entity.getId() == null);
            saveOrUpdate(entity);
            listView.notifyFromDetailView(entity, isNew);
            // TODO HPP find another way to close dialog.
            SwingUtilities.getRoot(AbstractDetailView.this).setVisible(false);
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
                    Class<?> paramClass = getPropertyReturnType(entity, dataModel);
                    if (dataModel.getFieldType() == FieldTypeEnum.TEXT_BOX) {
                        JFormattedTextField txtField = (JFormattedTextField) component;
                        method.invoke(entity, paramClass.cast(txtField.getValue()));
                    } else if (dataModel.getFieldType() == FieldTypeEnum.PASSWORD) {
                        JPasswordField pwdField = (JPasswordField) component;
                        method.invoke(entity, pwdField.getText());
                    } else if (dataModel.getFieldType() == FieldTypeEnum.COMBO_BOX) {
                        JComboBox comboBox = (JComboBox) component;
                        method.invoke(entity, paramClass.cast(comboBox.getSelectedItem()));
                    } else if (dataModel.getFieldType() == FieldTypeEnum.MULTI_SELECT_BOX) {
                        MultiSelectionBox multiBox = (MultiSelectionBox) component;
                        List unselected = multiBox.getSourceValues();
                        List selected = multiBox.getDestinationValues();
                        method.invoke(entity, selected);
                        // List<>

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
