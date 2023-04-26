package org.mvss.xlang.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.utils.ParserUtils;
import org.mvss.xlang.utils.RegexUtil;

import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@ToString
public class VariableDefinition extends Step {

    public static final ConcurrentHashMap<String, Class<?>> typeMap = new ConcurrentHashMap<>();

    private String type;
    private String name;
    private Object value;

    private String expr;

    @Override
    public Object execute(Runner runner, Scope scope) {
        if (!StringUtils.isBlank(expr)) {
            String replacedString = RegexUtil.replaceVariables(scope.getVariables(), expr);
            try {
                Class<?> variableType = Class.forName(type);
                value = ParserUtils.readValue(replacedString, variableType);
            } catch (ClassNotFoundException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        scope.getVariables().put(name, value);
        return this;
    }
}
