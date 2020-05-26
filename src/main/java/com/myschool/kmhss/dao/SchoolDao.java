package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="schools")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SchoolDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id", nullable = false, unique = true)
    private Long id;

    @Column(name="school_name")
    private String school_name;

    @Column(name="school_code")
    private String school_code;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "syllabus")
    private String syllabus;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name="active_status")
    private String activeStatus;

    @Column(name="login_name")
    private String loginName;

    @Column(name="login_pwd")
    private String loginPwd;

    @Column(name="exp_date", nullable = false, updatable = false)
    private String expiryDate;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

}
