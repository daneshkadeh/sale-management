package com.hbsoft.ssm.model;

import java.util.List;

public class ReferenceDataList<T> {
    private List<T> dataList;

    /*
     * This property does not work
     */
    private Class<T> clazz;

    public ReferenceDataList(List<T> dataList) {
        this.dataList = dataList;

        // TODO: this not work?
        // Type parameterizedType = ((ParameterizedType)
        // this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // this.clazz = (Class<T>) parameterizedType;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
