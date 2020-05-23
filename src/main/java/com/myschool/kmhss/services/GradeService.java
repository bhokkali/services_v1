package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.GradeDao;
import com.myschool.kmhss.dto.GradeDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.GradeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    @Autowired
    GradeRepository gradeRepository;

    public List<GradeDto> listGrades() throws CustomException {
        List<GradeDao> gradeDaos;
        List<GradeDto> gradeDtos = new ArrayList<>();
        gradeDaos = gradeRepository.findAll();
        for(GradeDao gradeDao: gradeDaos) {
            GradeDto gradeDto = new GradeDto();
            BeanUtils.copyProperties(gradeDao, gradeDto);
            gradeDtos.add(gradeDto);
        }
        return gradeDtos;
    }

    public void createUpdateGrade(GradeDao gradeDao) throws CustomException {
        if(gradeDao.getId() == null) {
            gradeDao.setCreatedDate(new Date());
        } else {
            gradeDao.setUpdatedDate(new Date());
        }
        gradeRepository.save(gradeDao);
    }
}
