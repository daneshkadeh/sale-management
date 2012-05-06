/*
 * MoneyComponent
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
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

import net.miginfocom.swing.MigLayout;

import org.springframework.util.Assert;

import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;

/**
 * @author Phan Hong Phuc
 * @since Mar 16, 2012
 */
public class MoneyComponent extends JPanel {
    private static final long serialVersionUID = -7556079748704045405L;

    private JFormattedTextField valueField;
    private JComboBox<CurrencyEnum> currencyCodeField;
    private Long currentValue;
    private CurrencyEnum currentCode;

    public MoneyComponent(Money money) {
        super();
        Assert.isTrue(money != null, "Money must not be null");
        setLayout(new MigLayout("ins 0", "[grow]0[]"));
        valueField = initValueField();
        currencyCodeField = initCurrencyCodeField();
        setMoney(money);
        resetCurrentMoney();
        add(valueField, "grow");
        add(currencyCodeField);
    }

    private JComboBox<CurrencyEnum> initCurrencyCodeField() {
        JComboBox<CurrencyEnum> ccf = new JComboBox<>(CurrencyEnum.values());
        ccf.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                fireValueChanged();
            }
        });
        // TODO Phuc: This workaround for nimbus minimum width for Combobox
        ccf.setMinimumSize(new Dimension(ccf.getPreferredSize().width + 3, ccf.getMinimumSize().height));
        return ccf;
    }

    private JFormattedTextField initValueField() {
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Long.class);
        final JFormattedTextField vf = new JFormattedTextField(formatter);
        vf.setHorizontalAlignment(JFormattedTextField.RIGHT);
        vf.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                commitEdit();
            }

            @Override
            public void focusGained(FocusEvent e) {
                resetCurrentMoney();
            }

        });
        return vf;
    }

    private void resetCurrentMoney() {
        currentValue = getMoney().getValue();
        currentCode = getMoney().getCurrencyCode();
    }

    public Money getMoney() {
        return Money.create((CurrencyEnum) currencyCodeField.getSelectedItem(), (Long) valueField.getValue());
    }

    @Override
    public void setEnabled(boolean enabled) {
        valueField.setEnabled(enabled);
        currencyCodeField.setEnabled(enabled);
    }

    public void setEditable(boolean editable) {
        valueField.setEditable(editable);
        currencyCodeField.setEditable(editable);
    }

    public void setMoney(Money money) {
        if (money == null) {
            valueField.setValue(0L);
        } else {
            valueField.setValue(money.getValue());
        }
        currencyCodeField.setSelectedItem(money.getCurrencyCode());
        fireValueChanged();
    }

    private void fireValueChanged() {
        for (Object l : listenerList.getListenerList()) {
            ChangeEvent ce = new ChangeEvent(MoneyComponent.this);
            if (l instanceof ChangeListener) {
                ((ChangeListener) l).stateChanged(ce);
            }
        }
    }

    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    @Override
    public boolean requestFocusInWindow() {
        return valueField.requestFocusInWindow();
    }

    public void addDocumentListener(DocumentListener listener) {
        valueField.getDocument().addDocumentListener(listener);
    }

    public void addItemListener(ItemListener listener) {
        currencyCodeField.addItemListener(listener);
    }

    public void commitEdit() {
        try {
            valueField.commitEdit();
            if (!currentValue.equals(getMoney().getValue()) || !currentCode.equals(getMoney().getCurrencyCode())) {
                fireValueChanged();
                resetCurrentMoney();
            }
        } catch (ParseException e1) {
            UIManager.getLookAndFeel().provideErrorFeedback(valueField);
        }
    }
}
