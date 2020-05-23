package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.ExamsDao;
import com.myschool.kmhss.dto.ExamsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamsRepository extends JpaRepository<ExamsDao, Long> {
    public List<ExamsDao> findBySchoolIdAndAcademicYearId(Long schoolId, Long academicYearId);
}
