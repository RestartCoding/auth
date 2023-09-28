package com.genepoint.auth.repository;

import com.genepoint.auth.entity.RolePermission;
import com.genepoint.auth.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId>, JpaSpecificationExecutor<RolePermission> {

    List<RolePermission> findAllByPermissionId(int permissionId);

    List<RolePermission> findAllByRoleIdIn(List<Integer> roleIds);

    int deleteByRoleId(int roleId);
}
