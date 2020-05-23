package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.TeacherDao;
import com.myschool.kmhss.dto.TeacherAppDto;
import com.myschool.kmhss.dto.TeacherDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @GetMapping(value = "getAllTeachersList")
    public ResponseEntity<List<TeacherDto>> getAllTeachersList() {
        List<TeacherDto> teacherDtos = teacherService.getAllTeachersList();
        return new ResponseEntity<List<TeacherDto>>(teacherDtos, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdateTeacher")
    public String createUpdateTeacher(@RequestBody TeacherDto teacherDto) throws CustomException {
        String result = teacherDto.getId() == null ? "Teacher details created successfully" : "Teacher details updated successfully";
        teacherService.createUpdateTeacher(teacherDto);
        return result;
    }

    @GetMapping(value = "/getSchoolTeachers/{schoolId}")
    public ResponseEntity<List<TeacherDto>> getSchoolTeachers(@PathVariable Long schoolId) {
        List<TeacherDto> teacherDtos = teacherService.getSchoolTeachers(schoolId);
        return new ResponseEntity<List<TeacherDto>>(teacherDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getTeacherInfo/{id}")
    public ResponseEntity<TeacherDto> getTeacherInfo(@PathVariable Long id) throws CustomException {
        TeacherDto teacherDto = teacherService.getTeaherInfo(id);
        return new ResponseEntity<TeacherDto>(teacherDto, HttpStatus.OK);
    }

    @PostMapping(value = "/teacherLogin/{currentAcademicYear}")
    public ResponseEntity<TeacherAppDto> teacherLogin(@RequestBody TeacherDao teacherDao, @PathVariable String currentAcademicYear) throws CustomException {
        TeacherAppDto teacherAppDto = teacherService.teacherLogin(teacherDao, currentAcademicYear);
        return new ResponseEntity<TeacherAppDto>(teacherAppDto, HttpStatus.OK);
    }

    @PostMapping(value = "/changeTeacherStatus")
    public ResponseEntity<String> changeTeacherStatus(@RequestBody TeacherDao teacherDao) throws CustomException {
        teacherService.changeTeacherStatus(teacherDao);
        return new ResponseEntity<String>("Teacher status changed successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/getSubjectTeachers/{subjectId}")
    public ResponseEntity<List<TeacherDto>> getSubjectTeachers(@PathVariable Long subjectId) throws CustomException {
        List<TeacherDto> teacherDtos = teacherService.getSubjectTeachers(subjectId);
        return new ResponseEntity<List<TeacherDto>>(teacherDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/checkAvailabilityTeacher/{entity}")
    public ResponseEntity<Boolean> checkAvailabilityParents(@PathVariable String entity, @RequestBody TeacherDto teacherDto) {
        Boolean result = false;
        if(entity.equals("mobile_no")) {
            result = teacherService.mobileAvailability(teacherDto);
        } else if(entity.equals("email")) {
            result = teacherService.emailAvailability(teacherDto);
        } else if(entity.equals("aadhar_no")) {
            result = teacherService.aadharAvailability(teacherDto);
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/teacherChangePassword")
    public ResponseEntity<String> teacherChangePassword(@RequestBody TeacherDao teacherDao) throws CustomException {
        teacherService.teacherChangePassword(teacherDao);
        return new ResponseEntity<String>("Password changed successfully", HttpStatus.OK);
    }
}
