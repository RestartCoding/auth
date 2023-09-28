package com.genepoint.auth.repository;

import com.genepoint.auth.entity.RolePermission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@SpringBootTest
public class RolePermissionRepositoryTest {

    @Resource
    private RolePermissionRepository rolePermissionRepository;

    @Test
    public void save(){
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(1);
        rolePermission.setPermissionId(1);
        RolePermission save = rolePermissionRepository.save(rolePermission);
    }
}
