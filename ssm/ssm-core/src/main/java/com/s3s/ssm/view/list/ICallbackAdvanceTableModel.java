package com.s3s.ssm.view.list;

/**
 * Call back function on AdvanceTableModel
 * 
 * @author phamcongbang
 * 
 * @param <T>
 */
public interface ICallbackAdvanceTableModel<T> {
    public Object getAttributeValueCallback(T entity, ColumnModel dataModel);

    public void setAttributeValueCallback(T entity, ColumnModel dataModel, Object aValue);
}
