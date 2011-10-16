package com.s3s.ssm.view;

import javax.swing.JPanel;

import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.DaoHelper;

public abstract class AbstractView extends JPanel {
    private static final long serialVersionUID = 1L;

    private DaoHelper daoHelper = ConfigProvider.getInstance().getDaoHelper();

    protected DaoHelper getDaoHelper() {
        return daoHelper;
    }
}
