/*
 * SaveEvent
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

import java.util.EventObject;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * 
 * @author Phan Hong Phuc
 * @since Feb 22, 2012
 */
public class SavedEvent<T extends AbstractIdOLObject> extends EventObject {
    private static final long serialVersionUID = -3306843792346320138L;

    private T entity;
    
    /**
     * @param source
     */
    public SavedEvent(Object source, T entity) {
        super(source);
        this.entity = entity;
    }
    
    /**
     * Get the entity saved.
     * @return the entity which is saved.
     */
    public T getEntity(){
        return entity;
    }

}
