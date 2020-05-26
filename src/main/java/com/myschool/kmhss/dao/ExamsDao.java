package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="exams")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExamsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="exam_id")
    public Long id;

    @Column(name="school_id")
    public Long schoolId;

    @Column(name="academic_year_id")
    public Long academicYearId;

    @Column(name="exam_name")
    public String examName;

    @Column(name="start_date")
    public String startDate;

    @Column(name="end_date")
    public String endDate;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name="academic_year_id", insertable = false, updatable = false)
    public AcademicYearDao academicYearDao;
}
