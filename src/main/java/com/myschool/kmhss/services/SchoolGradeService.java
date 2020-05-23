package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.ParentsDao;
import com.myschool.kmhss.dao.SchoolGradeDao;
import com.myschool.kmhss.dto.SchoolGradeDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.SchoolGradeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolGradeService {

    @Autowired
    SchoolGradeRepository schoolGradeRepository;

    public void createUpdteSchoolGrade(SchoolGradeDao schoolGradeDao) throws CustomException {
        if (schoolGradeDao.getId() == null) {
            schoolGradeDao.setCreatedDate(new Date());
        } else {
            Optional<SchoolGradeDao> schoolGradeDaoOptional = schoolGradeRepository.findById(schoolGradeDao.getId());
            if(schoolGradeDaoOptional.isPresent()) {
                schoolGradeDao.setUpdatedDate(new Date());
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, "Invalid School Id");
            }
        }
        schoolGradeRepository.save(schoolGradeDao);
    }

    public List<SchoolGradeDto> listSchoolGrades(Long schoolId, Long academicYearId) throws CustomException {
        List<SchoolGradeDao> schoolGradeDaos = schoolGradeRepository.getGradesSchoolId(schoolId, academicYearId);
        return convertDaoTODto(schoolGradeDaos);
    }

    public String findGradeName(Long schoolId) throws CustomException {
        return schoolGradeRepository.findGradeName(schoolId);
    }

    private List<SchoolGradeDto> convertDaoTODto(List<SchoolGradeDao> schoolGradeDaos) {
        List<SchoolGradeDto> schoolGradeDtos = new ArrayList<>();

        for(SchoolGradeDao schoolGradeDao: schoolGradeDaos) {
            schoolGradeDtos.add(convertSingleDaoToDto(schoolGradeDao));
        }

        return schoolGradeDtos;
    }

    private SchoolGradeDto convertSingleDaoToDto(SchoolGradeDao schoolGradeDao) {
        SchoolGradeDto dto = new SchoolGradeDto();
        BeanUtils.copyProperties(schoolGradeDao, dto);
        dto.setGradeName(schoolGradeDao.getGradeDao().getGrade_name());
        dto.setTeacherName(schoolGradeDao.getTeacherDao().getTeacherName());
        dto.setAcademicYear(schoolGradeDao.getAcademicYearDao().getAcademicYear());
        return dto;
    }

    public SchoolGradeDto getSchoolGradeInfo(Long schoolGradeId) throws CustomException {
        Optional<SchoolGradeDao> schoolGradeDao = schoolGradeRepository.findById(schoolGradeId);
        if(!schoolGradeDao.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid School Grade Id");
        }
        return convertSingleDaoToDto(schoolGradeDao.get());
    }

    public List<SchoolGradeDto> getTeacherGrades(Long schoolId, Long academicYearId, Long teacherId) {
        List<SchoolGradeDao> schoolGradeDaos = schoolGradeRepository.getTeacherGrades(schoolId, academicYearId, teacherId);
        return convertDaoTODto(schoolGradeDaos);
    }
}
