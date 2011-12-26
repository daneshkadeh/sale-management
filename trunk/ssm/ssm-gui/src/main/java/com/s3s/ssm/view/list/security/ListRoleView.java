package com.s3s.ssm.view.list.security;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.security.EditRoleView;
import com.s3s.ssm.view.security.ACLPanel;

public class ListRoleView extends AbstractListView<Role> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("isEnable", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<Role>> getDetailViewClass() {
        return EditRoleView.class;
    }

    @Override
    protected void showDetailView(Role entity) {
        // TODO This call requires sub class override Constructor method! It's not good.
        ACLPanel aclPanel = new ACLPanel(entity);
        aclPanel.setListView(this);
        // TODO HPP consider to listen the event from AbstractDetailView (not set reference to it).
        JScrollPane scrollPane = new JScrollPane(aclPanel);

        Window parentContainer = (Window) SwingUtilities.getRoot(this);
        JDialog dialog = new JDialog(parentContainer);
        dialog.setContentPane(scrollPane);
        dialog.setContentPane(scrollPane);
        dialog.setTitle(ControlConfigUtils.getString("label.Role.view.edit.title"));
        Dimension preferredSize = aclPanel.getPreferredSize();
        Dimension dialogSize = new Dimension(preferredSize.width + 100, preferredSize.height + 100);
        dialog.setSize(dialogSize);
        dialog.setLocationRelativeTo(parentContainer); // Display the dialog in the center.
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
        dialog.setVisible(true);
    }

}
