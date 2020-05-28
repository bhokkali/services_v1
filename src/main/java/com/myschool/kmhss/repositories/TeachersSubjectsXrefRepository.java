package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.TeachersSubjectsXrefDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachersSubjectsXrefRepository extends JpaRepository<TeachersSubjectsXrefDao, Long> {
    public TeachersSubjectsXrefDao findBySubjectIdAndTeacherId(Long subjectId, Long teacherId);

    @Query( value = "select * from teacherssubjectsxref as t1 inner join teachers as t2 on t1.teacher_id = t2.teacher_id where t2.school_id=?1 and t1.subject_id=?2", nativeQuery = true)
    public List<TeachersSubjectsXrefDao> findSchoolTeachersBySubject(Long schoolId, Long subjectId);
}
