package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;

import java.util.ArrayList;

@Getter
@Setter
@ToString//(callSuper = true)
public class FunctionDefinition extends Step {

    private String name;

    private ArrayList<String> inputParameters;

    private ArrayList<String> outputParameters;

    @Override
    public void execute(Runner runner, Scope scope) {
        System.out.println("Function definition: " + this);

        if (scope.getFunctions().containsKey(name)) {
            throw new RuntimeException("Function already defined " + name);
        }
        scope.getFunctions().put(name, steps);
    }
}
