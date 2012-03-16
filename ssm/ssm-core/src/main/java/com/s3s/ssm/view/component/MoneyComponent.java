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

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
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

    public MoneyComponent(Money money, List<String> currencyCodes) {
        super();
        Assert.isTrue(money != null, "Money must not be ");
        setLayout(new MigLayout("ins 0", "[grow]0[]"));
        valueField = initValueField();
        currencyCodeField = initCurrencyCodeField(currencyCodes);
        setMoney(money);
        add(valueField, "grow");
        add(currencyCodeField);
    }

    private JComboBox<String> initCurrencyCodeField(List<String> currencyCodes) {
        return new JComboBox<>(currencyCodes.toArray(new String[currencyCodes.size()]));
    }

    private JFormattedTextField initValueField() {
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Long.class);
        JFormattedTextField vf = new JFormattedTextField(formatter);
        vf.setHorizontalAlignment(JFormattedTextField.RIGHT);
        return vf;
    }

    public Money getMoney() {
        return Money.create((String) currencyCodeField.getSelectedItem(), (Long) valueField.getValue());
    }

    public void setMoney(Money money) {
        valueField.setValue(money.getValue());
        currencyCodeField.setSelectedItem(money.getCurrencyCode());
    }

}
