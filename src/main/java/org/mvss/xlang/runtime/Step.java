package org.mvss.xlang.runtime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mvss.xlang.dto.Scope;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@ToString
public class Step implements Serializable {

    protected ArrayList<Step> steps = new ArrayList<>();

    public void execute(Runner runner, Scope scope) throws Throwable {
        runner.run(steps, scope);
    }
}
