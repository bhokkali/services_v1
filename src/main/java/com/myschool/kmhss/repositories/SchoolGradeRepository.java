package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.SchoolGradeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolGradeRepository extends JpaRepository<SchoolGradeDao, Long> {

    //List<SchoolGradeDao> findBySchoolId(Long schoolId);

    @Query(value = "select B.grade_name from school_grades A, grades B where A.grade_id = ?1 and A.grade_id = B.grade_id", nativeQuery = true)
    String findGradeName(Long schoolId);

    @Query( value = "select count(*) from school_grades where academic_year_id=?1 ", nativeQuery = true)
    public Long findSchoolGradesCount(Long academicYearId);

    @Query( value = "select * from school_grades as t1 inner join grades as t2 on t1.grade_id = t2. grade_id where t1.school_id = ?1 and t1.academic_year_id = ?2 order by t2.priority asc", nativeQuery = true)
    List<SchoolGradeDao> getGradesSchoolId(Long schoolId, Long academicYearId);

    @Query( value = "select * from school_grades as t1 inner join grades as t2 on t1.grade_id = t2. grade_id where t1.school_id = ?1 and t1.academic_year_id = ?2 and t1.teacher_id = ?3 order by t2.priority asc", nativeQuery = true)
    List<SchoolGradeDao> getTeacherGrades(Long schoolId, Long academicYearId, Long teacherId);

}
