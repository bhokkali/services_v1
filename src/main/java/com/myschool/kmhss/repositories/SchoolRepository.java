package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.SchoolDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<SchoolDao,  Long> {
    public List<SchoolDao> findByActiveStatus(String status);
    public SchoolDao findByLoginNameAndLoginPwd(String loginName, String loginPwd);
    public SchoolDao findByIdAndLoginPwd(Long id, String pwd);
}
