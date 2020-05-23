package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myschool.kmhss.dao.ParentsDao;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentsAppDto {

    private Long id;
    private Long key;
    private Long schoolId;
    private String studentName;
    private Long parentId;
    private String registerNo;
    private String bloodGroup;
    private Long mobileNo;
    private Long aadharNo;
    public String gender;
    public String community;
    public String nationality;
    public String religion;
    private String status;
    private Date joiningDate;
    private Date relevingDate;

    private String academicYear;
    private String gradeName;
    private Long schoolGradeId;
    private Long academicYearId;
    private Long academicStudentId;

}
