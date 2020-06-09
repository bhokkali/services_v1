package com.myschool.kmhss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myschool.kmhss.dao.PermissionsDao;
import com.myschool.kmhss.dao.SchoolDao;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubadminsDto {

    private Long id;
    private Long schoolId;
    private String loginName;
    private String loginPwd;
    private String schoolName;
    private String schoolCode;
    private Long academicYearId;
    private String academicYear;
    private List<Long> permissions;
    private List<PermissionsDao> permissionsDaos;
}
