package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.GradeDao;
import com.myschool.kmhss.dao.ParentsDao;
import com.myschool.kmhss.dao.SchoolCircularsDao;
import com.myschool.kmhss.dto.SchoolCircularsDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.SchoolCircularsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolCircularsService {

    @Autowired
    SchoolCircularsRepository schoolCircularsRepository;

    public void createUpdateCircular(SchoolCircularsDao schoolCircularsDao) throws CustomException {
        if(schoolCircularsDao.getId() == null) {
            schoolCircularsDao.setCreatedDate(new Date());
        } else {
            Optional<SchoolCircularsDao> schoolCircularsDao1 = schoolCircularsRepository.findById(schoolCircularsDao.getId());
            if(!schoolCircularsDao1.isPresent()) {
                throw new CustomException(HttpStatus.NO_CONTENT, "Invalid Circular Id");
            }
            schoolCircularsDao.setUpdatedDate(new Date());
        }
        schoolCircularsRepository.save(schoolCircularsDao);
    }

    public List<SchoolCircularsDto> listSchoolCirculars(Long schoolId, Long academicYearId) throws CustomException {
        List<SchoolCircularsDao> schoolCircularsDaos = schoolCircularsRepository.findBySchoolIdAndAcademicYearId(schoolId, academicYearId);
        return convertDaoTODto(schoolCircularsDaos);
    }

    private List<SchoolCircularsDto> convertDaoTODto(List<SchoolCircularsDao> schoolCircularsDaos) {
        List<SchoolCircularsDto> schoolCircularsDtos = new ArrayList<>();

        for(SchoolCircularsDao schoolCircularsDao: schoolCircularsDaos) {
            SchoolCircularsDto dto = new SchoolCircularsDto();
            BeanUtils.copyProperties(schoolCircularsDao, dto);
            schoolCircularsDtos.add(dto);
        }

        return schoolCircularsDtos;
    }
}
