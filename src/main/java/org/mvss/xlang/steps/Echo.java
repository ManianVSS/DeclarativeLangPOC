package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import org.mvss.xlang.dto.Step;

@Getter
@Setter
public class Echo implements Step {

    @Override
    public boolean execute() throws Throwable {
        return false;
    }
}
