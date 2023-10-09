package com.genepoint.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xiabiao
 * @since 2023-08-26
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.genepoint.auth.repository")
@ServletComponentScan(basePackages = "com.genepoint.auth.servlet")
public class AuthApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class);
    }
}
