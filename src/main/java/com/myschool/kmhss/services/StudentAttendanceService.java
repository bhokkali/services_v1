package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicStudentsDao;
import com.myschool.kmhss.dao.StudentAttendanceDao;
import com.myschool.kmhss.dto.AcademicStudentsDto;
import com.myschool.kmhss.dto.StudentAttendanceDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.StudentAttendanceRepository;
import org.hibernate.usertype.CompositeUserType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentAttendanceService {

    @Autowired
    StudentAttendanceRepository studentAttendanceRepository;

    @Autowired
    AcademicStudentsService academicStudentsService;

    @Transactional
    public void createUpdateStudentAttendance(List<StudentAttendanceDao> studentAttendanceDaos, Long school_grade_id) throws CustomException {

        //Remove all attendance for the dates
        List<AcademicStudentsDao> academicStudentsDaos = academicStudentsService.listAcademicStudentsByGrade(school_grade_id);
        for(AcademicStudentsDao academicStudentsDao: academicStudentsDaos) {
            StudentAttendanceDao studentAttendanceDao = studentAttendanceDaos.stream()
                    .filter(each -> each.getAcademicStudentId().equals(academicStudentsDao.getId()))
                    .findAny()
                    .orElse(null);
            if(studentAttendanceDao == null) {
                Optional<StudentAttendanceDao> studentAttendanceDaoOptional = Optional.ofNullable(studentAttendanceRepository.findByAcademicStudentIdAndAbsentDate(academicStudentsDao.getId(), studentAttendanceDaos.get(0).getAbsentDate()));
                if (studentAttendanceDaoOptional.isPresent()) {
                    studentAttendanceRepository.deleteById(studentAttendanceDaoOptional.get().getId());
                }
            }
        }

        //make new entry
        for(StudentAttendanceDao studentAttendanceDao: studentAttendanceDaos) {

            if(studentAttendanceDao.getId() == null) {
                StudentAttendanceDao studentAttendanceDao1 = studentAttendanceRepository.findByAcademicStudentIdAndAbsentDate(studentAttendanceDao.getAcademicStudentId(), studentAttendanceDao.getAbsentDate());
                if(studentAttendanceDao1 != null)  {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Already Exists");
                }
                studentAttendanceDao.setCreatedDate(new Date());
            } else {
                studentAttendanceDao.setUpdatedDate(new Date());
            }
            studentAttendanceRepository.save(studentAttendanceDao);
        }

    }

    public void submitAttendance(StudentAttendanceDao studentAttendanceDao) {
        if(studentAttendanceDao.getId() == null) {
            studentAttendanceDao.setCreatedDate(new Date());
        } else {
            studentAttendanceDao.setUpdatedDate(new Date());
        }
        studentAttendanceRepository.save(studentAttendanceDao);
    }

    public void deleteAttendance(Long id) throws CustomException {
        Optional<StudentAttendanceDao> studentAttendanceDaoOptional = studentAttendanceRepository.findById(id);
        if(!studentAttendanceDaoOptional.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Attendance Data");
        }
        studentAttendanceRepository.deleteById(id);
    }

    public List<StudentAttendanceDto> listStudentGradeAttendance(Long schoolGradeId, String absentDate) throws CustomException, ParseException {

        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Date parsedDate = dateFormat.parse(absentDate);
        List<StudentAttendanceDao> studentAttendanceDaos = studentAttendanceRepository.findStudentGradeAttendance(schoolGradeId, absentDate);
        return convertDaoToDto(studentAttendanceDaos);
    }

    public List<StudentAttendanceDto> listStudentAttendanceCalendar(Long schoolGradeId) throws CustomException {
        List<StudentAttendanceDao> studentAttendanceDaos = studentAttendanceRepository.findStudentAttendanceCalendar(schoolGradeId);
        return convertDaoToDto(studentAttendanceDaos);
    }

    private List<StudentAttendanceDto> convertDaoToDto(List<StudentAttendanceDao> studentAttendanceDaos) {
        List<StudentAttendanceDto> studentAttendanceDtos = new ArrayList<>();

        for(StudentAttendanceDao studentAttendanceDao: studentAttendanceDaos) {
            studentAttendanceDtos.add(convertDaoToDtoSingle(studentAttendanceDao));
        }
        return studentAttendanceDtos;
    }

    private StudentAttendanceDto convertDaoToDtoSingle(StudentAttendanceDao studentAttendanceDao) {
        StudentAttendanceDto dto = new StudentAttendanceDto();
        BeanUtils.copyProperties(studentAttendanceDao, dto);
        dto.setGradeName(studentAttendanceDao.getAcademicStudentsDao().getSchoolGradeDao().getGradeDao().getGrade_name());
        dto.setStudentName(studentAttendanceDao.getAcademicStudentsDao().getStudentsDao().getStudentName());
        return dto;
    }

    public List<StudentAttendanceDto> listStudentAttendance(Long academicStudentId) {
        List<StudentAttendanceDao> studentAttendanceDaos = studentAttendanceRepository.findByAcademicStudentId(academicStudentId);
        return convertDaoToDto(studentAttendanceDaos);
    }

}
