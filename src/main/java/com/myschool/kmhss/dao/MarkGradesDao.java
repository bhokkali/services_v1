package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="mark_grades")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MarkGradesDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mark_grade_id")
    public Long id;

    @Column(name="school_id")
    public Long schoolId;

    @Column(name="min_mark")
    public Double minMark;

    @Column(name="max_mark")
    public Double maxMark;

    @Column(name="mark_grade")
    public String markGrade;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

}
