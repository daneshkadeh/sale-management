package com.s3s.ssm.view.security;

import javax.sql.DataSource;

import org.springframework.security.acls.ChildrenExistException;
import org.springframework.security.acls.jdbc.AclCache;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.objectidentity.ObjectIdentity;

public class CustomJdbcMutableAclService extends JdbcMutableAclService {
    private boolean foreignKeysInDatabase = true;
    private String deleteEntryByObjectIdentityForeignKeyAndSid = "delete from acl_entry where acl_entry.acl_object_identity=? and acl_entry.sid IN (select acl_sid.id from acl_sid where acl_sid.sid = ?)";
    private AclCache customeCache;
    public CustomJdbcMutableAclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
        super(dataSource, lookupStrategy, aclCache);
        customeCache = aclCache;
    }

    public void deleteAcl(ObjectIdentity objectIdentity, String strSid, boolean deleteChildren)
            throws ChildrenExistException {
        if (deleteChildren) {
            ObjectIdentity[] children = findChildren(objectIdentity);
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    deleteAcl(children[i], true);
                }
            }
        } else {
            if (!foreignKeysInDatabase) {
                // We need to perform a manual verification for what a FK would
                // normally do
                // We generally don't do this, in the interests of deadlock
                // management
                ObjectIdentity[] children = findChildren(objectIdentity);
                if (children != null) {
                    throw new ChildrenExistException("Cannot delete '" + objectIdentity + "' (has " + children.length
                            + " children)");
                }
            }
        }

        Long oidPrimaryKey = retrieveObjectIdentityPrimaryKey(objectIdentity);

        // Delete this ACL's ACEs in the acl_entry table
        deleteEntriesBySid(oidPrimaryKey, strSid);
        //remove data from cache
        customeCache.evictFromCache(objectIdentity);
    }

    protected void deleteEntriesBySid(Long oidPrimaryKey, String strSid) {
        jdbcTemplate.update(deleteEntryByObjectIdentityForeignKeyAndSid, new Object[] { oidPrimaryKey, strSid });
    }
}
