package com.s3s.ssm.context;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.s3s.ssm.entity.config.Institution;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.SalesChannel;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.operator.OperatorReferences;
import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomPermission;
import com.s3s.ssm.util.DaoHelper;

@Service("contextProvider")
public class OrgSalesContextProviderImpl implements OrgSalesContextProvider {
    private static final Log logger = LogFactory.getLog(OrgSalesContextProviderImpl.class);

    @Autowired
    private DaoHelper daoHelper;

    private String username;
    private Institution currInstitution;
    private Organization currOrganization;
    private SalesChannel currSalesChannel;
    private Stall currStall;

    public void loadContext() {
        // First, clear the current context
        clearContext();

        String operatorUserName = getCurrentUser();
        OperatorReferences references = getOperatorReferences(operatorUserName);
        if (references == null) {
            references = new OperatorReferences();

            DetachedCriteria operatorDC = daoHelper.getDao(Operator.class).getCriteria();
            operatorDC.add(Restrictions.eq("username", operatorUserName));
            Operator currOperator = daoHelper.getDao(Operator.class).findFirstByCriteria(operatorDC);
            if (currOperator == null) {
                throw new RuntimeException("Can not find login user in current context.");
            }
            references.setOperator(currOperator);
            try {
                references.setCurrInstitution(daoHelper.getDao(Institution.class).findAll().get(0));
                references.setCurrOrganization(daoHelper.getDao(Organization.class).findAll().get(0));
                // TODO: setup SalesChannel, Stall into context
            } catch (Exception e) {
                throw new RuntimeException("Missing configuration institution, organization...", e);
            }
            daoHelper.getDao(OperatorReferences.class).saveOrUpdate(references);
        }

        currInstitution = references.getCurrInstitution();
        currOrganization = references.getCurrOrganization();
        currSalesChannel = references.getCurrSalesChannel();
        currStall = references.getCurrStall();
    }

    private OperatorReferences getOperatorReferences(String operatorUserName) {
        DetachedCriteria dc = daoHelper.getDao(OperatorReferences.class).getCriteria();
        dc.createCriteria("operator").add(Restrictions.eq("username", operatorUserName));
        OperatorReferences references = daoHelper.getDao(OperatorReferences.class).findFirstByCriteria(dc);
        return references;
    }

    public void clearContext() {
        // TODO: will be implemented
    }

    public void saveContext() {
        String operatorUserName = getCurrentUser();
        OperatorReferences references = getOperatorReferences(operatorUserName);
        references.setCurrOrganization(currOrganization);
        references.setCurrSalesChannel(currSalesChannel);
        references.setCurrStall(currStall);
        daoHelper.getDao(OperatorReferences.class).saveOrUpdate(references);
    }

    public void setCurrentOrganization(Organization organzation) {
        currOrganization = organzation;
    }

    public void setCurrentSalesChannel(SalesChannel salesChannel) {
        currSalesChannel = salesChannel;
    }

    @Override
    public String getCurrentUser() {
        if (username != null) {
            return username;
        }

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

        // TODO: this code always return "admin" as username?
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

    @Deprecated
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

    public static Log getLogger() {
        return logger;
    }

    @Override
    public Institution getCurrentInstitution() {
        return currInstitution;
    }

    @Override
    public Organization getCurrentOrganization() {
        return currOrganization;
    }

    @Override
    public SalesChannel getCurrentSalesChannel() {
        return currSalesChannel;
    }

    public DaoHelper getDaoHelper() {
        return daoHelper;
    }

    public void setDaoHelper(DaoHelper daoHelper) {
        this.daoHelper = daoHelper;
    }
}
