package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="exam_grades")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExamGradesDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="exam_grade_id")
    public Long id;

    @Column(name = "exam_id")
    public Long examId;

    @Column(name="school_grade_id")
    public Long schoolGradeId;

    @Column(name="subject_id")
    public Long subjectId;

    @Temporal(TemporalType.DATE)
    @Column(name="exam_date")
    public Date examDate;

    @Column(name="max_mark")
    public Long maxMark;

    @Column(name="time_from")
    public String timeFrom;

    @Column(name="time_to")
    public String timeTo;

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
    @JoinColumn(name="subject_id", insertable = false, updatable = false)
    public SubjectDao subjectDao;

}
