package com.myschool.kmhss.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myschool.kmhss.dto.TeacherDto;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="teacher_attendance")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TeacherAttendanceDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="teacher_attendance_id")
    public Long id;

    @Column(name="school_id")
    public Long schoolId;

    @Column(name="academic_year_id")
    public Long academicYearId;

    @Column(name="teacher_id")
    public Long teacherId;

    @Column(name = "from_date")
    public String fromDate;

    @Column(name = "to_date")
    public String toDate;

    @Column(name="absent_period")
    public String absentPeriod;

    @Column(name = "leave_type")
    public String leaveType;

    @Column(name = "reason")
    public String reason;

    @Temporal(TemporalType.DATE)
    @Column(name="cr_date", nullable = false, updatable = false)
    public Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name="up_date", nullable = true, updatable = true)
    public Date updatedDate;

    @ManyToOne
    @JoinColumn(name="teacher_id", insertable = false, updatable = false)
    public TeacherDao teacherDao;

    @ManyToOne
    @JoinColumn(name="academic_year_id", insertable = false, updatable = false)
    public AcademicYearDao academicYearDao;

}
