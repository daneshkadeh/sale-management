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

import java.awt.FlowLayout;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.AdvanceTableModel;
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
        AbstractSingleEditView<T> implements TableModelListener {
    private static final long serialVersionUID = 5571051971772731048L;

    private final Log logger = LogFactory.getLog(AbstractMasterDetailView.class);

    private static final int NUM_OF_ROW = 10;
    private List<E> detailEntities;

    private ChildListView childListView;

    private Icon icon;
    private String label;
    private String tooltip;

    /**
     * 
     * @param request
     * @deprecated use the full param constructor instead, to set the icon, label, tooltip for the child list view.
     */
    public AbstractMasterDetailView(Map<String, Object> request) {
        this(request, null, null, null);
    }

    /**
     * 
     * @param request
     * @param icon
     *            icon of the child list view
     * @param label
     *            label of the child list view
     * @param tooltip
     *            tooltip of the child list view
     */
    public AbstractMasterDetailView(Map<String, Object> request, Icon icon, String label, String tooltip) {
        super(request);
        this.icon = icon;
        this.label = label;
        this.tooltip = tooltip;
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
     * Get the field name of child property, look from master entity.
     * 
     * @return
     */
    protected abstract String getChildFieldName();

    /**
     * Get the field name of parent property, look from child entity.
     * 
     * @return
     */
    protected abstract String getParentFieldName();

    @Override
    protected void initComponents() {

        super.initComponents();
        Map<String, Object> childParams = new HashMap<String, Object>();
        childParams.put(PARAM_PARENT_ID, entity.getId());
        childParams.put(PARAM_PARENT_CLASS, entity.getClass());
        childListView = new ChildListView(childParams, icon, label, tooltip);

        // Load list view immediately. This view not too large in MasterDetailView.
        childListView.loadView();
        childListView.addTableModelListener(this);
        add(childListView, "grow");
    }

    @Override
    protected T loadForEdit(List<String> eagerLoadedProperties) {
        eagerLoadedProperties.add(getChildFieldName());
        return super.loadForEdit(eagerLoadedProperties);
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
    private class ChildListView extends AListEntityView<E> {
        private static final long serialVersionUID = -8455234397691564647L;

        public ChildListView(Map<String, Object> params, Icon icon, String label, String tooltip) {
            super(params, icon, label, tooltip);
            pagingNavigator.setVisible(false); // Turn off the paging in the list view.
            btnRefresh.setVisible(false);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected List<E> loadData(int firstIndex, int maxResults) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
            // TODO Phuc: should be load from DB.
            detailEntities = new ArrayList<E>((Collection<E>) beanWrapper.getPropertyValue(getChildFieldName()));
            return detailEntities;
        };

        @Override
        public void notifyFromDetailView(E entity, boolean isNew) {
            super.notifyFromDetailView(entity, isNew);
            detailEntities.add(entity);
        };

        @Override
        protected Class<E> getGenericClass() {
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
        protected int getVisibleRowCount() {
            return AbstractMasterDetailView.this.getVisibleRowCount();
        }

        @Override
        protected int getPageSize() {
            return -1; // Turn of the paging on the child view.
        }

        @Override
        protected JPanel createFooterPanel(TableModel tableModel) {
            return AbstractMasterDetailView.this.createFooterPanel(tableModel);
        }

        @Override
        protected boolean preShowEditView(E entity, EditActionEnum action, Map<String, Object> detailParams) {
            return AbstractMasterDetailView.this.preShowEditView(entity, action, detailParams);
        }

        protected boolean defaultPreShowEditView(E entity, EditActionEnum action, Map<String, Object> detailParams) {
            return super.preShowEditView(entity, action, detailParams);
        }

        @Override
        protected E initEntity(E entity) {
            return super.initEntity(entity);
        }
    }

    /**
     * A delegation of {@link ChildListView#preShowEditView(AbstractIdOLObject, EditActionEnum, Map)}.
     * 
     * @param entity
     * @param action
     * @param detailParams
     * @return
     */
    protected boolean preShowEditView(E entity, EditActionEnum action, Map<String, Object> detailParams) {
        return childListView.defaultPreShowEditView(entity, action, detailParams);
    }

    /**
     * Override this to set number of rows visible on the child list.
     * 
     * @return
     */
    protected int getVisibleRowCount() {
        return NUM_OF_ROW;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        AdvanceTableModel<E> model = (AdvanceTableModel<E>) e.getSource();
        if (e.getType() == TableModelEvent.INSERT) {
            E ent = model.getEntity(e.getFirstRow()); // Put inside the if statement to avoid an empty list case.
            detailEntities.add(e.getFirstRow(), ent);
        } else if (e.getType() == TableModelEvent.DELETE) {
            detailEntities.remove(e.getFirstRow());
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
        return new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Just create a panel with no size
    }

    /**
     * Override <code>saveOrUpdate(T masterEntity, List<E> detailEntities)</code> if necessary
     */
    @Override
    protected void saveOrUpdate(T masterEntity) {
        BeanWrapper beanWrapper;
        // Set master into child entity
        for (E child : detailEntities) {
            beanWrapper = new BeanWrapperImpl(child);
            beanWrapper.setPropertyValue(getParentFieldName(), masterEntity);
        }

        // Set list of child to master entity
        beanWrapper = new BeanWrapperImpl(masterEntity);
        Collection<E> children = (Collection<E>) beanWrapper.getPropertyValue(getChildFieldName());
        children.removeAll(children);
        children.addAll(detailEntities);

        preSaveOrUpdate(masterEntity);

        // Saving...
        getDaoHelper().getDao(getMasterClass()).saveOrUpdate(masterEntity);
    };

    /**
     * Override to do something before saveOrUpdate.
     */
    protected void preSaveOrUpdate(T masterEntity) {
        // Template method
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
}
