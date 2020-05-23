package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.ExamReportDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExamReportRepository extends JpaRepository<ExamReportDao, Long> {

    public List<ExamReportDao> findByExamIdAndSchoolGradeId(Long examId, Long schoolGradeId);

    @Query( value = "select sum(t1.mark_obtained) from exam_marks as t1 inner join exam_grades as t2 on t1.exam_grade_id = t2.exam_grade_id where t1.student_id=?1 and t2.exam_id=?2", nativeQuery = true)
    public double getTotalMark(Long studentId, Long examId);

    @Query(value = "select sum(t3.max_mark) from exam_grades as t3 where t3.exam_id=?1 and t3.school_grade_id=?2", nativeQuery = true)
    public double getTotalMaxMark(Long examId, Long schoolGradeId);

    ExamReportDao findByExamIdAndSchoolGradeIdAndStudentId(Long examId, Long schoolGradeId, Long studentId);

    @Query(value = "select * from exam_report as t1 inner join exams as t2 on t1.exam_id = t2.exam_id where t2.academic_year_id = ?1 and t1.school_grade_id = ?2", nativeQuery = true)
    List<ExamReportDao> findAllExamReports(Long academicYearId, Long schoolGradeId);
}
