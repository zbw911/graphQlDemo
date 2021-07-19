package com.example.graphqltestdemo.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestData {

    private Map<String, Map<String, Object>> mapdata = new HashMap<>();

    public static RequestData fromMap(Map<String, Map<String, Object>> mapdata) {
        RequestData requestData = new RequestData();
        for (Map.Entry<String, Map<String, Object>> stringMapEntry : mapdata.entrySet()) {
            requestData.mapdata.put(stringMapEntry.getKey(), Collections.unmodifiableMap(stringMapEntry.getValue()));
        }
        return requestData;
    }

    public Map<String, Map<String, Object>> asMap() {
        return mapdata;
    }

    public <T> T get(String key, Class<T> a) {
        return (T) mapdata.get(key);
    }

    public Object getValueInGroupMap(String groupKey, String key) {
        if (!mapdata.containsKey(groupKey)) {
            return null;
        }
        final Map<String, Object> objectMap = mapdata.get(groupKey);
        if (objectMap == null) {
            return null;
        }
        return objectMap.get(key);
    }
}
