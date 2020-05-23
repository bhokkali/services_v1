package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.SuperAdminDao;
import com.myschool.kmhss.dto.SuperAdminDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.SuperAdminRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuperAdminService {

    @Autowired
    SuperAdminRepository superAdminRepository;

    public SuperAdminDto adminLogin(SuperAdminDao superAdminDao) throws CustomException {
        Optional<SuperAdminDao> superAdminDaoOptional =  Optional.ofNullable(superAdminRepository.findByLoginNameAndLoginPwd(superAdminDao.getLoginName(), superAdminDao.getLoginPwd()));
        if(superAdminDaoOptional.isPresent()) {
            SuperAdminDto superAdminDto = new SuperAdminDto();
            BeanUtils.copyProperties(superAdminDaoOptional.get(), superAdminDto);
            superAdminDto.setIsAdmin(true);
            return superAdminDto;
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Username or password");
        }
    }
}
