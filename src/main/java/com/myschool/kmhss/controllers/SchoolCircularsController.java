package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.SchoolCircularsDao;
import com.myschool.kmhss.dto.SchoolCircularsDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.SchoolCircularsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SchoolCircularsController {
    @Autowired
    SchoolCircularsService schoolCircularsService;

    @PutMapping(value = "/createUpdateCircular")
    public ResponseEntity<String> createUpdateCircular(@RequestBody SchoolCircularsDao schoolCircularsDao) throws CustomException {
        String result = schoolCircularsDao.getId() == null ? "Circular added successfully" : "Circular updated successfully";
        schoolCircularsService.createUpdateCircular(schoolCircularsDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/listSchoolCirculars")
    public ResponseEntity<List<SchoolCircularsDto>> listSchoolCirculars(
            @PathParam("school_id") Long school_id,
            @PathParam("academic_year_id") Long academic_year_id
            ) throws CustomException {
        List<SchoolCircularsDto> schoolCircularsDtos = schoolCircularsService.listSchoolCirculars(school_id, academic_year_id);
        return new ResponseEntity<List<SchoolCircularsDto>>(schoolCircularsDtos, HttpStatus.OK);
    }
}
