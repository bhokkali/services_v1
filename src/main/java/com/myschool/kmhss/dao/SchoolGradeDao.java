package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "school_grades")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SchoolGradeDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_grade_id")
    private Long id;

    @Column(name="school_id")
    private Long schoolId;

    @Column(name="grade_id")
    private Long gradeId;

    @Column(name="teacher_id")
    private Long teacherId;

    @Column(name="academic_year_id")
    private Long academicYearId;

    @Column(name="section_name")
    private String sectionName;

    @ManyToOne
    @JoinColumn(name = "grade_id", insertable = false, updatable= false)
    private GradeDao gradeDao;

    @ManyToOne
    @JoinColumn(name="teacher_id", insertable = false, updatable= false)
    public TeacherDao teacherDao;

    @ManyToOne
    @JoinColumn(name="academic_year_id", insertable = false, updatable = false)
    public AcademicYearDao academicYearDao;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

}
