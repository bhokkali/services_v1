package com.myschool.kmhss.dto;

import com.myschool.kmhss.dao.PermissionsDao;
import lombok.Data;

import java.util.List;

@Data
public class SubadminsLoginDto {

    private Long id;
    private Long schoolId;
    private String loginName;
    private SchoolDto schoolDto;
    private List<PermissionsDao> permissionsDaos;

}
