package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.utils.RegexUtil;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@ToString
public class VariableDefinition extends Step {

    public static final ConcurrentHashMap<String, Class<?>> typeMap = new ConcurrentHashMap<>();

    private String type;
    private String name;
    private Serializable value;

    private String expr;

    @Override
    public void execute(Runner runner, Scope scope) {
        System.out.println("Variable Definition: " + this);
        if (!StringUtils.isBlank(expr)) {
            value = RegexUtil.replaceVariables(scope.getVariables(), expr);
        }
        scope.getVariables().put(name, value);
    }
}
