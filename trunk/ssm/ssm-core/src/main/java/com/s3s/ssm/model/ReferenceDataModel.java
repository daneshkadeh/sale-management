package com.s3s.ssm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent the model on the AbstractDetailView for reference data. Eg. ComboBox, MultiSelectBox
 * 
 * @author phamcongbang
 * 
 */
public class ReferenceDataModel {
    private Map<String, List<Object>> refDataListMap = new HashMap<String, List<Object>>();

    public void putRefDataList(String refId, List<?> refDataList) {
        refDataListMap.put(refId, new ArrayList<Object>(refDataList));
    }

    // public void setRefDataListMap(Map<String, List<?>> refDataListMap) {
    // this.refDataListMap = refDataListMap;
    // }

    public Map<String, List<Object>> getRefDataListMap() {
        return refDataListMap;
    }
}
