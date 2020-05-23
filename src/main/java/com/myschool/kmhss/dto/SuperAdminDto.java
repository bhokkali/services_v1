package com.myschool.kmhss.dto;

import lombok.Data;

@Data
public class SuperAdminDto {

    private Long id;
    private String loginName;
    private String loginPwd;
    private Boolean isAdmin;
}
