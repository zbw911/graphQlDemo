package com.example.graphqltestdemo.handler;

import com.example.graphqltestdemo.handler.handlers.ExecutionContext;

import java.util.Map;

public interface TaskHandler {
    /**
     * Task的处理
     *
     * @param executionContext
     * @param configs
     * @return
     */
    Object handler(ExecutionContext executionContext, Map<String, Object> configs);
}