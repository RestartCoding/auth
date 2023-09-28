package com.genepoint.auth.service;

import com.genepoint.auth.controller.dto.permission.CreatePermissionDTO;
import com.genepoint.auth.entity.Permission;

/**
 * @author xiabiao
 * @since 2023-09-06
 */
public interface PermissionService {

    /**
     * create a new permission
     *
     * @param dto dto
     * @return permission
     * @throws com.genepoint.auth.exception.BusinessException if permission name already exist
     */
    Permission create(CreatePermissionDTO dto);

    /**
     * @param id permission id
     * @throws com.genepoint.auth.exception.BusinessException if this permission can't be remove
     */
    void removeById(int id);
}
