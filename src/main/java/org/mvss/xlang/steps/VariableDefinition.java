package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import org.mvss.xlang.dto.Step;

import java.io.Serializable;

@Getter
@Setter
public class VariableDefinition implements Step {
    private String name;
    private Serializable value;

    @Override
    public boolean execute() throws Throwable {
        //Runner.objectMapper.writeValueAsString(value)
        System.out.println("Variable named " + name + " with value:\n" + value);
        return true;
    }
}
