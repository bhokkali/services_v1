package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myschool.kmhss.dao.AcademicYearDao;
import com.myschool.kmhss.dao.SchoolDao;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TeacherAppDto {

    private Long id;
    private Long schoolId;
    private String teacherName;
    private String designation;
    private String qualification;
    private Long mobileNo;
    private Long aadharNo;
    private String email;
    private String address;
    private String status;
    private Date joiningDate;
    private Date relevingDate;
    public String loginName;
    public String loginPwd;
    public List<Long> subjects;
    public List<TeachersSubjectsXrefDto> subjectInfo;
    private SchoolDao schoolInfo;
    private AcademicYearDao currentAcademicYearInfo;

}
