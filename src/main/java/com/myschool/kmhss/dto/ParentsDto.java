package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myschool.kmhss.dao.StudentsDao;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParentsDto {

    private Long id;
    private Long schoolId;
    private String parentName;
    private String designation;
    private String qualification;
    private String relationship;
    private Long mobileNo;
    private Long aadharNo;
    private String email;
    private String address;
    private String status;
    private String loginPwd;

}
