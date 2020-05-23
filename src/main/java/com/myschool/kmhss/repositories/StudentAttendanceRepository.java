package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.StudentAttendanceDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendanceDao, Long> {

    @Query( value = "select * from student_attendance as t1 inner join academic_students as t2 on t1.academic_student_id = t2.academic_student_id where t2.school_grade_id = ?1 and t1.absent_date = ?2", nativeQuery = true)
    List<StudentAttendanceDao> findStudentGradeAttendance(Long studentGradeId, Date absentDate);

    StudentAttendanceDao findByAcademicStudentIdAndAbsentDate(Long academicStudentId, Date absentDate);

    List<StudentAttendanceDao> findByAcademicStudentId(Long academicStudentId);

    @Query( value = "select * from student_attendance as t1 inner join academic_students as t2 on t1.academic_student_id = t2.academic_student_id where t2.school_grade_id = ?1", nativeQuery = true)
    List<StudentAttendanceDao> findStudentAttendanceCalendar(Long studentGradeId);
}
