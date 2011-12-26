package com.s3s.ssm.view.security;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.acls.MutableAcl;
import org.springframework.security.acls.MutableAclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.Permission;
import org.springframework.security.acls.UnloadedSidException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.objectidentity.ObjectIdentityImpl;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;

import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.AbstractView;

public class ACLPanel extends AbstractView {
    private final String SPACE = "   ";
    private final String ADMIN_LABLE = ControlConfigUtils.getString("label.Role.view.edit.accessRule.admin");
    private final String CREATE_LABEL = ControlConfigUtils.getString("label.Role.view.edit.accessRule.create");
    private final String READ_LABEL = ControlConfigUtils.getString("label.Role.view.edit.accessRule.read");
    private final String WRITE_LABEL = ControlConfigUtils.getString("label.Role.view.edit.accessRule.write");
    private final String DELETE_LABEL = ControlConfigUtils.getString("label.Role.view.edit.accessRule.delete");
    
    private static final Integer ADMINISTRATION = 0;
    private static final Integer CREATE = 1;
    private static final Integer READ = 2;
    private static final Integer WRITE = 3;
    private static final Integer DELETE = 4;
    
    private CustomJdbcMutableAclService mutableAclService;
    //get reference to fire event to ListRoleView
    private AbstractListView<Role> listView;
    private JTextField codeRole;
    private JTextField txtRole;
    private JCheckBox chkEnable;
    JPanel aclBoard;
    JPanel entityBoard;
    
    private int[][] matrix;
    static ApplicationContext aclContext;
    private Sid role;
    private Role entity;
    int distance = 2;

    /**
     * Create the panel.
     */
    public ACLPanel(Role entity) {
        matrix = new int[100][5];
        aclContext = getAclContext();
        if(entity == null) {
            this.entity = new Role();
        } else {
            this.entity = entity;
        }
        
        
        if (entity != null) {
            role = new PrincipalSid(entity.getName());
        }

        mutableAclService = (CustomJdbcMutableAclService) BeanFactoryUtils.beanOfType(aclContext,
                MutableAclService.class);
        initialPresentationView();
    }

    private static ApplicationContext getAclContext() {
        if (aclContext == null) {
            return (new ClassPathXmlApplicationContext("security/acl-context.xml"));
        }
        return aclContext;
    }

    private void initialPresentationView() {
        // set layout
        setLayout(new MigLayout("wrap 2"));
        // set filter field
        entityBoard = new JPanel(new MigLayout("wrap 2"));

        JLabel lblCode = new JLabel(ControlConfigUtils.getString("label.Role.code"));
        entityBoard.add(lblCode);
        
        codeRole = new JTextField();
        entityBoard.add(codeRole);
        codeRole.setText(entity.getCode());
        codeRole.setColumns(10);
        
        JLabel lblRole = new JLabel(ControlConfigUtils.getString("label.Role.name"));
        entityBoard.add(lblRole);

        txtRole = new JTextField();
        entityBoard.add(txtRole);

        JLabel lblStatus = new JLabel(ControlConfigUtils.getString("label.Role.isEnable"));
        entityBoard.add(lblStatus);

        chkEnable = new JCheckBox();
        entityBoard.add(chkEnable);

        txtRole.setText(entity.getName());
        chkEnable.setSelected(entity.getIsEnable());
        txtRole.setColumns(10);

        aclBoard = new JPanel(new MigLayout("wrap 5"));

        Border blackline = BorderFactory.createLineBorder(Color.gray);
        TitledBorder title = BorderFactory.createTitledBorder(blackline, ControlConfigUtils.getString("label.Role.view.edit.accessRule"));
        title.setTitleJustification(TitledBorder.LEFT);
        aclBoard.setBorder(title);
        // set title for authorities
        JLabel lblA = new JLabel(ADMIN_LABLE + SPACE);
        aclBoard.add(lblA, "cell 1 1,alignx center");

        JLabel lblC = new JLabel(CREATE_LABEL + SPACE);
        aclBoard.add(lblC, "cell 2 1,alignx center");

        JLabel lblR = new JLabel(READ_LABEL + SPACE);
        aclBoard.add(lblR, "cell 3 1,alignx center");

        JLabel lblW = new JLabel(WRITE_LABEL + SPACE);
        aclBoard.add(lblW, "cell 4 1,alignx center");

        JLabel lblD = new JLabel(DELETE_LABEL + SPACE);
        aclBoard.add(lblD, "cell 5 1,alignx center");
        for (ACLResourceEnum resource : ACLResourceEnum.values()) {
            initialPresentationResource(resource.getMessage(), resource.getOrder() + distance);
        }

        add(entityBoard);
        add(aclBoard);
        
        JButton btnSave = new JButton(ControlConfigUtils.getString("default.button.save"));
//        add(btnSave);
        
        JButton btnCancel = new JButton(ControlConfigUtils.getString("default.button.cancel"));
//        add(btnCancel);

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                btnSaveActionPerformed(event);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                btnCancelActionPerformed(event);
            }
        });
        JPanel btnPanel = new JPanel(new MigLayout("wrap 2"));
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, "alignx center");
    }

    private void setMatrix(int x, int y, ItemEvent e) {
        int stateChange = e.getStateChange();
        switch (stateChange) {
        case ItemEvent.SELECTED:
            matrix[x][y] = 1;
            break;
        case ItemEvent.DESELECTED:
            matrix[x][y] = 0;
            break;
        default:
            matrix[x][y] = 0;
        }
    }

    private void updateMutableAcl(int row, MutableAcl mutableAcl) {
        if (matrix[row][ADMINISTRATION] == 1) {
            mutableAcl.insertAce(0, BasePermission.ADMINISTRATION, role, true);
        }
        if (matrix[row][CREATE] == 1) {
            mutableAcl.insertAce(0, BasePermission.CREATE, role, true);
        }
        if (matrix[row][READ] == 1) {
            mutableAcl.insertAce(0, BasePermission.READ, role, true);
        }
        if (matrix[row][WRITE] == 1) {
            mutableAcl.insertAce(0, BasePermission.WRITE, role, true);
        }
        if (matrix[row][DELETE] == 1) {
            mutableAcl.insertAce(0, BasePermission.DELETE, role, true);
        }
    }

    private void initialPresentationResource(String title, final int cellRow) {
        boolean viewAdmin = false;
        boolean viewCreate = false;
        boolean viewRead = false;
        boolean viewWrite = false;
        boolean viewDelete = false;

        ObjectIdentity oid = new ObjectIdentityImpl(ACLResourceEnum.class, cellRow - distance);
        MutableAcl mutableAcl;

        try {
            mutableAcl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException e) {
            mutableAcl = (MutableAcl) mutableAclService.createAcl(oid);
        }

        if (role != null) {
            Permission[] permissions = new Permission[1];
            Sid[] sids = new Sid[1];
            sids[0] = role;

            permissions[0] = BasePermission.ADMINISTRATION;
            try {
                viewAdmin = mutableAcl.isGranted(permissions, sids, false);
            } catch (NotFoundException e) {
                viewAdmin = false;
            } catch (UnloadedSidException e) {
                viewAdmin = false;
            }

            permissions[0] = BasePermission.CREATE;
            try {
                viewCreate = mutableAcl.isGranted(permissions, sids, false);
            } catch (NotFoundException e) {
                viewCreate = false;
            } catch (UnloadedSidException e) {
                viewAdmin = false;
            }

            permissions[0] = BasePermission.READ;
            try {
                viewRead = mutableAcl.isGranted(permissions, sids, false);
            } catch (NotFoundException e) {
                viewRead = false;
            } catch (UnloadedSidException e) {
                viewAdmin = false;
            }

            permissions[0] = BasePermission.WRITE;
            try {
                viewWrite = mutableAcl.isGranted(permissions, sids, false);
            } catch (NotFoundException e) {
                viewWrite = false;
            } catch (UnloadedSidException e) {
                viewAdmin = false;
            }

            permissions[0] = BasePermission.DELETE;
            try {
                viewDelete = mutableAcl.isGranted(permissions, sids, false);
            } catch (NotFoundException e) {
                viewDelete = false;
            } catch (UnloadedSidException e) {
                viewAdmin = false;
            }
        }
        
        JLabel lblUser = new JLabel(title);
        aclBoard.add(lblUser, "cell 0 " + cellRow);

        JCheckBox chkUserAdmin;
        JCheckBox chkUserCreate;
        JCheckBox chkUserRead;
        JCheckBox chkUserWrite;
        JCheckBox chkUserDelete;

        if (viewAdmin == true) {
            chkUserAdmin = new JCheckBox("", true);
            matrix[cellRow - distance][0] = 1;
        } else {
            chkUserAdmin = new JCheckBox();
        }
        if (viewCreate == true) {
            chkUserCreate = new JCheckBox("", true);
            matrix[cellRow - distance][1] = 1;
        } else {
            chkUserCreate = new JCheckBox();
        }
        if (viewRead == true) {
            chkUserRead = new JCheckBox("", true);
            matrix[cellRow - distance][2] = 1;
        } else {
            chkUserRead = new JCheckBox();
        }
        if (viewWrite == true) {
            chkUserWrite = new JCheckBox("", true);
            matrix[cellRow - distance][3] = 1;
        } else {
            chkUserWrite = new JCheckBox();
        }
        if (viewDelete == true) {
            chkUserDelete = new JCheckBox("", true);
            matrix[cellRow - distance][4] = 1;
        } else {
            chkUserDelete = new JCheckBox();
        }

        aclBoard.add(chkUserAdmin, "cell 1 " + cellRow + ",alignx center");
        chkUserAdmin.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setMatrix(cellRow - distance, 0, e);
            }
        });

        aclBoard.add(chkUserCreate, "cell 2 " + cellRow + ",alignx center");
        chkUserCreate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setMatrix(cellRow - distance, 1, e);
            }
        });

        aclBoard.add(chkUserRead, "cell 3 " + cellRow + ",alignx center");
        chkUserRead.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setMatrix(cellRow - distance, 2, e);
            }
        });

        aclBoard.add(chkUserWrite, "cell 4 " + cellRow + ",alignx center");
        chkUserWrite.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setMatrix(cellRow - distance, 3, e);
            }
        });

        aclBoard.add(chkUserDelete, "cell 5 " + cellRow + ",alignx center");
        chkUserDelete.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setMatrix(cellRow - distance, 4, e);
            }
        });
    }

    protected void saveOrUpdate(Role entity) {
        entity.setCode(codeRole.getText());
        entity.setName(txtRole.getText());
        entity.setIsEnable(chkEnable.isSelected());
        getDaoHelper().getDao(Role.class).saveOrUpdate(entity);
    }

    void btnSaveActionPerformed(ActionEvent evt) {
        saveOrUpdateACL();
        saveOrUpdate(entity);
        //fire event to ListRoleView
        boolean isNew = (entity.getId() == null);
        listView.notifyFromDetailView(entity, isNew);
        SwingUtilities.getRoot(this).setVisible(false);
    }

    protected void btnCancelActionPerformed(ActionEvent evt) {
        SwingUtilities.getRoot(this).setVisible(false);
    }
    
    @Transactional
    private void saveOrUpdateACL() {
        String strRole = txtRole.getText();
        for (ACLResourceEnum resource : ACLResourceEnum.values()) {
            ObjectIdentity uomCateOid = new ObjectIdentityImpl(ACLResourceEnum.class, resource.getOrder());
            mutableAclService.deleteAcl(uomCateOid, strRole, true);
            MutableAcl uomCateMutableAcl;
            try {
                uomCateMutableAcl = (MutableAcl) mutableAclService.readAclById(uomCateOid);
            } catch (NotFoundException e) {
                uomCateMutableAcl = (MutableAcl) mutableAclService.createAcl(uomCateOid);
            }
            role = new PrincipalSid(txtRole.getText());
            updateMutableAcl(resource.getOrder(), uomCateMutableAcl);
            mutableAclService.updateAcl(uomCateMutableAcl);
        }
        
    }

    public void setListView(AbstractListView<Role> listView) {
        this.listView = listView;
    }
    
}
