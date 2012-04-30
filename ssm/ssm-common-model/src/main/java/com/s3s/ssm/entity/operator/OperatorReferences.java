package com.s3s.ssm.entity.operator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.config.Institution;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.SalesChannel;

/**
 * This class stores current context of operator. It's updated when operator change context, menus.
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "operator_operator_references")
public class OperatorReferences extends AbstractIdOLObject {
    private Operator operator;
    private Institution currInstitution;
    private Organization currOrganization;
    private SalesChannel currSalesChannel;
    private Stall currStall;

    // class name of the view, will be shown immediately when user login the system. This is stored when operator exits
    // the application.
    private String lastMenuScreen;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operator_id")
    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curr_institution_id")
    public Institution getCurrInstitution() {
        return currInstitution;
    }

    public void setCurrInstitution(Institution currInstitution) {
        this.currInstitution = currInstitution;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curr_organization_id")
    public Organization getCurrOrganization() {
        return currOrganization;
    }

    public void setCurrOrganization(Organization currOrganization) {
        this.currOrganization = currOrganization;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curr_saleschan_id")
    public SalesChannel getCurrSalesChannel() {
        return currSalesChannel;
    }

    public void setCurrSalesChannel(SalesChannel currSalesChannel) {
        this.currSalesChannel = currSalesChannel;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curr_stall_id")
    public Stall getCurrStall() {
        return currStall;
    }

    public void setCurrStall(Stall currStall) {
        this.currStall = currStall;
    }

    @Column(name = "last_menu_screen", length = 256)
    public String getLastMenuScreen() {
        return lastMenuScreen;
    }

    public void setLastMenuScreen(String lastMenuScreen) {
        this.lastMenuScreen = lastMenuScreen;
    }

}
