package com.example.graphqltestdemo.handler.handlers.graphql;

import com.example.graphqltestdemo.handler.handlers.ExecutionContext;
import com.example.graphqltestdemo.handler.handlers.GraphQlTaskHandler;
import graphql.schema.DataFetcher;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/25 17:14
 */
public interface WorkFlowFetcher<T extends GraphQlTaskHandler.RuntimeWiringConfig.BaseFetcherConfig> {

    /**
     * 获取数据
     *
     * @param executionContext
     * @param configs
     * @return 结果
     */
    DataFetcher getFetcher(ExecutionContext executionContext, T configs);
}
