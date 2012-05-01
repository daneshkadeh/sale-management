/*
 * AbstractListDataView
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

package com.s3s.ssm.view.list;

import java.util.List;

import com.s3s.ssm.entity.AbstractBaseIdObject;

/**
 * TODO Phuc Just work-around list of DTO. It should be the parent of {@link AListEntityView}
 * 
 * @author Phan Hong Phuc
 * @since May 1, 2012
 */
public abstract class AListDataView<T extends AbstractBaseIdObject> extends AListView<T> {
    private static final long serialVersionUID = -7995535532858732146L;

    @Override
    protected abstract List<T> loadData(int firstIndex, int maxResults);

    @Override
    protected boolean isShowActivateButton() {
        return false;
    }

    @Override
    protected boolean isShowNewButton() {
        return false;
    }

    @Override
    protected boolean isShowDeleteButton() {
        return false;
    }

    @Override
    protected boolean isShowEditButton() {
        return false;
    }

    @Override
    protected boolean isShowExportButton() {
        return false;
    }

    @Override
    protected boolean isShowPrintButton() {
        return false;
    }

}
