package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.SubadminsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubadminsRepository extends JpaRepository<SubadminsDao, Long> {

    List<SubadminsDao> findBySchoolId(Long schoolId);

    SubadminsDao findByLoginNameAndLoginPwd(String loginName, String loginPwd);

}
