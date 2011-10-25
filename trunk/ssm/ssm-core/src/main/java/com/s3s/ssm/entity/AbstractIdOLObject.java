package com.s3s.ssm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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

    @Column(name = "usr_log_i", nullable = false)
    public String getUserInserted() {
        return userInserted;
    }

    @Deprecated
    public void setUserInserted(String userInserted) {
        this.userInserted = userInserted;
    }

    @Column(name = "dte_log_i", nullable = false)
    public Date getDateInserted() {
        return dateInserted;
    }

    @Deprecated
    public void setDateInserted(Date dateInserted) {
        this.dateInserted = dateInserted;
    }

    @Column(name = "usr_log_lu", nullable = false)
    public String getUserLastUpdate() {
        return userLastUpdate;
    }

    @Deprecated
    public void setUserLastUpdate(String userLastUpdate) {
        this.userLastUpdate = userLastUpdate;
    }

    @Column(name = "dte_log_lu", nullable = false)
    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    @Deprecated
    public void setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

}
