package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.TeacherAttendanceDao;
import com.myschool.kmhss.dto.TeacherAttendanceDto;
import com.myschool.kmhss.services.TeacherAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TeacherAttendanceController {

    @Autowired
    TeacherAttendanceService teacherAttendanceService;

    @PutMapping(value = "/addUpdateTeacherAttendance")
    public ResponseEntity<String> addUpdateTeacherAttendance(@RequestBody TeacherAttendanceDao teacherAttendanceDao) {
        String result = teacherAttendanceDao.getId() == null ? "Teacher attendance added sucessfully" : "Teacher attendacne updated successfully";
        teacherAttendanceService.addUpdateTeacherAttendance(teacherAttendanceDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/listSchoolStaffAttendance/{schoolId}/{academicYearId}")
    public ResponseEntity<List<TeacherAttendanceDto>> listSchoolStaffAttendance(@PathVariable Long schoolId,
                                                                           @PathVariable Long academicYearId,
                                                                           @PathParam("teacher_id") Long teacher_id) {
        List<TeacherAttendanceDto> teacherAttendanceDtos = new ArrayList<>();
        teacherAttendanceDtos = teacherAttendanceService.listSchoolStaffAttendance(schoolId, academicYearId, teacher_id);
        return new ResponseEntity<List<TeacherAttendanceDto>>(teacherAttendanceDtos, HttpStatus.OK);
    }

}
