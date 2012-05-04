package com.s3s.ssm.view.component;

import com.s3s.ssm.entity.catalog.Item;

/**
 * Search item based on item code. This component is used on invoice, import store, export store view.
 * 
 */
public class ItemSearchComponent extends ASearchComponent<Item> {
    private static final long serialVersionUID = 4116526234940250000L;

    @Override
    protected String[] getDisplayAttributes() {
        return new String[] { "code" };
    }

    @Override
    protected String[] getAttributeColumns() {
        return new String[] { "code", "sumUomName" };
    }

    @Override
    protected String[] getSearchedOnAttributes() {
        return new String[] { "code" };
    }

}
