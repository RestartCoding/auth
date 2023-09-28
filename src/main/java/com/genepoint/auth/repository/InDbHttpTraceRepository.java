package com.genepoint.auth.repository;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author xiabiao
 * @since 2023-09-06
 */
@Component
@AllArgsConstructor
public class InDbHttpTraceRepository implements HttpTraceRepository {

    private final com.genepoint.auth.repository.HttpTraceRepository httpTraceRepository;

    @Override
    public List<HttpTrace> findAll() {
        return Collections.emptyList();
    }

    @Override
    public void add(HttpTrace trace) {
        com.genepoint.auth.entity.HttpTrace httpTrace = new com.genepoint.auth.entity.HttpTrace();
        httpTrace.setUrl(trace.getRequest().getUri().toString());
        long startTimeMillis = trace.getTimestamp().toEpochMilli();
        httpTrace.setBeginTime(new Date(startTimeMillis));
        httpTrace.setEndTime(new Date(startTimeMillis + trace.getTimeTaken()));
        httpTrace.setTimeTaken(trace.getTimeTaken());
        httpTraceRepository.save(httpTrace);
    }
}
