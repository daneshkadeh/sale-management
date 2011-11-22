package com.s3s.ssm.view.security;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.s3s.ssm.entity.security.ServiceExample;
import com.s3s.ssm.entity.security.User;

public class ACLPanel extends JPanel {
	private CustomJdbcMutableAclService mutableAclService;
	private PlatformTransactionManager transactionManager;
	private JTextField txtRole;
	private int[][] matrix = new int[1][5];
	ApplicationContext aclContext = new ClassPathXmlApplicationContext(
			"config/acl-context.xml");

	/**
	 * Create the panel.
	 */
	public ACLPanel() {
		setLayout(new MigLayout("", "[][][][][][][]", "[][][][]"));

		JLabel lblRole = new JLabel("Role");
		add(lblRole, "cell 0 0,alignx trailing");

		txtRole = new JTextField();
		add(txtRole, "cell 1 0 4 1,alignx right");
		txtRole.setColumns(10);

		JLabel lblA = new JLabel("A");
		add(lblA, "cell 1 1,alignx center");

		JLabel lblC = new JLabel("C");
		add(lblC, "cell 2 1,alignx center");

		JLabel lblR = new JLabel("R");
		add(lblR, "cell 3 1,alignx center");

		JLabel lblW = new JLabel("W");
		add(lblW, "cell 4 1,alignx center");

		JLabel lblNewLabel = new JLabel("D");
		add(lblNewLabel, "cell 5 1,alignx center");

		JLabel lblUser = new JLabel("UOM Category");
		add(lblUser, "cell 0 2");

		JCheckBox chkUserAdmin = new JCheckBox("");
		add(chkUserAdmin, "cell 1 2");
		chkUserAdmin.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				matrix[0][0] = 1;
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					matrix[0][0] = 0;
				}
			}
		});

		JCheckBox chkUserCreate = new JCheckBox("");
		add(chkUserCreate, "cell 2 2");
		chkUserCreate.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				matrix[0][1] = 1;
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					matrix[0][1] = 0;
				}
			}
		});

		JCheckBox chkUserRead = new JCheckBox("");
		add(chkUserRead, "cell 3 2");
		chkUserRead.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				matrix[0][2] = 1;
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					matrix[0][2] = 0;
				}
			}
		});

		JCheckBox chkUserWrite = new JCheckBox("");
		add(chkUserWrite, "cell 4 2");
		chkUserWrite.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				matrix[0][3] = 1;
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					matrix[0][3] = 0;
				}
			}
		});

		JCheckBox chkUserDelete = new JCheckBox("");
		add(chkUserDelete, "cell 5 2");
		chkUserDelete.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				matrix[0][4] = 1;
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					matrix[0][4] = 0;
				}
			}
		});

		JButton btnSave = new JButton("Save");
		add(btnSave, "cell 6 3");

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 login();

				transactionManager = (PlatformTransactionManager) BeanFactoryUtils
						.beanOfType(aclContext,
								PlatformTransactionManager.class);
				TransactionTemplate tt = new TransactionTemplate(
						transactionManager);
				tt.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(
							TransactionStatus arg0) {
						mutableAclService = (CustomJdbcMutableAclService) BeanFactoryUtils
								.beanOfType(aclContext, MutableAclService.class);
						String strRole = txtRole.getText();
						ObjectIdentity oid = new ObjectIdentityImpl(
								ACLResources.class, ACLResources.UOM_CATEGORY);
						mutableAclService.deleteAcl(oid, strRole, true);
						MutableAcl mutableAcl = (MutableAcl) mutableAclService
								.readAclById(oid);

						Sid role = new GrantedAuthoritySid(strRole);

						if (matrix[ACLResources.UOM_CATEGORY][ACLResources.ADMIN] == 1) {
							mutableAcl.insertAce(0,
									BasePermission.ADMINISTRATION, role, true);
						}
						if (matrix[ACLResources.UOM_CATEGORY][ACLResources.CREATE] == 1) {
							mutableAcl.insertAce(0, BasePermission.CREATE,
									role, true);
						}
						if (matrix[ACLResources.UOM_CATEGORY][ACLResources.READ] == 1) {
							mutableAcl.insertAce(0, BasePermission.READ, role,
									true);
						}
						if (matrix[ACLResources.UOM_CATEGORY][ACLResources.WRITE] == 1) {
							mutableAcl.insertAce(0, BasePermission.WRITE, role,
									true);
						}
						if (matrix[ACLResources.UOM_CATEGORY][ACLResources.DELETE] == 1) {
							mutableAcl.insertAce(0, BasePermission.DELETE,
									role, true);
						}
						mutableAclService.updateAcl(mutableAcl);

					}
				});
			}
		});
	}

	void login() {
		// login before set authorize
		ApplicationContext auContext = new ClassPathXmlApplicationContext(
				"config/s3s-auth-security.xml");
		AuthenticationManager authenticationManager = (AuthenticationManager) BeanFactoryUtils
				.beanOfType(auContext, AuthenticationManager.class);
		// set token
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				"Hoang Le", "123456");
		// login
		Authentication authenticatedUser;
		try {
			authenticatedUser = authenticationManager.authenticate(token);
			// SecurityContextHolder.getContext().setAuthentication(
			// authenticatedUser);
			SecurityContextHolder.getContext().setAuthentication(
					authenticatedUser);
		} catch (BadCredentialsException e) {
			System.out.println(e.getMessage());
		}
	}
}
