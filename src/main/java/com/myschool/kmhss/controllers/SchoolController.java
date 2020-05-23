package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.SchoolDao;
import com.myschool.kmhss.dto.SchoolChangePwdDto;
import com.myschool.kmhss.dto.SchoolDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.SchoolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SchoolController {

    @Autowired
    SchoolServiceImpl schoolService;

    @GetMapping(value = "/listSchools")
    public ResponseEntity<List<SchoolDto>> listSchools() throws CustomException {
        List<SchoolDto> schoolDto;
        try {
            schoolDto = schoolService.listSchools();
        } catch(Exception ex) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        /*if (CollectionUtils.isEmpty(schoolDto)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "No Schools in the list");
        } */

        return new ResponseEntity<List<SchoolDto>>(schoolDto, HttpStatus.OK);
    }

    @PutMapping(value = "createUpdateSchool")
    public ResponseEntity<String> createUpdateSchool(@RequestBody SchoolDao schoolDao) throws CustomException {
        String result = schoolDao.getId() == null ? "School created successfully" : "School details updated successfully";
        schoolService.createUpdateSchool(schoolDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping(value = "deleteSchool/{id}")
    public String deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return "School deleted successfully";
    }

    @PostMapping(value = "changeSchoolStatus")
    public String changeSchoolStatus(@RequestBody SchoolDao schoolDao) throws CustomException {
        schoolService.changeSchoolStatus(schoolDao);
        return "School status updated successfully";
    }

    @GetMapping(value = "getSchoolInfo/{id}")
    public SchoolDto getSchoolInfo(@PathVariable Long id) throws CustomException {
        SchoolDto schoolDto = schoolService.getSchoolInfo(id);
        //return new ResponseEntity<SchoolDto>(schoolDto, HttpStatus.OK);
        return schoolDto;
    }

    @GetMapping(value = "/getActiveSchoolsList")
    public ResponseEntity<List<SchoolDto>> getActiveSchoolsList(@PathParam("status") String status) {
        List<SchoolDto> schoolDtos;
        schoolDtos = schoolService.getActiveSchools(status);
        return new ResponseEntity<List<SchoolDto>>(schoolDtos, HttpStatus.OK);
    }

    @PostMapping(value = "schoolLogin/{academicYearId}")
    public ResponseEntity<SchoolDto> schoolLogin(@RequestBody SchoolDao schoolDao, @PathVariable Long academicYearId) throws CustomException {
        SchoolDto schoolDto;
        schoolDto = schoolService.schoolLogin(schoolDao, academicYearId);
        return new ResponseEntity<SchoolDto>(schoolDto, HttpStatus.OK);
    }

    @PostMapping(value = "/schoolChangePassword")
    public ResponseEntity<String> schoolChangePassword(@RequestBody SchoolChangePwdDto schoolChangePwdDto) throws CustomException {
        schoolService.schoolChangePassword(schoolChangePwdDto);
        return new ResponseEntity<String>("SChool password changes successfully", HttpStatus.OK);
    }
}
