package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.runtime.Step;

@Getter
@Setter
@ToString//(callSuper = true)
public class FunctionDefinition extends Step {

    private String name;

    @Override
    public void execute(Runner runner, Scope scope) throws Throwable {
        System.out.println("Function definition: " + this);

        if (scope.getFunctions().containsKey(name)) {
            throw new RuntimeException("Function already defined " + name);
        }
        scope.getFunctions().put(name, steps);
    }
}
