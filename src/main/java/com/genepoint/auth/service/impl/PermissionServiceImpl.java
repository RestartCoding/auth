package com.genepoint.auth.service.impl;

import com.genepoint.auth.controller.dto.permission.CreatePermissionDTO;
import com.genepoint.auth.entity.Permission;
import com.genepoint.auth.entity.RolePermission;
import com.genepoint.auth.exception.BusinessException;
import com.genepoint.auth.repository.PermissionRepository;
import com.genepoint.auth.repository.RolePermissionRepository;
import com.genepoint.auth.service.PermissionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-09-06
 */
@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    private final PermissionRepository permissionRepository;

    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public Permission create(CreatePermissionDTO dto) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);

        Optional<Permission> permissionByName = permissionRepository.findByName(permission.getName());
        if (permissionByName.isPresent()) {
            log.error("permission name [{}] already exist", permission.getName());
            throw new BusinessException("name already exist");
        }

        return permissionRepository.save(permission);
    }

    @Override
    public void removeById(int id) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAllByPermissionId(id);
        if (rolePermissions.size() > 0) {
            log.error("failed to delete permission [{}] cause it's using", id);
            throw new BusinessException("You can't delete this permission cause it's using");
        }

        Optional<Permission> permissionById = permissionRepository.findById(id);
        if (permissionById.isEmpty()) {
            log.error("failed to delete permission [{}] cause it doesn't exist", id);
            throw new BusinessException("Permission doesn't exist");
        }

        permissionRepository.deleteById(id);
    }
}