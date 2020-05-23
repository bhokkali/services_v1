package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.PeriodsDao;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.PeriodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodsService {
    @Autowired
    PeriodsRepository periodsRepository;

    public List<PeriodsDao> getSchoolPeriods(Long SchoolId) {
        List<PeriodsDao> periodsDaos = periodsRepository.findBySchoolIdOrderByPriorityAsc(SchoolId);
        return periodsDaos;
    }

    public void createUpdatePeriod(PeriodsDao periodsDao) throws CustomException {
        if (periodsDao.getId() == null) {
            periodsDao.setCreatedDate(new Date());
        } else {
            Optional<PeriodsDao> periodsDao1 = periodsRepository.findById(periodsDao.getId());
            if(periodsDao1.isPresent()) {
                periodsDao.setUpdatedDate(new Date());
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Period Id");
            }
        }
        periodsRepository.save(periodsDao);
    }

    public void deletePeriod(Long periodId) throws CustomException {
        Optional<PeriodsDao> periodsDao1 = periodsRepository.findById(periodId);
        if(!periodsDao1.isPresent()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Period Id");
        }
        periodsRepository.deleteById(periodId);
    }




}
