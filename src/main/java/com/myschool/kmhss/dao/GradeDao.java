package com.myschool.kmhss.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="grades")
public class GradeDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grade_id")
    private Long id;

    @Column(name="grade_name")
    private String grade_name;

    @Column(name="priority")
    private Long priority;

    /*@OneToMany(mappedBy = "gradeDao")
    private SchoolGradeDao schoolGradeDao;
       */
    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

}
