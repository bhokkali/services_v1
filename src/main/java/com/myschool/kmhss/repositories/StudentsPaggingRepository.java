package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.StudentsDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsPaggingRepository extends PagingAndSortingRepository<StudentsDao, Long> {

    public Page<StudentsDao> findBySchoolId(Long schoolId, Pageable pageable);
}
