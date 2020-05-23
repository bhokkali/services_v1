package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.TeacherAttendanceDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherAttendanceRespository extends JpaRepository<TeacherAttendanceDao, Long> {

    public List<TeacherAttendanceDao> findBySchoolIdAndAcademicYearId(Long schoolId, Long academicYearId);
    public List<TeacherAttendanceDao> findBySchoolIdAndAcademicYearIdAndTeacherId(Long schoolId,
                                                                                  Long academicYearId,
                                                                                  Long teacherId);

}
