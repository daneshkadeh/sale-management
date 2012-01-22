package com.s3s.ssm.entity.operator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.view.component.SaleTargetModel;

@Entity
@Table(name = "o_sale_target")
public class SaleTarget extends AbstractIdOLObject implements SaleTargetModel {
    private Stall stall;
    private Integer year;
    private Double month1 = (double) 0;
    private Double month2 = (double) 0;
    private Double month3 = (double) 0;
    private Double month4 = (double) 0;
    private Double month5 = (double) 0;
    private Double month6 = (double) 0;
    private Double month7 = (double) 0;
    private Double month8 = (double) 0;
    private Double month9 = (double) 0;
    private Double month10 = (double) 0;
    private Double month11 = (double) 0;
    private Double month12 = (double) 0;

    @ManyToOne
    @JoinColumn(name = "id_stall", nullable = false)
    public Stall getStall() {
        return stall;
    }

    public void setStall(Stall stall) {
        this.stall = stall;
    }

    @Column(name = "year", length = 4)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "month1")
    public Double getMonth1() {
        return month1;
    }

    public void setMonth1(Double month1) {
        this.month1 = month1;
    }

    @Column(name = "month2")
    public Double getMonth2() {
        return month2;
    }

    public void setMonth2(Double month2) {
        this.month2 = month2;
    }

    @Column(name = "month3")
    public Double getMonth3() {
        return month3;
    }

    public void setMonth3(Double month3) {
        this.month3 = month3;
    }

    @Column(name = "month4")
    public Double getMonth4() {
        return month4;
    }

    public void setMonth4(Double month4) {
        this.month4 = month4;
    }

    @Column(name = "month5")
    public Double getMonth5() {
        return month5;
    }

    public void setMonth5(Double month5) {
        this.month5 = month5;
    }

    @Column(name = "month6")
    public Double getMonth6() {
        return month6;
    }

    public void setMonth6(Double month6) {
        this.month6 = month6;
    }

    @Column(name = "month7")
    public Double getMonth7() {
        return month7;
    }

    public void setMonth7(Double month7) {
        this.month7 = month7;
    }

    @Column(name = "month8")
    public Double getMonth8() {
        return month8;
    }

    public void setMonth8(Double month8) {
        this.month8 = month8;
    }

    @Column(name = "month9")
    public Double getMonth9() {
        return month9;
    }

    public void setMonth9(Double month9) {
        this.month9 = month9;
    }

    @Column(name = "month10")
    public Double getMonth10() {
        return month10;
    }

    public void setMonth10(Double month10) {
        this.month10 = month10;
    }

    @Column(name = "month11")
    public Double getMonth11() {
        return month11;
    }

    public void setMonth11(Double month11) {
        this.month11 = month11;
    }

    @Column(name = "month12")
    public Double getMonth12() {
        return month12;
    }

    public void setMonth12(Double month12) {
        this.month12 = month12;
    }
}
