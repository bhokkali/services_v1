package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.SchoolDao;
import com.myschool.kmhss.dao.SubadminsDao;
import com.myschool.kmhss.dto.SchoolDto;
import com.myschool.kmhss.dto.SubadminsDto;
import com.myschool.kmhss.dto.SubadminsLoginDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.SubadminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SubadminsController {

    @Autowired
    SubadminsService subadminsService;

    @GetMapping(value = "/listSubadmins")
    public ResponseEntity<List<SubadminsDto>> getSubadminsList(@PathParam("school_id") Long school_id) throws CustomException {
        List<SubadminsDto> subadminsDtos = subadminsService.getSubadminsList(school_id);
        return new ResponseEntity<List<SubadminsDto>>(subadminsDtos, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdateSubadmin")
    public String createUpdateSubadmin(@RequestBody SubadminsDto subadminsDto) throws CustomException {
        String result = subadminsDto.getId() == null ? "Subadmin added successfully" : "Subadmin updated successfully";
        subadminsService.createUpdateSubadmin(subadminsDto);
        return result;
    }

    @PostMapping(value = "/subadminLogin/{academicYearId}")
    public ResponseEntity<SubadminsLoginDto> subadminLogin(@RequestBody SubadminsDao subadminsDao, @PathVariable Long academicYearId) throws CustomException {
        SubadminsLoginDto subadminsLoginDto;
        subadminsLoginDto = subadminsService.subadminLogin(subadminsDao, academicYearId);
        return new ResponseEntity<SubadminsLoginDto>(subadminsLoginDto, HttpStatus.OK);
    }
}
