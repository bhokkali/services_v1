package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicYearDao;
import com.myschool.kmhss.dao.ParentsDao;
import com.myschool.kmhss.dto.ParentsAppDto;
import com.myschool.kmhss.dto.ParentsDto;
import com.myschool.kmhss.dto.ParentsPaginatedDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.ParentsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParentsService {
    @Autowired
    ParentsRepository parentsRepository;

    @Autowired
    AcademicYearSrevice academicYearSrevice;


    public ParentsPaginatedDto getAllParents(Long schoolId, Integer perPage, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, perPage, Sort.by(Sort.Direction.ASC, "parentName"));
        Page<ParentsDao> parentsDaoPage = parentsRepository.findBySchoolId(schoolId, pageable);
        List<ParentsDao> parentsDaos = new ArrayList<>();
        if(parentsDaoPage.hasContent()) {
            parentsDaos = parentsDaoPage.getContent();
        }
        ParentsPaginatedDto parentsPaginatedDto = new ParentsPaginatedDto();
        Long count = parentsRepository.findBySchoolIdCount(schoolId);
        parentsPaginatedDto.setCount(count);
        parentsPaginatedDto.setParentsList(convertDaoTODto(parentsDaos));
        return parentsPaginatedDto;
    }

    private List<ParentsDto> convertDaoTODto(List<ParentsDao> parentsDaos) {
        List<ParentsDto> parentsDtos = new ArrayList<>();

        for(ParentsDao parentsDao: parentsDaos) {
            ParentsDto dto = new ParentsDto();
            BeanUtils.copyProperties(parentsDao, dto);
            parentsDtos.add(dto);
        }

        return parentsDtos;
    }

    public void createUpdateParents(ParentsDao parentsDao) throws CustomException {

        if(!mobileAvailability(parentsDao)) {
            if(!emailAvailability(parentsDao)) {
                if(!aadharAvailability(parentsDao)) {
                    if (parentsDao.getId() == null) {
                        parentsDao.setCreatedDate(new Date());
                    } else {
                        Optional<ParentsDao> parentsDao1 = parentsRepository.findById(parentsDao.getId());
                        if (parentsDao1.isPresent()) {
                            parentsDao.setUpdatedDate(new Date());
                        } else {
                            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Parent Id");
                        }
                    }
                    parentsRepository.save(parentsDao);
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

    public Boolean mobileAvailability(ParentsDao parentsDao) {
        ParentsDao parentsDao1 = parentsRepository.findByMobileNo(parentsDao.getMobileNo());
        if(parentsDao1 != null && (parentsDao.getId() == null || parentsDao.getId() != parentsDao1.getId())) {
            return true;
        }
        return false;
    }

    public Boolean aadharAvailability(ParentsDao parentsDao) {
        ParentsDao parentsDao1 = parentsRepository.findByAadharNo(parentsDao.getAadharNo());
        if(parentsDao1 != null && (parentsDao.getId() == null || parentsDao.getId() != parentsDao1.getId())) {
            return true;
        }
        return false;
    }

    public Boolean emailAvailability(ParentsDao parentsDao) {
        ParentsDao parentsDao1 = parentsRepository.findByEmail(parentsDao.getEmail());
        if(parentsDao1 != null && (parentsDao.getId() == null || parentsDao.getId() != parentsDao1.getId())) {
            return true;
        }
        return false;
    }


    public ParentsDto getParentInfo(Long id) throws CustomException {
        Optional<ParentsDao> parentsDao = parentsRepository.findById(id);
        if(parentsDao.isPresent()) {
            ParentsDto dto = new ParentsDto();
            BeanUtils.copyProperties(parentsDao.get(), dto);
            return dto;
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "No Parent Found");
        }

    }

    public ParentsAppDto parentsLogin(ParentsDao parentsDao, String currentAcademicYear) throws CustomException {
        Optional<ParentsDao>  parentsDaoOptional = Optional.ofNullable(parentsRepository.findByMobileNoAndLoginPwd(parentsDao.getMobileNo(), parentsDao.getLoginPwd()));
        if(parentsDaoOptional.isPresent()) {
            if(parentsDaoOptional.get().getStatus().equals("Active")) {
                ParentsAppDto dto = new ParentsAppDto();
                BeanUtils.copyProperties(parentsDaoOptional.get(), dto);
                dto.setSchoolInfo(parentsDaoOptional.get().getSchoolDao());

                // find current academic year info
                AcademicYearDao academicYearDao = academicYearSrevice.getAcademicYearInfoFromYearString(currentAcademicYear);
                dto.setCurrentAcademicYearInfo(academicYearDao);

                return dto;
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, "You are not in Active State, please contact admin");
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Username or Password");
        }
    }

    public void changeParentStatus(Long parentId, String status) throws CustomException {
        Optional<ParentsDao> parentsDaoOptional = parentsRepository.findById(parentId);
        if(parentsDaoOptional.isPresent()) {
            ParentsDao parentsDao = parentsDaoOptional.get();
            parentsDao.setStatus(status);
            createUpdateParents(parentsDao);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Parent Id");
        }
    }

    public void parentChangePassword(ParentsDao parentsDao) throws CustomException {
        Optional<ParentsDao> parentsDaoOptional = parentsRepository.findById(parentsDao.getId());
        if(parentsDaoOptional.isPresent()) {
            ParentsDao parentsDao1 = parentsDaoOptional.get();
            parentsDao1.setLoginPwd(parentsDao.getLoginPwd());
            createUpdateParents(parentsDao1);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Parent Id");
        }
    }
}
