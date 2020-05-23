package com.myschool.kmhss.services;

import com.myschool.kmhss.dao.ExamReportDao;
import com.myschool.kmhss.dto.ExamGradesDto;
import com.myschool.kmhss.dto.ExamMarksDto;
import com.myschool.kmhss.dto.ExamReportDto;
import com.myschool.kmhss.dto.StudentExamReportDto;
import com.myschool.kmhss.exception.CustomException;
import com.myschool.kmhss.repositories.ExamReportRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExamReportService {

    @Autowired
    ExamReportRepository examReportRepository;

    @Autowired
    MarkGradesService markGradesService;

    @Autowired
    ExamGradeService examGradeService;

    @Autowired
    ExamMarksService examMarksService;

    @Transactional
    public void createUpdateExamReport(List<ExamReportDao> examReportDaos) throws CustomException {

        for(ExamReportDao examReportDao: examReportDaos) {

            //set total_marks
            double totalMark = examReportRepository.getTotalMark(examReportDao.getStudentId(), examReportDao.getExamId());
            BigDecimal bd = new BigDecimal(totalMark).setScale(2, RoundingMode.HALF_UP);
            double newTotalMark = bd.doubleValue();
            examReportDao.setTotalMarks(newTotalMark);

            //set stotal_max_marks
            double totalMaxMark = examReportRepository.getTotalMaxMark(examReportDao.getExamId(), examReportDao.getSchoolGradeId());
            examReportDao.setTotalMaxMarks(totalMaxMark);

            //set overall_percentage
            double totalMarkPercent = (totalMark / totalMaxMark) * 100;
            BigDecimal bd1 = new BigDecimal(totalMarkPercent).setScale(2, RoundingMode.HALF_UP);
            double newTotalMarkPercent = bd1.doubleValue();
            examReportDao.setOverallPercentage(newTotalMarkPercent);

            //set overall_grade
            int convertedMarkPercent = (int) totalMarkPercent;
            String totalMarkGrade = markGradesService.getSpecificGrade(convertedMarkPercent);
            examReportDao.setOverallGrade(totalMarkGrade);

            if(examReportDao.getId() == null) {
                examReportDao.setCreatedDate(new Date());
            } else {
                examReportDao.setUpdatedDate(new Date());
            }
           examReportRepository.save(examReportDao);
        }

    }

    public List<ExamReportDto> listExamReports(Long examId, Long schoolGradeId) throws CustomException {
        List<ExamReportDao> examReportDaos = examReportRepository.findByExamIdAndSchoolGradeId(examId, schoolGradeId);
        return convertDaoToDto(examReportDaos);
    }

    public List<ExamReportDto> listAllExamReports(Long academicYearId, Long schoolGradeId) throws CustomException {
        List<ExamReportDao> examReportDaos = examReportRepository.findAllExamReports(academicYearId, schoolGradeId);
        return convertDaoToDto(examReportDaos);
    }

    public StudentExamReportDto getStudentExamReport(Long examId, Long schoolGradeId, Long studentId) throws CustomException {

        StudentExamReportDto studentExamReportDto = new StudentExamReportDto();
        studentExamReportDto.setStudentId(studentId);

        //get Report
        ExamReportDao examReportDao = examReportRepository.findByExamIdAndSchoolGradeIdAndStudentId(examId, schoolGradeId, studentId);
        if(examReportDao == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Report not generated");
        }
        ExamReportDto examReportDto = convertDaoToDtoSingle(examReportDao);
        studentExamReportDto.setExamReportDto(examReportDto);

        //get marks list
        List<ExamMarksDto> examMarksDtos = examMarksService.ListStudentExamGradeMarks(examId, schoolGradeId, studentId);
        studentExamReportDto.setExamMarksDtoList(examMarksDtos);

        return studentExamReportDto;

    }

    private List<ExamReportDto> convertDaoToDto(List<ExamReportDao> examReportDaos) {
        List<ExamReportDto> examReportDtos = new ArrayList<>();

        for(ExamReportDao examReportDao: examReportDaos) {
            examReportDtos.add(convertDaoToDtoSingle(examReportDao));
        }
        return examReportDtos;
    }

    private ExamReportDto convertDaoToDtoSingle(ExamReportDao examReportDao) {
        ExamReportDto dto = new ExamReportDto();
        BeanUtils.copyProperties(examReportDao, dto);
        dto.setExamName(examReportDao.getExamsDao().getExamName());
        dto.setGradeName(examReportDao.getSchoolGradeDao().getGradeDao().getGrade_name());
        dto.setStudentName(examReportDao.getStudentsDao().getStudentName());
        return dto;
    }


}
