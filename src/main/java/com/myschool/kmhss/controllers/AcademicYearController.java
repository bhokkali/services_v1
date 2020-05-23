package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.AcademicYearDao;
import com.myschool.kmhss.dto.AcademicYearDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.AcademicYearSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AcademicYearController {
    @Autowired
    AcademicYearSrevice academicYearSrevice;

    @GetMapping(value = "/getAcademicYearsList")
    public ResponseEntity<List<AcademicYearDto>> getAcademicYearsList() {
        List<AcademicYearDto> academicYearDtos = new ArrayList<>();
        academicYearDtos = academicYearSrevice.getAcademicYearsList();
        return new ResponseEntity<List<AcademicYearDto>>(academicYearDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getAcademicYearInfo/{id}")
    public ResponseEntity<AcademicYearDao> getAcademicYearInfo(@PathVariable Long id ) throws CustomException {
        AcademicYearDao academicYearDao = academicYearSrevice.getAcademicYearInfo(id);
        return new ResponseEntity<AcademicYearDao>(academicYearDao, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdateAcademicYear")
    public ResponseEntity<String> createUpdateAcademicYear(@RequestBody AcademicYearDao academicYearDao) throws CustomException {
        String result = academicYearDao.getId() == null ? "Academic year created successfully" : "Academic year updated successfully";
        academicYearSrevice.createUpdateAcademicYear(academicYearDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/getAcademicYearInfoFromYearString")
    public ResponseEntity<AcademicYearDao> getAcademicYearInfoFromYearString(@PathParam("academic_year") String academic_year) throws CustomException {
        AcademicYearDao academicYearDao = academicYearSrevice.getAcademicYearInfoFromYearString(academic_year);
        return new ResponseEntity<AcademicYearDao>(academicYearDao, HttpStatus.OK);
    }
}
