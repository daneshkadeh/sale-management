package com.hbsoft.ssm.model;

import java.util.HashMap;
import java.util.Map;

public class ReferenceDataList {
    private Map<String, String> idLabelMap = new HashMap<String, String>();

    public void setIdLabelMap(Map<String, String> idLabelMap) {
        this.idLabelMap = idLabelMap;
    }

    public Map<String, String> getIdLabelMap() {
        return idLabelMap;
    }
}
