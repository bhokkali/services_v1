package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.SchoolDao;
import com.myschool.kmhss.dto.SchoolDto;
import com.myschool.kmhss.exception.CustomException;

import java.util.List;

public interface SchoolService {
    void createUpdateSchool(SchoolDao schoolDao) throws CustomException;
    List<SchoolDto> listSchools();
}
