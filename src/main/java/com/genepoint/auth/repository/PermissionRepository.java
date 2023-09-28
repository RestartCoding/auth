package com.genepoint.auth.repository;

import com.genepoint.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
public interface PermissionRepository extends CrudRepository<Permission, Integer>, JpaSpecificationExecutor<Permission> {

    Optional<Permission> findByName(String name);

    int countByIdIn(List<Integer> ids);
}
