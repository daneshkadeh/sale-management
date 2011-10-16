package com.hbsoft.ssm.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.hbsoft.ssm.entity.AbstractBaseIdObject;

/**
 * The list view with the search panel.
 * 
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractSearchListView<T extends AbstractBaseIdObject> extends AbstractListView<T> {
    private static final long serialVersionUID = -2256837250215615557L;

    protected abstract JPanel createSearchPanel();

    /**
     * Clear the criteria in search panel.
     */
    protected abstract void clearCriteria();

    protected JPanel createSearchButtonsPanel() {
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clearCriteria();
            }
        });

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(btnSearch);
        panel.add(btnClear);
        return panel;
    }

    @Override
    protected void addComponents() {
        add(createSearchPanel());
        add(createSearchButtonsPanel());
        super.addComponents();
    }

}
