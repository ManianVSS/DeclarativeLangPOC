package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.runtime.Step;

@Getter
@Setter
@ToString
public class Import extends Step {

    private String fileName;

    @Override
    public void execute(Runner runner, Scope scope) throws Throwable {
        runner.run(fileName, scope);
    }
}
