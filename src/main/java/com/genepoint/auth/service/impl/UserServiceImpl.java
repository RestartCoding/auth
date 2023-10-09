package com.genepoint.auth.service.impl;

import com.genepoint.auth.constant.SysConstant;
import com.genepoint.auth.controller.dto.user.CreateUserDTO;
import com.genepoint.auth.controller.dto.user.LoginDTO;
import com.genepoint.auth.controller.dto.user.UserPageQueryDTO;
import com.genepoint.auth.domain.Token;
import com.genepoint.auth.domain.TokenStorageStrategy;
import com.genepoint.auth.entity.Permission;
import com.genepoint.auth.entity.User;
import com.genepoint.auth.entity.UserRole;
import com.genepoint.auth.exception.BusinessException;
import com.genepoint.auth.repository.RoleRepository;
import com.genepoint.auth.repository.UserRepository;
import com.genepoint.auth.repository.UserRoleRepository;
import com.genepoint.auth.service.RoleService;
import com.genepoint.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiabiao
 * @since 2023-08-26
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleService roleService;

    private final TokenStorageStrategy tokenStorage;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<User> page(UserPageQueryDTO req) {
        Specification<User> spec = new Specification<>() {

            final List<Predicate> predicates = new ArrayList<>();

            @Override
            public Predicate toPredicate(@NonNull Root<User> root, @NonNull CriteriaQuery<?> cq, @NonNull CriteriaBuilder cb) {
                if (StringUtils.hasText(req.getUsername())) {
                    Predicate usernameLike = cb.like(root.get("username"), "%" + req.getUsername() + "%");
                    predicates.add(usernameLike);
                }
                if (req.getBeginTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("createTime"), req.getBeginTime()));
                }
                if (req.getEndTime() != null) {
                    predicates.add(cb.lessThan(root.<Date>get("createTime"), req.getEndTime()));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };

        return userRepository.findAll(spec, PageRequest.of(req.getPageNum() - 1, req.getPageSize()));
    }

    @Override
    public User info(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public void removeById(int id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            log.error("failed to delete user [{}] cause it doesn't exist", id);
            throw new BusinessException("User doesn't exist");
        }

        if (userById.get().getUsername().equals(SysConstant.USER_NAME_ADMIN)) {
            log.error("failed to delete user [{id}] cause it can't be deleted");
            throw new BusinessException("Can't delete this user");
        }

        // delete user
        userRepository.deleteById(id);

        // delete user role
        userRoleRepository.deleteByUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User create(CreateUserDTO param) {

        User user = new User();
        BeanUtils.copyProperties(param, user);
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            log.error("username [{}] already exist", user.getUsername());
            throw new BusinessException("Username already exist");
        }

        User saved = userRepository.save(user);

        if (param.getRoleIds() != null && param.getRoleIds().size() > 0) {
            int count = roleRepository.countAllByIdIn(param.getRoleIds());
            if (count != param.getRoleIds().size()) {
                throw new BusinessException("Invalid role id");
            }
            List<UserRole> userRoles = param.getRoleIds().stream().distinct().map(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(saved.getId());
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList());

            userRoleRepository.saveAll(userRoles);
        }

        return saved;
    }

    @Override
    public List<Permission> listPermission(int userId) {


        int count = userRepository.countById(userId);
        if (count == 0) {
            log.error("failed to list user permission cause user [{}] not found", userId);
            throw new BusinessException("User not found");
        }

        List<UserRole> userRoleByUserId = userRoleRepository.findAllByUserId(userId);
        if (userRoleByUserId.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> roleIds = userRoleByUserId.stream().map(UserRole::getRoleId).toList();
        return roleService.listByRoleId(roleIds);
    }

    @Override
    public Token login(LoginDTO dto) {
        Optional<User> userByUsername = userRepository.findByUsername(dto.getUsername());
        if (userByUsername.isEmpty()) {
            log.error("failed to login cause user [{}] not found", dto.getUsername());
            throw new BusinessException("Incorrect username or password");
        }
        User user = userByUsername.get();
        boolean passwordMatch = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (!passwordMatch) {
            log.error("failed to login cause incorrect password");
            throw new BusinessException("Incorrect username or password");
        }

        Optional<Token> optionalToken = tokenStorage.loadByUsername(user.getUsername());
        Token token;
        if (optionalToken.isEmpty()) {
            token = new Token(UUID.randomUUID().toString().replace("-", ""), user);
        } else {
            token = optionalToken.get();
            token.refresh();
        }

        tokenStorage.store(token);

        return token;
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        log.info("user [{}] logout", name);
        tokenStorage.removeByUsername(name);
    }
}