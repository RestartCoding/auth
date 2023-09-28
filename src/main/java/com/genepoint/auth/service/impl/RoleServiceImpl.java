package com.genepoint.auth.service.impl;

import com.genepoint.auth.controller.dto.role.CreateRoleDTO;
import com.genepoint.auth.entity.Permission;
import com.genepoint.auth.entity.Role;
import com.genepoint.auth.entity.RolePermission;
import com.genepoint.auth.entity.UserRole;
import com.genepoint.auth.exception.BusinessException;
import com.genepoint.auth.repository.PermissionRepository;
import com.genepoint.auth.repository.RolePermissionRepository;
import com.genepoint.auth.repository.RoleRepository;
import com.genepoint.auth.repository.UserRoleRepository;
import com.genepoint.auth.service.RoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final RolePermissionRepository rolePermissionRepository;

    private final UserRoleRepository userRoleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role create(CreateRoleDTO dto) {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        Optional<Role> roleByName = roleRepository.findByRoleName(role.getRoleName());
        if (roleByName.isPresent()) {
            log.error("role name [{}] already exist", role.getRoleName());
            throw new BusinessException("Role name already exist");
        }

        Role save = roleRepository.save(role);

        if (!CollectionUtils.isEmpty(dto.getPermissionIds())) {
            int count = permissionRepository.countByIdIn(dto.getPermissionIds());
            if (count < dto.getPermissionIds().size()) {
                log.error("Invalid permission id {}", dto.getPermissionIds());
                throw new BusinessException("Invalid permission id");
            }
            List<RolePermission> rolePermissions = dto.getPermissionIds().stream().distinct()
                    .map(permissionId -> {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(save.getId());
                        rolePermission.setPermissionId(permissionId);
                        return rolePermission;
                    }).collect(Collectors.toList());

            rolePermissionRepository.saveAll(rolePermissions);
        }
        return save;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeById(int id) {
        Optional<Role> roleById = roleRepository.findById(id);
        if (roleById.isEmpty()) {
            log.error("failed to delete role [{}] cause it doesn't exist", id);
            throw new BusinessException("Role doesn't exist");
        }

        List<UserRole> userRoles = userRoleRepository.findAllByRoleId(id);
        if (userRoles.size() > 0) {
            log.error("failed to delete role [{}] cause it's using", id);
            throw new BusinessException("Can't delete this role cause it's using");
        }

        roleRepository.deleteById(id);
        rolePermissionRepository.deleteByRoleId(id);
    }

    @Override
    public List<Permission> listByRoleId(List<Integer> roleIds) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleIdIn(roleIds);
        if (rolePermissions.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        Iterable<Permission> permissions = permissionRepository.findAllById(permissionIds);
        List<Permission> ret = new ArrayList<>();
        permissions.forEach(ret::add);
        return ret;
    }
}
