package com.genepoint.auth.repository;

import com.genepoint.auth.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@SpringBootTest
public class PermissionRepositoryTest {

    @Resource
    private PermissionRepository permissionRepository;

    @Test
    public void save(){
        Permission permission = new Permission();
        permission.setName("user:create");
//        permissionRepository.save(permission);
    }
}
