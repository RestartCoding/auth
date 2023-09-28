package com.genepoint.auth.repository;

import com.genepoint.auth.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {

    List<UserRole> findAllByRoleId(int roleId);

    List<UserRole> findAllByUserId(int userId);

    int deleteByUserId(int userId);
}
