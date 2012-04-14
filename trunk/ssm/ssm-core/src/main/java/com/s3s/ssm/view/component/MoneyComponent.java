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
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.text.NumberFormatter;

import net.miginfocom.swing.MigLayout;

import org.springframework.util.Assert;

import com.s3s.ssm.model.Money;

/**
 * @author Phan Hong Phuc
 * @since Mar 16, 2012
 */
public class MoneyComponent extends JPanel {
    private static final long serialVersionUID = -7556079748704045405L;

    private JFormattedTextField valueField;
    private JComboBox<String> currencyCodeField;
    private Long currentValue;
    private String currentCode;

    public MoneyComponent(Money money, List<String> currencyCodes) {
        super();
        Assert.isTrue(money != null, "Money must not be null");
        setLayout(new MigLayout("ins 0", "[grow]0[]"));
        valueField = initValueField();
        currencyCodeField = initCurrencyCodeField(currencyCodes);
        setMoney(money);
        add(valueField, "grow");
        add(currencyCodeField);
    }

    private JComboBox<String> initCurrencyCodeField(List<String> currencyCodes) {
        JComboBox<String> ccf = new JComboBox<>(currencyCodes.toArray(new String[currencyCodes.size()]));
        ccf.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                fireMoneyChanged();
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
        return Money.create((String) currencyCodeField.getSelectedItem(), (Long) valueField.getValue());
    }

    @Override
    public void setEnabled(boolean enabled) {
        valueField.setEnabled(enabled);
        currencyCodeField.setEnabled(enabled);
    }

    public void setMoney(Money money) {
        valueField.setValue(money.getValue());
        currencyCodeField.setSelectedItem(money.getCurrencyCode());
        fireMoneyChanged();
    }

    private void fireMoneyChanged() {
        for (Object l : listenerList.getListenerList()) {
            ChangeEvent ce = new ChangeEvent(MoneyComponent.this);
            if (l instanceof IMoneyChangedListener) {
                ((IMoneyChangedListener) l).doMoneyChanged(ce);
            }
        }
    }

    public void addMoneyChangeListener(IMoneyChangedListener listener) {
        listenerList.add(IMoneyChangedListener.class, listener);
    }

    public void removeMoneyChangeListener(IMoneyChangedListener listener) {
        listenerList.remove(IMoneyChangedListener.class, listener);
    }

    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    public boolean requestFocusInWindow() {
        return valueField.requestFocusInWindow();
    }

    public void commitEdit() {
        try {
            valueField.commitEdit();
            if (!currentValue.equals(getMoney().getValue()) || !currentCode.equals(getMoney().getCurrencyCode())) {
                fireMoneyChanged();
                resetCurrentMoney();
            }
        } catch (ParseException e1) {
            UIManager.getLookAndFeel().provideErrorFeedback(valueField);
        }
    }
}
