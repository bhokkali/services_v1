package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.SuperAdminDao;
import com.myschool.kmhss.dto.SuperAdminDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class SuperAdminController {

    @Autowired
    SuperAdminService superAdminService;

    @PostMapping(value = "/adminLogin")
    public ResponseEntity<SuperAdminDto> adminLogin(@RequestBody SuperAdminDao superAdminDao) throws CustomException {
        SuperAdminDto superAdminDto = superAdminService.adminLogin(superAdminDao);
        return new ResponseEntity<SuperAdminDto>(superAdminDto, HttpStatus.OK);
    }

}
