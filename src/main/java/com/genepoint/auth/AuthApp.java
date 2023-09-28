package com.genepoint.auth;

import com.genepoint.auth.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * @author xiabiao
 * @since 2023-08-26
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.genepoint.auth.repository")
@EnableTransactionManagement
@ServletComponentScan(basePackages = "com.genepoint.auth.servlet")
@EnableConfigurationProperties(Employee.class)
public class AuthApp implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AuthApp.class);

    @Resource
    private Employee employee;

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("employee names: {}", employee.getNames());
    }
}
