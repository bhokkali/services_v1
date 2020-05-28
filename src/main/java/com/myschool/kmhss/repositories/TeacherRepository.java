package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.TeacherDao;
import com.myschool.kmhss.dto.TeacherDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherDao, Long> {

    public List<TeacherDao> findBySchoolId(Long schoolId);
    public TeacherDao findByMobileNoAndLoginPwd(long mobileNo, String pwd);

    @Query( value = "select count(*) from teachers where status='Active' and school_id=?1", nativeQuery = true)
    public Long findSchoolActiveTeachersCount(Long schoolId);

    public TeacherDao findByMobileNo(long mobileNo);
    public TeacherDao findByEmail(String email);
    public TeacherDao findByAadharNo(long aadharNo);
}
