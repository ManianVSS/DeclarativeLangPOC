package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.utils.RegexUtil;

@Getter
@Setter
@ToString
public class Echo extends Step {
    private String message;

    @Override
    public Object execute(Runner runner, Scope scope) {
        System.out.println(RegexUtil.replaceVariables(scope::getVariable, message));
        return null;
    }
}
