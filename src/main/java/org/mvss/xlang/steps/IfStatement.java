package org.mvss.xlang.steps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.steps.conditions.Condition;

@Getter
@Setter
@ToString
public class IfStatement extends Step {

    @Override
    public void execute(Runner runner, Scope scope) throws Throwable {
        Step conditionStep;
        Step thenStep;
        if ((steps.size() < 2) || !((conditionStep = steps.get(0)) instanceof Condition) || !((thenStep = steps.get(1)) instanceof Then)) {
            throw new RuntimeException("No steps provided");
        }

        Scope localScope = new Scope();
        localScope.setParentScope(scope);

        if (((Condition) conditionStep).eval(runner, localScope)) {
            thenStep.execute(runner, localScope);
        } else if (steps.size() > 3) {
            for (int i = 2; i < steps.size() - 1; i++) {
                Step elseIfStep = steps.get(i);
                if (!(elseIfStep instanceof ElseIf)) {
                    throw new RuntimeException("Only ElseIf steps expected in If statement for indices 2 through n-1");
                }
                if (((ElseIf) elseIfStep).eval(runner, localScope)) {
                    elseIfStep.execute(runner, localScope);
                    return;
                }
            }
            //Since we did not return from any else if blocks
            runElseBlock(runner, localScope);
        } else {
            runElseBlock(runner, localScope);
        }
    }

    private void runElseBlock(Runner runner, Scope scope) throws Throwable {
        Step elseStep = steps.get(steps.size() - 1);
        if (!(elseStep instanceof Else)) {
            throw new RuntimeException("Last step in If statement can only be else");
        }
        elseStep.execute(runner, scope);
    }

    public static class Then extends Step {

    }

    public static class ElseIf extends Condition {

        @Override
        public void execute(Runner runner, Scope scope) throws Throwable {
            runner.run(steps.subList(1, steps.size()), scope);
        }

        @Override
        public boolean eval(Runner runner, Scope scope) throws Throwable {
            return evaluateSingleCondition(runner, scope);
        }
    }

    public static class Else extends Step {

    }
}
