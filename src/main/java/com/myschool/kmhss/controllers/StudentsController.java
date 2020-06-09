package com.myschool.kmhss.controllers;

import com.myschool.kmhss.constants.GlobalConstants;
import com.myschool.kmhss.dao.StudentsDao;
import com.myschool.kmhss.dto.StudentInputDto;
import com.myschool.kmhss.dto.StudentsAppDto;
import com.myschool.kmhss.dto.StudentsDto;
import com.myschool.kmhss.dto.StudentsPaginatedDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StudentsController {

    @Autowired
    StudentsService studentsService;

    @GetMapping(value = "/getAllStudents")
    public ResponseEntity<StudentsPaginatedDto> getAllStudents(
            @PathParam("school_id") Long school_id,
            @RequestParam(required = false, value = "per_page", defaultValue = GlobalConstants.DEFAULT_PER_PAGE_SIZE_VALUE) int perPage,
            @RequestParam(required = false, value = "page", defaultValue = GlobalConstants.DEFAULT_PAGE_NUMBER_VALUE) int pageNumber
    ) {
        StudentsPaginatedDto studentsPaginatedDto = studentsService.getAllStudents(school_id, perPage, pageNumber);
        return new ResponseEntity<StudentsPaginatedDto>(studentsPaginatedDto, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdateStudents")
    public ResponseEntity<String> createUpdateStudents(@RequestBody StudentInputDto studentInputDto) throws CustomException {
        String result = studentInputDto.getStudent_info().getId() == null ? "Student created successfully" : "Student updated successfully";
        studentsService.createUpdateStudents(
                studentInputDto.getStudent_info(),
                studentInputDto.getAcademic_year_id(),
                studentInputDto.getSchool_grade_id());
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/getStudentInfo/{id}")
    public ResponseEntity<StudentsDto> getStudentInfo(@PathVariable Long id) throws CustomException {
        StudentsDto studentsDto = studentsService.getStudentInfo(id);
        return new ResponseEntity<StudentsDto>(studentsDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteStudent/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) throws CustomException {
        studentsService.deleteStudent(id);
        return new ResponseEntity<String>("Student deleted siccessfully", HttpStatus.OK);
    }

    @GetMapping(value = "/getParentStudents")
    public ResponseEntity<List<StudentsAppDto>> getParentStudents(@PathParam("parent_id") Long parent_id) throws CustomException {
        List<StudentsAppDto> studentsAppDtos = studentsService.getParentStudents(parent_id);
        return new ResponseEntity<List<StudentsAppDto>>(studentsAppDtos, HttpStatus.OK);
    }

    @GetMapping(value = "searchStudent")
    public ResponseEntity<StudentsPaginatedDto> searchStudent(
            @PathParam("school_id") Long school_id,
            @PathParam("student_name") String student_name) throws CustomException {
        StudentsPaginatedDto studentsPaginatedDto = studentsService.searchStudents(school_id, student_name, 10, 0);
        return new ResponseEntity<StudentsPaginatedDto>(studentsPaginatedDto, HttpStatus.OK);
    }

}
