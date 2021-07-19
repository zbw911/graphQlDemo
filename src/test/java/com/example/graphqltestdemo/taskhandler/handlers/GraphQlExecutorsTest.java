package com.example.graphqltestdemo.taskhandler.handlers;

import com.example.graphqltestdemo.handler.JsonUtils;
import com.example.graphqltestdemo.handler.StreamHelper;
import com.example.graphqltestdemo.handler.handlers.graphql.DataFetchersCreator;
import com.example.graphqltestdemo.handler.handlers.graphql.GraphQlExecutors;
import graphql.ExecutionResult;
import graphql.schema.DataFetcher;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/23 14:42
 */
public class GraphQlExecutorsTest {
    @Test
    public void name() {
        String schema = "type Query{hello: String} schema{query: Query}";
        String query = "{hello}";
        final TypeRuntimeWiring build = newTypeWiring("Query")
                .dataFetcher("hello", new StaticDataFetcher("world")).build();

        GraphQlExecutors graphQlExecutors = new GraphQlExecutors(schema, query, Arrays.asList(build));
        System.out.println(graphQlExecutors.getResult().toSpecification());
    }

    @Test
    public void outputquery() {
        String query = "{\n" +
                "    query(aabb_lt_type: \"c\" , aabb_lt_id :4362,  aabb_order_type:2){\n" +
                "        returncode\n" +
                "        message\n" +
                "        result {\n" +
                "            total\n" +
                "            seriesid\n" +
                "            items{\n" +
                "                aabb_topic_piccount\n" +
                "                aabb_lt_type\n" +
                "                biz_id\n" +
                "                title\n" +
                "                authorinfo\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        System.out.println(query);
    }

    @Test
    public void aabb_test() {
        String schema = StreamHelper
                .getStringFromClasspath("gql/aabb_get_topics_list.graphql");

        String query = "{\n" +
                "    query(aabb_lt_type: \"c\" , aabb_lt_id :4362,  aabb_order_type:2){\n" +
                "        returncode\n" +
                "        message\n" +
                "        result {\n" +
                "            total\n" +
                "            seriesid\n" +
                "            items{\n" +
                "                aabb_topic_piccount\n" +
                "                aabb_lt_type\n" +
                "                biz_id\n" +
                "                title\n" +
                "                authorinfo\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        final List<String> strings = Arrays.asList("aabb_lt_type", "aabb_lt_id", "aabb_order_type");
        String url = "http://data.api.zhangbaowei.com.cn/data/page/aabb_get_topics_list?_appid=aabb&page_num=1&page_size=50&aabb_lt_type=c&aabb_lt_id=4362&aabb_order_type=2";
        DataFetchersCreator dataFetchersCreator = new DataFetchersCreator(url, "get", "utf-8", 3 * 1000, 3 * 1000, strings);
        final DataFetcher fetcher = dataFetchersCreator.getFetcher();

        final TypeRuntimeWiring build = newTypeWiring("Query")
                .dataFetcher("query", fetcher).build();

        GraphQlExecutors graphQlExecutors = new GraphQlExecutors(schema, query, Arrays.asList(build));
        System.out.println(graphQlExecutors.getResult().toSpecification());
    }

    @Test
    public void author_query_test() {
        String schema = StreamHelper
                .getStringFromClasspath("gql/aabb_get_topics_list.graphql");

        String query = "{\n" +
                "    queryauthor(authorid:[1,2,3]){\n" +
                "        returncode\n" +
                "        message\n" +
                "        result {\n" +
                "            sex\n" +
                "            mobilephone\n" +
                "            testName\n" +
                "            newtestName\n" +
                "            authorid\n" +
                "            isbindwlt\n" +
                "        }\n" +
                "    }\n" +
                "}";

        final List<String> strings = Arrays.asList("aabb_lt_type", "aabb_lt_id", "aabb_order_type");
        String url = "http://author.api.zhangbaowei.com.cn/api/go_authorInfo/getauthorinfolist?_appid=aabb&authoridlist=7510013,4856509,8428090";
        DataFetchersCreator dataFetchersCreator = new DataFetchersCreator(url, "get", "utf-8", 3 * 1000, 3 * 1000, strings);
        final DataFetcher fetcher = dataFetchersCreator.getFetcher();

        final TypeRuntimeWiring build = newTypeWiring("Query")
                .dataFetcher("queryauthor", fetcher).build();
        final TypeRuntimeWiring build2 = newTypeWiring("Items")
                .dataFetcher("authorinfo", fetcher).build();
        GraphQlExecutors graphQlExecutors = new GraphQlExecutors(schema, query, Arrays.asList(build));
        System.out.println(graphQlExecutors.getResult().toSpecification());
    }

    @Test
    public void multiQueryTest() {
        String schema = StreamHelper
                .getStringFromClasspath("gql/schema.graphqls");
        String query = "{\n" +
                "    query(aabb_lt_type: \"c\" , aabb_lt_id :4362,  aabb_order_type:2){\n" +
                "        returncode\n" +
                "        message\n" +
                "        result {\n" +
                "            total\n" +
                "            seriesid\n" +
                "            items{\n" +
                "                   authorinfo{\n" +
                "                   returncode\n" +
                "                   result{\n" +
                "                         testName\n" +
                "                       }\n" +
                "                   }\n" +
                "                aabb_topic_piccount\n" +
                "                aabb_lt_type\n" +
                "                biz_id\n" +
                "                title\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        System.out.println(query);
        String url = "http://data.api.zhangbaowei.com.cn/data/page/aabb_get_topics_list?_appid=aabb&page_num=1&page_size=50&aabb_lt_type=c&aabb_lt_id=4362&aabb_order_type=2";
        DataFetchersCreator dataFetchersCreator = new DataFetchersCreator(url, "get", "utf-8", 3 * 1000, 3 * 1000, Arrays.asList("aabb_lt_type", "aabb_lt_id", "aabb_order_type"));
        final DataFetcher fetcher = dataFetchersCreator.getFetcher();

        final TypeRuntimeWiring build = newTypeWiring("Query")
                .dataFetcher("query", fetcher)
                .build();

        String url2 = "http" + "://author.api.zhangbaowei.com.cn/api/go_authorInfo/getauthorinfolist?_appid=aabb&authoridlist=7510013,4856509,8428090";
        DataFetchersCreator dataFetchersCreator2 = new DataFetchersCreator(url2, "get", "utf-8", 3 * 1000, 3 * 1000, Arrays.asList("1", "2", "3"));
        final DataFetcher fetcher2 = dataFetchersCreator2.getFetcher();
        final TypeRuntimeWiring build2 = newTypeWiring("Items")
                .dataFetcher("authorinfo", fetcher2)
                .build();

        GraphQlExecutors graphQlExecutors = new GraphQlExecutors(schema, query, Arrays.asList(build2, build));

        final ExecutionResult result = graphQlExecutors.getResult();

        if (result.getErrors().size() > 0) {
            System.out.println(result.getErrors());
        } else {
            System.out.println(JsonUtils.toPrettyString(result.toSpecification()));
        }

        Assert.isTrue(result.getErrors().size() == 0);
    }

}
