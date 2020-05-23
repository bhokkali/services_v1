package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "parents")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParentsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id")
    public Long id;

    @Column(name="school_id")
    public Long schoolId;

    @Column(name="parent_name")
    public String parentName;

    @Column(name="designation")
    public String designation;

    @Column(name="qualification")
    public String qualification;

    @Column(name="relationship")
    public String relationship;

    @Column(name="mobile_no", unique=true)
    public Long mobileNo;

    @Column(name="email", unique=true)
    public String email;

    @Column(name="aadhar_no")
    public Long aadharNo;

    @Column(name="address")
    public String address;

    @Column(name="status")
    public String status;

    @Column(name="login_pwd")
    public String loginPwd;

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
