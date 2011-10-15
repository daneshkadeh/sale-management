package com.hbsoft.ssm.view;

import javax.swing.JPanel;

import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.util.DaoHelper;

public abstract class AbstractView extends JPanel {
    private static final long serialVersionUID = 1L;

    private DaoHelper daoHelper = ConfigProvider.getInstance().getDaoHelper();

    protected DaoHelper getDaoHelper() {
        return daoHelper;
    }
}
