package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.runtime.Step;
import org.mvss.xlang.utils.DataUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@ToString
public class FunctionCall extends Step {

    private String name;

    private ArrayList<String> outputParameters;

    @Override
    public void execute(Runner runner, Scope scope) throws Throwable {
        System.out.println("Function call: " + this);

        if (!scope.getFunctions().containsKey(name)) {
            throw new RuntimeException("Could not find function named: " + name);
        }
        Scope functionScope = new Scope();
//        functionScope.setParent(scope);
        functionScope.setCurrentFunction(name);
        functionScope.setVariables(DataUtils.cloneConcurrentMap(scope.getVariables()));
        functionScope.setFunctions(DataUtils.cloneConcurrentMap(scope.getFunctions()));
//        functionScope.setOutputParameters(outputParameters);

        ArrayList<Step> functionStep = scope.getFunctions().get(name);
        runner.run(functionStep, functionScope);

        //Copy back output parameters to parent scope
        ConcurrentHashMap<String, Serializable> functionScopeVariables = functionScope.getVariables();
        ConcurrentHashMap<String, Serializable> scopeVariables = scope.getVariables();
        for (String outputParameter : outputParameters) {
            if (functionScopeVariables.containsKey(outputParameter)) {
                scopeVariables.put(outputParameter, functionScopeVariables.get(outputParameter));
            }
        }
    }
}
