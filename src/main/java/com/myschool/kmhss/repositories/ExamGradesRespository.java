package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.ExamGradesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamGradesRespository extends JpaRepository<ExamGradesDao, Long> {

    public List<ExamGradesDao> findByExamIdAndSchoolGradeId(Long examId, Long schoolGradeId);
    public ExamGradesDao findByExamIdAndSchoolGradeIdAndSubjectId(Long examId, Long schoolGradeId, Long subjectId);

}
