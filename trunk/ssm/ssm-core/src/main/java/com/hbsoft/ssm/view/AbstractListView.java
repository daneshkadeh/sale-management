package com.hbsoft.ssm.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.view.CustomerView.RowListener;
import com.hbsoft.ssm.view.object.DetailDataModel;

public abstract class AbstractListView<T extends Object> extends JFrame {
	JButton btnSearch;
	JButton btnSort;
	JTable tblListEntities;
	JScrollPane jScrollPane;
	
//	Class<T> clazz;
	protected List<T> entities;
	List<DetailDataModel> listDataModel = new ArrayList<DetailDataModel>();
	public boolean selector = false;
	
	public AbstractListView() {
		// TODO: should get command class from T
//		clazz = getCommandClass();
		initialPresentationView(listDataModel);
		initComponents();
	}

	protected abstract void initialPresentationView(List<DetailDataModel> listDataModel);

	protected List<T> loadData() {
		return new ArrayList<T>();
	}
	

	// protected Class<T> getCommandClass() {
	// return null;
	// }
	
	private void initComponents() {
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSearchActionPerformed(evt);
			}
		});
		btnSort = new JButton("Sort");
		btnSort.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSortActionPerformed(evt);
			}
		});
		
		tblListEntities = new JTable();
		displayCustomerList();
		tblListEntities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblListEntities.getSelectionModel().addListSelectionListener(
				new RowListener());

		jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(tblListEntities);
		
		Container container = getContentPane();
		container.setLayout(new MigLayout("fillx,insets 1, width :1000:"));
		
		JPanel pnlListEntities = new JPanel(new MigLayout());
		pnlListEntities.add(jScrollPane, "wrap");
		container.add(pnlListEntities, "wrap");
		
		JPanel pnlButton = new JPanel(new MigLayout());
		pnlButton.add(btnSearch);
		pnlButton.add(btnSort);
		container.add(pnlButton, "wrap");
		pack();
	}
	
	private void displayCustomerList() {
		entities = loadData();
		Vector<String> tableHeaders = new Vector<String>();
		Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
		for (DetailDataModel dataModel : listDataModel) {
			tableHeaders.add(dataModel.getLabel());
		}
		for (T entity : entities) {
			Vector<Object> oneRow = new Vector<Object>();
			for (Method method : entity.getClass().getMethods()) {
				DetailDataModel dataModel = getDataModelFromGetMethod(method.getName());
				if (dataModel != null) {
					try {
						oneRow.add(method.invoke(entity));
					} catch (Exception e) {
						throw new RuntimeException("Can not invoke method " + method.getName());
					}
				}
			}
			tableData.add(oneRow);
		}
		tblListEntities
				.setModel(new DefaultTableModel(tableData, tableHeaders));
		selector = false;
		
	}
	
	private DetailDataModel getDataModelFromGetMethod(String setMethodName) {
		for (DetailDataModel dataModel : listDataModel) {
			if (setMethodName.equals("get"
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
	
	protected void btnSortActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
	}

	protected void btnSearchActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	protected class RowListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting()) {
				return;
			}
			try {
				selector  = true;
			} catch (Exception e) {
			}
		}
	}
}
