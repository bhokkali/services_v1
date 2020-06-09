package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicYearDao;
import com.myschool.kmhss.dao.AdminPermissionsDao;
import com.myschool.kmhss.dao.PermissionsDao;
import com.myschool.kmhss.dao.SubadminsDao;
import com.myschool.kmhss.dto.SubadminsDto;
import com.myschool.kmhss.dto.SubadminsLoginDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.AdminPermissionsRepository;
import com.myschool.kmhss.repositories.SubadminsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubadminsService {

    @Autowired
    SubadminsRepository subadminsRepository;

    @Autowired
    AdminPermissionsRepository adminPermissionsRepository;

    @Autowired
    AcademicYearSrevice academicYearSrevice;

    @Autowired
    SchoolServiceImpl schoolService;

    public List<SubadminsDto> getSubadminsList(Long schoolId) {
        List<SubadminsDao> subadminsDaos = subadminsRepository.findBySchoolId(schoolId);
        return convertDaoToDto(subadminsDaos);
    }

    private List<SubadminsDto> convertDaoToDto(List<SubadminsDao> subadminsDaos) {
        List<SubadminsDto> subadminsDtos = new ArrayList<>();
        for(SubadminsDao subadminsDao: subadminsDaos) {
            SubadminsDto dto = new SubadminsDto();
            BeanUtils.copyProperties(subadminsDao, dto);

            List<PermissionsDao> permissionsDaos = new ArrayList<>();
            for(AdminPermissionsDao adminPermissionsDao: subadminsDao.getAdminPermissionsDaos()) {
                permissionsDaos.add(adminPermissionsDao.getPermissionsDao());
            }
            dto.setPermissionsDaos(permissionsDaos);
            subadminsDtos.add(dto);
        }
        return subadminsDtos;
    }

    @Transactional
    public void createUpdateSubadmin(SubadminsDto subadminsDto) throws CustomException {
        SubadminsDao subadminsDao = new SubadminsDao();
        BeanUtils.copyProperties(subadminsDto, subadminsDao);
        if (subadminsDao.getId() == null) {
            subadminsDao.setCreatedDate(new Date());
        } else {
            Optional<SubadminsDao> subadminsDaoOptional = subadminsRepository.findById(subadminsDao.getId());
            if (subadminsDaoOptional.isPresent()) {
                subadminsDao.setUpdatedDate(new Date());
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Subadmin Id");
            }
        }
        subadminsDao = subadminsRepository.save(subadminsDao);


        if(!CollectionUtils.isEmpty(subadminsDto.getPermissions())) {
            //Remove all existing records
            adminPermissionsRepository.deleteBySubadminId(subadminsDao.getId());
            //Now add one by one
            for(Long permission: subadminsDto.getPermissions()) {
                AdminPermissionsDao adminPermissionsDao1 = new AdminPermissionsDao();
                adminPermissionsDao1.setPermissionId(permission);
                adminPermissionsDao1.setSubadminId(subadminsDao.getId());
                adminPermissionsRepository.save(adminPermissionsDao1);
            }
        }
    }

    public SubadminsLoginDto subadminLogin(SubadminsDao subadminsDao, Long academicYearId) throws CustomException {
        Optional<SubadminsDao> subadminsDaoOptional = Optional.ofNullable(subadminsRepository.findByLoginNameAndLoginPwd(subadminsDao.getLoginName(), subadminsDao.getLoginPwd()));
        if(subadminsDaoOptional.isPresent()) {
            SubadminsDao subadminsDao1 = subadminsDaoOptional.get();
                //get academic year info
                AcademicYearDao academicYearDao = academicYearSrevice.getAcademicYearInfo(academicYearId);
                if(academicYearDao == null) {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Academic Year");
                } else {
                    SubadminsLoginDto dto = new SubadminsLoginDto();
                    BeanUtils.copyProperties(subadminsDao1, dto);
                    dto.setSchoolDto(schoolService.getSchoolLoginInfo(dto.getSchoolId(), academicYearId));

                    List<PermissionsDao> permissionsDaos = new ArrayList<>();
                    for(AdminPermissionsDao adminPermissionsDao: subadminsDao1.getAdminPermissionsDaos()) {
                        permissionsDaos.add(adminPermissionsDao.getPermissionsDao());
                    }
                    dto.setPermissionsDaos(permissionsDaos);
                    return dto;
                    //dto.setAcademicYear(academicYearDao.getAcademicYear());
                    //dto.setSchoolName(subadminsDao1.getSchoolDao().getSchool_name());
                    //dto.setSchoolCode(subadminsDao1.getSchoolDao().getSchool_code());
                    //dto.setSchoolDao(subadminsDao1.getSchoolDao());
                    //return dto;
                }

        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Username and password");
        }
    }

}
