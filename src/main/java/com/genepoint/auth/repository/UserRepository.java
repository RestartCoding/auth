package com.genepoint.auth.repository;

import com.genepoint.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-08-26
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    int countById(int id);

    Optional<User> findByUsername(String username);
}