package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.GradeDao;
import com.myschool.kmhss.dto.GradeDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GradeController {

    @Autowired
    GradeService gradeService;

    @GetMapping(value = "/listGrades")
    public ResponseEntity<List<GradeDto>> listGrades() throws CustomException {
        List<GradeDto> gradeDtos = gradeService.listGrades();
        return new ResponseEntity<List<GradeDto>>(gradeDtos, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdateGrade")
    public String createUpdateGrade(@RequestBody GradeDao gradeDao) throws CustomException  {
        String result = gradeDao.getId() == null ? "Grade created successfully" : "Grade updated successfully";
        gradeService.createUpdateGrade(gradeDao);
        return result;
    }


}
