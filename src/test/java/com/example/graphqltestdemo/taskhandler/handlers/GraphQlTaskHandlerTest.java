package com.example.graphqltestdemo.taskhandler.handlers;

import com.example.graphqltestdemo.handler.JsonUtils;
import com.example.graphqltestdemo.handler.StreamHelper;
import com.example.graphqltestdemo.handler.TaskHandler;
import com.example.graphqltestdemo.handler.handlers.ExecutionContext;
import com.example.graphqltestdemo.handler.handlers.GraphQlTaskHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/26 9:09
 */
public class GraphQlTaskHandlerTest {
    @Test
    public void configTest() {
        String strconfig = StreamHelper
                .getStringFromClasspath("gqlconfig/configdemo.json");
        GraphQlTaskHandler.GraphQlConfig config = new GraphQlTaskHandler.GraphQlConfig(strconfig);

        System.out.println(JsonUtils.toPrettyString(config));
    }

    @Test
    public void testHandler() {
        TaskHandler taskHandler = new GraphQlTaskHandler();
        String strconfig = StreamHelper
                .getStringFromClasspath("gqlconfig/configdemo.json");
        final Object handler = taskHandler.handler(new ExecutionContext(), JsonUtils.toObject(strconfig, new TypeReference<>() {
        }));

        System.out.println(JsonUtils.toPrettyString(handler));
    }
}
