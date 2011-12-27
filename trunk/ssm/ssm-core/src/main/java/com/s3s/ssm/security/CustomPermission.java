/*
 * CustomPermission
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.security;

import java.security.BasicPermission;

import org.springframework.security.acls.Permission;
import org.springframework.security.acls.domain.AbstractPermission;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.DefaultPermissionFactory;

/**
 * @author Le Thanh Hoang
 *
 */
public class CustomPermission extends AbstractPermission {
    public static final CustomPermission READ = new CustomPermission(1 << 0, 'R'); // 1
    public static final CustomPermission WRITE = new CustomPermission(1 << 1, 'W'); // 2
    public static final CustomPermission CREATE = new CustomPermission(1 << 2, 'C'); // 4
    public static final CustomPermission DELETE = new CustomPermission(1 << 3, 'D'); // 8
    public static final CustomPermission ADMINISTRATION = new CustomPermission(1 << 4, 'A'); // 16
    public static final CustomPermission EXECUTE = new CustomPermission(1 << 5, 'E'); // 32
 
    protected static CustomPermissionFactory defaultPermissionFactory = new CustomPermissionFactory();

    /**
     * Registers the public static permissions defined on this class. This is mandatory so
     * that the static methods will operate correctly.
     */
    static {
        registerPermissionsFor(CustomPermission.class);
    }

    protected CustomPermission(int mask, char code) {
        super(mask, code);
    }

    protected final static void registerPermissionsFor(Class subClass) {
        defaultPermissionFactory.registerPublicPermissions(subClass);
    }
    
    public final static Permission buildFromMask(int mask) {
        return defaultPermissionFactory.buildFromMask(mask);
    }

    public final static Permission[] buildFromMask(int[] masks) {
        return defaultPermissionFactory.buildFromMask(masks);
    }

    public final static Permission buildFromName(String name) {
        return defaultPermissionFactory.buildFromName(name);
    }

    public final static Permission[] buildFromName(String[] names) {
        return defaultPermissionFactory.buildFromName(names);
    }

}
