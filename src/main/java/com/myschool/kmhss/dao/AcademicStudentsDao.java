package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "academic_students")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AcademicStudentsDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="academic_student_id")
    public Long id;

    @Column(name="school_id")
    public Long schoolId;

    @Column(name="academic_year_id")
    public Long academicYearId;

    @Column(name="school_grade_id")
    public Long schoolGradeId;

    @Column(name="student_id")
    public Long studentId;

    @Column(name="promotion_status")
    public String promotionStatus;

    @Column(name = "promotion_grade_id")
    public Long promotionGradeId;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name="student_id", insertable = false, updatable = false)
    public StudentsDao studentsDao;

    @ManyToOne
    @JoinColumn(name="school_grade_id", insertable = false, updatable = false)
    public SchoolGradeDao schoolGradeDao;

    @ManyToOne
    @JoinColumn(name="academic_year_id", insertable = false, updatable = false)
    public AcademicYearDao academicYearDao;

}
