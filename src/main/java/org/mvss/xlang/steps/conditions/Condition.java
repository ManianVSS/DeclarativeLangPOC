package org.mvss.xlang.steps.conditions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.steps.operations.Operation;

@Getter
@Setter
@ToString
public abstract class Condition extends Operation {

    @Override
    public void execute(Runner runner, Scope scope) throws Throwable {
        if (StringUtils.isNotBlank(resultVar)) {
            boolean result = eval(runner, scope);
            scope.putVariable(resultVar, result);
        }
    }

    public abstract boolean eval(Runner runner, Scope scope) throws Throwable;
}
