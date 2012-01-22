package com.s3s.ssm.entity.operator;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.security.User;

@Entity
@Table(name = "o_stall")
public class Stall extends AbstractCodeOLObject {
    private String name;
    private User manager;
    private Boolean isActive = true;
    private List<User> staffs;
    private List<SaleTarget> salesTarget = new ArrayList<SaleTarget>();

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", nullable = false)
    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Column(name = "is_active", nullable = false)
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "o_stall_user", joinColumns = { @JoinColumn(name = "id_stall") }, inverseJoinColumns = { @JoinColumn(name = "id_user") })
    public
            List<User> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<User> staffs) {
        this.staffs = staffs;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stall", fetch = FetchType.LAZY)
    public List<SaleTarget> getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(List<SaleTarget> salesTarget) {
        this.salesTarget = salesTarget;
    }

}
