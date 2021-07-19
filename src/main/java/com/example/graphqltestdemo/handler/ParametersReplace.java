package com.example.graphqltestdemo.handler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数替换
 *
 * @author zhangbaowei
 */
public class ParametersReplace {
    static final Pattern PATTERN = Pattern.compile("\\$\\{(.+?)\\}");
    private final String template;
    StringBuffer buffer = new StringBuffer();

    public ParametersReplace(String template) {
        this.template = template;
    }

    public String replace(Map<String, Object> map) {
        if (map == null) {
            return template;
        }
        Matcher matcher = PATTERN.matcher(template);

        while (matcher.find()) {
            String variable = matcher.group(1);
            final Object replace = map.get(variable);
            if (replace == null) {
                continue;
            }
            matcher.appendReplacement(buffer,
                    replace.toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
