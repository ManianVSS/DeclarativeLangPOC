package org.mvss.xlang.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;

public class ParserUtils {

    public static final TypeReference<HashMap<String, Serializable>> genericHashMapObjectType = new TypeReference<>() {
    };

    public static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    public static final String[] YAML_STR_TO_ESCAPE = {",", ":", "&", "*", "?", "|", ">", "%", "@"};

    public static <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException {

        String contentTrim = content.trim();

        if (StringUtils.startsWithAny(contentTrim, YAML_STR_TO_ESCAPE)
                || (contentTrim.startsWith("-") && !RegexUtil.isNumeric(contentTrim))) {
            return objectMapper.readValue("\"" + content + "\"", valueType);
        }

        return objectMapper.readValue(content, valueType);
    }
}
