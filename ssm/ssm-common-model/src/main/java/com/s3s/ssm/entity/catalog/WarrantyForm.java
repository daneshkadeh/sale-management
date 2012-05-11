package com.s3s.ssm.entity.catalog;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * Each sold article can have a warranty form. (Phieu bao hanh)
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "ca_warranty_form")
public class WarrantyForm extends AbstractIdOLObject {
    private static final long serialVersionUID = 1L;
    private Article article;
    private Date startMaintainDate;
    private Date endMaintainDate;
    private Set<DetailWarranty> detailsWarranties = new HashSet<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Column(name = "start_maintain_date")
    public Date getStartMaintainDate() {
        return startMaintainDate;
    }

    public void setStartMaintainDate(Date startMaintainDate) {
        this.startMaintainDate = startMaintainDate;
    }

    @Column(name = "end_maintain_date")
    public Date getEndMaintainDate() {
        return endMaintainDate;
    }

    public void setEndMaintainDate(Date endMaintainDate) {
        this.endMaintainDate = endMaintainDate;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "warrantyForm")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<DetailWarranty> getDetailsWarranties() {
        return detailsWarranties;
    }

    public void setDetailsWarranties(Set<DetailWarranty> detailsWarranties) {
        this.detailsWarranties = detailsWarranties;
    }
}
