package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.ExamReportDao;
import com.myschool.kmhss.dto.ExamReportDto;
import com.myschool.kmhss.dto.StudentExamReportDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.ExamReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ExamReportController {

    @Autowired
    ExamReportService examReportService;

    @PutMapping(value = "/createUpdateExamReport")
    public ResponseEntity<String> createUpdateExamReport(@RequestBody List<ExamReportDao> examReportDaos) throws CustomException {
        //String result = examReportDao.getId() == null ? "Exam report added successfully" : "Exam report updated successfully";
        examReportService.createUpdateExamReport(examReportDaos);
        return new ResponseEntity<String>("Report Generated successfully..", HttpStatus.OK);
    }

    @GetMapping(value = "/listExamReports")
    public ResponseEntity<List<ExamReportDto>> listExamReports(
            @PathParam("exam_id") Long exam_id,
            @PathParam("school_grade_id") Long school_grade_id) throws CustomException {
        List<ExamReportDto> examReportDtos = examReportService.listExamReports(exam_id, school_grade_id);
        return new ResponseEntity<List<ExamReportDto>>(examReportDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/listAllExamReports")
    public ResponseEntity<List<ExamReportDto>> listAllExamReports(
            @PathParam("academic_year_id") Long academic_year_id,
            @PathParam("school_grade_id") Long school_grade_id) throws CustomException {
        List<ExamReportDto> examReportDtos = examReportService.listAllExamReports(academic_year_id, school_grade_id);
        return new ResponseEntity<List<ExamReportDto>>(examReportDtos, HttpStatus.OK);
    }


    @GetMapping(value = "/getStudentExamReport")
    public ResponseEntity<StudentExamReportDto> getStudentExamReport(
            @PathParam("exam_id") Long exam_id,
            @PathParam("school_grade_id") Long school_grade_id,
            @PathParam("student_id") Long student_id
    ) throws CustomException {

        StudentExamReportDto studentExamReportDto = new StudentExamReportDto();
        studentExamReportDto = examReportService.getStudentExamReport(exam_id, school_grade_id, student_id);
        return new ResponseEntity<StudentExamReportDto>(studentExamReportDto, HttpStatus.OK);

    }
}
