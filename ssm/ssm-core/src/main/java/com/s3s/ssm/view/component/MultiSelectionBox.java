/*
 * MultiSelectionBox
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
package com.s3s.ssm.view.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXList;

/**
 * The component multi-selection box. This component includes the source list box, the destination list box and 4
 * buttons to move elements between these 2 list boxes. </p> The useful methods:
 * <ul>
 * <li>{@link #getSourceValues()}</li>
 * <li>{@link #getDestinationValues()}</li>
 * </ul>
 * 
 * @author Phan Hong Phuc
 */
public class MultiSelectionBox<T> extends JPanel {
    private static final long serialVersionUID = 1L;

    // Subcomponents
    private JXList lstSource;
    private JXList lstDest;
    private JButton btnSelectSingle;
    private JButton btnSelectAll;
    private JButton btnDeselectSingle;
    private JButton btnDeselectAll;

    // Data
    private List<T> sources = new ArrayList<T>();
    private List<T> destinations = new ArrayList<T>();

    // Renderer
    private ListCellRenderer<T> cellRenderer;

    /**
     * Initialize multiselectbox with the source values and the destination values. And renderer of 2 list box is
     * {@link DefaultListCellRenderer}.
     * 
     * @param sources
     * @param destinations
     */
    public MultiSelectionBox(List<T> sources, List<T> destinations) {
        this(sources, destinations, null);
    }

    /**
     * Initialize multiselectbox with the source values and the destination values, and the renderer of 2 list box.
     * 
     * @param sources
     * @param destinations
     * @param cellRenderer
     */
    public MultiSelectionBox(List<T> sources, List<T> destinations, ListCellRenderer<T> cellRenderer) {
        super();
        this.sources = sources;
        this.destinations = destinations;
        this.cellRenderer = cellRenderer;
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("ins 0", "[grow,fill]0[center]0[grow,fill]", "[center]"));
        // /////// Init 2 JList ///////////
        lstSource = createJList(sources, cellRenderer);
        lstDest = createJList(destinations, cellRenderer);

        // Move the selected element when double click to it.
        lstSource.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    move(lstSource, lstDest, false);
                }
            }
        });
        lstDest.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    move(lstDest, lstSource, false);
                }
            }
        });

        // ////////// Init the buttons /////////////
        btnDeselectSingle = new JButton("<");
        btnDeselectSingle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                move(lstDest, lstSource, false);
            }
        });

        btnDeselectAll = new JButton("<<");
        btnDeselectAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                move(lstDest, lstSource, true);
            }
        });

        btnSelectSingle = new JButton(">");
        btnSelectSingle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                move(lstSource, lstDest, false);
            }
        });

        btnSelectAll = new JButton(">>");
        btnSelectAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                move(lstSource, lstDest, true);
            }
        });

        JScrollPane sourceScrollpane = new JScrollPane(lstSource);
        sourceScrollpane.setPreferredSize(new Dimension(100, 200));
        JScrollPane desScrollpane = new JScrollPane(lstDest);
        desScrollpane.setPreferredSize(new Dimension(100, 200));
        add(sourceScrollpane, "cell 0 0");
        add(btnSelectAll, "flowy, cell 1 0, growx");
        add(btnSelectSingle, "cell 1 0, growx");
        add(btnDeselectSingle, "cell 1 0, growx");
        add(btnDeselectAll, "cell 1 0, growx");
        add(desScrollpane, "cell 2 0");
    }

    private JXList createJList(List<T> data, ListCellRenderer<T> renderer) {
        DefaultListModel<T> listModel = new DefaultListModel<>();
        for (T d : data) {
            listModel.addElement(d);
        }
        JXList jList = new JXList(listModel);
        jList.setCellRenderer(renderer);
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        jList.setSelectionModel(selectionModel);
        jList.setAutoscrolls(true);
        return jList;
    }

    private void enableDisableButtons() {
        DefaultListModel<T> sourceModel = (DefaultListModel<T>) lstSource.getModel();
        DefaultListModel<T> destModel = (DefaultListModel<T>) lstDest.getModel();
        btnDeselectAll.setEnabled(destModel.getSize() > 0);
        btnDeselectSingle.setEnabled(destModel.getSize() > 0);
        btnSelectAll.setEnabled(sourceModel.getSize() > 0);
        btnSelectSingle.setEnabled(sourceModel.getSize() > 0);
    }

    /**
     * Move elements from JList <code>from</code> to JList <code>to</code>.
     * 
     * @param from
     * @param to
     * @param allElements
     *            move all elements if <code>true</code>, else just move the selected values of <code>from</code> JList.
     */
    private void move(JXList from, JXList to, boolean allElements) {
        DefaultListModel<T> fromModel = (DefaultListModel<T>) from.getModel();
        DefaultListModel<T> toModel = (DefaultListModel<T>) to.getModel();
        if (allElements) {
            for (int i = 0; i < fromModel.getSize(); i++) {
                toModel.addElement(fromModel.get(i));
            }
            fromModel.removeAllElements();
        } else {
            for (T o : (List<T>) from.getSelectedValuesList()) {
                fromModel.removeElement(o);
                toModel.addElement(o);
            }
        }
        enableDisableButtons();
    }

    private List<T> getAllValuesOfJList(JXList jList) {
        DefaultListModel<T> sourceModel = (DefaultListModel<T>) jList.getModel();
        List<T> sourceData = new ArrayList<T>(sourceModel.size());
        for (int i = 0; i < sourceModel.size(); i++) {
            sourceData.add(sourceModel.get(i));
        }
        return sourceData;
    }

    /**
     * Get destination data.
     * 
     * @return the list of values in destination list box.
     */
    public List<T> getDestinationValues() {
        return getAllValuesOfJList(lstDest);
    }

    /**
     * Get source data.
     * 
     * @return the list of values in sources list box.
     */
    public List<T> getSourceValues() {
        return getAllValuesOfJList(lstSource);
    }
}
