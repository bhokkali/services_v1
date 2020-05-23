package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.GradeDao;
import com.myschool.kmhss.dao.SchoolGradeDao;
import com.myschool.kmhss.dao.TimeTableDao;
import com.myschool.kmhss.dto.TimeTableDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TimeTableController {

    @Autowired
    TimeTableService timeTableService;

    @PutMapping(value = "/createTimeTable")
    public ResponseEntity<String> createTimeTable(@RequestBody TimeTableDao timeTableDao) throws CustomException {
        timeTableService.createTimeTable(timeTableDao);
        return new ResponseEntity<String>("Time table created", HttpStatus.OK);
    }

    @GetMapping(value = "/listSchoolTimeTable/{schoolId}/{academicYearId}")
    public ResponseEntity<List<TimeTableDto>> listSchoolTimeTable(@PathVariable Long schoolId, @PathVariable Long academicYearId) throws CustomException {
        List<TimeTableDto> timeTableDtos = timeTableService.listSchoolTimeTable(schoolId, academicYearId);
        return new ResponseEntity<List<TimeTableDto>>(timeTableDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/getGradeTimeTable")
    public ResponseEntity<List<TimeTableDto>> listGradeTimeTable(@RequestBody SchoolGradeDao schoolGradeDao) throws CustomException {
        List<TimeTableDto> timeTableDtos = timeTableService.listGradeTimeTable(schoolGradeDao.getSchoolId(), schoolGradeDao.getAcademicYearId(), schoolGradeDao.getId());
        return new ResponseEntity<List<TimeTableDto>>(timeTableDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getTeacherTimeTable")
    public ResponseEntity<List<TimeTableDto>> getTeacherTimeTable(
            @PathParam("school_id") Long school_id,
            @PathParam("academic_year_id") Long academic_year_id,
            @PathParam("teacher_id") Long teacher_id
    ) throws CustomException {
        List<TimeTableDto> timeTableDtos = timeTableService.listTeacherTimeTable(school_id, academic_year_id, teacher_id);
        return new ResponseEntity<List<TimeTableDto>>(timeTableDtos, HttpStatus.OK);
    }
}
