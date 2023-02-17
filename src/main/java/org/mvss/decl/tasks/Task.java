package org.mvss.decl.tasks;

import java.io.Serializable;

public interface Task extends Serializable
{
   TaskResult execute();
}
