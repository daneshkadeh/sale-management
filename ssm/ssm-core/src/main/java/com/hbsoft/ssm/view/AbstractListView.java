package com.hbsoft.ssm.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hbsoft.ssm.view.object.DetailDataModel;

public class AbstractListView<T> extends JFrame {
	JButton btnSearch;
	JButton btnSort;
	JTable tblListEntities;
	JScrollPane jScrollPane;
	
	List<T> entities;
	List<DetailDataModel> listDataModel;
	
	public AbstractListView() {
		initComponents();
	}

	private void initComponents() {
		btnSearch = new JButton("Search");
		btnSort = new JButton("Sort");
		
	}
}
