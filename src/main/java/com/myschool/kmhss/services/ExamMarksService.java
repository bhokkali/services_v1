package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.ExamGradesDao;
import com.myschool.kmhss.dao.ExamMarksDao;
import com.myschool.kmhss.dto.ExamGradesDto;
import com.myschool.kmhss.dto.ExamMarksDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.ExamGradesRespository;
import com.myschool.kmhss.repositories.ExamMarksRepository;
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
public class ExamMarksService {

    @Autowired
    ExamMarksRepository examMarksRepository;

    @Autowired
    ExamGradesRespository examGradesRespository;

    @Autowired
    ExamGradeService examGradeService;

    @Autowired
    MarkGradesService markGradesService;

    @Transactional
    public void createUpdateExamMarks(List<ExamMarksDao> examMarksDaos) throws CustomException {

        for(ExamMarksDao examMarksDao: examMarksDaos) {
            Optional<ExamGradesDao> examGradesDao = examGradesRespository.findById(examMarksDao.getExamGradeId());
            if(!examGradesDao.isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid School grade");
            }

            Optional<ExamMarksDao> examMarksDaoOptional = Optional.ofNullable(examMarksRepository.findByStudentIdAndExamGradeId(
                    examMarksDao.getStudentId(),
                    examMarksDao.getExamGradeId()
            ));

            if(examMarksDaoOptional.isPresent() && !examMarksDaoOptional.get().getId().equals(examMarksDao.getId())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Mark Already added to this subject");
            }

            if(examMarksDao.getAttendedStatus().equals("Yes")) {
                double marksDoubleValue = (double) examGradesDao.get().getMaxMark();
                double markPercent = (examMarksDao.getMarkObtained() / marksDoubleValue) * 100;
                examMarksDao.setMarkPercentage(markPercent);

                int convertedMarkPercent = (int) markPercent;
                String markGrade = markGradesService.getSpecificGrade(convertedMarkPercent);
                examMarksDao.setMarkGrade(markGrade);
            }

            if(examMarksDao.getId() == null) {
                examMarksDao.setCreatedDate(new Date());
            } else {
                examMarksDao.setUpdatedDate(new Date());
            }
            examMarksRepository.save(examMarksDao);
        }
    }

    /*public List<ExamMarksDto> ListStudentMarks(Long studentId, Long examGradeId) throws CustomException {
        List<ExamMarksDao> examMarksDaos = examMarksRepository.findByStudentIdAndExamGradeId(studentId, examGradeId);
        return convertDaoToDto(examMarksDaos);
    } */

    private List<ExamMarksDto> convertDaoToDto(List<ExamMarksDao> examMarksDaos) {
        List<ExamMarksDto> examMarksDtos = new ArrayList<>();

        for(ExamMarksDao examMarksDao: examMarksDaos) {
            examMarksDtos.add(convertDaoToDtoSingle(examMarksDao));
        }
        return examMarksDtos;
    }

    private ExamMarksDto convertDaoToDtoSingle(ExamMarksDao examMarksDao) {
        ExamMarksDto dto = new ExamMarksDto();
        BeanUtils.copyProperties(examMarksDao, dto);
        dto.setExamName(examMarksDao.getExamGradesDao().getExamsDao().getExamName());
        dto.setGradeName(examMarksDao.getExamGradesDao().getSchoolGradeDao().getGradeDao().getGrade_name());
        dto.setStudentName(examMarksDao.getStudentsDao().getStudentName());
        dto.setSubjectName(examMarksDao.getExamGradesDao().getSubjectDao().getSubject_name());
        dto.setMaxMark(examMarksDao.getExamGradesDao().getMaxMark());
        return dto;
    }

    public List<ExamMarksDto> ListExamGradeMarks(Long examId, Long schoolGradeId) throws CustomException {
        List<ExamGradesDto> examGradesDtos = examGradeService.listGradeExams(examId, schoolGradeId);
        if(CollectionUtils.isEmpty(examGradesDtos)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "No Grade Found");
        }

        List<ExamMarksDto> examMarksDtos = new ArrayList<>();

        for(ExamGradesDto examGradesDto: examGradesDtos) {
            List<ExamMarksDao> examMarksDaos = examMarksRepository.findByExamGradeId(examGradesDto.getId());
            for(ExamMarksDao examMarksDao: examMarksDaos) {
                examMarksDtos.add(convertDaoToDtoSingle(examMarksDao));
            }
        }

        return examMarksDtos;
    }

    public List<ExamMarksDto> ListStudentExamGradeMarks(Long examId, Long schoolGradeId, Long studentId) throws CustomException {
        List<ExamGradesDto> examGradesDtos = examGradeService.listGradeExams(examId, schoolGradeId);
        if(CollectionUtils.isEmpty(examGradesDtos)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "No Grade Found");
        }

        List<ExamMarksDto> examMarksDtos = new ArrayList<>();

        for(ExamGradesDto examGradesDto: examGradesDtos) {
            //List<ExamMarksDao> examMarksDaos = examMarksRepository.findByStudentIdAndExamGradeId(studentId, examGradesDto.getId());
            //for(ExamMarksDao examMarksDao: examMarksDaos) {
            Optional<ExamMarksDao> examMarksDaoOptional = Optional.ofNullable(examMarksRepository.findByStudentIdAndExamGradeId(studentId, examGradesDto.getId()));
                examMarksDtos.add(convertDaoToDtoSingle(examMarksDaoOptional.get()));
           // }
        }

        return examMarksDtos;
    }
}
