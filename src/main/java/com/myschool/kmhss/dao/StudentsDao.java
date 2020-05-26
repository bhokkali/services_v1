package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "students")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StudentsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    public Long id;

    @Column(name="school_id")
    public Long schoolId;

    @Column(name="student_name")
    public String studentName;

    @Column(name="parent_id")
    private Long parentId;

    @ManyToOne
    @JoinColumn(name="parent_id", insertable = false, updatable= false)
    //public Long parentId;
    public ParentsDao parentsDao;

    @Column(name="register_no")
    private String registerNo;

    @Column(name="blood_group")
    public String bloodGroup;

    @Column(name="mobile_no")
    public Long mobileNo;

    @Column(name="aadhar_no")
    public Long aadharNo;

    @Column(name="dob")
    public String dob;

    @Column(name="gender")
    public String gender;

    @Column(name="community")
    public String community;

    @Column(name="nationality")
    public String nationality;

    @Column(name="religion")
    public String religion;

    @Column(name="status")
    public String status;

    @Column(name="joining_date")
    public String joiningDate;

    @Column(name="releving_date")
    public String relevingDate;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;
}
