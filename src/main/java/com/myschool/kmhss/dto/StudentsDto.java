package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myschool.kmhss.dao.ParentsDao;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentsDto {

    private Long id;
    private Long key;
    private Long schoolId;
    private String studentName;
    private Long parentId;
    private String registerNo;
    private String bloodGroup;
    private Long mobileNo;
    private Long aadharNo;
    private String dob;
    private String gender;
    private String community;
    private String nationality;
    private String religion;
    private String status;
    private String joiningDate;
    private String relevingDate;

    //private ParentsDao parentsDao;
    private String parentName;
    private Long schoolGradeId;
    private String gradeName;

}
