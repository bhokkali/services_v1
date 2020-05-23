package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.MarkGradesDao;
import com.myschool.kmhss.dto.MarkGradesDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.MarkGradesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MarkGradesService {

    @Autowired
    MarkGradesRepository markGradesRepository;

    public void createUpdateMarkGrade(MarkGradesDao markGradesDao) throws CustomException {
        if(markGradesDao.getId() == null) {
            markGradesDao.setCreatedDate(new Date());
        } else {
            markGradesDao.setUpdatedDate(new Date());
        }
        markGradesRepository.save(markGradesDao);
    }

    public List<MarkGradesDto> listMarkGrades(Long schoolId) throws CustomException {
        List<MarkGradesDao> markGradesDaos = markGradesRepository.findBySchoolId(schoolId);
        return convertDaoToDto(markGradesDaos);
    }

    private List<MarkGradesDto> convertDaoToDto(List<MarkGradesDao> markGradesDaos) {
        List<MarkGradesDto> markGradesDtos = new ArrayList<>();

        for(MarkGradesDao markGradesDao: markGradesDaos) {
            MarkGradesDto dto = new MarkGradesDto();
            BeanUtils.copyProperties(markGradesDao, dto);
            markGradesDtos.add(dto);
        }
        return markGradesDtos;
    }

    public String getSpecificGrade(Integer mark) throws CustomException {
        return markGradesRepository.findByMark(mark);
    }

}
