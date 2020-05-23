package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.SuperAdminDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdminDao, Long> {
    SuperAdminDao findByLoginNameAndLoginPwd(String loginName, String loginPwd);
}
