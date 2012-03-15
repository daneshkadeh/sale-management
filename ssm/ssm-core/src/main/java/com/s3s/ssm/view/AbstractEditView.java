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
import java.util.Map;

import javax.swing.SwingUtilities;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.view.WindowUtilities;

/**
 * @author Phan Hong Phuc
 * 
 */
public abstract class AbstractEditView<T extends AbstractIdOLObject> extends AbstractView {

    private static final long serialVersionUID = 5467303241585854634L;
    protected Class<? extends AbstractBaseIdObject> parentClass;
    protected Long parentId;
    protected T entity;

    public AbstractEditView(Map<String, Object> inputParams) {
        super(inputParams);
        EditActionEnum action = (EditActionEnum) request.get(PARAM_ACTION);
        if (action == EditActionEnum.NEW) {
            entity = loadForCreate();
        } else if (action == EditActionEnum.EDIT) {
            entity = loadForEdit();
        } else {
            throw new UnsupportedOperationException("This operation is not handled : " + action);
        }

        parentId = (Long) request.get(PARAM_PARENT_ID);
        parentClass = (Class) request.get(PARAM_PARENT_CLASS);
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
        return getDaoHelper().getDao(getEntityClass()).findById((Long) request.get(PARAM_ENTITY_ID));
    }

    public void setParent(Long parentId, Class<? extends AbstractIdOLObject> parentClass) {
        this.parentId = parentId;
        this.parentClass = parentClass;
    }

    public Object getParentObject() {
        return getDaoHelper().getDao(getParentClass()).findById(getParentId());
    }

    public Long getParentId() {
        return parentId;
    }

    public Class<? extends AbstractBaseIdObject> getParentClass() {
        return parentClass;
    }

    public void setListView(AbstractListView<T> listView) {
        this.request.put(PARAM_LIST_VIEW, listView);
    }

    public AbstractListView<T> getListView() {
        return (AbstractListView<T>) request.get(PARAM_LIST_VIEW);
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return (Class<T>) Solution3sClassUtils.getArgumentClass(getClass());
    }

    protected void setSizeParentWindoẉ̣(Dimension size) {
        Window window = (Window) SwingUtilities.getRoot(this);
        window.setSize(size);
        WindowUtilities.centerOnScreen(window);
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
