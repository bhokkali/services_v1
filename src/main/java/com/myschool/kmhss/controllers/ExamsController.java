package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.ExamsDao;
import com.myschool.kmhss.dto.ExamsDto;
import com.myschool.kmhss.services.ExamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ExamsController {

    @Autowired
    ExamsService examsService;

    @PutMapping(value = "/createUpdateExam")
    public ResponseEntity<String> createUpdateExam(@RequestBody ExamsDao examsDao) {
        String result = examsDao.getId() != null ? "Exam details updated successfully" : "Exam details added successfully";
        examsService.createUpdateExams(examsDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/listExams")
    public ResponseEntity<List<ExamsDto>> listExams(
            @PathParam("school_id") Long school_id,
            @PathParam("academic_year_id") Long academic_year_id
    ) {
        List<ExamsDto> examsDtos = examsService.listExams(
                school_id, academic_year_id );
        return new ResponseEntity<List<ExamsDto>>(examsDtos, HttpStatus.OK);
    }


}
