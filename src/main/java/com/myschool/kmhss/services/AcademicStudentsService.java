package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.AcademicStudentsDao;
import com.myschool.kmhss.dao.AcademicYearDao;
import com.myschool.kmhss.dto.AcademicStudentsDto;
import com.myschool.kmhss.dto.SchoolGradeDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.AcademicStudentsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.undo.CannotUndoException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AcademicStudentsService {

    @Autowired
    AcademicStudentsRepository academicStudentsRepository;

    @Autowired
    AcademicYearSrevice academicYearSrevice;

    @Autowired
    StudentsService studentsService;

    @Autowired
    SchoolGradeService schoolGradeService;

    public void createUpdateAcademicStudents(AcademicStudentsDao academicStudentsDao) {

        if(academicStudentsDao.getId() == null) {
            academicStudentsDao.setCreatedDate(new Date());
        } else {
            academicStudentsDao.setUpdatedDate(new Date());
        }
        academicStudentsRepository.save(academicStudentsDao);
    }

    public List<AcademicStudentsDto> listAcademicStudents(Long schoolId, Long academicYearId, Long schoolGradeId) throws CustomException {
        List<AcademicStudentsDao> academicStudentsDaos = academicStudentsRepository.findBySchoolIdAndAcademicYearIdAndSchoolGradeId(
                schoolId,
                academicYearId,
                schoolGradeId
                );
        return convertDaoToDto(academicStudentsDaos);
    }

    public List<AcademicStudentsDao> listAcademicStudentsByGrade(Long schoolGradeId) {
        List<AcademicStudentsDao> academicStudentsDaos = academicStudentsRepository.findBySchoolGradeId(schoolGradeId);
        return academicStudentsDaos;
    }

    private List<AcademicStudentsDto> convertDaoToDto(List<AcademicStudentsDao> academicStudentsDaos) throws CustomException {
        List<AcademicStudentsDto> academicStudentsDtos = new ArrayList<>();

        for(AcademicStudentsDao academicStudentsDao: academicStudentsDaos) {
            AcademicStudentsDto dto = new AcademicStudentsDto();
            BeanUtils.copyProperties(academicStudentsDao, dto);
            dto.setStudentName(academicStudentsDao.getStudentsDao().getStudentName());
            dto.setGradeName(academicStudentsDao.getSchoolGradeDao().getGradeDao().getGrade_name());
            dto.setAcademicYear(academicStudentsDao.getAcademicYearDao().getAcademicYear());

            // get promotion grade
            if(academicStudentsDao.getPromotionStatus().equals("Promoted") || academicStudentsDao.getPromotionStatus().equals("Depromoted")) {
                SchoolGradeDto schoolGradeDto = schoolGradeService.getSchoolGradeInfo(academicStudentsDao.getPromotionGradeId());
                dto.setPromotedGradeName(schoolGradeDto.getGradeName());
            }
            academicStudentsDtos.add(dto);
        }
        return academicStudentsDtos;
    }

    public AcademicStudentsDao getStudentCurrentGrade(Long schoolId, Long studentId) {
        AcademicStudentsDao academicStudentsDao = academicStudentsRepository.findBySchoolIdAndStudentIdAndPromotionStatus(
                schoolId, studentId, "InProgress"
        );
        if(academicStudentsDao != null) {
            return academicStudentsDao;
        }
        return null;
    }

    public AcademicStudentsDao getAcademicStudentInfo(Long schoolId, Long academicYearId, Long studentId) {
        AcademicStudentsDao academicStudentsDao = academicStudentsRepository.findBySchoolIdAndAcademicYearIdAndStudentIdAndPromotionStatus(
                schoolId, academicYearId, studentId, "InProgress"
        );
        if(academicStudentsDao != null) {
            return academicStudentsDao;
        }
        return null;

    }

    @Transactional
    public void updateAcademicPromotion(List<AcademicStudentsDto> academicStudentsDtos) throws CustomException {
        for(AcademicStudentsDto academicStudentsDto: academicStudentsDtos) {
            Optional<AcademicStudentsDao> academicStudentsDaoOptional = academicStudentsRepository.findById(academicStudentsDto.getId());
            if(academicStudentsDaoOptional.isPresent()) {
                // move optional obj to main object
                AcademicStudentsDao academicStudentsDaoMain = academicStudentsDaoOptional.get();

                // find next Academic Year Id
                AcademicYearDao academicYearDao = academicYearSrevice.getNextAcademicYear(academicStudentsDaoMain.getAcademicYearId());

                //string Status
                String promoStatus = academicStudentsDto.getPromotionStatus();
                //Operation against prootion status
                if(promoStatus.equals("Promoted") || promoStatus.equals("Depromoted")) {
                    AcademicStudentsDao academicStudentsDao = new AcademicStudentsDao();
                    academicStudentsDao.setSchoolId(academicStudentsDaoMain.getSchoolId());
                    academicStudentsDao.setAcademicYearId(academicYearDao.getId());
                    academicStudentsDao.setStudentId(academicStudentsDaoMain.getStudentId());
                    academicStudentsDao.setPromotionStatus("InProgress");
                    academicStudentsDao.setSchoolGradeId(academicStudentsDto.getSchoolGradeId());
                    /*if(promoStatus.equals("Promoted")) {
                        academicStudentsDao.setSchoolGradeId(academicStudentsDto.getSchoolGradeId());
                    } else if(promoStatus.equals("Depromoted")) {
                        academicStudentsDao.setSchoolGradeId(academicStudentsDaoMain.getSchoolGradeId());
                    } */
                    createUpdateAcademicStudents(academicStudentsDao);
                } else {
                    studentsService.changeStudentStatus(academicStudentsDaoMain.getStudentId(), "InActive");
                }

                academicStudentsDaoMain.setPromotionStatus(academicStudentsDto.getPromotionStatus());
                academicStudentsDaoMain.setPromotionGradeId(academicStudentsDto.getSchoolGradeId());
                createUpdateAcademicStudents(academicStudentsDaoMain);

            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Academic Id");
            }
        }
    }


}
