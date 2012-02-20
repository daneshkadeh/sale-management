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
    protected AbstractListView<T> listView;
    protected Class<? extends AbstractBaseIdObject> parentClass;
    protected Long parentId;
    protected T entity;

    public AbstractEditView(T entity, Long parentId, Class<? extends AbstractIdOLObject> parentClass) {
        super();
        if (entity == null) {
            try {
                entity = getEntityClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("There is a problem when init the entity");
            }
        }
        this.entity = entity;
        this.parentId = parentId;
        this.parentClass = parentClass;
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

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return (Class<T>) Solution3sClassUtils.getArgumentClass(getClass());
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
