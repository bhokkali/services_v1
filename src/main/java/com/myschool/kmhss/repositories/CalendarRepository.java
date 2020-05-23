package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.CalendarDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarDao, Long> {
    public List<CalendarDao> findBySchoolIdAndAcademicYearId(Long schoolId, Long academicYearId);

}
