package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.ExamGradesDao;
import com.myschool.kmhss.dto.ExamGradesDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.ExamGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ExamGradesController {

    @Autowired
    ExamGradeService examGradeService;

    @PutMapping(value = "/createUpdateExamGrade")
    public ResponseEntity<String> createUpdateExamGrade(@RequestBody ExamGradesDao examGradesDao) throws CustomException {
        String result = examGradesDao.getId() == null ? "Exam timetable added successfully" : "Exam timetable updated successfully";
        examGradeService.createUpdateExamGrade(examGradesDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/listGradeExams")
    public ResponseEntity<List<ExamGradesDto>> listGradeExams(
            @PathParam("exam_id") Long exam_id,
            @PathParam("school_grade_id") Long school_grade_id) {
        List<ExamGradesDto> examGradesDtos = examGradeService.listGradeExams(exam_id, school_grade_id);
        return new ResponseEntity<List<ExamGradesDto>>(examGradesDtos, HttpStatus.OK);
    }
}
