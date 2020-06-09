package com.myschool.kmhss.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="admin_permissions")
public class AdminPermissionsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name="subadmin_id")
    private Long subadminId;

    @Column(name="permission_id")
    private Long permissionId;

    @ManyToOne
    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
    private PermissionsDao permissionsDao;
}
