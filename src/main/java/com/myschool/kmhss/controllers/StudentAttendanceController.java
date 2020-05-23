package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.StudentAttendanceDao;
import com.myschool.kmhss.dto.StudentAttendanceDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.StudentAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StudentAttendanceController {

    @Autowired
    StudentAttendanceService studentAttendanceService;

    @PutMapping(value = "/createUpdateStudentAttendance")
    public ResponseEntity<String> createUpdateStudentAttendance(
            @RequestBody List<StudentAttendanceDao> studentAttendanceDaos,
            @PathParam("school_grade_id") Long school_grade_id
            ) throws CustomException {
        studentAttendanceService.createUpdateStudentAttendance(studentAttendanceDaos, school_grade_id);
        return new ResponseEntity<String>("Student Attendance Added successfully", HttpStatus.OK);
    }

    @PutMapping(value = "/submitAttendance")
    public ResponseEntity<String> submitAttendance(@RequestBody StudentAttendanceDao studentAttendanceDao) {
        String result = studentAttendanceDao.getId() == null ? "Attendance added successfully" : "Attendance updted successfully";
        studentAttendanceService.submitAttendance(studentAttendanceDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAttendance")
    public ResponseEntity<String> deleteAttendance(@PathParam("id") Long id) throws CustomException {
        studentAttendanceService.deleteAttendance(id);
        return new ResponseEntity<String>("Attendance deleted successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/listStudentGradeAttendance")
    public ResponseEntity<List<StudentAttendanceDto>> listStudentGradeAttendance(
            @PathParam("school_grade_id") Long school_grade_id,
            @PathParam("absent_date") String absent_date
    ) throws CustomException, ParseException {
        List<StudentAttendanceDto> studentAttendanceDtos = studentAttendanceService.listStudentGradeAttendance(school_grade_id, absent_date);
        return new ResponseEntity<List<StudentAttendanceDto>>(studentAttendanceDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getStudentAttendance")
    public ResponseEntity<List<StudentAttendanceDto>> getStudentAttendance(@PathParam("academic_student_id") Long academic_student_id) {
        List<StudentAttendanceDto> studentAttendanceDtos = studentAttendanceService.listStudentAttendance(academic_student_id);
        return new ResponseEntity<List<StudentAttendanceDto>>(studentAttendanceDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/listStudentAttendanceCalendar")
    public ResponseEntity<List<StudentAttendanceDto>> listStudentAttendanceCalendar(
            @PathParam("school_grade_id") Long school_grade_id
    ) throws CustomException {
        List<StudentAttendanceDto> studentAttendanceDtos = studentAttendanceService.listStudentAttendanceCalendar(school_grade_id);
        return new ResponseEntity<List<StudentAttendanceDto>>(studentAttendanceDtos, HttpStatus.OK);
    }
}
