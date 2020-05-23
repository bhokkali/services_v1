package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="student_attendance")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StudentAttendanceDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_attendance_id")
    public Long id;

    @Column(name="academic_student_id")
    public Long academicStudentId;

    @Temporal(TemporalType.DATE)
    @Column(name = "absent_date")
    public Date absentDate;

    @Column(name="absent_period")
    public String absentPeriod;

    @Column(name = "reason")
    public String reason;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    public Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    public Date updatedDate;

    @ManyToOne
    @JoinColumn(name="academic_student_id", insertable = false, updatable = false)
    public AcademicStudentsDao academicStudentsDao;

}