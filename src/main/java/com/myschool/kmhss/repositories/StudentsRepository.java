package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.SchoolDao;
import com.myschool.kmhss.dao.StudentsDao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface StudentsRepository extends JpaRepository<StudentsDao, Long> {

    //public List<StudentsDao> findBySchoolId(Long schoolId);

    @Query( value = "select * from students where school_id=?1 order by student_id desc limit 1", nativeQuery = true)
    StudentsDao findLastStudentInfo(Long schoolId);

    //@Query(value = "select * from students as t1 inner join parents as t2 on t1.parent_id = t2.parent_id where t2.parent_id = ?1", nativeQuery = true)
    @Query(value = "select * from students as t1 inner join parents as t2 on t1.parent_id = t2.parent_id inner join academic_students as t3 on t1.student_id = t3.student_id where t2.parent_id = ?1 and t3.promotion_status='InProgress'", nativeQuery = true)
    List<StudentsDao> findParentStudnets(Long parentId);

    @Query(value = "select count(*) from students where parent_id=?1 and status='Active'", nativeQuery = true)
    public Long findActiveStudentsCountByParentId(Long parentId);

    @Query( value = "select count(*) from students where school_id=?1 ", nativeQuery = true)
    public Long findBySchoolIdCount(Long schoolId);

    @Query( value = "select count(*) from students where status='Active' and school_id=?1 ", nativeQuery = true)
    public Long findSchoolActiveStudentsCount(Long schoolId);

}
