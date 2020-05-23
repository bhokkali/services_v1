package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicYearDao;
import com.myschool.kmhss.dao.TeacherDao;
import com.myschool.kmhss.dao.TeachersSubjectsXrefDao;
import com.myschool.kmhss.dto.TeacherAppDto;
import com.myschool.kmhss.dto.TeacherDto;
import com.myschool.kmhss.dto.TeachersSubjectsXrefDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.TeacherRepository;
import com.myschool.kmhss.repositories.TeachersSubjectsXrefRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeachersSubjectsXrefRepository teachersSubjectsXrefRepository;

    @Autowired
    AcademicYearSrevice academicYearSrevice;

    public List<TeacherDto> getAllTeachersList() {
        List<TeacherDao> teacherDaos = teacherRepository.findAll();

        return convertDaoTODto(teacherDaos);

    }

    public void createUpdateTeacher(TeacherDto teacherDto) throws CustomException {
        if(!mobileAvailability(teacherDto)) {
            if(!emailAvailability(teacherDto)) {
                if (!aadharAvailability(teacherDto)) {
                    TeacherDao teacherDao = new TeacherDao();
                    BeanUtils.copyProperties(teacherDto, teacherDao);
                    if (teacherDto.getId() == null) {
                        teacherDao.setCreatedDate(new Date());
                    } else {
                        teacherDao.setUpdatedDate(new Date());
                    }
                    teacherDao = teacherRepository.save(teacherDao);

                    if(!CollectionUtils.isEmpty(teacherDto.getSubjects())) {
                        for (Long subject : teacherDto.getSubjects()) {
                            TeachersSubjectsXrefDao teachersSubjectsXrefDao = new TeachersSubjectsXrefDao();
                            teachersSubjectsXrefDao.setTeacherId(teacherDao.getId());
                            teachersSubjectsXrefDao.setSubjectId(subject);
                            teachersSubjectsXrefRepository.save(teachersSubjectsXrefDao);
                        }
                    }
                } else {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Aaadhar number already exists");
                }
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Email Address already exists");
            }
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Mobile number already exists");
        }

    }

    public List<TeacherDto> getSchoolTeachers(Long schoolId) {
        List<TeacherDao> teacherDaos = teacherRepository.findBySchoolId(schoolId);
        return convertDaoTODto(teacherDaos);
    }

    public List<TeacherDto> getSubjectTeachers(Long subjectId) throws CustomException {
        List<TeachersSubjectsXrefDao> teachersSubjectsXrefDaos = teachersSubjectsXrefRepository.findBySubjectId(subjectId);
        if(!CollectionUtils.isEmpty(teachersSubjectsXrefDaos)) {
            List<TeacherDao> teacherDaos = new ArrayList<>();
            for(TeachersSubjectsXrefDao teachersSubjectsXrefDao: teachersSubjectsXrefDaos) {
                Optional<TeacherDao> teacherDao = teacherRepository.findById(teachersSubjectsXrefDao.getTeacherId());
                if(teacherDao.isPresent()) {
                    teacherDaos.add(teacherDao.get());
                } else {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Problem in Subject id");
                }
            }
            return convertDaoTODto(teacherDaos);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "No Teacher Found for tis subject");
        }

    }

    private List<TeacherDto> convertDaoTODto(List<TeacherDao> teacherDaos) {
        List<TeacherDto> teacherDtos = new ArrayList<>();

        for(TeacherDao teacherDao: teacherDaos) {
            TeacherDto dto = new TeacherDto();
            BeanUtils.copyProperties(teacherDao, dto);

            if (!CollectionUtils.isEmpty(teacherDao.getSubjectInfo())) {
                List<TeachersSubjectsXrefDao> teachersSubjectsXrefDaos = teacherDao.getSubjectInfo();
                List<TeachersSubjectsXrefDto> teachersSubjectsXrefDtos = new ArrayList<>();
                for(TeachersSubjectsXrefDao teachersSubjectsXrefDao: teachersSubjectsXrefDaos) {
                    TeachersSubjectsXrefDto teachersSubjectsXrefDto = new TeachersSubjectsXrefDto();
                    teachersSubjectsXrefDto.setSubjectId(teachersSubjectsXrefDao.getSubjectId());
                    teachersSubjectsXrefDto.setSubjectName(teachersSubjectsXrefDao.getSubjectInfo().getSubject_name());
                    teachersSubjectsXrefDtos.add(teachersSubjectsXrefDto);
                }
                if (!CollectionUtils.isEmpty(teachersSubjectsXrefDtos)) {
                    //dto.setSubjectInfo(teachersSubjectsXrefDtos);
                    dto.setSubjectInfo(teachersSubjectsXrefDtos);
                }
            }


           /* TeachersSubjectsXrefDao teachersSubjectsXrefDao = (TeachersSubjectsXrefDao) teacherDao.getSubjectInfo();
            TeachersSubjectsXrefDto teachersSubjectsXrefDto = new TeachersSubjectsXrefDto();

            if (!CollectionUtils.isEmpty(teachersSubjectsXrefDao)) {
                teachersSubjectsXrefDto.setSubjectId(teachersSubjectsXrefDao.getSubjectId());
                teachersSubjectsXrefDto.setSubjectName(teachersSubjectsXrefDao.getSubjectInfo().getSubject_name());
                dto.setSubjectInfo((List<TeachersSubjectsXrefDao>) teachersSubjectsXrefDto);
            } */
            teacherDtos.add(dto);
        }

        return teacherDtos;
    }

    public TeacherDto getTeaherInfo(Long id) throws CustomException {
        Optional<TeacherDao> teacherDao = teacherRepository.findById(id);
        if(teacherDao.isPresent()) {
            TeacherDto dto = new TeacherDto();
            BeanUtils.copyProperties(teacherDao.get(), dto);
            return dto;
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "No Teacher Found");
        }

    }

    public TeacherAppDto teacherLogin(TeacherDao teacherDao, String currentAcademicYear) throws CustomException {
        long mobile_no = teacherDao.getMobileNo();
        String pwd = teacherDao.getLoginPwd();
        Optional<TeacherDao> teacherDaoOptional = Optional.ofNullable(teacherRepository.findByMobileNoAndLoginPwd(mobile_no, pwd));
        if(teacherDaoOptional.isPresent()) {
            if(teacherDaoOptional.get().getStatus().equals("Active")) {
                TeacherAppDto dto = new TeacherAppDto();
                BeanUtils.copyProperties(teacherDaoOptional.get(), dto);

                dto.setSchoolInfo(teacherDaoOptional.get().getSchoolDao());

                // find current academic year info
                AcademicYearDao academicYearDao = academicYearSrevice.getAcademicYearInfoFromYearString(currentAcademicYear);
                dto.setCurrentAcademicYearInfo(academicYearDao);

                return dto;
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "You are not in Active State, please contact admin");
            }
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Username or Password");
        }
    }

    public void changeTeacherStatus(TeacherDao teacherDao) throws CustomException {
        Optional<TeacherDao> teacherDaoOptional = teacherRepository.findById(teacherDao.getId());
        if(teacherDaoOptional.isPresent()) {
            TeacherDao teacherDao1 = teacherDaoOptional.get();
            teacherDao1.setStatus(teacherDao.getStatus());
            teacherDao1.setRelevingDate(new Date());
            teacherRepository.save(teacherDao1);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Teacher Id");
        }
    }

    public Boolean mobileAvailability(TeacherDto teacherDto) {
        TeacherDao teacherDao = teacherRepository.findByMobileNo(teacherDto.getMobileNo());
        if(teacherDao != null && (teacherDto.getId() == null || teacherDto.getId() != teacherDao.getId())) {
            return true;
        }
        return false;
    }

    public Boolean aadharAvailability(TeacherDto teacherDto) {
        TeacherDao teacherDao = teacherRepository.findByAadharNo(teacherDto.getAadharNo());
        if(teacherDao != null && (teacherDto.getId() == null || teacherDto.getId() != teacherDao.getId())) {
            return true;
        }
        return false;
    }

    public Boolean emailAvailability(TeacherDto teacherDto) {
        TeacherDao teacherDao = teacherRepository.findByEmail(teacherDto.getEmail());
        if(teacherDao != null && (teacherDto.getId() == null || teacherDto.getId() != teacherDao.getId())) {
            return true;
        }
        return false;
    }

    public void teacherChangePassword(TeacherDao teacherDao) throws CustomException {
        Optional<TeacherDao> teacherDaoOptional = teacherRepository.findById(teacherDao.getId());
        if(teacherDaoOptional.isPresent()) {
            TeacherDao teacherDao1 = teacherDaoOptional.get();
            teacherDao1.setLoginPwd(teacherDao.getLoginPwd());
            teacherDao1.setUpdatedDate(new Date());
            teacherRepository.save(teacherDao1);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Teacher Id");
        }

    }

}

