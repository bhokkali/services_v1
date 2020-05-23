package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.SchoolCircularsDao;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolCircularsRepository extends JpaRepository<SchoolCircularsDao, Long> {

    public List<SchoolCircularsDao> findBySchoolIdAndAcademicYearId(Long schoolId, Long academicYearId);

}
