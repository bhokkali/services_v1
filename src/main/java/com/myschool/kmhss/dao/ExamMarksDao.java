package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="exam_marks")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExamMarksDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mark_id")
    public Long id;

    @Column(name = "exam_grade_id")
    public Long examGradeId;

    @Column(name="student_id")
    public Long studentId;

    @Column(name="attended_status")
    public String attendedStatus;

    @Column(name="mark_obtained")
    public Double markObtained;

    @Column(name="mark_percentage")
    public Double markPercentage;

    @Column(name="mark_grade")
    public String markGrade;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name="exam_grade_id", insertable = false, updatable = false)
    public ExamGradesDao examGradesDao;

    @ManyToOne
    @JoinColumn(name="student_id", insertable = false, updatable = false)
    public StudentsDao studentsDao;

}
