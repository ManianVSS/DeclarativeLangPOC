package org.mvss.xlang.steps.operations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.steps.Step;

@Getter
@Setter
@ToString
public abstract class Operation extends Step {
    protected String resultVar;

    @Override
    public abstract void execute(Runner runner, Scope scope) throws Throwable;
}
