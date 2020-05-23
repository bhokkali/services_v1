package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.TimeTableDao;
import com.myschool.kmhss.dto.TimeTableDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.TimeTableRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTableService {

    @Autowired
    TimeTableRepository timeTableRepository;

    public void createTimeTable(TimeTableDao timeTableDao) throws CustomException {
        if(validateTimeTable(timeTableDao)) {
            timeTableRepository.save(timeTableDao);
        }

    }

    private Boolean validateTimeTable(TimeTableDao timeTableDao) throws CustomException {
        /*List<TimeTableDao> timeTableDao1 = timeTableRepository.findBySchoolGradeIdAndPeriodId(
                timeTableDao.getSchoolGradeId(),
                timeTableDao.getPeriodId()
        );
        if(!CollectionUtils.isEmpty(timeTableDao1)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "School grade and period already exists");
        }*/


        /*if(timeTableDao.getId() == null) {
            List<TimeTableDao> timeTableDao2 = timeTableRepository.findBySchoolGradeIdAndPeriodIdAndSubjectId(
                    timeTableDao.getSchoolGradeId(),
                    timeTableDao.getPeriodId(),
                    timeTableDao.getSubjectId()
            );
            if (!CollectionUtils.isEmpty(timeTableDao2)) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "School grade and period and subject id already exists");
            }
        } */

        List<TimeTableDao> timeTableDao3 = timeTableRepository.findByPeriodIdAndTeacherIdAndWeekdayAndAcademicYearId(
                timeTableDao.getPeriodId(),
                timeTableDao.getTeacherId(),
                timeTableDao.getWeekday(),
                timeTableDao.getAcademicYearId()
        );
        if(!CollectionUtils.isEmpty(timeTableDao3)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "teacher already engaged in this period already exists");
        }

        return true;
    }

    public List<TimeTableDto> listSchoolTimeTable(Long schoolId, Long academicYearId) throws CustomException {
        List<TimeTableDao> timeTableDaos = timeTableRepository.findBySchoolIdAndAcademicYearIdOrderByIdAsc(schoolId, academicYearId);
        if(CollectionUtils.isEmpty(timeTableDaos)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "No timetable records found");
        }

        return convertDaoTODto(timeTableDaos);
    }

    public List<TimeTableDto> listGradeTimeTable(Long schoolId, Long academicYearId, Long schoolGradeId) throws CustomException {
        List<TimeTableDao> timeTableDaos = timeTableRepository.findBySchoolIdAndAcademicYearIdAndSchoolGradeId(schoolId, academicYearId, schoolGradeId);
        return convertDaoTODto(timeTableDaos);
    }

    public List<TimeTableDto> listTeacherTimeTable(Long schoolId, Long academicYearId, Long teacherId) throws CustomException {
        List<TimeTableDao> timeTableDaos = timeTableRepository.findBySchoolIdAndAcademicYearIdAndTeacherId(schoolId, academicYearId, teacherId);
        return convertDaoTODto(timeTableDaos);
    }

    private List<TimeTableDto> convertDaoTODto(List<TimeTableDao> timeTableDaos) {
        List<TimeTableDto> timeTableDtos = new ArrayList<>();

        for(TimeTableDao timeTableDao: timeTableDaos) {
            TimeTableDto dto = new TimeTableDto();
            BeanUtils.copyProperties(timeTableDao, dto);

            dto.setAcademicYear(timeTableDao.getAcademicYearDao().getAcademicYear());
            dto.setGradeName(timeTableDao.getSchoolGradeDao().getGradeDao().getGrade_name());
            dto.setPeriodName(timeTableDao.getPeriodsDao().getPeriodName());
            dto.setTeacherName(timeTableDao.getTeacherDao().getTeacherName());
            dto.setSubjectName(timeTableDao.getSubjectDao().getSubject_name());
            timeTableDtos.add(dto);
        }

        return timeTableDtos;
    }
}
