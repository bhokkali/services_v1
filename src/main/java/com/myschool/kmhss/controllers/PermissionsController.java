package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.PermissionsDao;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PermissionsController {

    @Autowired
    PermissionsService permissionsService;

    @GetMapping(value="/listPermissions")
    public ResponseEntity<List<PermissionsDao>> getPermissons() throws CustomException {
        List<PermissionsDao> periodsDaos = permissionsService.getPermissons();
        return new ResponseEntity<List<PermissionsDao>>(periodsDaos, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdatePermission")
    public String createUpdatePermission(@RequestBody PermissionsDao permissionsDao) throws CustomException {
        String result = permissionsDao.getId() == null ? "Permission added successfully" : "Permission updated successfully";
        permissionsService.createUpdatePermission(permissionsDao);
        return result;
    }
}
