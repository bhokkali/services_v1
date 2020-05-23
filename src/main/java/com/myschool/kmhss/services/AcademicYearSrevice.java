package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicYearDao;
import com.myschool.kmhss.dto.AcademicYearDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.AcademicYearRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AcademicYearSrevice {
    @Autowired
    AcademicYearRepository academicYearRepository;
    
    public List<AcademicYearDto> getAcademicYearsList() {
        List<AcademicYearDao> academicYearDaos = academicYearRepository.findAll();
        List<AcademicYearDto> academicYearDtos = new ArrayList<>();
        for(AcademicYearDao academicYearDao: academicYearDaos) {
            AcademicYearDto dto = new AcademicYearDto();
            BeanUtils.copyProperties(academicYearDao, dto);
            academicYearDtos.add(dto);
        }
        return academicYearDtos;
    }

    public AcademicYearDao getAcademicYearInfo(Long id) throws CustomException {
        Optional<AcademicYearDao> academicYearDaoOptional = academicYearRepository.findById(id);
        if(academicYearDaoOptional.isPresent()) {
            return academicYearDaoOptional.get();
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Academic Year");
        }
    }

    public void createUpdateAcademicYear(AcademicYearDao academicYearDao) throws CustomException {
            if (academicYearDao.getId() == null) {
                Optional<AcademicYearDao> academicYearDaoOptional = Optional.ofNullable(academicYearRepository.findByAcademicYear(academicYearDao.getAcademicYear()));
                if(!academicYearDaoOptional.isPresent()) {
                    academicYearDao.setCreatedDate(new Date());
                } else {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Academic year already exists");
                }
            } else {
                Optional<AcademicYearDao> academicYearDaoOptional1 = academicYearRepository.findById(academicYearDao.getId());
                if(academicYearDaoOptional1.isPresent()) {
                    academicYearDao.setUpdatedDate(new Date());
                } else {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Academic year");
                }
            }
            academicYearRepository.save(academicYearDao);
    }

    public AcademicYearDao getNextAcademicYear(Long academicYearId) {
        AcademicYearDao academicYearDao = academicYearRepository.findNextAcademicYear(academicYearId);
        return academicYearDao;
    }

    public AcademicYearDao getAcademicYearInfoFromYearString(String academicYear) throws CustomException {
        Optional<AcademicYearDao> academicYearDaoOptional = Optional.ofNullable(academicYearRepository.findByAcademicYear(academicYear));
        if(!academicYearDaoOptional.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Academic year not added, please contact admin");
        }
        return academicYearDaoOptional.get();
    }
}
