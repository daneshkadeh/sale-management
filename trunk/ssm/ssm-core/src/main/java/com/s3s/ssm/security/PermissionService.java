package com.s3s.ssm.security;

import java.security.BasicPermission;
import java.security.Permission;
import java.security.Principal;
import java.security.UnresolvedPermission;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.s3s.ssm.entity.PermissionEntity;
import com.s3s.ssm.entity.PrincipalEntity;
import com.s3s.ssm.security.permission.ActionsPermission;
import com.s3s.ssm.security.permission.RecordPermission;
import com.s3s.ssm.service.PermissionEntityService;
import com.s3s.ssm.service.PrincipalEntityService;
import com.s3s.ssm.util.ConfigProvider;

public class PermissionService {

    static private final Certificate[] EMPTY_CERTS = new Certificate[0];

    private static PermissionEntityService permissionEntityService = ConfigProvider.getInstance()
            .getPermissionEntityService();
    private static PrincipalEntityService principalEntityService = ConfigProvider.getInstance()
            .getPrincipalEntityService();

    /**
     * Add a permission into a principal
     * 
     * @param principal
     * @param permission
     */
    public static void addPermission(BasePrincipal principal, ActionsPermission permission) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setName(permission.getName());
        permissionEntity.setActions(permission.getActions());
        permissionEntity.setPermissionClass(permission.getClass().getName());
        permissionEntityService.save(permissionEntity);
        // add permission to principal
        PrincipalEntity principalEntity = principalEntityService.findByName(principal.getName());
        if(principalEntity == null) {
        	principalEntity = new PrincipalEntity(principal);
        	principalEntityService.save(principalEntity);
        }
        principalEntity.getPermissions().add(permissionEntity);
        principalEntityService.update(principalEntity);
    }
    
    public static void addPermissionSet(BasePrincipal principal, Set<ActionsPermission> permissionSet) {
    	for (ActionsPermission permission : permissionSet) {
    		addPermission(principal, permission);
    	}
    }

    public static List<Permission> findPermissions(Set<Principal> principalSet) {
        List<Permission> permissionList = new ArrayList<Permission>();
        for (Principal principal : principalSet) {
            permissionList.addAll(findPermission(principal));
        }
        return permissionList;
    }

    private static List<Permission> findPermission(Principal principal) {
        List<Permission> permissionList = new ArrayList<Permission>();
        PrincipalEntity principalEntity = principalEntityService.findByName(principal.getName());
        Set<PermissionEntity> permissionEntitySet = principalEntity.getPermissions();
        Permission perm;
        for (PermissionEntity permissionEntity : permissionEntitySet) {
            perm = createPermission(permissionEntity);
            permissionList.add(perm);
        }
        return permissionList;
    }

    private static Permission createPermission(PermissionEntity permissionEntity) {
        return createPermission(permissionEntity.getName(), permissionEntity.getPermissionClass(),
                permissionEntity.getActions());
    }

    private static Permission createPermission(String name, String clazzStr, String actions) {
        Permission perm = null;
        Class clazz = null;
        try {
            clazz = Class.forName(clazzStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (clazz == null) {
            perm = new UnresolvedPermission(clazzStr, name, actions, EMPTY_CERTS);
        } else if (clazz.equals(RecordPermission.class)) {
            perm = new RecordPermission(name, actions);
        }
        return perm;
    }
}
