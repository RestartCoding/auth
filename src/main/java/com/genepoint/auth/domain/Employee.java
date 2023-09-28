package com.genepoint.auth.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author xiabiao
 * @since 2023-09-27
 */
@ConfigurationProperties(prefix = "employee")
@Getter
@Setter
public class Employee {

    private List<String> names;
}
