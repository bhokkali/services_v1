package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.SubjectDao;
import com.myschool.kmhss.dto.SubjectDto;
import com.myschool.kmhss.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping(value = "/listSubjects")
    public ResponseEntity<List<SubjectDto>> listSubjects() {
        List<SubjectDto> subjectDtos = subjectService.listSubjects();
        return new ResponseEntity<List<SubjectDto>>(subjectDtos, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdateSubject")
    public String createUpdateSubject(@RequestBody SubjectDao subjectDao) {
        String result = subjectDao.getId() == null ? "Subject created sucessfully" : "Subject updated successfully";
        subjectService.createUpdateSubject(subjectDao);
        return result;
    }

    @GetMapping(value = "/listSchoolActiveSubjects")
    public ResponseEntity<List<SubjectDto>> listSchoolActiveSubjects(@PathParam("school_id") Long school_id) {
        List<SubjectDto> subjectDtos = subjectService.listSchoolActiveSubjects(school_id);
        return new ResponseEntity<List<SubjectDto>>(subjectDtos, HttpStatus.OK);
    }


}
