/*
 * ATimeComponent
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
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Phan Hong Phuc
 * @since Apr 27, 2012
 */
public class TimeComponent extends JPanel implements ChangeListener {
    private static final long serialVersionUID = 3938641713515938912L;
    // private static final int YEAR_MONTH_DAY = 1;
    // private static final int HOUR_DAY = 2;

    private static final int YEAR_MAX = 99;
    private static final int MONTH_MAX = 12;
    private static final int DAY_MAX = 30;
    private static final int HOUR_MAX = 24;
    private static final int MINUTE_MAX = 60;
    private JSpinner yearSpn;
    private JSpinner monthSpn;
    private JSpinner daySpn;
    private JSpinner hourSpn;
    private JSpinner minSpn;

    public TimeComponent() {
        FlowLayout fl = new FlowLayout(0, 0, 0);
        setLayout(fl);

        SpinnerNumberModel yearModel = new CycleSpinnerNumberModel(0, 0, YEAR_MAX, 1);
        yearSpn = new JSpinner(yearModel);
        yearSpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));
        SpinnerNumberModel monthModel = new CycleSpinnerNumberModel(0, 0, MONTH_MAX, 1);
        monthSpn = new JSpinner(monthModel);
        monthSpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));
        SpinnerNumberModel dayModel = new CycleSpinnerNumberModel(0, 0, DAY_MAX, 1);
        daySpn = new JSpinner(dayModel);
        daySpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));
        SpinnerNumberModel hourModel = new CycleSpinnerNumberModel(0, 0, HOUR_MAX, 1);
        hourSpn = new JSpinner(hourModel);
        hourSpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));
        SpinnerNumberModel minModel = new CycleSpinnerNumberModel(0, 0, MINUTE_MAX, 1);
        minSpn = new JSpinner(minModel);
        minSpn.setPreferredSize(new Dimension(46, yearSpn.getPreferredSize().height));

        minSpn.addChangeListener(this);
        hourSpn.addChangeListener(this);
        daySpn.addChangeListener(this);
        monthSpn.addChangeListener(this);
        yearSpn.addChangeListener(this);

        add(yearSpn);
        add(new JLabel(ControlConfigUtils.getString("TimeComponent.year")));
        add(monthSpn);
        add(new JLabel(ControlConfigUtils.getString("TimeComponent.month")));
        add(daySpn);
        add(new JLabel(ControlConfigUtils.getString("TimeComponent.day")));
        add(hourSpn);
        add(new JLabel(ControlConfigUtils.getString("TimeComponent.hour")));
        add(minSpn);
        add(new JLabel(ControlConfigUtils.getString("TimeComponent.minute")));
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner source = (JSpinner) e.getSource();
        if (source == minSpn && ((int) source.getValue() == MINUTE_MAX)) {
            minSpn.setValue(0);
            hourSpn.setValue(hourSpn.getNextValue());
        }
        if (source == hourSpn && ((int) source.getValue() == HOUR_MAX)) {
            hourSpn.setValue(0);
            daySpn.setValue(daySpn.getNextValue());
        }
        if (source == daySpn && ((int) source.getValue() == DAY_MAX)) {
            daySpn.setValue(0);
            monthSpn.setValue(monthSpn.getNextValue());
        }
        if (source == monthSpn && ((int) source.getValue() == MONTH_MAX)) {
            monthSpn.setValue(0);
            yearSpn.setValue(yearSpn.getNextValue());
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

    public void setValueByMinute(long minute) {
        long minVal = minute % MINUTE_MAX;
        minSpn.setValue((int) minVal);
        setValueByHour(minute / MINUTE_MAX);
    }

    public void setValueByHour(long hour) {
        long hVal = hour % HOUR_MAX;
        hourSpn.setValue((int) hVal);
        setValueByDay(hour / HOUR_MAX);
    }

    public void setValueByDay(long day) {
        long dVal = day % DAY_MAX;
        daySpn.setValue((int) dVal);
        setValueByMonth(day / DAY_MAX);

    }

    public void setValueByMonth(long month) {
        long monVal = month % MONTH_MAX;
        monthSpn.setValue((int) monVal);
        setValueByYear(month / MONTH_MAX);

    }

    public void setValueByYear(long year) {
        yearSpn.setValue((int) year);
    }

    /**
     * Get time as mili-seconds.
     * 
     * @return
     */
    public long getValue() {
        return ((Integer) minSpn.getValue()) * 60 * 1000L + ((Integer) hourSpn.getValue()) * 60 * 60 * 1000L
                + ((Integer) daySpn.getValue()) * 24 * 60 * 60 * 1000L + ((Integer) monthSpn.getValue()) * 30 * 24 * 60
                * 60 * 1000L + ((Integer) yearSpn.getValue()) * 12 * 30 * 24 * 60 * 60 * 1000L;
    }

    /**
     * Set the time by miliseconds
     * 
     * @param milisecond
     */
    public void setValue(long milisecond) {
        setValueByMinute(milisecond / 1000 / 60);
    }

    public void addChangeListener(ChangeListener listener) {
        yearSpn.addChangeListener(listener);
        monthSpn.addChangeListener(listener);
        daySpn.addChangeListener(listener);
        hourSpn.addChangeListener(listener);
        minSpn.addChangeListener(listener);
    }
}