/*
 * ExportDialog
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.export.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;

import com.s3s.ssm.export.exporter.ExportTypeEnum;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ExportDialog extends JDialog {
    public int APPROVE_OPTION = 0;
    private final JPanel contentPanel = new JPanel();
    private JButton okButton;
    private JButton cancelButton;
    private JTextField tfdFilePath;
    private File selectedFile;
    private JTextField tfdRangePage;
    private JRadioButton allRadio;
    private JRadioButton curPageRadio;
    private JRadioButton pagesRadio;
    private ButtonGroup btnGroup;
    private JComboBox cmbExportType;
    private Map<JRadioButton, Integer> radioBtn2Value;

    private List<String> fieldList;
    private Map<String, String> labels;

    public final static int ALL_PAGE = -1;
    public final static int CUR_PAGE = 0;
    public final static int RANGE_PAGE = 1;
    public static int FIRST_PAGE = 0;
    public static int LAST_PAGE = 0;

    /**
     * Create the dialog.
     */
    public ExportDialog(Window parent, Dialog.ModalityType model) {
        super(parent, "", model);
        radioBtn2Value = new HashMap<JRadioButton, Integer>(3);
        initialComponent();

    }

    private void initialComponent() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new MigLayout("wrap 2"));
        {
            JLabel lblSave = new JLabel("SAVING DIR");
            JButton chooseFile = new JButton("...");

            chooseFile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performChooseFileAction();
                }
            });

            tfdFilePath = new JTextField();
            tfdFilePath.setColumns(20);
            JLabel lblType = new JLabel("SAVE TYPE");

            cmbExportType = new JComboBox(ExportTypeEnum.values());

            JPanel savePanel = new JPanel();
            savePanel.add(tfdFilePath);
            savePanel.add(chooseFile);

            contentPanel.add(lblSave);
            contentPanel.add(savePanel);
            contentPanel.add(lblType);
            contentPanel.add(cmbExportType);

            setPageRangePanel();

        }
        setButtonPanel();
    }

    private void setPageRangePanel() {
        JPanel pageRangePanel = new JPanel(new MigLayout());

        Border blackline = BorderFactory.createLineBorder(Color.gray);
        TitledBorder title = BorderFactory.createTitledBorder(blackline, "Page range");
        title.setTitleJustification(TitledBorder.LEFT);
        pageRangePanel.setBorder(title);

        tfdRangePage = new JTextField();
        tfdRangePage.setColumns(10);

        allRadio = new JRadioButton("All", true);
        curPageRadio = new JRadioButton("Current page", false);
        pagesRadio = new JRadioButton("Pages", false);

        radioBtn2Value.put(allRadio, ALL_PAGE);
        radioBtn2Value.put(curPageRadio, CUR_PAGE);
        radioBtn2Value.put(pagesRadio, RANGE_PAGE);

        btnGroup = new ButtonGroup();
        btnGroup.add(allRadio);
        btnGroup.add(curPageRadio);
        btnGroup.add(pagesRadio);

        pageRangePanel.add(allRadio, "cell 0 0");
        pageRangePanel.add(curPageRadio, "cell 0 1");
        pageRangePanel.add(pagesRadio, "cell 0 2");
        pageRangePanel.add(tfdRangePage, "cell 1 2");

        contentPanel.add(pageRangePanel, "cell 0 2, span 2");
    }

    private void setButtonPanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            okButton = new JButton("OK");

            okButton.setActionCommand("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performOKAction();
                }
            });
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);
        }
        {
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performCancelAction();
                }
            });
            buttonPane.add(cancelButton);
        }
    }

    private void performChooseFileAction() {
        FileDialog fileDialog = new FileDialog(this);
        fileDialog.setVisible(true);
        fileDialog.getDirectory();

        File[] files = fileDialog.getFiles();
        if (files.length > 0) {
            selectedFile = files[0];
            tfdFilePath.setText(selectedFile.getAbsolutePath());
        }
        this.repaint();
    }

    private void performOKAction() {
        APPROVE_OPTION = 1;
        this.dispose();
    }

    private void performCancelAction() {
        APPROVE_OPTION = 0;
        this.dispose();
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public int getPageRange() {
        for (JRadioButton rb : radioBtn2Value.keySet()) {
            if (rb.isSelected()) {
                int value = radioBtn2Value.get(rb);
                if (value == RANGE_PAGE) {
                    // set page range
                    try {
                        String[] ranges = StringUtils.split(tfdRangePage.getText(), "-");
                        FIRST_PAGE = Integer.parseInt(ranges[0]);
                        LAST_PAGE = Integer.parseInt(ranges[1]);
                    } catch (NumberFormatException e) {
                        // TODO process the exception
                        e.printStackTrace();
                    }
                }
                return value;
            }
        }
        return ALL_PAGE;
    }

    public ExportTypeEnum getExportType() {
        return (ExportTypeEnum) cmbExportType.getSelectedItem();

    }
}
