package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.AcademicYearDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYearDao, Long> {
    public AcademicYearDao findByAcademicYear(String academicYear);

    @Query(value = "select * from academic_years where academic_year_id > ?1 order by academic_year_id asc limit 1", nativeQuery = true)
    AcademicYearDao findNextAcademicYear(Long academicYearId);

}
