package com.s3s.ssm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ListCellRenderer;

/**
 * This class represent the model on the AbstractDetailView for reference data. Eg. ComboBox, MultiSelectBox
 * 
 * @author phamcongbang
 * 
 */
public class ReferenceDataModel {
    private final Map<String, ReferenceData> refDataListMap = new HashMap<String, ReferenceDataModel.ReferenceData>();

    public void putRefDataList(String refId, ReferenceData refData) {
        refDataListMap.put(refId, refData);
    }

    public Map<String, ReferenceData> getRefDataListMap() {
        return refDataListMap;
    }

    /**
     * The reference data include the list of values and the renderer for those values.<br/>
     * 
     * @author Phan Hong Phuc
     * 
     */
    public class ReferenceData {
        private List<?> refDataList = new ArrayList<>();
        private ListCellRenderer<Object> listCellRenderer;

        /**
         * Init reference data with a list of values. Renderer for the values is {@link DefaultListCellRenderer}.
         * 
         * @param refDataList
         *            list of values
         */
        public ReferenceData(List<?> refDataList) {
            super();
            this.setRefDataList(refDataList);
        }

        /**
         * Init reference data with a list of values. Renderer for the values is <code>renderer</code>.
         * 
         * @param refDataList
         *            list of values
         * @param renderer
         *            the renderer
         */
        public ReferenceData(List<?> refDataList, ListCellRenderer<Object> renderer) {
            this.setRefDataList(refDataList);
            this.setListCellRenderer(renderer);
        }

        public ListCellRenderer<Object> getListCellRenderer() {
            return listCellRenderer;
        }

        public void setListCellRenderer(ListCellRenderer<Object> listCellRenderer) {
            this.listCellRenderer = listCellRenderer;
        }

        public List<?> getRefDataList() {
            return refDataList;
        }

        public void setRefDataList(List<?> refDataList) {
            this.refDataList = refDataList;
        }
    }
}
