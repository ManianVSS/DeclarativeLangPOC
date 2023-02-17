package org.mvss.decl.tasks;

import lombok.*;
import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Exec implements Task
{
   private String   command;
   private String[] args;

   @Override
   public TaskResult execute()
   {
      TaskResult taskResult = new TaskResult();
      try
      {
         ProcResult procResult = new ProcBuilder( command, args ).withOutputStream( System.out ).withErrorStream( System.err ).run();
         taskResult.setSuccessful( procResult.getExitValue() == 0 );
      }
      catch ( ExternalProcessFailureException externalProcessFailureException )
      {
         externalProcessFailureException.getExitValue();
      }
      catch ( Throwable t )
      {
         taskResult.setError( t );
      }

      return taskResult;
   }

}
