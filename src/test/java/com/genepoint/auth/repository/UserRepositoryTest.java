package com.genepoint.auth.repository;

import com.genepoint.auth.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-08-26
 */
@SpringBootTest
public class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;

    @Test
    public void findById(){
        Optional<User> optionalUser = userRepository.findById(7);
//        Assert.isTrue(optionalUser.isPresent());
//        User user = optionalUser.get();
//        Assert.isTrue(user.getRoles().size() > 0);
    }

    @Test
    public void save(){
        User user = new User();
        user.setUsername("genepoint");
        user.setPassword("12345678");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
//        User saved = userRepository.save(user);
//        Assert.isTrue(saved.getId() != null);
    }
}
