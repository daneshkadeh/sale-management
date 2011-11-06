package com.s3s.ssm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * This optimistic locking object has userInserted, dateInserted, userLastUpdate, dateLastUpdate
 * 
 * @author phamcongbang
 * 
 */
@MappedSuperclass
public abstract class AbstractIdOLObject extends AbstractBaseIdObject {
    private String userInserted;
    private Date dateInserted;
    private String userLastUpdate;
    private Date dateLastUpdate;
    private Long version;

    @Column(name = "usr_log_i")
    public String getUserInserted() {
        return userInserted;
    }

    @Deprecated
    public void setUserInserted(String userInserted) {
        this.userInserted = userInserted;
    }

    @Column(name = "dte_log_i")
    public Date getDateInserted() {
        return dateInserted;
    }

    @Deprecated
    public void setDateInserted(Date dateInserted) {
        this.dateInserted = dateInserted;
    }

    @Column(name = "usr_log_lu")
    public String getUserLastUpdate() {
        return userLastUpdate;
    }

    @Deprecated
    public void setUserLastUpdate(String userLastUpdate) {
        this.userLastUpdate = userLastUpdate;
    }

    @Column(name = "dte_log_lu")
    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    @Deprecated
    public void setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    @Column(name = "version")
    @Version
    public Long getVersion() {
        return version;
    }

    /**
     * Only used by Hibernate.
     * 
     * @param version
     */
    @Deprecated
    public void setVersion(Long version) {
        this.version = version;
    }

}