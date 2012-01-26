package com.s3s.ssm.entity.operator;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Operator> staffs = new HashSet<Operator>();
    private Set<SaleTarget> salesTarget = new HashSet<SaleTarget>();

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "o_stall_user", joinColumns = { @JoinColumn(name = "id_stall") }, inverseJoinColumns = { @JoinColumn(name = "id_user") })
    public
            Set<Operator> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Operator> staffs) {
        this.staffs = staffs;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stall", fetch = FetchType.EAGER)
    public Set<SaleTarget> getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(Set<SaleTarget> salesTarget) {
        this.salesTarget = salesTarget;
    }

    public String toString() {
        return name;
    }
}
