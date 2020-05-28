package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.ParentsDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentsRepository extends PagingAndSortingRepository<ParentsDao, Long> {

    public Page<ParentsDao> findBySchoolId(Long schoolId, Pageable pageable);
    public ParentsDao findByMobileNoAndLoginPwd(long mobileNo, String pwd);
    public ParentsDao findByMobileNo(long mobileNo);
    public ParentsDao findByEmail(String email);
    public ParentsDao findByAadharNo(long aadharNo);

    @Query( value = "select count(*) from parents where school_id=?1 ", nativeQuery = true)
    public Long findBySchoolIdCount(Long schoolId);

    @Query( value = "select count(*) from parents where status='Active' and school_id=?1", nativeQuery = true)
    public Long findSchoolActiveParentsCount(Long schoolId);

}
