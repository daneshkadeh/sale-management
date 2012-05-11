/*
 * HourMinuteSecondComponent
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

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Phan Hong Phuc
 * @since May 11, 2012
 */
public class HourMinuteSecondComponent extends JPanel implements ChangeListener {
    private static final long serialVersionUID = 3938641713515938912L;

    private static final int HOUR_MAX = 999;
    private static final int MINUTE_MAX = 60;
    private static final int SECOND_MAX = 60;

    private JSpinner hourSpn;
    private JSpinner minSpn;
    private JSpinner secSpn;

    public HourMinuteSecondComponent() {
        FlowLayout fl = new FlowLayout(0, 0, 0);
        setLayout(fl);

        SpinnerNumberModel hourModel = new CycleSpinnerNumberModel(0, 0, HOUR_MAX, 1);
        hourSpn = new JSpinner(hourModel);
        // hourSpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));
        SpinnerNumberModel minModel = new CycleSpinnerNumberModel(0, 0, MINUTE_MAX, 1);
        minSpn = new JSpinner(minModel);
        // minSpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));
        SpinnerNumberModel secModel = new CycleSpinnerNumberModel(0, 0, SECOND_MAX, 1);
        secSpn = new JSpinner(secModel);

        minSpn.addChangeListener(this);
        hourSpn.addChangeListener(this);
        secSpn.addChangeListener(this);

        add(hourSpn);
        add(new JLabel(ControlConfigUtils.getString("TimeComponent.hour")));
        add(minSpn);
        add(new JLabel(ControlConfigUtils.getString("TimeComponent.minute")));
        add(secSpn);
        add(new JLabel(ControlConfigUtils.getString("TimeComponent.second")));
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner source = (JSpinner) e.getSource();
        if (source == secSpn && ((int) source.getValue() == SECOND_MAX)) {
            secSpn.setValue(0);
            minSpn.setValue(minSpn.getNextValue());
        }
        if (source == minSpn && ((int) source.getValue() == MINUTE_MAX)) {
            minSpn.setValue(0);
            hourSpn.setValue(hourSpn.getNextValue());
        }
    }

    public class CycleSpinnerNumberModel extends SpinnerNumberModel {
        private static final long serialVersionUID = -8398387199137895712L;

        public CycleSpinnerNumberModel(int value, int minimum, int maximum, int stepSize) {
            super(value, minimum, maximum, stepSize);
        }

        @Override
        public Object getNextValue() {
            Object value = super.getNextValue();
            if (value == null) {
                value = getMinimum();
            }
            return value;

        }

        @Override
        public Object getPreviousValue() {
            Object value = super.getPreviousValue();
            if (value == null) {
                value = (Integer) getMaximum() - 1;
            }
            return value;
        }
    }

    public void setValueBySecond(long second) {
        long secVal = second % SECOND_MAX;
        secSpn.setValue((int) secVal);
        setValueByHour(second / SECOND_MAX);
    }

    public void setValueByMinute(long minute) {
        long minVal = minute % MINUTE_MAX;
        minSpn.setValue((int) minVal);
        setValueByHour(minute / MINUTE_MAX);
    }

    public void setValueByHour(long hour) {
        long hVal = hour % HOUR_MAX;
        hourSpn.setValue((int) hVal);
    }

    /**
     * Get time as mili-seconds.
     * 
     * @return
     */
    public long getValue() {
        return ((Integer) secSpn.getValue()) * 1000L * ((Integer) minSpn.getValue()) * 60 * 1000L
                + ((Integer) hourSpn.getValue()) * 60 * 60 * 1000L;
    }

    /**
     * Set the time by miliseconds
     * 
     * @param milisecond
     */
    public void setValue(long milisecond) {
        setValueBySecond(milisecond / 1000);
    }

    public void addChangeListener(ChangeListener listener) {
        hourSpn.addChangeListener(listener);
        minSpn.addChangeListener(listener);
        secSpn.addChangeListener(listener);
    }

    public void setEditable(boolean editable) {
        ((DefaultEditor) hourSpn.getEditor()).getTextField().setEditable(editable);
        ((DefaultEditor) minSpn.getEditor()).getTextField().setEditable(editable);
        ((DefaultEditor) secSpn.getEditor()).getTextField().setEditable(editable);
    }
}
