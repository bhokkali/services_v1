package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicStudentsDao;
import com.myschool.kmhss.dao.SchoolDao;
import com.myschool.kmhss.dao.StudentsDao;
import com.myschool.kmhss.dto.SchoolDto;
import com.myschool.kmhss.dto.StudentsAppDto;
import com.myschool.kmhss.dto.StudentsDto;
import com.myschool.kmhss.dto.StudentsPaginatedDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.StudentsPaggingRepository;
import com.myschool.kmhss.repositories.StudentsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsService {

    @Autowired
    StudentsRepository studentsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    AcademicStudentsService academicStudentsService;

    @Autowired
    ParentsService parentsService;

    @Autowired
    SchoolServiceImpl schoolService;

    @Autowired
    StudentsPaggingRepository studentsPaggingRepository;

    public StudentsPaginatedDto getAllStudents(Long schoolId, Integer perPage, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, perPage, Sort.by(Sort.Direction.ASC, "studentName"));
        Page<StudentsDao> studentsDaoPage = studentsPaggingRepository.findBySchoolId(schoolId, pageable);

        List<StudentsDao> studentsDaos = new ArrayList<>();
        if(studentsDaoPage.hasContent()) {
            studentsDaos = studentsDaoPage.getContent();
        }

        StudentsPaginatedDto studentsPaginatedDto = new StudentsPaginatedDto();
        Long studentsCount = studentsRepository.findBySchoolIdCount(schoolId);
        studentsPaginatedDto.setCount(studentsCount);
        studentsPaginatedDto.setStudentsList(convertDaoTODto(studentsDaos));
        return studentsPaginatedDto;
    }

    private List<StudentsDto> convertDaoTODto(List<StudentsDao> studentsDaos) {
        List<StudentsDto> studentsDtos = new ArrayList<>();

        for(StudentsDao studentsDao: studentsDaos) {
            StudentsDto dto = new StudentsDto();
            BeanUtils.copyProperties(studentsDao, dto);
            dto.setKey(studentsDao.getId());
            dto.setParentName(studentsDao.getParentsDao().getParentName());
            AcademicStudentsDao academicStudentsDao = academicStudentsService.getStudentCurrentGrade(studentsDao.getSchoolId(), studentsDao.getId());
            if(academicStudentsDao != null) {
                dto.setSchoolGradeId(academicStudentsDao.getSchoolGradeId());
            }
            studentsDtos.add(dto);
        }

        return studentsDtos;
    }

    public void createUpdateStudents(StudentsDao studentsDao, Long academicYearId, Long schoolGradeId) throws CustomException {
        if (studentsDao.getId() == null) {
            StudentsDao studentsDaoLast = studentsRepository.findLastStudentInfo(studentsDao.getSchoolId());
            if(studentsDaoLast != null) {
                String[] registerNumArr = studentsDaoLast.getRegisterNo().split("-");
                Long regNo = Long.parseLong(registerNumArr[1]) + 1;
                String registerNo = registerNumArr[0].concat("-").concat(Long.toString(regNo));
                studentsDao.setRegisterNo(registerNo);
            } else {
                SchoolDto schoolDto = schoolService.getSchoolInfo(studentsDao.getSchoolId());
                String registerNumber = schoolDto.getSchool_code()+"-1";
                studentsDao.setRegisterNo(registerNumber);
            }
            studentsDao.setCreatedDate(new Date());
        } else {
            Optional<StudentsDao> academicYearDaoOptional1 = studentsRepository.findById(studentsDao.getId());
            if(academicYearDaoOptional1.isPresent()) {
                studentsDao.setUpdatedDate(new Date());
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Student Id");
            }
        }
        StudentsDao studentsDao1 = studentsRepository.save(studentsDao);


        AcademicStudentsDao academicStudentsDao = new AcademicStudentsDao();
        academicStudentsDao.setSchoolId(studentsDao1.getSchoolId());
        academicStudentsDao.setAcademicYearId(academicYearId);
        academicStudentsDao.setSchoolGradeId(schoolGradeId);
        academicStudentsDao.setStudentId(studentsDao1.getId());
        academicStudentsDao.setPromotionStatus("InProgress");

        if (studentsDao.getId() != null) {
            AcademicStudentsDao academicStudentsDao1 = academicStudentsService.getAcademicStudentInfo(
                    studentsDao.getSchoolId(), academicYearId, studentsDao.getId());

           if (academicStudentsDao1 != null)
               academicStudentsDao.setId(academicStudentsDao1.getId());
        }

        academicStudentsService.createUpdateAcademicStudents(academicStudentsDao);
    }

    public StudentsDto getStudentInfo(Long id) throws CustomException {
        Optional<StudentsDao> studentsDao = studentsRepository.findById(id);
        if(studentsDao.isPresent()) {
            StudentsDto dto = new StudentsDto();
            BeanUtils.copyProperties(studentsDao.get(), dto);
            return dto;
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "No Student Found");
        }

    }

    public void deleteStudent(Long id) throws CustomException {
        Optional<StudentsDao> studentsDao = studentsRepository.findById(id);
        if(studentsDao.isPresent()) {
            studentsRepository.deleteById(id);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "No Student Found");
        }
    }

    public List<StudentsAppDto> getParentStudents(Long parentId) throws CustomException {
        List<StudentsDao> studentsDaos = studentsRepository.findParentStudnets(parentId);
        return convertDaoTOAppDto(studentsDaos);
    }

    private List<StudentsAppDto> convertDaoTOAppDto(List<StudentsDao> studentsDaos) {
        List<StudentsAppDto> studentsAppDtos = new ArrayList<>();

        for(StudentsDao studentsDao: studentsDaos) {
            StudentsAppDto dto = new StudentsAppDto();
            BeanUtils.copyProperties(studentsDao, dto);
            dto.setKey(studentsDao.getId());
            AcademicStudentsDao academicStudentsDao = academicStudentsService.getStudentCurrentGrade(studentsDao.getSchoolId(), studentsDao.getId());
            if(academicStudentsDao != null) {
                dto.setAcademicYear(academicStudentsDao.getAcademicYearDao().getAcademicYear());
                dto.setAcademicYearId(academicStudentsDao.getAcademicYearId());
                dto.setGradeName(academicStudentsDao.getSchoolGradeDao().getGradeDao().getGrade_name());
                dto.setSchoolGradeId(academicStudentsDao.getSchoolGradeId());
                dto.setAcademicStudentId(academicStudentsDao.getId());
            }
            studentsAppDtos.add(dto);
        }

        return studentsAppDtos;
    }

    public void changeStudentStatus(Long studentId, String status) throws CustomException {
        Optional<StudentsDao> studentsDaoOptional = studentsRepository.findById(studentId);
        if(studentsDaoOptional.isPresent()) {
            StudentsDao studentsDao1 = studentsDaoOptional.get();
            studentsDao1.setStatus(status);
            studentsDao1.setRelevingDate(new Date());
            studentsRepository.save(studentsDao1);
            Long activeStudentCount = getActiveStudentsAgainstParentId(studentsDao1.getParentId());
            if(activeStudentCount <= 0 ) { // chagne parent status
                parentsService.changeParentStatus(studentsDao1.getParentId(), "InActive");
            }
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid student id");
        }
    }

    public Long getActiveStudentsAgainstParentId(Long parentId) throws CustomException {
        Long studentCount = studentsRepository.findActiveStudentsCountByParentId(parentId);
        return studentCount;
    }

}
