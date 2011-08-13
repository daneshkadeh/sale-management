package com.hbsoft.ssm.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

import com.hbsoft.ssm.view.object.DetailDataModel;
import com.hbsoft.ssm.view.object.FieldType;

public abstract class AbstractDetailView<T> extends JFrame {
	protected List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
	protected Map<DetailDataModel, JComponent> mapFields = new HashMap<DetailDataModel, JComponent>();
	JButton btnOK;
	JButton btnCancel;
	Class<T> clazz;
	T entity;
	private Integer JTEXTFIELD_SIZE = 20;

	public AbstractDetailView() {
		// TODO: should get command class from T
		clazz = getCommandClass();
		try {
			entity = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		initialPresentationView(listDataModel);
		initComponents();
	}

	protected Class<T> getCommandClass() {
		return null;
	}

	public abstract void initialPresentationView(
			List<DetailDataModel> listDataModel);

	private void initComponents() {// Layout the screen
		Container container = getContentPane();
		container.setLayout(new MigLayout("fillx,insets 1, width :500:"));

		JPanel pnlEdit = new JPanel(new MigLayout());
		for (DetailDataModel dataModel : listDataModel) {
			JLabel dataLabel = new JLabel(dataModel.getLabel());
			pnlEdit.add(dataLabel);
			JTextField dataField = null;
			switch (dataModel.getFieldType()) {
			case TEXT_BOX:
				dataField = new JTextField(JTEXTFIELD_SIZE);
				dataField.setEditable(dataModel.isEditable());
				dataField.setEnabled(dataModel.isEnable());
				mapFields.put(dataModel, dataField);
				break;
			default:
				throw new RuntimeException("FieldType does not supported!");
			}
			pnlEdit.add(dataField, "wrap");
		}

		container.add(pnlEdit, "wrap");

		btnOK = new JButton("OK");
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		JPanel pnlButton = new JPanel(new MigLayout("center, , width :500:"));
		pnlButton.add(btnOK, "center");
		pnlButton.add(btnCancel);
		container.add(pnlButton);
		pack();
	}

	protected void btnOKActionPerformed(ActionEvent evt) {
		bindAndValidate(entity);
		saveOrUpdate(entity);
	}

	protected void saveOrUpdate(T entity2) {
		// TODO Should abtract this method also. getDao(T).saveOrUpdate(entity2)

	}

	protected void bindAndValidate(T entity2) {
		for (Method method : entity2.getClass().getMethods()) {
			DetailDataModel dataModel = getDataModel(method.getName());
			if (dataModel != null) {
				JComponent component = mapFields.get(dataModel);
				if (dataModel.getFieldType() == FieldType.TEXT_BOX) {
					JTextComponent textComponent = (JTextComponent) component;
					try {
						Method getMethod = entity2.getClass().getMethod(
								"get"
										+ capitalizeFirstChar(dataModel
												.getFieldName()));
						Class<?> paramClass = getMethod.getReturnType();
						if (paramClass.equals(Double.class)) {
							method.invoke(entity2,
									Double.valueOf(textComponent.getText()));
						} else if (paramClass.equals(Integer.class)) {
							method.invoke(entity2,
									Integer.valueOf(textComponent.getText()));
						} else if (paramClass.equals(String.class)) {
							method.invoke(entity2, textComponent.getText());
						} else {
							throw new RuntimeException("Do not support class "
									+ paramClass.getCanonicalName());
						}

					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}
			}
		}
		// TODO: must validate object before saving
	}

	private DetailDataModel getDataModel(String name) {
		for (DetailDataModel dataModel : listDataModel) {
			if (name.equals("set"
					+ capitalizeFirstChar(dataModel.getFieldName()))) {
				return dataModel;
			}
		}
		return null;
	}

	private static String capitalizeFirstChar(String fieldName) {
		return (fieldName.substring(0, 1).toUpperCase())
				+ fieldName.substring(1);
	}

	protected void btnCancelActionPerformed(ActionEvent evt) {
		this.dispose();
	}
}
