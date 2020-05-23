package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.ExamMarksDao;
import com.myschool.kmhss.dto.ExamGradesDto;
import com.myschool.kmhss.dto.ExamMarksDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.ExamMarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ExamMarksController {

    @Autowired
    ExamMarksService examMarksService;

    @PutMapping(value = "/createUpdateExamMarks")
    public ResponseEntity<String> createUpdateExamMarks(@RequestBody List<ExamMarksDao> examMarksDaos) throws CustomException {
        //String result = examMarksDao.getId() == null ? "Exam marks added successfully" : "Exam mark updated successfully";
        String result = "Marks added successfully";
        examMarksService.createUpdateExamMarks(examMarksDaos);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    /*@GetMapping(value = "/ListStudentMarks")
    public ResponseEntity<List<ExamMarksDto>> ListStudentMarks(
            @PathParam("student_id") Long student_id,
            @PathParam("exam_grade_id") Long exam_grade_id) throws CustomException {
        List<ExamMarksDto> examMarksDtos = examMarksService.ListStudentMarks(student_id, exam_grade_id);
        return new ResponseEntity<List<ExamMarksDto>>(examMarksDtos, HttpStatus.OK);
    } */

    @GetMapping(value = "/listExamGradeMarks")
    public ResponseEntity<List<ExamMarksDto>> listExamGradeMarks(
            @PathParam("exam_id") Long exam_id,
            @PathParam("school_grade_id") Long school_grade_id) throws CustomException {
        List<ExamMarksDto> examMarksDtos = examMarksService.ListExamGradeMarks(exam_id, school_grade_id);
        return new ResponseEntity<List<ExamMarksDto>>(examMarksDtos, HttpStatus.OK);
    }
}
