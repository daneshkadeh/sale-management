package com.s3s.ssm.view;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.util.Solution3sClassUtils;

/**
 * This view is used for a master entity and its detail entities.</br> Example: An invoice and list detail invoice. User
 * can input directly invoice and detail invoice information on this screen.
 * 
 * @author phamcongbang
 * @author Phan Hong Phuc
 * 
 * @param <T>
 *            parent entity type
 * @param <E>
 *            child entity type
 */
public abstract class AbstractMasterDetailView<T extends AbstractBaseIdObject, E extends AbstractBaseIdObject> extends
        AbstractDetailView<T> {
    private static final long serialVersionUID = 5571051971772731048L;

    private final Log logger = LogFactory.getLog(AbstractMasterDetailView.class);

    private List<E> detailEntities = new ArrayList<E>();

    /**
     * The default constructor.
     * 
     * @param entity
     */
    public AbstractMasterDetailView(T entity) {
        super(entity);
    }

    /**
     * Init presentation of list detail objects.
     */
    protected abstract void initialListDetailPresentationView(List<DetailAttribute> listDataModel);

    /**
     * Get detail view class of child entity.
     * 
     * @return
     */
    protected abstract Class<? extends AbstractDetailView<E>> getChildDetailViewClass();

    /**
     * Get the field name of child property.
     * 
     * @return
     */
    protected abstract String getChildFieldName();

    @Override
    protected void initComponents() throws Exception {
        super.initComponents();
        add(new ChildListView(), "grow");
    }

    private class ChildListView extends AbstractListView<E> {
        private static final long serialVersionUID = -8455234397691564647L;

        @Override
        protected List<E> loadData() {
            Method getChildListMethod = Solution3sClassUtils.getGetterMethod(getMasterClass(), getChildFieldName());
            try {
                // List<E> childEntitiesList = (List<E>) getChildListMethod.invoke(entity);
                List<E> copyChildEntitiesList = new ArrayList<E>((Collection<E>) getChildListMethod.invoke(entity));
                // Collections.copy(copyChildEntitiesList, childEntitiesList);
                return copyChildEntitiesList;
            } catch (Exception e) {
                logger.error(e.getCause());
                logger.error(e.getMessage());
                throw new RuntimeException("There is problem when loadData for child entities");
            }
        };

        @Override
        protected Class<E> getEntityClass() {
            return getDetailClass();
        }

        @Override
        protected Class<? extends AbstractDetailView<E>> getDetailViewClass() {
            return getChildDetailViewClass();
        }

        @Override
        protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
            initialListDetailPresentationView(listDataModel);
        }
    }

    /**
     * Update detailEntities with master injected.
     */
    @Override
    protected void saveOrUpdate(T masterEntity) {
        saveOrUpdate(masterEntity, detailEntities);
    };

    protected abstract void saveOrUpdate(T masterEntity, List<E> detailEntities);

    protected Class<T> getMasterClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) controllerType;
    }

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
