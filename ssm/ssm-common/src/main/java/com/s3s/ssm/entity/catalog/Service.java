package com.s3s.ssm.entity.catalog;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "s_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class Service extends Product {

}
