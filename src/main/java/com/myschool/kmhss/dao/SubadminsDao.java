package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "subadmins")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SubadminsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subadmin_id")
    private Long id;

    @Column(name="school_id")
    private Long schoolId;

    @Column(name="login_name")
    private String loginName;

    @Column(name="login_pwd")
    private String loginPwd;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="subadmin_id", referencedColumnName = "subadmin_id", insertable = false, updatable= false)
    public List<AdminPermissionsDao> adminPermissionsDaos;

    @OneToOne
    @JoinColumn(name="school_id", insertable = false, updatable= false)
    public SchoolDao schoolDao;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;
}

