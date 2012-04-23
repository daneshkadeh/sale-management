/*
 * ListCustomerView
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

package com.s3s.ssm.view.list.contact;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.detail.contact.EditMultiCustomerView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractSearchListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * 
 * @author phamcongbang
 * 
 */
public class ListCustomerView extends AbstractSearchListView<Partner> {
    /**
     * 
     */
    private static final long serialVersionUID = 2964366183405416076L;

    private JTextField fieldCode;
    private JTextField fieldName;
    private JTextField fieldPhone;
    private JTextField fieldFax;
    private JTextField fieldEmail;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("phone", ListRendererType.TEXT);
        listDataModel.addColumn("fax", ListRendererType.TEXT);
        listDataModel.addColumn("email", ListRendererType.TEXT);
        listDataModel.addColumn("website", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Partner>> getEditViewClass() {
        return EditMultiCustomerView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.createCriteria("listProfiles").add(Restrictions.eq("type", PartnerProfileTypeEnum.CUSTOMER));
        if (StringUtils.isNotBlank(fieldCode.getText())) {
            dc.add(Restrictions.ilike("code", fieldCode.getText(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(fieldName.getText())) {
            dc.add(Restrictions.ilike("name", fieldName.getText(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(fieldEmail.getText())) {
            dc.add(Restrictions.like("email", fieldEmail.getText(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(fieldPhone.getText())) {
            dc.add(Restrictions.like("phone", fieldPhone.getText(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(fieldFax.getText())) {
            dc.add(Restrictions.like("name", fieldFax.getText(), MatchMode.ANYWHERE));
        }

        return dc;
    }

    @Override
    protected JPanel createSearchPanel() {
        fieldCode = new JTextField();
        fieldName = new JTextField();
        fieldPhone = new JTextField();
        fieldFax = new JTextField();
        fieldEmail = new JTextField();

        JPanel panel = new JPanel(new MigLayout("ins 0, fill", "grow"));
        panel.add(new JLabel(ControlConfigUtils.getString("label.Partner.code")), "right");
        panel.add(fieldCode, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Partner.name")), "right");
        panel.add(fieldName, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Partner.email")), "right");
        panel.add(fieldEmail, "grow, wrap");

        panel.add(new JLabel(ControlConfigUtils.getString("label.Partner.phone")), "right");
        panel.add(fieldPhone, "grow");
        panel.add(new JLabel(ControlConfigUtils.getString("label.Partner.fax")), "right");
        panel.add(fieldFax, "grow");
        return panel;
    }

    @Override
    protected void clearCriteria() {
        fieldCode.setText(null);
        fieldName.setText(null);
        fieldEmail.setText(null);
        fieldPhone.setText(null);
        fieldFax.setText(null);
    }

}
