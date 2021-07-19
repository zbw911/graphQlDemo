package com.example.graphqltestdemo.handler.handlers.graphql.fetchers;

import com.example.graphqltestdemo.handler.handlers.ExecutionContext;
import com.example.graphqltestdemo.handler.handlers.GraphQlTaskHandler;
import com.example.graphqltestdemo.handler.handlers.graphql.WorkFlowFetcher;
import graphql.schema.DataFetcher;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/25 17:17
 */
public class EsWorkFlowFetcher implements WorkFlowFetcher<GraphQlTaskHandler.RuntimeWiringConfig.EsFetcherConfig> {

    @Override
    public DataFetcher getFetcher(ExecutionContext executionContext, GraphQlTaskHandler.RuntimeWiringConfig.EsFetcherConfig configs) {
        return null;
    }
}
