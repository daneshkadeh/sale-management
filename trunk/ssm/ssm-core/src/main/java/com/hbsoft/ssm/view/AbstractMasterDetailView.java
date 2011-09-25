package com.hbsoft.ssm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.hbsoft.ssm.entity.AbstractBaseIdObject;
import com.hbsoft.ssm.model.DetailDataModel;
import com.hbsoft.ssm.util.i18n.ControlConfigUtils;

/**
 * This view is used for a master entity and its detail entities.</br> Example: An invoice and list detail invoice. User
 * can input directly invoice and detail invoice information on this screen.
 * 
 * @author phamcongbang
 * 
 * @param <T>
 *            parent entity type
 * @param <E>
 *            child entity type
 */
public abstract class AbstractMasterDetailView<T extends AbstractBaseIdObject, E extends AbstractBaseIdObject> extends
        AbstractDetailView<T> {
    private static final long serialVersionUID = 5571051971772731048L;
    private List<E> detailEntities = new ArrayList<E>();
    private Class<E> detailClazz;

    private AbstractListView<E> listDetailView;

    public AbstractMasterDetailView() {
        super();

    }

    /**
     * Init presentation of list detail objects.
     * 
     */
    protected abstract void initialListDetailPresentationView(List<DetailDataModel> listDataModel);

    /**
     * Init presentation of a detail object.
     * 
     */
    protected abstract void initialEditDetailPresentationView(List<DetailDataModel> listDataModel, E entity);

    protected void initComponents() throws Exception {
        super.initComponents();
        listDetailView = new AbstractCommonListView<E>() {
            private static final long serialVersionUID = -8455234397691564647L;

            protected Class<E> getEntityClass() {
                return getDetailClass();
            };

            @Override
            protected void initialPresentationView(List<DetailDataModel> listDataModel) {
                initialListDetailPresentationView(listDataModel);
            }

            @Override
            protected JPanel createButtonPanel(JTable table) {
                JPanel pnlButton = new JPanel();
                JButton btnInsertRow = new JButton(ControlConfigUtils.getString("ListView.Common.Button.InsertRow"));
                btnInsertRow.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openDetailView();
                    }

                });
                pnlButton.add(btnInsertRow);
                return pnlButton;
            }

            protected List<E> loadData() {
                return detailEntities;
            };

            @Override
            protected AbstractDetailView<E> getDetailView() {
                return new AbstractDetailView<E>() {
                    private static final long serialVersionUID = 3347750315592358049L;

                    @Override
                    public void initialPresentationView(List<DetailDataModel> listDataModel, E entity) {
                        initialEditDetailPresentationView(listDataModel, entity);
                        // listDataModel.addAll(listDetailDataModel);
                    }

                    protected void saveOrUpdate(E detailEntity) {
                        detailEntities.add(detailEntity);
                    };

                    protected Class<E> getEntityClass() {
                        return getDetailClass();
                    };
                };
            }
        };
        add(listDetailView, "grow");
    }

    /**
     * Update detailEntities with master injected. Subclasses must override this.
     */
    protected void saveOrUpdate(T master) {
        saveOrUpdate(master, detailEntities);
    };

    protected void saveOrUpdate(T master, List<E> detailEntities) {
    };

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
