/*
 * PagingSelector
 * 
 * Project: SSM
 * 
 * Copyright 2010 by S3SSoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of S3SSoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with S3SSoft.
 */

package com.s3s.ssm.view.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * The component support select the back, next, beginning and ending page also allowing to fill the selected page
 * number. It's fire the event for {@link S3sPageChangeListener} when the current page number is changed.
 * 
 * @author Phan Hong Phuc
 * @since Nov 13, 2011
 * 
 */
public class S3sPagingSelector extends JPanel {
    private static final long serialVersionUID = -8782466982711611127L;
    private JButton btnBegining;
    private JButton btnBack;
    private JButton btnNext;
    private JButton btnEnding;
    private JLabel lblTotalPageNumber;
    private int totalPage = 1;
    private JFormattedTextField txtCurrentPageNumber;

    private List<S3sPageChangeListener> pageChangeListeners = new ArrayList<>();

    /**
     * Init the component with number of total pages and the default current page is 1.
     * 
     * @param totalPage
     *            number total of pages.
     */
    public S3sPagingSelector(int totalPage) {
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
    public S3sPagingSelector(int totalPage, int currentPage) {
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
                setCurrentPage(S3sPagingSelector.this.totalPage);
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
        txtCurrentPageNumber = new JFormattedTextField(NumberFormat.getIntegerInstance());
        txtCurrentPageNumber.setColumns(3);
        ((AbstractDocument) txtCurrentPageNumber.getDocument()).setDocumentFilter(new PositiveNumberFilter());
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

    public void setCurrentPage(int currentPage) {
        checkCurrentPage(totalPage, currentPage);
        boolean isBeginning = (currentPage == 1);
        boolean isEndding = (currentPage == totalPage);
        btnBack.setEnabled(!isBeginning);
        btnBegining.setEnabled(!isBeginning);
        btnNext.setEnabled(!isEndding);
        btnEnding.setEnabled(!isEndding);
        txtCurrentPageNumber.setValue(currentPage);
        firePageChangeListener();
    }

    public void setTotalPage(int totalPage) {
        checkTotalPage(totalPage);
        this.totalPage = totalPage;
        lblTotalPageNumber.setText("/" + totalPage);
        // Reset the current page if the total page < current page.
        if (totalPage < getCurrentPage()) {
            setCurrentPage(1);
        }
    }

    public int getCurrentPage() {
        return (int) (txtCurrentPageNumber.getValue() == null ? 1 : txtCurrentPageNumber.getValue());
    }

    private void firePageChangeListener() {
        ChangeEvent e = new ChangeEvent(this);
        for (S3sPageChangeListener pl : pageChangeListeners) {
            pl.doPageChanged(e);
        }
    }

    public void addPageChangeListener(S3sPageChangeListener listener) {
        pageChangeListeners.add(listener);
    }

    public void removePageChangeListener(S3sPageChangeListener listener) {
        pageChangeListeners.remove(listener);
    }

    public class PositiveNumberFilter extends DocumentFilter {

        /**
         * {@inheritDoc}
         */
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            System.err.println("String: " + string);
            super.insertString(fb, offset, string, attr);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            System.err.println("String: " + text);
            super.replace(fb, offset, length, text, attrs);
        }

    }

}
