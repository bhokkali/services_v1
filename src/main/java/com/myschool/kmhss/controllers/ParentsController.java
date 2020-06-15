package com.myschool.kmhss.controllers;

import com.myschool.kmhss.constants.GlobalConstants;
import com.myschool.kmhss.dao.ParentsDao;
import com.myschool.kmhss.dto.ParentsAppDto;
import com.myschool.kmhss.dto.ParentsDto;
import com.myschool.kmhss.dto.ParentsPaginatedDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.services.ParentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ParentsController {

    @Autowired
    ParentsService parentsService;

    @GetMapping(value = "/getAllParents")
    public ResponseEntity<ParentsPaginatedDto> getAllParents(
            @PathParam("school_id") Long school_id,
            @RequestParam(required = false, value = "per_page", defaultValue = GlobalConstants.DEFAULT_PER_PAGE_SIZE_VALUE) int perPage,
            @RequestParam(required = false, value = "page", defaultValue = GlobalConstants.DEFAULT_PAGE_NUMBER_VALUE) int pageNumber
            ) {
        ParentsPaginatedDto parentsPaginatedDto = parentsService.getAllParents(school_id, perPage, pageNumber);
        return new ResponseEntity<ParentsPaginatedDto>(parentsPaginatedDto, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdateParents")
    public String createUpdateParents(@RequestBody ParentsDao parentsDao) throws CustomException {
        String result = parentsDao.getId() == null ? "Parent created successfully" : "Parent updated successfully";
        parentsService.createUpdateParents(parentsDao);
        return result;
    }

    @GetMapping(value = "/getParentInfo/{id}")
    public  ResponseEntity<ParentsDto> getParentInfo(@PathVariable Long id) throws CustomException {
        ParentsDto parentsDto = parentsService.getParentInfo(id);
        return new ResponseEntity<ParentsDto>(parentsDto, HttpStatus.OK);
    }

    @PostMapping(value = "/parentsLogin/{currentAcademicYear}")
    public ResponseEntity<ParentsAppDto> parentsLogin(@RequestBody ParentsDao parentsDao, @PathVariable String currentAcademicYear) throws CustomException {
        ParentsAppDto parentsAppDto = parentsService.parentsLogin(parentsDao, currentAcademicYear);
        return new ResponseEntity<ParentsAppDto>(parentsAppDto, HttpStatus.OK);
    }

    @PostMapping(value = "/checkAvailabilityParents/{entity}")
    public ResponseEntity<Boolean> checkAvailabilityParents(@PathVariable String entity, @RequestBody ParentsDao parentsDao) {
        Boolean result = false;
        if(entity.equals("mobile_no")) {
            result = parentsService.mobileAvailability(parentsDao);
        } else if(entity.equals("email")) {
            result = parentsService.emailAvailability(parentsDao);
        } else if(entity.equals("aadhar_no")) {
            result = parentsService.aadharAvailability(parentsDao);
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/parentChangePassword")
    public ResponseEntity<String> parentChangePassword(@RequestBody ParentsDao parentsDao) throws CustomException {
        parentsService.parentChangePassword(parentsDao);
        return new ResponseEntity<String>("Password changed successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/searchParents")
    public ResponseEntity<ParentsPaginatedDto> searchParents(
            @PathParam("school_id") Long school_id,
            @PathParam("parent_name") String parent_name,
            @PathParam("status") String status,
            @RequestParam(required = false, value = "per_page", defaultValue = GlobalConstants.DEFAULT_PER_PAGE_SIZE_VALUE) int perPage,
            @RequestParam(required = false, value = "page", defaultValue = GlobalConstants.DEFAULT_PAGE_NUMBER_VALUE) int pageNumber
    ) throws CustomException {
        ParentsPaginatedDto parentsPaginatedDto = parentsService.searchParents(school_id, parent_name, status, perPage, pageNumber);
        return new ResponseEntity<ParentsPaginatedDto>(parentsPaginatedDto, HttpStatus.OK);
    }
}
