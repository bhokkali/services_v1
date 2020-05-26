package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TeacherAttendanceDto {

    public Long schoolId;
    public Long academicYearId;
    public String academicYear;
    public Long teacherId;
    public String teacherName;
    public String fromDate;
    public String toDate;
    public String absentPeriod;
    public String leaveType;
    public String reason;

}
