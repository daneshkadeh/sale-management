/*
 * AbstractEditView
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

package com.s3s.ssm.view;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.WindowUtilities;

/**
 * @author Phan Hong Phuc
 * 
 */
public abstract class AbstractEditView<T extends AbstractIdOLObject> extends AbstractView {
    private static final long serialVersionUID = 5467303241585854634L;
    protected AbstractListView<T> listView;
    protected Class<? extends AbstractBaseIdObject> parentClass;
    protected Long parentId;
    protected T entity;

    protected JToolBar toolbar;
    private JButton btnSave;
    // private JButton btnSaveClose;
    private JButton btnSaveNew;
    private JButton btnNew;
    private JButton btnExit;

    public AbstractEditView(Map<String, Object> inputParams) {
        super(inputParams);
        String action = (String) params.get("action");
        if ("new".equals(action)) {
            this.entity = loadForCreate();
        } else if ("edit".equals(action)) {
            this.entity = loadForEdit();
        } else {
            throw new UnsupportedOperationException("This operation is not handled : " + action);
        }

        this.parentId = (Long) this.params.get("parentId");
        this.parentClass = (Class) this.params.get("parentClass");
        toolbar = createToolBar();
    }

    /**
     * Append default attributes for entity.
     * 
     * @param entity
     */
    protected T loadForCreate() {
        try {
            return getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("There is a problem when init the entity");
        }

    }

    /**
     * Append missing attributes for entity.
     * 
     * @param entity
     */
    protected T loadForEdit() {
        return getDaoHelper().getDao(getEntityClass()).findById((Long) this.params.get("entityId"));
    }

    public void setParent(Long parentId, Class<? extends AbstractIdOLObject> parentClass) {
        this.parentId = parentId;
        this.parentClass = parentClass;
    }

    public Long getParentId() {
        return parentId;
    }

    public Class<? extends AbstractBaseIdObject> getParentClass() {
        return parentClass;
    }

    public void setListView(AbstractListView<T> listView) {
        this.listView = listView;
    }

    protected JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        toolbar.setFloatable(false);
        btnSave = new JButton(ImageUtils.getImageIcon(ImageConstants.SAVE_ICON));
        btnSave.setToolTipText(ControlConfigUtils.getString("default.button.save"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doSave();
            }
        });

        // btnSaveClose = new JButton("Luu va dong");
        // btnSaveClose.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // if (doSave()) {
        // doClose();
        // }
        // }
        // });

        btnSaveNew = new JButton(ImageUtils.getImageIcon(ImageConstants.SAVE_NEW_ICON));
        btnSaveNew.setToolTipText(ControlConfigUtils.getString("edit.button.saveNew"));
        btnSaveNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doSave()) {
                    doNew();
                }
            }
        });

        btnNew = new JButton(ImageUtils.getImageIcon(ImageConstants.NEW_ICON));
        btnNew.setToolTipText(ControlConfigUtils.getString("edit.button.new"));
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doNew();
            }
        });

        btnExit = new JButton(ImageUtils.getImageIcon(ImageConstants.EXIT_ICON));
        btnExit.setToolTipText(ControlConfigUtils.getString("edit.button.exit"));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doClose();
            }
        });

        JButton btnFullScreen = new JButton(ImageUtils.getImageIcon(ImageConstants.FULLSCREEN_ICON));
        btnFullScreen.setToolTipText(ControlConfigUtils.getString("edit.button.fullscreen"));
        btnFullScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSizeParentWindoẉ̣(WindowUtilities.getFullScreenSize());
            }
        });

        JButton btnMinimize = new JButton(ImageUtils.getImageIcon(ImageConstants.MINIMIZE_ICON));
        btnMinimize.setToolTipText(ControlConfigUtils.getString("edit.button.minimize"));
        btnMinimize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSizeParentWindoẉ̣(getFitSize());
            }
        });

        toolbar.add(btnNew);
        toolbar.add(btnSave);
        toolbar.add(btnSaveNew);
        // toolbar.add(btnSaveClose);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(btnMinimize);
        toolbar.add(btnFullScreen);
        toolbar.add(btnExit);
        return toolbar;
    }

    /**
     * Check dirty before close or create new entity.
     * 
     * @param isNew
     *            <code>true</code> if create new entity, <code>false</code> close the current entity.
     */
    protected abstract void doNew();

    /**
     * @return true if saving success.
     */
    protected abstract boolean doSave();

    protected abstract void doClose();

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return (Class<T>) Solution3sClassUtils.getArgumentClass(getClass());
    }

    protected void setSizeParentWindoẉ̣(Dimension size) {
        Window window = (Window) SwingUtilities.getRoot(this);
        window.setSize(size);
        WindowUtilities.centerOnScreen(window);
    }

    public void setVisibleToolbar(boolean visible) {
        toolbar.setVisible(visible);
    }

    /**
     * Get the size of the dialog fit with the edit view. Maximum size is equal with the fullscreen size.
     * 
     * @param detailViewSize
     * @return
     */
    public Dimension getFitSize() {
        Dimension detailViewSize = getPreferredSize();
        int w = detailViewSize.width + 25;
        int h = detailViewSize.height + 45;
        Dimension fullSize = WindowUtilities.getFullScreenSize();
        if (w > fullSize.width) {
            w = fullSize.width;
        }
        if (h > fullSize.height) {
            h = fullSize.height;
        }
        return new Dimension(w, h);
    }
}
