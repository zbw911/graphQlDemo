package com.example.graphqltestdemo.handler.handlers.graphql.fetchers;

import com.example.graphqltestdemo.handler.handlers.ExecutionContext;
import com.example.graphqltestdemo.handler.handlers.GraphQlTaskHandler;
import com.example.graphqltestdemo.handler.handlers.graphql.WorkFlowFetcher;
import graphql.schema.DataFetcher;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/25 17:28
 */
public class FetcherFactory {
    public static DataFetcher create(String type, ExecutionContext executionContext, GraphQlTaskHandler.RuntimeWiringConfig.BaseFetcherConfig configs) {
        WorkFlowFetcher workFlowFetcher = null;
        if (GraphQlTaskHandler.RuntimeWiringConfig.ApiFetcherConfig.class.equals(configs.getClass())) {
            workFlowFetcher = new ApiWorkFlowFetcher();
        } else if (GraphQlTaskHandler.RuntimeWiringConfig.DbFetcherConfig.class.equals(configs.getClass())) {
            workFlowFetcher = new DbWorkFlowFetcher();
        } else if (GraphQlTaskHandler.RuntimeWiringConfig.MainDataFetcherConfig.class.equals(configs.getClass())) {
            workFlowFetcher = new MainDataWorkFlowFetcher();
        } else if (GraphQlTaskHandler.RuntimeWiringConfig.EsFetcherConfig.class.equals(configs.getClass())) {
            workFlowFetcher = new EsWorkFlowFetcher();
        } else {
            throw new RuntimeException("不支持的type:" + type);
        }

        return workFlowFetcher.getFetcher(executionContext, configs);
    }
}
