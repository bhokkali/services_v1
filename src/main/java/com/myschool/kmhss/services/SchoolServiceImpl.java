package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicStudentsDao;
import com.myschool.kmhss.dao.AcademicYearDao;
import com.myschool.kmhss.dao.SchoolDao;
import com.myschool.kmhss.dto.SchoolChangePwdDto;
import com.myschool.kmhss.dto.SchoolDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    AcademicYearSrevice academicYearSrevice;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SchoolGradeRepository schoolGradeRepository;

    @Autowired
    ParentsRepository parentsRepository;

    @Autowired
    StudentsRepository studentsRepository;

    @Autowired
    AcademicStudentsRepository academicStudentsRepository;

    @Override
    public List<SchoolDto> listSchools() {
        List<SchoolDto> schoolDtos = new ArrayList<>();
        List<SchoolDao> schoolDaos = schoolRepository.findAll();

        if (!CollectionUtils.isEmpty(schoolDaos)) {
            for(SchoolDao schoolDao: schoolDaos) {
                SchoolDto dto = new SchoolDto();
                BeanUtils.copyProperties(schoolDao, dto);
                schoolDtos.add(dto);
            }
        }
        return schoolDtos;
    }

    @Override
    public void createUpdateSchool(SchoolDao schoolDao) throws CustomException {
        if (schoolDao.getId() != null) {
            Optional<SchoolDao> schoolDaoOptional = schoolRepository.findById(schoolDao.getId());
            if(schoolDaoOptional.isPresent()) {
                SchoolDao schoolDao1 = schoolDaoOptional.get();
                //schoolDao.setActiveStatus(schoolDao1.getActiveStatus());
                //schoolDao.setLoginName(schoolDao1.getLoginName());
                //schoolDao.setLoginPwd(schoolDao1.getLoginPwd());
                schoolDao.setUpdatedDate(new Date());
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid School ID");
            }

        } else {
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            cal.add(Calendar.YEAR, 1); // to get previous year add -1
            Date nextYear = cal.getTime();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate= formatter.format(nextYear);
            schoolDao.setExpiryDate(strDate);

            schoolDao.setCreatedDate(new Date());
        }
        schoolRepository.save(schoolDao);

    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }

    public void changeSchoolStatus(SchoolDao schoolDao) throws CustomException {
        Optional<SchoolDao>  schoolDaoOptional = schoolRepository.findById(schoolDao.getId());
       if(schoolDaoOptional.isPresent()) {
           SchoolDao schoolDao1 = schoolDaoOptional.get();
           schoolDao1.setActiveStatus(schoolDao.getActiveStatus());
           schoolRepository.save(schoolDao1);
       } else {
           throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Info");
       }

    }

    public SchoolDto getSchoolInfo(Long id) throws CustomException {
        Optional<SchoolDao> schoolDaoOptional = schoolRepository.findById(id);
        if(schoolDaoOptional.isPresent()) {
            SchoolDto schoolDto = new SchoolDto();
            BeanUtils.copyProperties(schoolDaoOptional.get(), schoolDto);
            return schoolDto;
        } else {
            throw  new CustomException(HttpStatus.BAD_REQUEST, "No School Found");
        }

    }

    public List<SchoolDto> getActiveSchools(String status) {
        List<SchoolDto> schoolDtos = new ArrayList<>();
        List<SchoolDao> schoolDaos = schoolRepository.findByActiveStatus(status);

        if (!CollectionUtils.isEmpty(schoolDaos)) {
            for(SchoolDao schoolDao: schoolDaos) {
                SchoolDto dto = new SchoolDto();
                BeanUtils.copyProperties(schoolDao, dto);
                schoolDtos.add(dto);
            }
        }
        return schoolDtos;

    }

    public SchoolDto schoolLogin(SchoolDao schoolDao, Long academicYearId) throws CustomException {
        Optional<SchoolDao> schoolDaoOptional = Optional.ofNullable(schoolRepository.findByLoginNameAndLoginPwd(schoolDao.getLoginName(), schoolDao.getLoginPwd()));
        if(schoolDaoOptional.isPresent()) {
            return getSchoolDto(schoolDaoOptional.get(), academicYearId);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Username and password");
        }
    }

    public SchoolDto getSchoolLoginInfo(Long schoolId, Long academicYearId) throws CustomException {
        Optional<SchoolDao> schoolDaoOptional = schoolRepository.findById(schoolId);
        if(schoolDaoOptional.isPresent()) {
            return getSchoolDto(schoolDaoOptional.get(), academicYearId);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid School Id");
        }
    }



    private SchoolDto getSchoolDto(SchoolDao schoolDao, Long academicYearId) throws CustomException {
        //SchoolDao schoolDao1 = schoolDaoOptional.get();
        if(schoolDao.getActiveStatus().equals("Active")) {
            //get academic year info
            AcademicYearDao academicYearDao = academicYearSrevice.getAcademicYearInfo(academicYearId);
            if(academicYearDao == null) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Academic Year");
            } else {
                SchoolDto dto = new SchoolDto();
                BeanUtils.copyProperties(schoolDao, dto);
                dto.setAcademicYearId(academicYearDao.getId());
                dto.setAcademicYear(academicYearDao.getAcademicYear());
                dto.setTeachersCount(teacherRepository.findSchoolActiveTeachersCount(dto.getId()));
                dto.setGradesCount(schoolGradeRepository.findSchoolGradesCount(academicYearId, dto.getId()));
                dto.setParentsCount(parentsRepository.findSchoolActiveParentsCount(dto.getId()));
                dto.setStudentsCount(studentsRepository.findSchoolActiveStudentsCount(dto.getId()));
                return dto;
            }
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "School is not in Active Status");
        }
    }

    public void schoolChangePassword(SchoolChangePwdDto schoolChangePwdDto) throws CustomException {
        Optional<SchoolDao> schoolDaoOptional = schoolRepository.findById(schoolChangePwdDto.getId());
        if(schoolDaoOptional.isPresent()) {
            SchoolDao schoolDao = schoolDaoOptional.get();
            if(schoolDao.getLoginPwd().equals(schoolChangePwdDto.getOldPwd())) {
                schoolDao.setLoginPwd(schoolChangePwdDto.getNewPwd());
                schoolRepository.save(schoolDao);
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Old Password");
            }
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid School Id");
        }
    }
}
