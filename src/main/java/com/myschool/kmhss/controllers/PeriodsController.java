package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.ParentsDao;
import com.myschool.kmhss.dao.PeriodsDao;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.PeriodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PeriodsController {

    @Autowired
    PeriodsService periodsService;

    @GetMapping(value="/getSchoolPeriods/{schoolId}")
    public ResponseEntity<List<PeriodsDao>>  getSchoolPeriods(@PathVariable Long schoolId) throws CustomException {
        List<PeriodsDao> periodsDaos = periodsService.getSchoolPeriods(schoolId);
        return new ResponseEntity<List<PeriodsDao>>(periodsDaos, HttpStatus.OK);
    }

    @PutMapping(value = "createUpdatePeriod")
    public String createUpdatePeriod(@RequestBody PeriodsDao periodsDao) throws CustomException {
        String result = periodsDao.getId() == null ? "Period created successfully" : "Period updated successfully";
        periodsService.createUpdatePeriod(periodsDao);
        return result;
    }

    @DeleteMapping(value = "deletePeriod/{periodId}")
    public String deletePeriod(@PathVariable Long periodId) throws CustomException {
        periodsService.deletePeriod(periodId);
        return "Period deleted successfully";
    }
}
