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
import java.lang.reflect.Method;
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
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.view.list.AbstractListView;
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

    private List<E> detailEntities = new ArrayList<E>();

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
    private class ChildListView extends AbstractListView<E> {
        private static final long serialVersionUID = -8455234397691564647L;
        private static final int NUM_OF_ROW = 10;

        public ChildListView(Map<String, Object> params, Icon icon, String label, String tooltip) {
            super(params, icon, label, tooltip);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected List<E> loadData(int pageNumber) {
            Method getChildListMethod = Solution3sClassUtils.getGetterMethod(getMasterClass(), getChildFieldName());
            try {
                // TODO Phuc: should be load from DB.
                detailEntities = new ArrayList<E>((Collection<E>) getChildListMethod.invoke(entity));
                return detailEntities;
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
        protected JPanel createFooterPanel(TableModel tableModel) {
            return AbstractMasterDetailView.this.createFooterPanel(tableModel);
        }
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

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, T entity, Map<String, Object> request) {
        // TODO Auto-generated method stub

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
     * Update detailEntities with master injected.
     */
    @Override
    protected void saveOrUpdate(T masterEntity) {
        saveOrUpdate(masterEntity, detailEntities);
    };

    protected void saveOrUpdate(T masterEntity, List<E> detailEntities) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(masterEntity);
        Collection<E> children = (Collection<E>) beanWrapper.getPropertyValue(getChildFieldName());
        children.removeAll(children);
        children.addAll(detailEntities);
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
}
