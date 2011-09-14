package com.hbsoft.ssm.model;

import java.util.List;

public class ReferenceDataList {
    private List<?> dataList;

    public ReferenceDataList(List<?> dataList) {
        this.dataList = dataList;

        // TODO: this not work?
        // Type parameterizedType = ((ParameterizedType)
        // this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // this.clazz = (Class<T>) parameterizedType;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public List<?> getDataList() {
        return dataList;
    }
}
