package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.GradeDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<GradeDao, Long> {
}
