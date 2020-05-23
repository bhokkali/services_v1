package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.MarkGradesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkGradesRepository extends JpaRepository<MarkGradesDao, Long> {

    public List<MarkGradesDao> findBySchoolId(Long schoolId);

    @Query(value = "select mark_grade from mark_grades where min_mark <= ?1 and max_mark >= ?1", nativeQuery = true)
    String findByMark(Integer mark);
}


