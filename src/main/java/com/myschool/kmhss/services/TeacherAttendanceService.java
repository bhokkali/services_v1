package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.TeacherAttendanceDao;
import com.myschool.kmhss.dao.TeachersSubjectsXrefDao;
import com.myschool.kmhss.dto.TeacherAttendanceDto;
import com.myschool.kmhss.repositories.TeacherAttendanceRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeacherAttendanceService {

    @Autowired
    TeacherAttendanceRespository teacherAttendanceRespository;

    public void addUpdateTeacherAttendance(TeacherAttendanceDao teacherAttendanceDao) {
        if(teacherAttendanceDao.getId() == null) {
            teacherAttendanceDao.setCreatedDate(new Date());
        } else {
            teacherAttendanceDao.setUpdatedDate(new Date());
        }
        teacherAttendanceRespository.save(teacherAttendanceDao);
    }

    public List<TeacherAttendanceDto> listSchoolStaffAttendance(Long schoolId, Long academicYearId, Long teacherId) {
        List<TeacherAttendanceDao> teacherAttendanceDaos = new ArrayList<>();
        if(teacherId == null) {
            teacherAttendanceDaos = teacherAttendanceRespository.findBySchoolIdAndAcademicYearId(schoolId, academicYearId);
        } else {
            teacherAttendanceDaos = teacherAttendanceRespository.findBySchoolIdAndAcademicYearIdAndTeacherId(schoolId, academicYearId, teacherId);
        }

        return convertDaoToDto(teacherAttendanceDaos);
    }

    private List<TeacherAttendanceDto> convertDaoToDto(List<TeacherAttendanceDao> teacherAttendanceDaos) {
        List<TeacherAttendanceDto> teacherAttendanceDtos = new ArrayList<>();

        for(TeacherAttendanceDao teacherAttendanceDao1: teacherAttendanceDaos) {
            TeacherAttendanceDto dto = new TeacherAttendanceDto();
            BeanUtils.copyProperties(teacherAttendanceDao1, dto);
            dto.setTeacherName(teacherAttendanceDao1.getTeacherDao().getTeacherName());
            dto.setAcademicYear(teacherAttendanceDao1.getAcademicYearDao().getAcademicYear());
            teacherAttendanceDtos.add(dto);
        }
        return teacherAttendanceDtos;
    }
}

