package com.genepoint.auth.repository;

import com.genepoint.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    int countAllByIdIn(List<Integer> ids);

    Optional<Role> findByRoleName(String roleName);
}
