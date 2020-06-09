package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.PermissionsDao;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.PermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionsService {

    @Autowired
    PermissionsRepository permissionsRepository;

    public List<PermissionsDao> getPermissons() {
        List<PermissionsDao> permissionsDaos = permissionsRepository.findAll();
        return permissionsDaos;
    }

    public void createUpdatePermission(PermissionsDao permissionsDao) throws CustomException {
        if (permissionsDao.getId() == null) {
            permissionsDao.setCreatedDate(new Date());
        } else {
            Optional<PermissionsDao> permissionsDao1 = permissionsRepository.findById(permissionsDao.getId());
            if(permissionsDao1.isPresent()) {
                permissionsDao.setUpdatedDate(new Date());
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Permission Id");
            }
        }
        permissionsRepository.save(permissionsDao);
    }

}
