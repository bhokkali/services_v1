package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicStudentsDao;
import com.myschool.kmhss.dao.ExamsDao;
import com.myschool.kmhss.dto.AcademicStudentsDto;
import com.myschool.kmhss.dto.ExamsDto;
import com.myschool.kmhss.repositories.ExamsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExamsService {

    @Autowired
    ExamsRepository examsRepository;

    public void createUpdateExams(ExamsDao examsDao) {

        if(examsDao.getId() == null) {
            examsDao.setCreatedDate(new Date());
        } else {
            examsDao.setUpdatedDate(new Date());
        }
        examsRepository.save(examsDao);
    }

    public List<ExamsDto> listExams(Long schoolId, Long academicYearId) {
        List<ExamsDao> examsDaos = examsRepository.findBySchoolIdAndAcademicYearId(schoolId, academicYearId);
        return convertDaoToDto(examsDaos);
    }

    private List<ExamsDto> convertDaoToDto(List<ExamsDao> examsDaos) {
        List<ExamsDto> examsDtos = new ArrayList<>();

        for(ExamsDao examsDao: examsDaos) {
            ExamsDto dto = new ExamsDto();
            BeanUtils.copyProperties(examsDao, dto);
            dto.setAcademicYear(examsDao.getAcademicYearDao().getAcademicYear());
            examsDtos.add(dto);
        }
        return examsDtos;
    }
}
