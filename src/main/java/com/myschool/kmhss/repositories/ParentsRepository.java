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

    @Query( value = "select * from parents t1 where t1.school_id=?1 and t1.status=?2 and LOWER(t1.parent_name) like ?3%", nativeQuery = true)
    public Page<ParentsDao> searchParents(Long schoolId, String status, String parentName, Pageable pageable);

    @Query( value = "select count(*) from parents t1 where t1.school_id=?1 and status=?2 and LOWER(t1.parent_name) like ?3%", nativeQuery = true)
    public Long searchParentsCount(Long schoolId, String status, String parentName);

}
