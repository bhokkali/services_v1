package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="time_table")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TimeTableDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="time_table_id")
    private Long id;

    @Column(name="school_id")
    private Long schoolId;

    @Column(name="academic_year_id")
    private Long academicYearId;

    @ManyToOne
    @JoinColumn(name="academic_year_id", insertable = false, updatable = false)
    public AcademicYearDao academicYearDao;

    @Column(name="school_grade_id")
    private Long schoolGradeId;

    @ManyToOne
    @JoinColumn(name="school_grade_id", insertable = false, updatable = false)
    public SchoolGradeDao schoolGradeDao;

    @Column(name="period_id")
    private Long periodId;

    @ManyToOne
    @JoinColumn(name="period_id", insertable = false, updatable = false)
    public PeriodsDao periodsDao;

    @Column(name="subject_id")
    private Long subjectId;

    @ManyToOne
    @JoinColumn(name="subject_id", insertable = false, updatable = false)
    public SubjectDao subjectDao;

    @Column(name="teacher_id")
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name="teacher_id", insertable = false, updatable = false)
    public TeacherDao teacherDao;

    @Column(name="weekday")
    private String weekday;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

}
