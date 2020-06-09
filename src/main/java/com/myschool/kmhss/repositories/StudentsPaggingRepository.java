package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.StudentsDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsPaggingRepository extends PagingAndSortingRepository<StudentsDao, Long> {

    public Page<StudentsDao> findBySchoolId(Long schoolId, Pageable pageable);

    @Query( value = "select * from students as t1 where t1.school_id=?1 and t1.student_name like %?2%", nativeQuery = true)
    public Page<StudentsDao> searchStudents(Long schoolId, String schoolName, Pageable pageable);
}
