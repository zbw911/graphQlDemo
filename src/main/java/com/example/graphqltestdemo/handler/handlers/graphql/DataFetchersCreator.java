package com.example.graphqltestdemo.handler.handlers.graphql;

import com.example.graphqltestdemo.handler.ParametersReplace;
import graphql.schema.DataFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/23 10:33
 */
public class DataFetchersCreator {

    private final String charset;
    private final Integer connTimeout;
    private final Integer readTimeout;
    private final List<String> argument;
    private final String method;
    private String url;

    public DataFetchersCreator(String url
            , String method
            , String charset
            , Integer connTimeout
            , Integer readTimeout
            , List<String> argument) {
        this.method = method;
        this.url = url;
        this.charset = charset;
        this.connTimeout = connTimeout;
        this.readTimeout = readTimeout;
        if (argument == null) {
            argument = new ArrayList<>();
        }
        this.argument = argument;
    }

    public DataFetcher getFetcher() {
        return dataFetchingEnvironment -> {

            final Map<String, Object> arguments = dataFetchingEnvironment.getArguments();

            if (arguments != null && arguments.size() > 0) {
                ParametersReplace replace = new ParametersReplace(this.url);
                this.url = replace.replace(arguments);
            }

            final Object source = dataFetchingEnvironment.getSource();

            if (source != null && source instanceof Map) {
                ParametersReplace replace = new ParametersReplace(this.url);
                this.url = replace.replace((Map<String, Object>) source);
            }

//            final Object s = HttpClientUtils.get(url, charset, connTimeout, readTimeout, Object.class);

            String s = "";
            return s;
        };
    }
}
