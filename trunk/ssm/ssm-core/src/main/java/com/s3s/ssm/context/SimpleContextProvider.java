package com.s3s.ssm.context;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.acls.MutableAcl;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.UnloadedSidException;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.objectidentity.ObjectIdentityImpl;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.entity.security.User;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomJdbcMutableAclService;
import com.s3s.ssm.security.CustomPermission;
import com.s3s.ssm.util.ConfigProvider;

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        CustomJdbcMutableAclService mutableAclService = (CustomJdbcMutableAclService) ConfigProvider.getInstance()
                .getMutableAclService();
        ObjectIdentity oid = new ObjectIdentityImpl(ACLResourceEnum.class, aclResource.getOrder());
        MutableAcl mutableAcl;
        try {
            mutableAcl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException e) {
            logger.error(e);
            return Collections.emptySet();
        }
        CustomPermission[] permissions = new CustomPermission[1];
        Sid[] sids = new Sid[1];
        // get currency user
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> roleList = currUser.getRoles();
        for (Role role : roleList) {
            //TODO Hoang must customize the code section below
            sids[0] = new PrincipalSid(role.getName());
            permissions[0] = CustomPermission.ADMINISTRATION;
            try {
                if (mutableAcl.isGranted(permissions, sids, false) == true) {
                    cusPermissionSet.add(CustomPermission.ADMINISTRATION);
                }
            } catch (NotFoundException e) {
                logger.info(e);
            } catch (UnloadedSidException e) {
                logger.info(e);
            }
            permissions[0] = CustomPermission.CREATE;
            try {
                if (mutableAcl.isGranted(permissions, sids, false) == true) {
                    cusPermissionSet.add(CustomPermission.CREATE);
                }
            }catch (NotFoundException e) {
                logger.info(e);
            } catch (UnloadedSidException e) {
                logger.info(e);
            }
            
            permissions[0] = CustomPermission.READ;
            try {
                if (mutableAcl.isGranted(permissions, sids, false) == true) {
                    cusPermissionSet.add(CustomPermission.READ);
                }
            }catch (NotFoundException e) {
                logger.info(e);
            } catch (UnloadedSidException e) {
                logger.info(e);
            }
            
            permissions[0] = CustomPermission.WRITE;
            try {
                if (mutableAcl.isGranted(permissions, sids, false) == true) {
                    cusPermissionSet.add(CustomPermission.WRITE);
                }
            }catch (NotFoundException e) {
                logger.info(e);
            } catch (UnloadedSidException e) {
                logger.info(e);
            }
            
            permissions[0] = CustomPermission.DELETE;
            try{
                if (mutableAcl.isGranted(permissions, sids, false) == true) {
                    cusPermissionSet.add(CustomPermission.DELETE);
                }
            }catch (NotFoundException e) {
                logger.info(e);
            } catch (UnloadedSidException e) {
                logger.info(e);
            }
            
            permissions[0] = CustomPermission.EXECUTE;
            try {
                if (mutableAcl.isGranted(permissions, sids, false) == true) {
                    cusPermissionSet.add(CustomPermission.EXECUTE);
                }
            }catch (NotFoundException e) {
                logger.info(e);
            } catch (UnloadedSidException e) {
                logger.info(e);
            }
            
        }
        return cusPermissionSet;
    }
}
