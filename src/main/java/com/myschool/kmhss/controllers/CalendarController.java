package com.myschool.kmhss.controllers;

import com.myschool.kmhss.dao.CalendarDao;
import com.myschool.kmhss.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @GetMapping(value = "/getSchoolCalendar/{school_id}/{academic_year_id}")
    public ResponseEntity<List<CalendarDao>> getSchoolCalendar(@PathVariable Long school_id,@PathVariable Long academic_year_id) {
        List<CalendarDao> calendarDaos = calendarService.getSchoolCalendar(school_id, academic_year_id);
        return new ResponseEntity<List<CalendarDao>>(calendarDaos, HttpStatus.OK);
    }

    @PutMapping(value = "/createUpdateCalendar")
    public ResponseEntity<String> createUpdateCalendar(@RequestBody CalendarDao calendarDao) {
        String result = calendarDao.getId() == null ? "Calnedar event added successfully" : "Calendar event updated successfully";
        calendarService.createUpdateCalendar(calendarDao);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
