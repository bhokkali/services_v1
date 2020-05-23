package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.ExamGradesDao;
import com.myschool.kmhss.dto.ExamGradesDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.ExamGradesRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExamGradeService {

    @Autowired
    ExamGradesRespository examGradesRespository;

    public void createUpdateExamGrade(ExamGradesDao examGradesDao) throws CustomException {
        Optional<ExamGradesDao> examGradesDaoOptional = Optional.ofNullable(examGradesRespository.findByExamIdAndSchoolGradeIdAndSubjectId(
                examGradesDao.getExamId(),
                examGradesDao.getSchoolGradeId(),
                examGradesDao.getSubjectId()
        ));

        if(examGradesDaoOptional.isPresent() && !examGradesDaoOptional.get().getId().equals(examGradesDao.getId())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Subject already added to this grade in exam time table");
        }

        if(examGradesDao.getId() == null) {
            examGradesDao.setCreatedDate(new Date());
        } else {
            examGradesDao.setUpdatedDate(new Date());
        }
        examGradesRespository.save(examGradesDao);
    }

    public List<ExamGradesDto> listGradeExams(Long examId, Long schoolGradeId) {
        List<ExamGradesDao> examGradesDaos = examGradesRespository.findByExamIdAndSchoolGradeId(examId, schoolGradeId);
        return convertDaoToDto(examGradesDaos);

    }

    private List<ExamGradesDto> convertDaoToDto(List<ExamGradesDao> examGradesDaos) {
        List<ExamGradesDto> examGradesDtos = new ArrayList<>();

        for(ExamGradesDao examGradesDao: examGradesDaos) {
            ExamGradesDto dto = new ExamGradesDto();
            BeanUtils.copyProperties(examGradesDao, dto);
            dto.setExamName(examGradesDao.getExamsDao().getExamName());
            dto.setGradeName(examGradesDao.getSchoolGradeDao().getGradeDao().getGrade_name());
            dto.setSectionName(examGradesDao.getSchoolGradeDao().getSectionName());
            dto.setSubjectName(examGradesDao.getSubjectDao().getSubject_name());
            examGradesDtos.add(dto);
        }
        return examGradesDtos;
    }
}
