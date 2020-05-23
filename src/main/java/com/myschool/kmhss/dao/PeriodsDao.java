package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="periods_master")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PeriodsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="period_id")
    private Long id;

    @Column(name="school_id")
    private Long schoolId;

    @Column(name="priority")
    private Long priority;

    @Column(name="period_name")
    private String periodName;

    @Column(name="period_type")
    private String periodType;

    @Column(name="period_short_name")
    private String periodShortName;

    @Column(name="time_from")
    private String timeFrom;

    @Column(name="time_to")
    private String timeTo;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    private Date updatedDate;

}
