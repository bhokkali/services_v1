package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.MarkGradesDao;
import com.myschool.kmhss.dto.MarkGradesDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.MarkGradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MarkGradesController {

    @Autowired
    MarkGradesService markGradesService;

    @PutMapping(value = "/createUpdateMarkGrade")
    public ResponseEntity<String> createUpdateMarkGrade(@RequestBody MarkGradesDao markGradesDao) throws CustomException {
        String result = markGradesDao.getId() == null ? "Mark Grades addedssuccessfully" : "Mark Grades updated successfully";
        markGradesService.createUpdateMarkGrade(markGradesDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/listMarkGrades")
    public ResponseEntity<List<MarkGradesDto>> listMarkGrades(@PathParam("school_id") Long school_id) throws CustomException {
        List<MarkGradesDto> markGradesDtos = markGradesService.listMarkGrades(school_id);
        return new ResponseEntity<List<MarkGradesDto>>(markGradesDtos, HttpStatus.OK);
    }

}
