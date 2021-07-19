package com.example.graphqltestdemo.handler.handlers.graphql.fetchers;

import com.example.graphqltestdemo.handler.handlers.ExecutionContext;
import com.example.graphqltestdemo.handler.handlers.GraphQlTaskHandler;
import com.example.graphqltestdemo.handler.handlers.graphql.DataFetchersCreator;
import com.example.graphqltestdemo.handler.handlers.graphql.WorkFlowFetcher;
import graphql.schema.DataFetcher;

import java.util.List;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/25 17:15
 */
public class ApiWorkFlowFetcher implements WorkFlowFetcher<GraphQlTaskHandler.RuntimeWiringConfig.ApiFetcherConfig> {

    @Override
    public DataFetcher getFetcher(ExecutionContext executionContext, GraphQlTaskHandler.RuntimeWiringConfig.ApiFetcherConfig configs) {
        final String url = configs.getUrl();
        final String method = configs.getMethod();
        final String charset = configs.getCharset();
        final Integer connTimeout = configs.getConnTimeout();
        final Integer readTimeout = configs.getReadTimeout();
        final List<String> argument = configs.getArgument();

        final DataFetcher fetcher = new DataFetchersCreator(url, method, charset, connTimeout, readTimeout, argument).getFetcher();

        return fetcher;
    }
}
