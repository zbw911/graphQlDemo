package com.example.graphqltestdemo.handler.handlers;

import com.example.graphqltestdemo.handler.JsonUtils;
import com.example.graphqltestdemo.handler.ParametersReplace;
import com.example.graphqltestdemo.handler.TaskHandler;
import com.example.graphqltestdemo.handler.handlers.graphql.GraphQlExecutors;
import com.example.graphqltestdemo.handler.handlers.graphql.fetchers.FetcherFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import graphql.ExecutionResult;
import graphql.GraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/25 16:49
 */

public class GraphQlTaskHandler implements TaskHandler {
    @Override
    public Object handler(ExecutionContext executionContext, Map<String, Object> configs) {

        GraphQlConfig config = new GraphQlConfig(configs);
        String query = config.getQuery();
        String schema = config.getSchema();
        List<TypeRuntimeWiring> typeRuntimeWiringList = new ArrayList<>();

        for (RuntimeWiringConfig.BaseFetcherConfig baseFetcherConfig : config.getWiringConfigList()) {
            final String datasourceType = baseFetcherConfig.getDatasourceType();
            final String typeName = baseFetcherConfig.getTypeName();
            final String fieldName = baseFetcherConfig.getFieldName();

            final DataFetcher dataFetcher = FetcherFactory.create(datasourceType, executionContext, baseFetcherConfig);
            final TypeRuntimeWiring typeRuntimeWiring = newTypeWiring(typeName)
                    .dataFetcher(fieldName, dataFetcher)
                    .build();

            typeRuntimeWiringList.add(typeRuntimeWiring);
        }

        final String replacedQuery = new ParametersReplace(query).replace(
                executionContext
                        .getRequestData()
                        .asMap()
                        .get("parameter")
        );

        final GraphQlExecutors graphQlExecutors = new GraphQlExecutors(schema, replacedQuery, typeRuntimeWiringList);

        final ExecutionResult result = graphQlExecutors.getResult();

        if (result.getErrors().size() > 0) {
            String errors = "";
            for (GraphQLError error : result.getErrors()) {
                errors += error.toString();
            }
            throw new RuntimeException(errors);
        }

        return result.toSpecification();
    }

    public static class GraphQlConfig {

        private String query;
        private String schema;
        private List<RuntimeWiringConfig.BaseFetcherConfig> wiringConfigList = new ArrayList<>();

        public GraphQlConfig(String config) {
            this(JsonUtils.toObject(config, new TypeReference<Map<String, Object>>() {
            }));
        }

        public GraphQlConfig(Map<String, Object> configMap) {
            query = configMap.get("query").toString();
            schema = configMap.get("schema").toString();
            final List<Map<String, Object>> wirings = (List<Map<String, Object>>) configMap.get("wiring");
            parseRuntimeWiringConfigMap(wirings);
        }

        public String getQuery() {
            return query;
        }

        public GraphQlConfig setQuery(String query) {
            this.query = query;
            return this;
        }

        public String getSchema() {
            return schema;
        }

        public GraphQlConfig setSchema(String schema) {
            this.schema = schema;
            return this;
        }

        public List<RuntimeWiringConfig.BaseFetcherConfig> getWiringConfigList() {
            return wiringConfigList;
        }

        public GraphQlConfig setWiringConfigList(List<RuntimeWiringConfig.BaseFetcherConfig> wiringConfigList) {
            this.wiringConfigList = wiringConfigList;
            return this;
        }

        private void parseRuntimeWiringConfigMap(List<Map<String, Object>> wirings) {
            for (Map<String, Object> wiring : wirings) {
                switch (wiring.get("datasourceType").toString()) {
                    case "api": {
                        final String s = JsonUtils.toString(wiring);
                        final RuntimeWiringConfig.ApiFetcherConfig apiFetcherConfig = JsonUtils.toObject(s, RuntimeWiringConfig.ApiFetcherConfig.class);
                        wiringConfigList.add(apiFetcherConfig);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 配置的数据结构
     */
    public static class RuntimeWiringConfig {
        public static class ApiFetcherConfig extends BaseFetcherConfig {
            String url;
            String method = "get";
            String charset = "utf-8";
            Integer connTimeout = 1000;
            Integer readTimeout = 1000;
            List<String> argument = new ArrayList<>();

            public String getUrl() {
                return url;
            }

            public ApiFetcherConfig setUrl(String url) {
                this.url = url;
                return this;
            }

            public String getMethod() {
                return method;
            }

            public ApiFetcherConfig setMethod(String method) {
                this.method = method;
                return this;
            }

            public String getCharset() {
                return charset;
            }

            public ApiFetcherConfig setCharset(String charset) {
                this.charset = charset;
                return this;
            }

            public Integer getConnTimeout() {
                return connTimeout;
            }

            public ApiFetcherConfig setConnTimeout(Integer connTimeout) {
                this.connTimeout = connTimeout;
                return this;
            }

            public Integer getReadTimeout() {
                return readTimeout;
            }

            public ApiFetcherConfig setReadTimeout(Integer readTimeout) {
                this.readTimeout = readTimeout;
                return this;
            }

            public List<String> getArgument() {
                return argument;
            }

            public ApiFetcherConfig setArgument(List<String> argument) {
                this.argument = argument;
                return this;
            }
        }

        public static abstract class BaseFetcherConfig {
            private String id;
            private String datasourceType;
            private String typeName;
            private String fieldName;

            public String getId() {
                return id;
            }

            public BaseFetcherConfig setId(String id) {
                this.id = id;
                return this;
            }

            public String getDatasourceType() {
                return datasourceType;
            }

            public BaseFetcherConfig setDatasourceType(String datasourceType) {
                this.datasourceType = datasourceType;
                return this;
            }

            public String getTypeName() {
                return typeName;
            }

            public BaseFetcherConfig setTypeName(String typeName) {
                this.typeName = typeName;
                return this;
            }

            public String getFieldName() {
                return fieldName;
            }

            public BaseFetcherConfig setFieldName(String fieldName) {
                this.fieldName = fieldName;
                return this;
            }
        }

        public static class DbFetcherConfig extends BaseFetcherConfig {
            String dbType;
            String dbConn;
            String dbauthor;
            String dbPassword;
            //select a,b,c from table where x=:x and y = :y
            String query;

            public String getDbType() {
                return dbType;
            }

            public DbFetcherConfig setDbType(String dbType) {
                this.dbType = dbType;
                return this;
            }

            public String getDbConn() {
                return dbConn;
            }

            public DbFetcherConfig setDbConn(String dbConn) {
                this.dbConn = dbConn;
                return this;
            }

            public String getDbauthor() {
                return dbauthor;
            }

            public DbFetcherConfig setDbauthor(String dbauthor) {
                this.dbauthor = dbauthor;
                return this;
            }

            public String getDbPassword() {
                return dbPassword;
            }

            public DbFetcherConfig setDbPassword(String dbPassword) {
                this.dbPassword = dbPassword;
                return this;
            }

            public String getQuery() {
                return query;
            }

            public DbFetcherConfig setQuery(String query) {
                this.query = query;
                return this;
            }
        }

        public static class EsFetcherConfig extends BaseFetcherConfig {

        }

        public static class MainDataFetcherConfig extends BaseFetcherConfig {

        }
    }
}
