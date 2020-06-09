package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="permissions")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PermissionsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="permission_id")
    private Long id;

    @Column(name="permission_name")
    private String permissionName;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;
}
