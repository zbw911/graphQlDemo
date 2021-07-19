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
public class DbWorkFlowFetcher implements WorkFlowFetcher<GraphQlTaskHandler.RuntimeWiringConfig.DbFetcherConfig> {
    @Override
    public DataFetcher getFetcher(ExecutionContext executionContext, GraphQlTaskHandler.RuntimeWiringConfig.DbFetcherConfig configs) {
        String dbType = configs.getDbType();
        String dbConn = configs.getDbConn();

        //  query

        // return ..

        // context . args

        //  params = args.foreach. id

        // api taskhander  . . .   get(url, params)

        //   (object.result.authorid)

        // <>  if( resut.returncode<>0 ) throw defautlvalueExecption() ..
        // else return value

        return null;
    }
}
