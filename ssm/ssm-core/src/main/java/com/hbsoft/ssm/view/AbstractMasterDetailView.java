package com.hbsoft.ssm.view;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hbsoft.ssm.model.DetailDataModel;

public abstract class AbstractMasterDetailView<T, E> extends JPanel {
    private T masterEntity;
    private Class<T> masterClazz;
    protected Map<DetailDataModel, JComponent> mapFields = new HashMap<DetailDataModel, JComponent>();

    private List<E> detailEntities;
    private Class<E> detailClazz;
    private JTable tblListDetailEntities;
    private JScrollPane jScrollPane;

    private List<DetailDataModel> listMasterDataModel = new ArrayList<DetailDataModel>();

    private List<DetailDataModel> listDetailDataModel = new ArrayList<DetailDataModel>();

    public AbstractMasterDetailView() {
        masterClazz = getMasterClass();
        detailClazz = getDetailClass();
        try {
            setMasterEntity(masterClazz.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        initialPresentationView(listMasterDataModel, listDetailDataModel);
        initComponents();
    }

    protected abstract void initialPresentationView(List<DetailDataModel> listMasterDataModel2,
            List<DetailDataModel> listDetailDataModel2);

    private void initComponents() {
        // TODO Auto-generated method stub

    }

    protected Class<T> getMasterClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) controllerType;
    }

    protected Class<E> getDetailClass() {
        Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return (Class<E>) controllerType;
    }

    public void setMasterEntity(T masterEntity) {
        this.masterEntity = masterEntity;
    }

    public T getMasterEntity() {
        return masterEntity;
    }

    public void setDetailEntities(List<E> detailEntities) {
        this.detailEntities = detailEntities;
    }

    public List<E> getDetailEntities() {
        return detailEntities;
    }
}
