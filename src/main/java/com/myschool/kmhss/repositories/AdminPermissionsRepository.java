package com.myschool.kmhss.repositories;

import com.myschool.kmhss.dao.AdminPermissionsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  AdminPermissionsRepository extends JpaRepository<AdminPermissionsDao, Long> {

    AdminPermissionsDao findByPermissionIdAndSubadminId(Long permissionId, Long subadminId);

    void deleteBySubadminId(Long subadminId);

}
