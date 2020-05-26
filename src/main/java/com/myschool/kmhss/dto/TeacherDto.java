package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myschool.kmhss.dao.TeachersSubjectsXrefDao;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TeacherDto {

    private Long id;
    private Long schoolId;
    private String teacherName;
    private String designation;
    private String qualification;
    private Long mobileNo;
    private Long aadharNo;
    private String email;
    private String address;
   // private String subjects;
    private String status;
    private String joiningDate;
    private String relevingDate;
    private String loginName;
    private String loginPwd;
    private List<Long> subjects;
    //public List<TeachersSubjectsXrefDao> subjectInfo;
    private List<TeachersSubjectsXrefDto> subjectInfo;

}
