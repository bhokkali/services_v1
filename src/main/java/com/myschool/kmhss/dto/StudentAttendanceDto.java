package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StudentAttendanceDto {

    public Long id;
    public Long academicStudentId;
    public String absentDate;
    public String absentPeriod;
    public String reason;
    public String studentName;
    public String gradeName;

}
