package com.hbsoft.ssm.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represent the model on the AbstractDetailView for reference data. Eg. ComboBox, MultiSelectBox
 * 
 * @author phamcongbang
 * 
 */
public class ReferenceDataModel {
    // private List<ReferenceDataList> refDataList = new ArrayList<ReferenceDataList>();
    private Map<String, ReferenceDataList> refDataListMap = new HashMap<String, ReferenceDataList>();

    //
    // public void setRefDataList(List<ReferenceDataList> refDataList) {
    // this.refDataList = refDataList;
    // }
    //
    // public List<ReferenceDataList> getRefDataList() {
    // return refDataList;
    // }

    public void putRefDataList(String refId, ReferenceDataList refDataList) {
        refDataListMap.put(refId, refDataList);
    }

    public void setRefDataListMap(Map<String, ReferenceDataList> refDataListMap) {
        this.refDataListMap = refDataListMap;
    }

    public Map<String, ReferenceDataList> getRefDataListMap() {
        return refDataListMap;
    }
}
