package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "school_circulars")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SchoolCircularsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="circular_id")
    public Long id;

    @Column(name="school_id")
    public Long schoolId;

    @Column(name="academic_year_id")
    public Long academicYearId;

    @Column(name="circular_date")
    public String circularDate;

    @Column(name="circular_title")
    public String circularTitle;

    @Column(name="circular_message")
    public String circularMessage;

    @Column(name="circular_to")
    public String circularTo;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

}
