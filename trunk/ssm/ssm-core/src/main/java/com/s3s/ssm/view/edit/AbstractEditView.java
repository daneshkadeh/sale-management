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

package com.s3s.ssm.view.edit;

import java.awt.Dimension;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.WindowUtilities;
import com.s3s.ssm.view.AbstractView;
import com.s3s.ssm.view.list.AbstractListView;

/**
 * @author Phan Hong Phuc
 * 
 */
public abstract class AbstractEditView<T extends AbstractBaseIdObject> extends AbstractView {
    public static final String NEW_TITLE = ControlConfigUtils.getString("label.tab.new");

    private static final long serialVersionUID = 5467303241585854634L;

    protected T entity;

    public AbstractEditView(Map<String, Object> inputParams) {
        super(inputParams);
        EditActionEnum action = (EditActionEnum) request.get(PARAM_ACTION);
        if (action == EditActionEnum.NEW) {
            entity = loadForCreate();
        } else if (action == EditActionEnum.EDIT) {
            entity = loadForEdit(new ArrayList<String>());
        } else {
            throw new UnsupportedOperationException("This operation is not handled : " + action);
        }

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
     * @param eagerLoadedProperties
     *            the name of properties need to load with the entity but they are mark as LAZY (cause the lazy load
     *            exception).
     */
    protected T loadForEdit(List<String> eagerLoadedProperties) {
        DetachedCriteria dc = getDaoHelper().getDao(getEntityClass()).getCriteria();
        dc.add(Restrictions.eq("id", (Long) request.get(PARAM_ENTITY_ID)));
        for (String path : eagerLoadedProperties) {
            dc.setFetchMode(path, FetchMode.JOIN);
        }
        return getDaoHelper().getDao(getEntityClass()).findByCriteria(dc).get(0);
    }

    public final String getTitle() {
        return entity.getId() == null ? NEW_TITLE : getDefaultTitle(entity);
    }

    protected String getDefaultTitle(T entity) {
        return entity.getId().toString();
    }

    public boolean focus() {
        return requestFocusInWindow();
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

    public T getEntity() {
        return entity;
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
