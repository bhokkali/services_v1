package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SchoolDto {
    private Long id;
    private String school_name;
    private String school_code;
    private String address;
    private String contactNumber;
    private String email;
    private String syllabus;
    private String panNumber;
    private String activeStatus;
    private String loginName;
    private String loginPwd;
    private Long academicYearId;
    private String academicYear;
    private Long teachersCount;
    private Long gradesCount;
    private Long studentsCount;
}
