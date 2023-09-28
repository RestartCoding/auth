package com.genepoint.auth.repository;

import com.genepoint.auth.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@SpringBootTest
public class UserRoleRepositoryTest {

    @Resource
    private UserRoleRepository userRoleRepository;

    @Test
    public void save(){
        UserRole userRole = new UserRole(7, 2);
        UserRole save = userRoleRepository.save(userRole);
    }
}
