package com.example.graphqltestdemo.taskhandler.handlers;

import com.example.graphqltestdemo.handler.handlers.graphql.DataFetchersCreator;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironmentImpl;
import org.junit.jupiter.api.Test;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/23 14:57
 */
public class DataFetchersCreatorTest {
    @Test
    public void name() throws Exception {
        String url = "http://data.api.zhangbaowei.com.cn/data/page/aabb_get_topics_list?_appid=aabb&page_num=1&page_size=50&aabb_lt_type=c&aabb_lt_id=4362&aabb_order_type=2";
        DataFetchersCreator dataFetchersCreator = new DataFetchersCreator(url, "get", "utf-8", 3 * 1000, 3 * 1000, null);
        final DataFetcher fetcher = dataFetchersCreator.getFetcher();
        final Object o = fetcher.get(new DataFetchingEnvironmentImpl.Builder().build());

        System.out.println(o);
    }
}
