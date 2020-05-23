package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.TeachersSubjectsXrefDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachersSubjectsXrefRepository extends JpaRepository<TeachersSubjectsXrefDao, Long> {
    public List<TeachersSubjectsXrefDao> findBySubjectId(Long subjectId);
}
