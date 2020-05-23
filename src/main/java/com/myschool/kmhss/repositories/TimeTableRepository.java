package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.TimeTableDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTableDao, Long> {


    List<TimeTableDao> findBySchoolGradeIdAndPeriodId(Long schoolGradeId, Long periodId);
    List<TimeTableDao> findBySchoolGradeIdAndPeriodIdAndSubjectId(
            Long schoolGradeId,
            Long periodId,
            Long subjectId
    );
    List<TimeTableDao> findByPeriodIdAndTeacherIdAndWeekdayAndAcademicYearId(
            Long periodId,
            Long teacherId,
            String weekday,
            Long academicYearId
    );

    List<TimeTableDao> findBySchoolIdAndAcademicYearIdOrderByIdAsc(Long schoolId, Long academicYearId);

    List<TimeTableDao> findBySchoolIdAndAcademicYearIdAndSchoolGradeId(
            Long schoolId,
            Long academicYearId,
            Long schoolGradeId
    );

    List<TimeTableDao> findBySchoolIdAndAcademicYearIdAndTeacherId(
            Long schoolId,
            Long academicYearId,
            Long teacherId
    );

}
