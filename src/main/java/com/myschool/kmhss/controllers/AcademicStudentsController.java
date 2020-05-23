package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.AcademicStudentsDao;
import com.myschool.kmhss.dto.AcademicStudentsDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.AcademicStudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AcademicStudentsController {

    @Autowired
    AcademicStudentsService academicStudentsService;

    @PutMapping(value = "/createUpdateAcademicStudents")
    public ResponseEntity<String> createUpdateAcademicStudents(@RequestBody AcademicStudentsDao academicStudentsDao) {
        String result = academicStudentsDao.getId() == null ? "Academic Students added sucessfully" : "Academic Students updated successfully";
        academicStudentsService.createUpdateAcademicStudents(academicStudentsDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/listAcademicStudents")
    public ResponseEntity<List<AcademicStudentsDto>> listAcademicStudents(
            @PathParam("school_id") Long school_id,
            @PathParam("academic_year_id") Long academic_year_id,
            @PathParam("school_grade_id") Long school_grade_id
            ) throws CustomException {
        List<AcademicStudentsDto> academicStudentsDtos = academicStudentsService.listAcademicStudents(
                school_id, academic_year_id, school_grade_id );
        return new ResponseEntity<List<AcademicStudentsDto>>(academicStudentsDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/updateAcademicPromotion")
    public ResponseEntity<String> updateAcademicPromotion(@RequestBody List<AcademicStudentsDto> academicStudentsDtos) throws CustomException {
        academicStudentsService.updateAcademicPromotion(academicStudentsDtos);
        return new ResponseEntity<String>("Promotion updated successfully", HttpStatus.OK);
    }



}
