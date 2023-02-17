package org.mvss.decl.tasks;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TaskResult implements Serializable
{
   private Instant                       startTime  = Instant.now();
   private Instant                       endTime;
   private boolean                       successful = false;
   private Throwable                     error;
   private HashMap<String, Serializable> result;
}
