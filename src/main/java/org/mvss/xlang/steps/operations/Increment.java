package org.mvss.xlang.steps.operations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Increment extends Operation {

    @Override
    public void execute(Runner runner, Scope scope) throws Throwable {
        if (StringUtils.isBlank(resultVar) || !scope.hasLocalVariable(resultVar)) {
            throw new RuntimeException("Result variable missing");
        }

        Serializable existingVariable = scope.getVariable(resultVar);

        if (existingVariable instanceof Integer) {
            scope.putVariable(resultVar, (Integer) existingVariable + 1);
        } else if (existingVariable instanceof Long) {
            scope.putVariable(resultVar, (Long) existingVariable + 1);
        } else if (existingVariable instanceof Float) {
            scope.putVariable(resultVar, (Float) existingVariable + 1);
        } else if (existingVariable instanceof Double) {
            scope.putVariable(resultVar, (Double) existingVariable + 1);
        } else {
            throw new RuntimeException("Increment can't be performed on type " + existingVariable.getClass().getCanonicalName());
        }
    }
}
