package com.genepoint.auth.service;

import com.genepoint.auth.controller.dto.user.CreateUserDTO;
import com.genepoint.auth.controller.dto.user.LoginDTO;
import com.genepoint.auth.controller.dto.user.UserPageQueryDTO;
import com.genepoint.auth.domain.Token;
import com.genepoint.auth.entity.Permission;
import com.genepoint.auth.entity.User;
import com.genepoint.auth.exception.BusinessException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xiabiao
 * @since 2023-08-26
 */
public interface UserService {

    Page<User> page(UserPageQueryDTO req);

    User info(int id);

    /**
     * remove user
     *
     * @param id user id
     * @throws BusinessException if this user can't be removed or if this user doesn't exist
     */
    void removeById(int id);

    /**
     * @param param param
     * @return user created
     */
    User create(CreateUserDTO param);

    List<Permission> listPermission(int userId);

    /**
     * @param dto dto
     * @return user info
     * @throws BusinessException if incorrect username or password
     */
    Token login(LoginDTO dto);

    void logout();
}
