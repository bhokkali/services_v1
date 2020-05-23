package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.SchoolGradeDao;
import com.myschool.kmhss.dto.SchoolGradeDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.SchoolGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SchoolGradeController {

    @Autowired
    SchoolGradeService schoolGradeService;

    @PutMapping(value = "/createUpdteSchoolGrade")
    public ResponseEntity<String> createUpdteSchoolGrade(@RequestBody  SchoolGradeDao schoolGradeDao) throws CustomException {
        String result = schoolGradeDao.getId() == null ? "School grade created successfully" : "School grade updated successfully";
        schoolGradeService.createUpdteSchoolGrade(schoolGradeDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/listSchoolGrades")
    public ResponseEntity<List<SchoolGradeDto>> listSchoolGrades(
            @PathParam("school_id") Long school_id,
            @PathParam("academic_year_id") Long academic_year_id
    ) throws CustomException {
        List<SchoolGradeDto> schoolGradeDtos = new ArrayList<>();
        schoolGradeDtos = schoolGradeService.listSchoolGrades(school_id, academic_year_id);
        return new ResponseEntity<List<SchoolGradeDto>>(schoolGradeDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getTeacherGrades")
    public ResponseEntity<List<SchoolGradeDto>> getTeacherGrades(
            @PathParam("school_id") Long school_id,
            @PathParam("academic_year_id") Long academic_year_id,
            @PathParam("teacher_id") Long teacher_id
    ) throws CustomException {
        List<SchoolGradeDto> schoolGradeDtos = new ArrayList<>();
        schoolGradeDtos = schoolGradeService.getTeacherGrades(school_id, academic_year_id, teacher_id);
        return new ResponseEntity<List<SchoolGradeDto>>(schoolGradeDtos, HttpStatus.OK);
    }

    /*@GetMapping(value = "/listSchoolGrades/{schoolId}")
    public ResponseEntity<List<SchoolGradeDto>> findGradeName(@PathVariable Long schoolId) throws CustomException {
        List<SchoolGradeDto> schoolGradeDtos = new ArrayList<>();
        schoolGradeDtos = schoolGradeService.listSchoolGrades(schoolId);
        return new ResponseEntity<List<SchoolGradeDto>>(schoolGradeDtos, HttpStatus.OK);
    }*/
}
