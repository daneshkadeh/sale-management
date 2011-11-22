package com.s3s.ssm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.jdesktop.swingx.JXDatePicker;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.model.ReferenceDataModel.ReferenceData;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.MultiSelectionBox;

/**
 * @author Pham Cong Bang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractDetailView<T extends AbstractCodeOLObject> extends AbstractView {
    private static final long serialVersionUID = 1L;

    private final Log logger = LogFactory.getLog(AbstractDetailView.class);

    // TODO HPP consider to remove this reference. We should fire event to
    // listView instead of keep a reference.
    protected AbstractListView<T> listView;

    protected DetailDataModel detailDataModel = new DetailDataModel();
    protected Map<DetailAttribute, JComponent> mapFields = new HashMap<>();

    private JButton btnOK;
    private JButton btnCancel;
    protected T entity;

    private final ReferenceDataModel refDataModel = new ReferenceDataModel();

    private final static int DEFAULT_TEXTFIELD_COLUMN = 20;

    /**
     * The default constructor, init the detail view with new entity.
     */
    public AbstractDetailView() {
        try {
            T entity = getEntityClass().newInstance();
            contructView(entity);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("There is a problem when init the detail view");
            throw new RuntimeException(e.getCause());
        }

    }

    /**
     * Initialize the detail view. TODO: concrete classes should not override this constructor.
     * 
     * @param entity
     *            the entity of detail view.
     */
    public AbstractDetailView(T entity) {
        this.entity = entity;
        contructView(entity);
    }

    // public AbstractDetailView() {
    // if (entity == null) {
    // try {
    // entity = getEntityClass().newInstance();
    // } catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    //
    // }
    // contructView(entity);
    // }

    private void contructView(T entity) {
        initialPresentationView(detailDataModel, entity);
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

    public abstract void initialPresentationView(DetailDataModel detailDataModel, T entity);

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

        JPanel pnlEdit = new JPanel(new MigLayout("wrap 2", "[][fill]"));
        for (DetailAttribute attribute : detailDataModel.getDetailAttributes()) {
            String label = ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + attribute.getName());
            JLabel lblLabel = new JLabel(label);
            JComponent dataField;

            Method getMethod = entity.getClass().getMethod("get" + StringUtils.capitalize(attribute.getName()));
            Object value = getMethod.invoke(entity);
            ReferenceData referenceData = refDataModel.getRefDataListMap().get(attribute.getReferenceDataId());
            switch (attribute.getType()) {
            case TEXTBOX:
                Class<?> propertyReturnType = getPropertyReturnType(entity, attribute);
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
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);

                ((JFormattedTextField) dataField).setValue(value);
                break;
            case PASSWORD:
                dataField = new JPasswordField(DEFAULT_TEXTFIELD_COLUMN);
                ((JPasswordField) dataField).setEditable(attribute.isEditable());
                dataField.setEnabled(attribute.isEnable());
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);

                ((JTextField) dataField).setText(ObjectUtils.toString(value));
                break;
            case DROPDOWN:
                // get the referenceDataList from ReferenceDataModel using
                // referenceDataId of column.
                dataField = new JComboBox<>(referenceData.getValues().toArray());
                ((JComboBox<?>) dataField).setRenderer(referenceData.getRenderer());
                pnlEdit.add(lblLabel);
                pnlEdit.add(dataField);
                ((JComboBox<?>) dataField).setSelectedItem(value);
                break;
            case MULTI_SELECT_BOX:
                // TODO HPP
                dataField = new MultiSelectionBox<>(referenceData.getValues(), new ArrayList<>());
                pnlEdit.add(lblLabel, "top");
                pnlEdit.add(dataField);

                // ((MultiSelectionBox) dataField)

                break;
            case DATE:
                DateTime date = (DateTime) value;
                dataField = new JXDatePicker();
                if (date != null) {
                    ((JXDatePicker) dataField).setDate(date.toDate());
                }
                pnlEdit.add(lblLabel, "top");
                pnlEdit.add(dataField);
                break;
            // case RADIO_BUTTON:
            // // TODO HPP
            // break;
            default:
                throw new RuntimeException("FieldType does not supported!");
            }
            mapFields.put(attribute, dataField);
        }

        btnOK = new JButton("OK");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
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
        getDaoHelper().getDao(getEntityClass()).saveOrUpdate(entity);
    }

    protected Set<ConstraintViolation<T>> bindAndValidate(T entity) {
        try {
            for (Method method : entity.getClass().getMethods()) {
                DetailAttribute attribute = getAttributeModelFromSetMethod(method.getName());
                if (attribute == null || !attribute.isEditable()) {
                    continue;
                }
                JComponent component = mapFields.get(attribute);
                Class<?> paramClass = getPropertyReturnType(entity, attribute);
                switch (attribute.getType()) {
                case TEXTBOX:
                    JFormattedTextField txtField = (JFormattedTextField) component;
                    method.invoke(entity, paramClass.cast(txtField.getValue()));
                    break;
                case PASSWORD:
                    JPasswordField pwdField = (JPasswordField) component;
                    method.invoke(entity, pwdField.getText());
                    break;
                case CHECKBOX:

                    break;
                case DATE:
                    JXDatePicker dateField = (JXDatePicker) component;
                    method.invoke(entity, new DateTime(dateField.getDate()));
                    break;
                case DROPDOWN:
                    JComboBox<?> comboBox = (JComboBox<?>) component;
                    method.invoke(entity, paramClass.cast(comboBox.getSelectedItem()));
                    break;
                case MULTI_SELECT_BOX:
                    MultiSelectionBox<?> multiBox = (MultiSelectionBox<?>) component;
                    // List<?> unselected = multiBox.getSourceValues();
                    List<?> selected = multiBox.getDestinationValues();
                    method.invoke(entity, selected);
                    // List<>

                    // TODO: set selected value into entity
                    // for each item in listData of entity, remove, then set
                    // selected into entity. (prevent
                    // hibernate mapping issue).

                    break;
                case RADIO_BUTTON:

                    break;

                default:
                    throw new RuntimeException("Do not support FieldTypeEnum " + attribute.getType());
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error(e.getCause());
            throw new RuntimeException("Problem happens when bindAndValidate detailView:" + e.getMessage());
        }

        // TODO: must validate object before saving
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();

        Validator validator = factory.getValidator();
        return validator.validate(entity);
    }

    /**
     * Get type of property from attributeModel and getter of entity. The getter must follow convention get+FieldName.
     * TODO: This returnType could be set directly to DetailDataModel?
     * 
     * @param entity
     * @param attribute
     * @return
     * @throws NoSuchMethodException
     */
    private Class<?> getPropertyReturnType(T entity, DetailAttribute attribute) {
        try {
            Method getMethod = entity.getClass().getMethod("get" + StringUtils.capitalize(attribute.getName()));
            Class<?> paramClass = getMethod.getReturnType();
            return paramClass;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private DetailAttribute getAttributeModelFromSetMethod(String setMethodName) {
        for (DetailAttribute attribute : detailDataModel.getDetailAttributes()) {
            if (setMethodName.equals("set" + StringUtils.capitalize(attribute.getName()))) {
                return attribute;
            }
        }
        return null;
    }

    protected void btnCancelActionPerformed(ActionEvent evt) {
        // TODO find another way to close dialog.
        SwingUtilities.getRoot(AbstractDetailView.this).setVisible(false);
    }
}
