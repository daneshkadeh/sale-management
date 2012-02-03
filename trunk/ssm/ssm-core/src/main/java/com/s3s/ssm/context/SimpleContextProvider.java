/*
 * SimpleContextProvider
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
package com.s3s.ssm.context;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomPermission;

/**
 * This class is not implemented.
 * 
 * @author phamcongbang
 * 
 */
@Service("contextProvider")
public class SimpleContextProvider implements ContextProvider {
    private static final Log logger = LogFactory.getLog(SimpleContextProvider.class);

    @Override
    public String getCurrentUser() {
        String username;

        Object principal = null;
        try {
            principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            // TODO: this default user is used to testing, code will be delete on production
            if (principal == null) {
                return "admin";
            } else {
                return "exceptionUser";
            }
        }

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    @Override
    public Long getCurrentPOS() {
        return 1L;
    }

    @Override
    public Float getCurrencyRate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPaymentMethod() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<CustomPermission> getPermissions(ACLResourceEnum aclResource) {
        Set<CustomPermission> cusPermissionSet = new HashSet<CustomPermission>();
        // CustomJdbcMutableAclService mutableAclService = (CustomJdbcMutableAclService) ConfigProvider.getInstance()
        // .getMutableAclService();
        // ObjectIdentity oid = new ObjectIdentityImpl(ACLResourceEnum.class, aclResource.getOrder());
        // MutableAcl mutableAcl;
        // try {
        // mutableAcl = (MutableAcl) mutableAclService.readAclById(oid);
        // } catch (NotFoundException e) {
        // logger.error(e);
        // return Collections.emptySet();
        // }
        // CustomPermission[] permissions = new CustomPermission[1];
        // Sid[] sids = new Sid[1];
        // // get currency user
        // User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Set<Role> roleList = currUser.getRoles();
        // for (Role role : roleList) {
        // // TODO Hoang must customize the code section below
        // sids[0] = new PrincipalSid(role.getName());
        // permissions[0] = CustomPermission.ADMINISTRATION;
        // try {
        // if (mutableAcl.isGranted(permissions, sids, false) == true) {
        // cusPermissionSet.add(CustomPermission.ADMINISTRATION);
        // }
        // } catch (NotFoundException e) {
        // logger.info(e);
        // } catch (UnloadedSidException e) {
        // logger.info(e);
        // }
        // permissions[0] = CustomPermission.CREATE;
        // try {
        // if (mutableAcl.isGranted(permissions, sids, false) == true) {
        // cusPermissionSet.add(CustomPermission.CREATE);
        // }
        // } catch (NotFoundException e) {
        // logger.info(e);
        // } catch (UnloadedSidException e) {
        // logger.info(e);
        // }
        //
        // permissions[0] = CustomPermission.READ;
        // try {
        // if (mutableAcl.isGranted(permissions, sids, false) == true) {
        // cusPermissionSet.add(CustomPermission.READ);
        // }
        // } catch (NotFoundException e) {
        // logger.info(e);
        // } catch (UnloadedSidException e) {
        // logger.info(e);
        // }
        //
        // permissions[0] = CustomPermission.WRITE;
        // try {
        // if (mutableAcl.isGranted(permissions, sids, false) == true) {
        // cusPermissionSet.add(CustomPermission.WRITE);
        // }
        // } catch (NotFoundException e) {
        // logger.info(e);
        // } catch (UnloadedSidException e) {
        // logger.info(e);
        // }
        //
        // permissions[0] = CustomPermission.DELETE;
        // try {
        // if (mutableAcl.isGranted(permissions, sids, false) == true) {
        // cusPermissionSet.add(CustomPermission.DELETE);
        // }
        // } catch (NotFoundException e) {
        // logger.info(e);
        // } catch (UnloadedSidException e) {
        // logger.info(e);
        // }
        //
        // permissions[0] = CustomPermission.EXECUTE;
        // try {
        // if (mutableAcl.isGranted(permissions, sids, false) == true) {
        // cusPermissionSet.add(CustomPermission.EXECUTE);
        // }
        // } catch (NotFoundException e) {
        // logger.info(e);
        // } catch (UnloadedSidException e) {
        // logger.info(e);
        // }
        //
        // }
        cusPermissionSet.add(CustomPermission.ADMINISTRATION);
        return cusPermissionSet;
    }
}
