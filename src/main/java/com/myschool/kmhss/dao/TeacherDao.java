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
@Table(name= "teachers")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TeacherDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    public Long id;

    @Column(name="school_id")
    public Long schoolId;

    @Column(name="teacher_name")
    public String teacherName;

    @Column(name="designation")
    public String designation;

    @Column(name="qualification")
    public String qualification;

    @Column(name="mobile_no")
    public Long mobileNo;

    @Column(name="email")
    public String email;

    @Column(name="aadhar_no")
    public Long aadharNo;

    @Column(name="address")
    public String address;

    //@Column(name="subjects")
    //public List<Long> subjects;

    @OneToOne
    @JoinColumn(name="school_id", insertable = false, updatable= false)
    public SchoolDao schoolDao;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="teacher_id", referencedColumnName = "teacher_id", insertable = false, updatable= false)
    public List<TeachersSubjectsXrefDao> subjectInfo;

    @Column(name="status")
    public String status;

    @Column(name="login_name")
    public String loginName;

    @Column(name="login_pwd")
    public String loginPwd;

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
