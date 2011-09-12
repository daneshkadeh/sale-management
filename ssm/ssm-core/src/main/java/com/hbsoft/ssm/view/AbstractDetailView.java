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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.model.FieldTypeEnum;
import com.hbsoft.ssm.util.i18n.ControlConfigUtils;

public abstract class AbstractDetailView<T> extends JPanel {
    private static final long serialVersionUID = 1L;

    private Log logger = LogFactory.getLog(AbstractDetailView.class);

    protected List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
    protected Map<DetailDataModel, JComponent> mapFields = new HashMap<DetailDataModel, JComponent>();
    private JButton btnOK;
    private JButton btnCancel;
    private Class<T> clazz;
    private T entity;
    private Integer JTEXTFIELD_SIZE = 20;

    public AbstractDetailView() {
        clazz = getEntityClass();
        try {
            entity = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        initialPresentationView(listDataModel);
        initComponents();
    }

    protected Class<T> getEntityClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) controllerType;
    }

    public abstract void initialPresentationView(List<DetailDataModel> listDataModel);

    private void initComponents() {
        // Layout the screen
        setLayout(new MigLayout("fillx,insets 1, width :500:"));

        JPanel pnlEdit = new JPanel(new MigLayout("wrap 2"));
        for (DetailDataModel dataModel : listDataModel) {
            String label = ControlConfigUtils.getString("label." + getEntityClass().getSimpleName() + "."
                    + dataModel.getFieldName());
            JLabel lblLabel = new JLabel(label);
            pnlEdit.add(lblLabel);
            JComponent dataField;
            switch (dataModel.getFieldType()) {
            case TEXT_BOX:
                dataField = new JTextField(JTEXTFIELD_SIZE);
                ((JTextField) dataField).setEditable(dataModel.isEditable());
                dataField.setEnabled(dataModel.isEnable());
                mapFields.put(dataModel, dataField);
                break;
            default:
                throw new RuntimeException("FieldType does not supported!");
            }
            pnlEdit.add(dataField);
        }

        add(pnlEdit, "wrap");

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

        JPanel pnlButton = new JPanel(new MigLayout("center, , width :500:"));
        pnlButton.add(btnOK, "center");
        pnlButton.add(btnCancel);
        add(pnlButton);
    }

    protected void btnOKActionPerformed(ActionEvent evt) {
        Set<ConstraintViolation<T>> validateResult = bindAndValidate(entity);
        if (CollectionUtils.isEmpty(validateResult)) {
            saveOrUpdate(entity);
        } else {
            for (ConstraintViolation<T> violation : validateResult) {
                logger.error(violation.getMessage());
            }
            JOptionPane.showMessageDialog(this, "Some fields are invalid!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    protected void saveOrUpdate(T entity2) {
        // TODO Should abtract this method also. getDao(T).saveOrUpdate(entity2)

    }

    protected Set<ConstraintViolation<T>> bindAndValidate(T entity2) {
        for (Method method : entity2.getClass().getMethods()) {
            DetailDataModel dataModel = getDataModelFromSetMethod(method.getName());
            if (dataModel != null) {
                JComponent component = mapFields.get(dataModel);
                if (dataModel.getFieldType() == FieldTypeEnum.TEXT_BOX) {
                    JTextComponent textComponent = (JTextComponent) component;
                    try {
                        Method getMethod = entity2.getClass().getMethod(
                                "get" + StringUtils.capitalize(dataModel.getFieldName()));
                        Class<?> paramClass = getMethod.getReturnType();
                        if (textComponent.getText().isEmpty()) {
                            method.invoke(entity2, (Object) null);
                        } else if (paramClass.equals(Double.class)) {
                            method.invoke(entity2, Double.valueOf(textComponent.getText()));
                        } else if (paramClass.equals(Integer.class)) {
                            method.invoke(entity2, Integer.valueOf(textComponent.getText()));
                        } else if (paramClass.equals(String.class)) {
                            method.invoke(entity2, textComponent.getText());
                        } else {
                            throw new RuntimeException("Do not support class " + paramClass.getCanonicalName());
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }

        // TODO: must validate object before saving
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();

        Validator validator = factory.getValidator();
        return validator.validate(entity2);
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
