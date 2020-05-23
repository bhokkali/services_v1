package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.ExamMarksDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamMarksRepository extends JpaRepository<ExamMarksDao, Long> {

    public ExamMarksDao findByStudentIdAndExamGradeId(Long studentId, Long examGradeId);
    public List<ExamMarksDao> findByExamGradeId(Long examGradeId);

}
