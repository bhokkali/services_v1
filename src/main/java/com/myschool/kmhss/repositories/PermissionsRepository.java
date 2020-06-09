package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.PermissionsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<PermissionsDao, Long> {

}
