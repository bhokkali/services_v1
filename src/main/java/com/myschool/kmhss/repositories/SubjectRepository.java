package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.SubjectDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectDao, Long> {

    @Query( value = "select distinct t1.subject_id, t1.subject_code, t1.subject_name, t1.cr_date, t1.up_date from subjects as t1 inner join teacherssubjectsxref as t2 on t1.subject_id = t2.subject_id inner join teachers as t3 on t2.teacher_id = t3.teacher_id where t3.school_id = ?1", nativeQuery = true)
    List<SubjectDao> findSchoolActiveSubjects(Long schoolId);

}
