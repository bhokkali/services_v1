package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.PeriodsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodsRepository extends JpaRepository<PeriodsDao, Long> {
    public List<PeriodsDao> findBySchoolIdOrderByPriorityAsc(Long schoolId);
}
