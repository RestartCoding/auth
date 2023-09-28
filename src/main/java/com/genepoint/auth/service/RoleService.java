package com.genepoint.auth.service;

import com.genepoint.auth.controller.dto.role.CreateRoleDTO;
import com.genepoint.auth.entity.Permission;
import com.genepoint.auth.entity.Role;
import com.genepoint.auth.exception.BusinessException;

import java.util.List;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
public interface RoleService {

    Role create(CreateRoleDTO createRoleDTO);

    /**
     * remove role
     *
     * @param id role id
     * @throws BusinessException if this role can't be deleted or if it doesn't exist
     */
    void removeById(int id);

    /**
     * list role's permission
     *
     * @param roleIds role id list
     * @return permission list
     * @throws BusinessException if there is at least one role id not found
     */
    List<Permission> listByRoleId(List<Integer> roleIds);
}
