/*
 * PagingNavigator
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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.text.NumberFormatter;

/**
 * The component support select the back, next, beginning and ending page also allowing to fill the selected page
 * number. It's fire the event for {@link IPageChangeListener} when the current page number is changed.
 * 
 * @author Phan Hong Phuc
 * @since Nov 13, 2011
 * 
 */
public class PagingNavigator extends JPanel {
    private static final long serialVersionUID = -8782466982711611127L;
    private JButton btnBegining;
    private JButton btnBack;
    private JButton btnNext;
    private JButton btnEnding;
    private JLabel lblTotalPageNumber;
    private int totalPage = 1;
    private JFormattedTextField txtCurrentPageNumber;

    private List<IPageChangeListener> pageChangeListeners = new ArrayList<>();

    /**
     * Init the component with number of total pages and the default current page is 1.
     * 
     * @param totalPage
     *            number total of pages.
     */
    public PagingNavigator(int totalPage) {
        this(totalPage, 1);
    }

    /**
     * Init the component with number of total pages and the initial current page.
     * 
     * @param totalPage
     *            number total of pages.
     * @param currentPage
     *            the current page number.
     */
    /**
     * @param totalPage
     * @param currentPage
     */
    public PagingNavigator(int totalPage, int currentPage) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkTotalPage(totalPage);
        checkCurrentPage(totalPage, currentPage);

        btnBegining = new JButton("|<");
        btnBack = new JButton("<");
        btnNext = new JButton(">");
        btnEnding = new JButton(">|");

        btnBegining.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentPage(1);
            }
        });

        btnEnding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentPage(PagingNavigator.this.totalPage);
            }
        });

        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentPage(getCurrentPage() - 1);
            }
        });

        btnNext.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentPage(getCurrentPage() + 1);
            }
        });

        this.totalPage = totalPage;
        NumberFormatter numFormatter = new NumberFormatter();
        numFormatter.setValueClass(Integer.class);
        txtCurrentPageNumber = new JFormattedTextField(numFormatter);
        txtCurrentPageNumber.setColumns(3);
        txtCurrentPageNumber.setInputVerifier(new InRangeVerifier());
        txtCurrentPageNumber.setHorizontalAlignment(JTextField.RIGHT);
        txtCurrentPageNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    validateCurrentPageInput(txtCurrentPageNumber);
                }
            }

        });

        lblTotalPageNumber = new JLabel();
        setCurrentPage(currentPage);
        setTotalPage(totalPage);

        add(btnBegining);
        add(btnBack);
        add(txtCurrentPageNumber);
        add(lblTotalPageNumber);
        add(btnNext);
        add(btnEnding);
    }

    private void checkTotalPage(int totalPage) {
        if (totalPage <= 0) {
            throw new IllegalArgumentException("The total page must greater than 0");
        }
    }

    private void checkCurrentPage(int totalPage, int currentPage) {
        if (currentPage <= 0 || currentPage > totalPage) {
            throw new IllegalArgumentException("The current page must greater than 0 and less than or equal totalPage");
        }
    }

    /**
     * Set the current page.
     * 
     * @param currentPage
     *            the currentPage.
     */
    public void setCurrentPage(int currentPage) {
        checkCurrentPage(totalPage, currentPage);
        enableDisableButtons(currentPage, totalPage);
        txtCurrentPageNumber.setValue(currentPage);
        firePageChangeListener();
    }

    /**
     * Enable or disable the buttons when currentPage or totalPage is changed.
     */
    private void enableDisableButtons(int currentPage, int totalPage) {
        boolean isBeginning = (currentPage == 1);
        boolean isEndding = (currentPage == totalPage);
        btnBack.setEnabled(!isBeginning);
        btnBegining.setEnabled(!isBeginning);
        btnNext.setEnabled(!isEndding);
        btnEnding.setEnabled(!isEndding);
    }

    /**
     * Re-set the total page.
     * 
     * @param totalPage
     *            the total page.
     */
    public void setTotalPage(int totalPage) {
        checkTotalPage(totalPage);
        if (totalPage < getCurrentPage()) {
            throw new IllegalArgumentException("The total page can't not less than currentPage");
        }

        this.totalPage = totalPage;
        enableDisableButtons(getCurrentPage(), totalPage);
        lblTotalPageNumber.setText("/" + totalPage);
    }

    /**
     * Get the current page.
     * 
     * @return the current page.
     */
    public int getCurrentPage() {
        return (int) (txtCurrentPageNumber.getValue() == null ? 1 : txtCurrentPageNumber.getValue());
    }

    private void firePageChangeListener() {
        ChangeEvent e = new ChangeEvent(this);
        for (IPageChangeListener pl : pageChangeListeners) {
            pl.doPageChanged(e);
        }
    }

    public void addPageChangeListener(IPageChangeListener listener) {
        pageChangeListeners.add(listener);
    }

    public void removePageChangeListener(IPageChangeListener listener) {
        pageChangeListeners.remove(listener);
    }

    public class InRangeVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JFormattedTextField currentPageNumberField = (JFormattedTextField) input;
            return validateCurrentPageInput(currentPageNumberField);
        }
    }

    private boolean validateCurrentPageInput(JFormattedTextField currentPageNumberField) {
        try {
            currentPageNumberField.commitEdit();
            int value = (int) currentPageNumberField.getValue();
            if ((value >= 1) && (value <= totalPage)) {
                setCurrentPage(value);
                currentPageNumberField.setBackground(Color.WHITE);
                return true;
            } else {
                currentPageNumberField.setBackground(Color.PINK);
                return false;
            }
        } catch (ParseException e) {
            currentPageNumberField.setBackground(Color.PINK);
        }
        return false;
    }

}
