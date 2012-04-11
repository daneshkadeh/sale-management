/*
 * AbstractMasterDetailView
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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;

/**
 * This view is used for a master entity and its detail entities.</br> Example: An invoice and list detail invoice. User
 * can input directly invoice and detail invoice information on this screen. </br> Defect: still not bindAndValidate
 * detail entity correctly, master entity is still null before bindAndValidate.
 * 
 * @author phamcongbang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 *            parent entity type
 * @param <E>
 *            child entity type
 */
public abstract class AbstractMasterDetailView<T extends AbstractIdOLObject, E extends AbstractIdOLObject> extends
        AbstractSingleEditView<T> {
    private static final long serialVersionUID = 5571051971772731048L;

    private final Log logger = LogFactory.getLog(AbstractMasterDetailView.class);

    private List<E> detailEntities = new ArrayList<E>();

    private ChildListView childListView;

    /**
     * The default constructor.
     * 
     * @param entity
     */
    public AbstractMasterDetailView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * Init presentation of list detail objects.
     */
    protected abstract void initialListDetailPresentationView(ListDataModel listDataModel);

    /**
     * Get detail view class of child entity.
     * 
     * @return
     */
    protected abstract Class<? extends AbstractEditView<E>> getChildDetailViewClass();

    /**
     * Get the field name of child property.
     * 
     * @return
     */
    protected abstract String getChildFieldName();

    @Override
    protected void initComponents() {

        super.initComponents();
        Map<String, Object> childParams = new HashMap<String, Object>();
        childParams.put(PARAM_PARENT_ID, entity.getId());
        childParams.put(PARAM_PARENT_CLASS, entity.getClass());
        childListView = new ChildListView(childParams);

        // Load list view immediately. This view not too large in MasterDetailView.
        childListView.loadView();
        add(childListView, "grow");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, T entity) {
        super.customizeComponents(name2AttributeComponent, entity);
    }

    /**
     * TODO: The ChildListView must be have smaller size than normal list view (about 7 rows) so that it does not cover
     * the screen.
     * 
     */
    private class ChildListView extends AbstractListView<E> {
        private static final long serialVersionUID = -8455234397691564647L;
        private static final int NUM_OF_ROW = 10;

        public ChildListView(Map<String, Object> params) {
            super(params);
        }

        @Override
        protected List<E> loadData(int pageNumber) {
            Method getChildListMethod = Solution3sClassUtils.getGetterMethod(getMasterClass(), getChildFieldName());
            try {
                List<E> copyChildEntitiesList = new ArrayList<E>((Collection<E>) getChildListMethod.invoke(entity));
                return copyChildEntitiesList;
            } catch (Exception e) {
                logger.error(e.getCause());
                logger.error(e.getMessage());
                throw new RuntimeException("There is problem when loadData for child entities");
            }
        };

        @Override
        public void notifyFromDetailView(E entity, boolean isNew) {
            super.notifyFromDetailView(entity, isNew);
            detailEntities.add(entity);
        };

        @Override
        protected Class<E> getEntityClass() {
            return getDetailClass();
        }

        @Override
        protected Class<? extends AbstractEditView<E>> getEditViewClass() {
            return getChildDetailViewClass();
        }

        @Override
        protected void initialPresentationView(ListDataModel listDataModel) {
            initialListDetailPresentationView(listDataModel);
        }

        @Override
        protected ReferenceDataModel initReferenceDataModel() {
            return refDataModel;
        }

        @Override
        protected int getPageSize() {
            return NUM_OF_ROW;
        }

        @Override
        public void tableChanged(TableModelEvent e) {
            super.tableChanged(e);
            AbstractMasterDetailView.this.tableChanged(e);
        }

        @Override
        protected JPanel createFooterPanel(TableModel tableModel) {
            return AbstractMasterDetailView.this.createFooterPanel(tableModel);
        }
    }

    /**
     * Override this method to create footer panel for the list view.
     * 
     * @param tableModel
     * @return
     */
    protected JPanel createFooterPanel(TableModel tableModel) {
        // Template method
        return new JPanel();
    }

    /**
     * The child class override this method to perform something when the main table data changed.
     * 
     * @param e
     */
    protected void tableChanged(TableModelEvent e) {
        // Template method
    }

    /**
     * Update detailEntities with master injected.
     */
    @Override
    protected void saveOrUpdate(T masterEntity) {
        saveOrUpdate(masterEntity, detailEntities);
    };

    protected void saveOrUpdate(T masterEntity, List<E> detailEntities) {
        getDaoHelper().getDao(getMasterClass()).saveOrUpdate(masterEntity);
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getMasterClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) controllerType;
    }

    @SuppressWarnings("unchecked")
    protected Class<E> getDetailClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return (Class<E>) controllerType;
    }

    public void setDetailEntities(List<E> detailEntities) {
        this.detailEntities = detailEntities;
    }

    public List<E> getDetailEntities() {
        return detailEntities;
    }
}
