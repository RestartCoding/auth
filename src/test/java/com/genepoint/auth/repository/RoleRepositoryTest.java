package com.genepoint.auth.repository;

import com.genepoint.auth.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@SpringBootTest
public class RoleRepositoryTest {

    @Resource
    private RoleRepository roleRepository;

    @Test
    public void save() {
        Role role = new Role();
        role.setRoleName("admin");
//        Role save = roleRepository.save(role);
//        Assert.isTrue(save.getId() != null);
    }
}
