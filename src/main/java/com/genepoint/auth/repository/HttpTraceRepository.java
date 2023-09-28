package com.genepoint.auth.repository;

import com.genepoint.auth.entity.HttpTrace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xiabiao
 * @since 2023-09-06
 */
public interface HttpTraceRepository extends JpaRepository<HttpTrace, Long>, JpaSpecificationExecutor<HttpTrace> {
}
