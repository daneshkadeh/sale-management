package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.param.Item;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;

public class ListItemView extends AbstractListView<Item> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<Item>> getDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}