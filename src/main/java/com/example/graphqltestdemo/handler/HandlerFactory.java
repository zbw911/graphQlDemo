package com.example.graphqltestdemo.handler;

import com.example.graphqltestdemo.handler.handlers.GraphQlTaskHandler;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/5/13 9:16
 */
public class HandlerFactory {

    private static final Map<String, Class<? extends TaskHandler>> handlerMap = new HashMap<>(5);

    static {

        handlerMap.put("graphql", GraphQlTaskHandler.class);

    }

    public static TaskHandler createHandler(String type) {
        if (handlerMap.containsKey(type)) {
            Class<?> InputClass = handlerMap.get(type);
            try {
                Constructor constructor = InputClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                TaskHandler taskHandler = (TaskHandler) constructor.newInstance();

                return taskHandler;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new RuntimeException("不存在的handler:" + type);
    }

    private static TaskHandler proxyHandler(TaskHandler target, CacheConfig config) {
        return (TaskHandler) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        Object returnValue = method.invoke(target, args);
                        return returnValue;
                    }
                });
    }
}
