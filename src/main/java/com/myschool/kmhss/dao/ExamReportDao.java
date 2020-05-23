package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="exam_report")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExamReportDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id")
    public Long id;

    @Column(name = "exam_id")
    public Long examId;

    @Column(name="school_grade_id")
    public Long schoolGradeId;

    @Column(name="student_id")
    public Long studentId;

    @Column(name="total_marks")
    public Double totalMarks;

    @Column(name="total_max_marks")
    public Double totalMaxMarks;

    @Column(name="overallPercentage")
    public Double overallPercentage;

    @Column(name="overallGrade")
    public String overallGrade;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name="exam_id", insertable = false, updatable = false)
    public ExamsDao examsDao;

    @ManyToOne
    @JoinColumn(name="school_grade_id", insertable = false, updatable = false)
    public SchoolGradeDao schoolGradeDao;

    @ManyToOne
    @JoinColumn(name="student_id", insertable = false, updatable = false)
    public StudentsDao studentsDao;
}
