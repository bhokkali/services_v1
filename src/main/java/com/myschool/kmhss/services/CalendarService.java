package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.CalendarDao;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CalendarService {
    @Autowired
    CalendarRepository calendarRepository;

    public List<CalendarDao> getSchoolCalendar(Long schoolId, Long academicYearId) {
        List<CalendarDao> calendarDaos = calendarRepository.findBySchoolIdAndAcademicYearId(schoolId, academicYearId);
        return calendarDaos;
    }

    public void createUpdateCalendar(CalendarDao calendarDao) {
        if(calendarDao.getId() == null) {
            calendarDao.setCreatedDate(new Date());
        } else {
            calendarDao.setUpdatedDate(new Date());
        }
        calendarRepository.save(calendarDao);
    }
}
