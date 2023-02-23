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
public class ForStatement extends Step {

    @Override
    public void execute(Runner runner, Scope scope) throws Throwable {
        Step initStep;
        Step conditionStep;
        Step updateStep;
        Step doStep;

        if ((steps.size() != 4) ||
                !((initStep = steps.get(0)) instanceof ForStatement.Init) ||
                !((conditionStep = steps.get(1)) instanceof Condition) ||
                !((updateStep = steps.get(2)) instanceof Update) ||
                !((doStep = steps.get(3)) instanceof ForStatement.Do)
        ) {
            throw new RuntimeException("For syntax error missing or misplaced init/<condition>/update/do block");
        }
        Scope localScope = new Scope();
        localScope.setParentScope(scope);

        for (initStep.execute(runner, localScope);
             ((Condition) conditionStep).eval(runner, localScope);
             updateStep.execute(runner, localScope)

        ) {
            try {
                doStep.execute(runner, localScope);
            } catch (ForStatementBreakException breakException) {
                break;
            } catch (ForStatementContinueException continueException) {
                //continue;
            }
        }
    }

    public static class Init extends Step {

    }

    public static class Do extends Step {

    }

    public static class Update extends Step {

    }

    public static class ForStatementBreakException extends Exception {
        public ForStatementBreakException() {
            super("");
        }
    }

    public static class Break extends Step {

        @Override
        public void execute(Runner runner, Scope scope) throws Throwable {
            throw new ForStatementBreakException();
        }
    }

    public static class ForStatementContinueException extends Exception {
        public ForStatementContinueException() {
            super("");
        }
    }

    public static class Continue extends Step {

        @Override
        public void execute(Runner runner, Scope scope) throws Throwable {
            throw new ForStatementContinueException();
        }
    }
}
